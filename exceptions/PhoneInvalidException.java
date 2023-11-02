package exceptions;

/**
 * PhoneInvalidException
 */
public class PhoneInvalidException extends IllegalArgumentException {
    public PhoneInvalidException(String message) {
        super(message);
    }

    public PhoneInvalidException() {
        super();
    }

    public PhoneInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneInvalidException(Throwable cause) {
        super(cause);
    }
    
    @Override
    public String toString() {
        return "PhoneInvalidException [message=" + getMessage() + "]";
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