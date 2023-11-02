package exceptions;

/**
 * SurnameInvalidException
 */
public class DateInvalidException extends IllegalArgumentException {
    public DateInvalidException(String message) {
        super(message);
    }

    public DateInvalidException() {
        super();
    }

    public DateInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateInvalidException(Throwable cause) {
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