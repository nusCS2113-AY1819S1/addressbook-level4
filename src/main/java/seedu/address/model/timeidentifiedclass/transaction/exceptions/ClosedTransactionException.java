package seedu.address.model.timeidentifiedclass.transaction.exceptions;
import java.lang.Exception;

public class ClosedTransactionException extends Exception {
    private static final String EXCEPTION_MESSAGE = "Cannot edit a closed transaction.";

    public ClosedTransactionException() {}

    public String message() {
        return EXCEPTION_MESSAGE;
    }
}

