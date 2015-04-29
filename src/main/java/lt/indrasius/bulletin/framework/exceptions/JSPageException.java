package lt.indrasius.bulletin.framework.exceptions;

/**
 * Created by mantas on 15.4.21.
 */
public class JSPageException extends Exception {
    public JSPageException(String message) {
        super(message);
    }

    public JSPageException(String message, Throwable inner) {
        super(message, inner);
    }
}
