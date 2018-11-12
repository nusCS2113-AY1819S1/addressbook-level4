package seedu.address.request.requestparser;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.request.requestcommands.CommandSecondary;

/**
 * Represents a Parser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface RequestParser<T extends CommandSecondary> {

    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    T parse(String userInput) throws ParseException;
}
