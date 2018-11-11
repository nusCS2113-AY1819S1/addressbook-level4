package seedu.address.logic.commands;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.formatter.KeywordsOutputFormatter;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.searchhistory.KeywordType;

/**
 * The base class for all find commands on Person class.
 */
public abstract class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names/tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [\\tag] [\\exclude] KEYWORD [MORE_KEYWORDS]...\n"
            + "Example1: " + COMMAND_WORD + " alice bob charlie\n"
            + "Example2: " + COMMAND_WORD + " " + FindCommandParser.EXCLUDE_OPTION_STRING + " alice\n"
            + "Example3: " + COMMAND_WORD + " " + FindCommandParser.TAG_OPTION_STRING + " President";

    protected void executeSearch(Model model, Predicate<Person> predicate, KeywordType type, List<String> keywords) {
        model.recordKeywords(type, keywords);
        model.executeSearch(predicate);
    }

    /**
     * Returns a CommandResult object that stores the formatted keywords history string.
     */
    protected CommandResult getCommandResultWithKeywordsHistory(Model model) {
        KeywordsOutputFormatter formatter = new KeywordsOutputFormatter();
        String keywordHistoryString = formatter.getOutputString(model.getReadOnlyKeywordsRecord());
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size())
                        + keywordHistoryString);
    }
}
