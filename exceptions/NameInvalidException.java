package exceptions;

/**
 * NameInvalidException
 */
public class NameInvalidException extends IllegalArgumentException {
    public NameInvalidException(String message) {
        super(message);
    }

    public NameInvalidException() {
        super();
    }

    public NameInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public NameInvalidException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "NameInvalidException [message=" + getMessage() + "]";
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public Throwable getCause() {
        return super.getCause();
    }
}