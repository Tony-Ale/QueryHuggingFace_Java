import org.json.JSONException;

public class SystemMessage extends Messages {
    public SystemMessage(String content) throws JSONException {
        super("system", content);
    }
}
