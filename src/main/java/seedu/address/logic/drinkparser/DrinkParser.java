package seedu.address.logic.drinkparser;

import seedu.address.logic.drinkcommands.DrinkCommand;
import seedu.address.logic.drinkparser.exceptions.DrinkParseException;

/**
 * Represents a Parser that is able to parse user input into a {@code DrinkCommand} of type {@code T}.
 */
public interface DrinkParser<T extends DrinkCommand> {

    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @throws DrinkParseException if {@code userInput} does not conform the expected format
     */
    T parse(String userInput) throws DrinkParseException;
}