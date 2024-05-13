import org.json.JSONException;

public class CreateMessage {
    public String message = "";

    public CreateMessage() {
    }

    public CreateMessage(String message) {
        this.message = message;
    }

    public AIMessage AIMessage(String message) throws JSONException {
        this.message = message;
        return new AIMessage(this.message);
    }

    public HumanMessage HumanMessage(String message) throws JSONException {
        this.message = message;
        return new HumanMessage(this.message);
    }

    public SystemMessage SystemMessage(String message) throws JSONException {
        this.message = message;
        return new SystemMessage(this.message);
    }
}
