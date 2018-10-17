package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindPersonSubCommand;
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

        String[] keywords = trimmedArgs.split("\\s+");
        List<String> keywordsList = new ArrayList<String>(Arrays.asList(keywords));

        if (!hasValidInputFormat(keywordsList)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (isExcludeTagSearch(keywordsList)) {
            removesFirstTwoItemsFromKeywordsList(keywordsList);
            return new FindTagSubCommand(new TagContainsKeywordsPredicate(keywordsList), true);
        } else if (isIncludeTagSearch(keywordsList)) {
            keywordsList.remove(0);
            return new FindTagSubCommand(new TagContainsKeywordsPredicate(keywordsList));
        } else if (isExcludePersonSearch(keywordsList)) {
            keywordsList.remove(0);
            return new FindPersonSubCommand(new NameContainsKeywordsPredicate(keywordsList), true);
        } else {
            return new FindPersonSubCommand(new NameContainsKeywordsPredicate(keywordsList));
        }
    }

    private boolean isExcludePersonSearch(List<String> keywordsList) {
        return keywordsList.get(0).equals(EXCLUDE_OPTION_STRING);
    }

    private boolean isIncludeTagSearch(List<String> keywordsList) {
        return keywordsList.size() >= 2 && keywordsList.get(0).equals(TAG_OPTION_STRING)
                && !keywordsList.get(1).equals(EXCLUDE_OPTION_STRING);
    }

    private boolean isExcludeTagSearch(List<String> keywordsList) {
        return keywordsList.size() >= 2 && keywordsList.get(0).equals(TAG_OPTION_STRING)
                && keywordsList.get(1).equals(EXCLUDE_OPTION_STRING);
    }

    /**
     * Returns false if the user's input is in the incorrect format
     */
    private boolean hasValidInputFormat(List<String> keywordsList) {
        if (keywordsList.get(0).equals(TAG_OPTION_STRING) && keywordsList.size() == 1) {
            return false;
        } else if (keywordsList.get(0).equals(TAG_OPTION_STRING)
                && keywordsList.get(0).equals(EXCLUDE_OPTION_STRING) && keywordsList.size() == 2) {
            return false;
        } else if (keywordsList.get(0).equals(EXCLUDE_OPTION_STRING) && keywordsList.size() == 1) {
            return false;
        }
        return true;
    }

    private void removesFirstTwoItemsFromKeywordsList(List<String> keywordsList) {
        assert keywordsList.size() >= 2;
        keywordsList.remove(1);
        keywordsList.remove(0);
    }
}
