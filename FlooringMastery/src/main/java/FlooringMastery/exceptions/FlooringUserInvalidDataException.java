package FlooringMastery.exceptions;

public class FlooringUserInvalidDataException extends Exception {

    public FlooringUserInvalidDataException(String message) {
        super(message);
    }

    public FlooringUserInvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
