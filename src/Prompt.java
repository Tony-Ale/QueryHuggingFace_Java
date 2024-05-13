import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;

public class Prompt {
    protected Messages message = new Messages();
    protected ArrayList<Messages> messages = new ArrayList<Messages>();

    public Prompt() {
    }

    public String setPrompt(Messages[] message, boolean singleMsg) throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for(Messages msg: message) {
            jsonArray.put(msg.Message());
            if (singleMsg){
                this.messages.add(msg);
            }
        }

        return jsonArray.toString();
    }

    public String conversation(String llmResponse, String userQuery) throws JSONException {
        if (userQuery == null) {
            Messages ai_message = this.message.AIMessage(llmResponse);
            this.messages.add(ai_message);
        } else {
            Messages human_message = this.message.HumanMessage(userQuery);
            this.messages.add(human_message);
        }
        return this.setPrompt(messages.toArray(new Messages[this.messages.size()]), false);
    }

    public String conversation(String response, boolean bot) throws JSONException {
        if (bot){
            return this.conversation(response, null);
        }else{
            return this.conversation(null, response);
        }
    }
}
