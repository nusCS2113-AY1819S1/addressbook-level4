package seedu.address.logic.drinkcommands.exceptions;

/**
 * Represents an error which occurs during execution of a {@link DrinkCommand}.
 */
public class DrinkCommandException extends Exception {
    public DrinkCommandException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public DrinkCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
