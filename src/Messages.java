import org.json.JSONException;
import org.json.JSONObject;

public class Messages extends CreateMessage {
    protected String role;
    protected String content;

    public Messages(String role, String content) throws JSONException {
        super(content);
        this.role = role;
        this.content = content;
    }

    public Messages() {
    }

    public JSONObject Message() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("role", this.role);
        jsonObject.put("content", this.content);
        return jsonObject;
    }

    public String string_Message() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("role", this.role);
        jsonObject.put("content", this.content);
        return jsonObject.toString();
    }
}
