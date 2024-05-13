import org.json.JSONException;

public class HumanMessage extends Messages {
    public HumanMessage(String content) throws JSONException {
        super("user", content);
    }
}
