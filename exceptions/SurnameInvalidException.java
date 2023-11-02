package exceptions;

/**
 * SurnameInvalidException
 */
public class SurnameInvalidException extends IllegalArgumentException {
    public SurnameInvalidException(String message) {
        super(message);
    }

    public SurnameInvalidException() {
        super();
    }

    public SurnameInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public SurnameInvalidException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "SurnameInvalidException [message=" + getMessage() + "]";
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