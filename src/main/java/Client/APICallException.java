package Client;

public class APICallException extends Exception {

    public APICallException(Exception e) {
        super(e);
    }

    public APICallException(String message) {
        super(message);
    }
}
