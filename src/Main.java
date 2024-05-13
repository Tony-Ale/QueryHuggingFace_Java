import java.io.IOException;
import java.util.Scanner;
import org.json.JSONException;

public class Main {

    public static void main(String[] args) throws IOException, JSONException {
        String API_URL = "https://api-inference.huggingface.co/models/mistralai/Mistral-7B-Instruct-v0.2";
        String API_TOKEN = "Insert API token here";
        QueryHuggingfaceModel model = new QueryHuggingfaceModel(API_URL, API_TOKEN);
        Messages message = new Messages();
        Prompt prompt = new Prompt();
        Messages[] instruct_msg = new Messages[]{message.SystemMessage("You are a bot, your name is Bob, only respond to question asked, don't add any other information")};
        prompt.setPrompt(instruct_msg, true);

        while(true) {
            Scanner input = new Scanner(System.in);
            System.out.println("Chat with Model: ");
            String query = input.nextLine();
            String chain = prompt.conversation(query, false);
            String parsed_input = model.queryModel(chain + "\n" + query);
            System.out.println("\nAI response: " + parsed_input.strip() + "\n");
            prompt.conversation(parsed_input, true);
        }
    }
}
