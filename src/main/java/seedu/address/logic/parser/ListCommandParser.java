package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ListCommand parse(String userInput) throws ParseException {
        List<String> showAll = new ArrayList<>();
        if (parseAll(userInput)) {
            return new ListCommand(ListCommand.LIST_KEY_ALL, new DepartmentContainsKeywordsPredicate(showAll));
        } else if (parseEvent(userInput)) {
            return new ListCommand(ListCommand.LIST_KEY_EVENT, new DepartmentContainsKeywordsPredicate(showAll));
        } else if (parseDep(userInput)) {
            String relevantInfo = userInput.replaceAll("(?i)dep", "");

            if (relevantInfo.equalsIgnoreCase(" ")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
            DepartmentContainsKeywordsPredicate predicate = preparePredicate(relevantInfo);
            return new ListCommand(ListCommand.LIST_KEY_DEPARTMENT, predicate);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Checks if first word of the userInput is equals to "all"
     * @return boolean true if it is equal, if not, false
     */
    private boolean parseAll(String userInput) {
        return userInput.trim().equalsIgnoreCase("all people");
    }

    /**
     * Checks if first word of the userInput is equals to "dep"
     * @return boolean true if it is equal, if not, false
     */
    private boolean parseDep(String userInput) {
        return userInput.trim().split("\\s+")[0]
                .equalsIgnoreCase("dep");
    }

    /**
     * Checks if first word of the userInput is equals to "dep"
     * @return boolean true if it is equal, if not, false
     */
    private boolean parseEvent(String userInput) {
        return userInput.trim().equalsIgnoreCase("all events");
    }
    /**
     * Splits the userInput by whitespace and places it into a DepartmentContainsKeywordPredicate
     * @return a new predicate
     */
    private DepartmentContainsKeywordsPredicate preparePredicate(String userInput) {
        String[] departments = userInput.trim().split("\\s+");
        return new DepartmentContainsKeywordsPredicate(Arrays.asList(departments));
    }

}
