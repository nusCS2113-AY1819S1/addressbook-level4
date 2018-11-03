package seedu.planner.ui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import impl.org.controlsfx.autocompletion.SuggestionProvider;
import seedu.planner.logic.commands.AddCommand;
import seedu.planner.logic.commands.AddLimitCommand;
import seedu.planner.logic.commands.CheckLimitCommand;
import seedu.planner.logic.commands.ClearCommand;
import seedu.planner.logic.commands.DeleteCommand;
import seedu.planner.logic.commands.DeleteCommandByDateEntry;
import seedu.planner.logic.commands.DeleteLimitCommand;
import seedu.planner.logic.commands.EditCommand;
import seedu.planner.logic.commands.EditLimitCommand;
import seedu.planner.logic.commands.ExitCommand;
import seedu.planner.logic.commands.ExportExcelCommand;
import seedu.planner.logic.commands.FindCommand;
import seedu.planner.logic.commands.FindTagCommand;
import seedu.planner.logic.commands.HelpCommand;
import seedu.planner.logic.commands.HistoryCommand;
import seedu.planner.logic.commands.ListCommand;
import seedu.planner.logic.commands.RedoCommand;
import seedu.planner.logic.commands.SelectCommand;
import seedu.planner.logic.commands.SortCommand;
import seedu.planner.logic.commands.StatisticCommand;
import seedu.planner.logic.commands.SummaryByDateCommand;
import seedu.planner.logic.commands.SummaryByMonthCommand;
import seedu.planner.logic.commands.SummaryCommand;
import seedu.planner.logic.commands.UndoCommand;

/**
 *  This object is responsible for the logic behind the {@code AutoCompleteBox} that decides the range of possible
 *  suggested texts that are to appear whenever a user enters a new word.
 */
public class CustomSuggestionProvider {

    private static String patternStr = "t/(.*)"; // TODO: ZHITHON autocomplete by tags soon
    private static Pattern pattern = Pattern.compile(patternStr);

    private static Set<String> commandKeywordsSet =
            new HashSet<>(Arrays.asList(AddCommand.COMMAND_WORD, AddLimitCommand.COMMAND_WORD,
                    CheckLimitCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD,
                    DeleteCommandByDateEntry.COMMAND_WORD, DeleteLimitCommand.COMMAND_WORD,
                    EditCommand.COMMAND_WORD, EditLimitCommand.COMMAND_WORD,
                    ExitCommand.COMMAND_WORD, ExportExcelCommand.COMMAND_WORD, FindCommand.COMMAND_WORD,
                    FindTagCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD,
                    ListCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD,
                    SortCommand.COMMAND_WORD, StatisticCommand.COMMAND_WORD, SummaryCommand.COMMAND_WORD,
                    UndoCommand.COMMAND_WORD));

    private static Set<String> commandKeywordsUnderscoreSet =
            new HashSet<>(Arrays.asList(AddLimitCommand.COMMAND_WORD_UNDERSCORE,
                    CheckLimitCommand.COMMAND_WORD_UNDERSCORE, DeleteCommandByDateEntry.COMMAND_WORD_UNDERSCORE,
                    DeleteLimitCommand.COMMAND_WORD_UNDERSCORE, EditLimitCommand.COMMAND_WORD_UNDERSCORE,
                    ExportExcelCommand.COMMAND_WORD_UNDERSCORE, FindTagCommand.COMMAND_WORD_UNDERSCORE));

    private static Set<String> fullCommandKeywordsSet = Stream.concat(commandKeywordsSet.stream(),
            commandKeywordsUnderscoreSet.stream()).collect(Collectors.toSet());

    private static Set<String> summaryKeywordsSet = new HashSet<>(Arrays.asList(SummaryByMonthCommand.COMMAND_MODE_WORD,
            SummaryByDateCommand.COMMAND_MODE_WORD));

    private static Set<String> possibleTagKeywordsSet = new HashSet<>(Arrays.asList(AddCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD));

    private static Set<String> sortKeywordsSet = Stream.concat(SortCommand.ORDER_SET.stream(),
            SortCommand.CATEGORY_SET.stream()).collect(Collectors.toSet());

    private static Set<String> emptySet = new HashSet<>();

    private static Map<String, Integer> tagKeywordsMap = new HashMap<>();

    private static SuggestionProvider<String> suggestionProvider = SuggestionProvider.create(commandKeywordsSet);

    private static void clearSuggestions() {
        suggestionProvider.clearSuggestions();
    }

    public static SuggestionProvider<String> getSuggestions() {
        return suggestionProvider;
    }

    private static void updateSuggestions(Set<String> newSuggestions) {
        suggestionProvider.addPossibleSuggestions(newSuggestions);
    }

    /**
     * This method is responsible for deciding which collection of Strings is to be used as the reference collection
     * for comparison to the input String by the user. It can autocomplete the command word or when a tag is expected
     * of the user input.
     * @param userInput is the current word/substring to be automatically completed
     */
    public static void updateSuggestions(String userInput) {
        String[] inputs = userInput.split(" ");
        clearSuggestions();

        if (inputs[0].equals(SortCommand.COMMAND_WORD) && inputs.length > 1) {
            if (inputs.length > 3) {
                updateSuggestions(emptySet);
            } else if (SortCommand.ORDER_SET.contains(inputs[1])) {
                updateSuggestions(SortCommand.CATEGORY_SET);
            } else if (SortCommand.CATEGORY_SET.contains(inputs[1])) {
                updateSuggestions(SortCommand.ORDER_SET);
            } else if (inputs.length > 2 && !inputs[2].isEmpty()) {
                updateSuggestions(emptySet);
            } else {
                updateSuggestions(sortKeywordsSet);
            }
        } else if (inputs[0].equals(SummaryCommand.COMMAND_WORD) && inputs.length > 1) {
            if (inputs.length > 2) {
                updateSuggestions(emptySet);
            } else if (inputs[1].equals(SummaryByDateCommand.COMMAND_MODE_WORD)) {
                updateSuggestions(SummaryByMonthCommand.COMMAND_MODE_WORD);
            } else if (inputs[1].equals(SummaryByMonthCommand.COMMAND_MODE_WORD)) {
                updateSuggestions(SummaryByDateCommand.COMMAND_MODE_WORD);
            } else {
                updateSuggestions(summaryKeywordsSet);
            }
        } else if (inputs[0].equals(FindTagCommand.COMMAND_WORD) && inputs.length > 1) {
            updateSuggestions(tagKeywordsMap.keySet());
        } else if (fullCommandKeywordsSet.contains(inputs[0]) || inputs.length > 1) {
            updateSuggestions(emptySet);
        } else {
            updateSuggestions(commandKeywordsSet);
        }

    }

    public static void updateTagSet(HashMap<String, Integer> newTagKeywordsMap) {
        tagKeywordsMap = newTagKeywordsMap;
    }

}
