package seedu.address.model.shopday.exceptions;

import java.lang.Exception;

public class ClosedShopDayException extends Exception {
    private static String EXCEPTION_MESSAGE = "Unable to alter a day that is in the past!";

    public ClosedShopDayException() {}

    public String getExceptionMessage() {
        return EXCEPTION_MESSAGE;
    }
}
