package seedu.planner.ui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import impl.org.controlsfx.autocompletion.SuggestionProvider;
import seedu.planner.logic.commands.AddCommand;
import seedu.planner.logic.commands.ClearCommand;
import seedu.planner.logic.commands.DeleteCommand;
import seedu.planner.logic.commands.DeleteCommandByDateEntry;
import seedu.planner.logic.commands.EditCommand;
import seedu.planner.logic.commands.ExitCommand;
import seedu.planner.logic.commands.ExportExcelCommand;
import seedu.planner.logic.commands.FindCommand;
import seedu.planner.logic.commands.FindTagCommand;
import seedu.planner.logic.commands.HelpCommand;
import seedu.planner.logic.commands.HistoryCommand;
import seedu.planner.logic.commands.LimitCommand;
import seedu.planner.logic.commands.ListCommand;
import seedu.planner.logic.commands.RedoCommand;
import seedu.planner.logic.commands.SelectCommand;
import seedu.planner.logic.commands.SortCommand;
import seedu.planner.logic.commands.SummaryCommand;
import seedu.planner.logic.commands.UndoCommand;
import seedu.planner.model.tag.Tag;

public class CustomSuggestionProvider {

    private static Set<String> commandKeywordsSet =
            new HashSet<>(Arrays.asList(AddCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD,
                    DeleteCommandByDateEntry.COMMAND_WORD, EditCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD,
                    ExportExcelCommand.COMMAND_WORD, FindCommand.COMMAND_WORD, FindTagCommand.COMMAND_WORD,
                    HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, LimitCommand.COMMAND_WORD,
                    ListCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD,
                    SortCommand.COMMAND_WORD, SummaryCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD));

    private static Set<String> sortKeywordsSet = Stream.concat(SortCommand.ORDER_SET.stream(),
            SortCommand.CATEGORY_SET.stream()).collect(Collectors.toSet());

    private static Set<String> emptySet = new HashSet<>();

    private static Set<String> tagKeywordsSet = new HashSet<>();

    private static SuggestionProvider<String> suggestionProvider = SuggestionProvider.create(commandKeywordsSet);

    private static void clearSuggestions() {
        suggestionProvider.clearSuggestions();
    }

    private static void updateSuggestions(Set<String> newSuggestions) {
        suggestionProvider.addPossibleSuggestions(newSuggestions);
    }

    public static SuggestionProvider<String> getSuggestions() {
        return suggestionProvider;
    }

    public static void updateSuggestions(String inputString) {
        String[] inputs = inputString.split(" ");
        clearSuggestions();
        if (inputs[0].equals(SortCommand.COMMAND_WORD) && inputs.length > 1) {
            if (SortCommand.ORDER_SET.contains(inputs[1])) {
                updateSuggestions(SortCommand.CATEGORY_SET);
            } else if (SortCommand.CATEGORY_SET.contains(inputs[1])) {
                updateSuggestions(SortCommand.ORDER_SET);
            } else if (inputs.length > 2 && !inputs[2].isEmpty()){
                updateSuggestions(emptySet);
            } else {
                updateSuggestions(sortKeywordsSet);
            }
        } else if (commandKeywordsSet.contains(inputs[0]) || inputs.length > 1) {
            updateSuggestions(emptySet);
        } else {
            updateSuggestions(commandKeywordsSet);
        }
    }

    public CustomSuggestionProvider() {
    }

    private void createTagSet(){

    }

    public static void addTagToAutoCompleteTagSet(Tag tag) {
        tagKeywordsSet.add(tag.tagName);
    }

}
