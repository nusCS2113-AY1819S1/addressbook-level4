package seedu.address.model.timeidentifiedclass.exceptions;

/**
 * This exception represents a situation in which the product name list is unequal in size to the
 * product quantity list for a given transaction.
 */
public class ProductQuantityMismatchException extends Exception {
    public static final String EXCEPTION_MESSAGE = "Transaction %s has product list size %s and quantity list size %s!";

    public ProductQuantityMismatchException() {}

    public String getExceptionMessage() {
        return EXCEPTION_MESSAGE;
    }
}
