package seedu.address.logic.drinkparser.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a parse error encountered by a parser.
 */
public class DrinkParseException extends IllegalValueException {

    public DrinkParseException(String message) {
        super(message);
    }

    public DrinkParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
