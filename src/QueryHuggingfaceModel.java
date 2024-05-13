import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class QueryHuggingfaceModel {

    protected String API_TOKEN;
    protected String API_URL;


    // i created a private constructor so that other classes cant create a new object of this class
    public QueryHuggingfaceModel(String API_URL, String API_TOKEN){
        this.API_URL = API_URL;
        this.API_TOKEN = API_TOKEN;
    }

    public String queryModel(String query) throws JSONException{
        URL url = createurl(this.API_URL);

        StringBuilder jsonResponse = new StringBuilder();
        try{
            jsonResponse = makeHttpRequest(url, query);

        }catch (IOException e){
            System.out.println("Error closing input stream");
        }
        return extractLLM_Response(jsonResponse);
    }

    // Convert string to URL
    private static URL createurl(String stringurl){
        URL url = null;
        try{
            url = new URL(stringurl);
        } catch (MalformedURLException e){
            System.out.println("Error with creating url");
        }
        return url;
    }

    //Make a Httpsrequest to hugging face server
    private StringBuilder makeHttpRequest(URL url, String query) throws IOException, JSONException {
        // Process query so that will be in Json format
        JSONObject data = new JSONObject();

        //setting metrics to tune response of the LLM
        JSONObject properties = new JSONObject();
        properties.put("temperature", 0.5);
        properties.put("return_full_text", false);

        data.put("inputs", query);
        data.put("parameters", properties);
        query = data.toString();

        StringBuilder jsonResponse = new StringBuilder();
        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputstream = null;
        try{
            urlConnection =(HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Authorization", "Bearer "+this.API_TOKEN);
            //urlConnection.setReadTimeout(10000);
            //urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            try(OutputStream out = urlConnection.getOutputStream()){
                byte[] input = query.getBytes(StandardCharsets.UTF_8);
                out.write(input, 0, input.length);
                inputstream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputstream);
            }
        }catch (IOException e){
            System.out.println("Problem retrieving result from LLM");
        }finally {
            if (urlConnection!=null){
                urlConnection.disconnect();
            }
            if (inputstream != null){
                inputstream.close();
            }
        }
        return jsonResponse;
    }

    // Read input stream
    private static StringBuilder readFromStream(InputStream inputstream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputstream!=null){
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
            String line = reader.readLine();
            while (line!=null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output;
    }

    // Parsing Json response
    public static String extractLLM_Response(StringBuilder response){
        String output = "";
        try{
            response.deleteCharAt(0); // to remove square brackets
            response.deleteCharAt(response.length() - 1);
            JSONObject jsonObject = new JSONObject(response.toString());
            output = jsonObject.getString("generated_text");
        }catch (JSONException e){
            System.out.println("Error parsing the json data");
        }
        return output;
    }

}
