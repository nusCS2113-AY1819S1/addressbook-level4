package seedu.address.model.shopday.exceptions;

/**
 * TODO
 */
public class ClosedShopDayException extends Exception {
    private static final String EXCEPTION_MESSAGE = "Unable to alter a day that is in the past!";

    public ClosedShopDayException() {}

    public String getExceptionMessage() {
        return EXCEPTION_MESSAGE;
    }
}
