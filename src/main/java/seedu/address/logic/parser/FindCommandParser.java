package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindNameSubCommand;
import seedu.address.logic.commands.FindTagSubCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    public static final String TAG_OPTION_STRING = "\\tag";
    public static final String EXCLUDE_OPTION_STRING = "\\exclude";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] splittedArgumentsArray = trimmedArgs.split("\\s+");
        List<String> argumentsList = new ArrayList<>(Arrays.asList(splittedArgumentsArray));

        int sizeOfList = argumentsList.size();

        if (isExcludeTagSearch(argumentsList)) {
            List<String> keywordsList = argumentsList.subList(2, sizeOfList);
            return new FindTagSubCommand(new TagContainsKeywordsPredicate(keywordsList), true);
        } else if (isIncludeTagSearch(argumentsList)) {
            List<String> keywordsList = argumentsList.subList(1, sizeOfList);
            return new FindTagSubCommand(new TagContainsKeywordsPredicate(keywordsList));
        } else if (isExcludePersonSearch(argumentsList)) {
            List<String> keywordsList = argumentsList.subList(1, sizeOfList);
            return new FindNameSubCommand(new NameContainsKeywordsPredicate(keywordsList), true);
        } else if (isIncludeNameSearch(argumentsList)) {
            return new FindNameSubCommand(new NameContainsKeywordsPredicate(argumentsList));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    private boolean isExcludeTagSearch(List<String> argumentsList) {
        return argumentsList.size() > 2 && (argumentsList.get(0).equals(TAG_OPTION_STRING)
                && argumentsList.get(1).equals(EXCLUDE_OPTION_STRING) || argumentsList.get(1).equals(TAG_OPTION_STRING)
                && argumentsList.get(0).equals(EXCLUDE_OPTION_STRING));
    }

    private boolean isExcludePersonSearch(List<String> argumentsList) {
        return argumentsList.size() > 1 && argumentsList.get(0).equals(EXCLUDE_OPTION_STRING)
                && !argumentsList.get(1).equals(TAG_OPTION_STRING);
    }

    private boolean isIncludeTagSearch(List<String> argumentsList) {
        return argumentsList.size() > 1 && argumentsList.get(0).equals(TAG_OPTION_STRING)
                && !argumentsList.get(1).equals(EXCLUDE_OPTION_STRING);
    }

    private boolean isIncludeNameSearch(List<String> argumentsList) {
        return argumentsList.size() > 0 && !argumentsList.get(0).equals(TAG_OPTION_STRING)
                && !argumentsList.get(0).equals(EXCLUDE_OPTION_STRING);
    }
}
