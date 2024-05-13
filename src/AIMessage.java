
import org.json.JSONException;

public class AIMessage extends Messages {
    public AIMessage(String content) throws JSONException {
        super("assistant", content);
    }
}