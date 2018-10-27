package seedu.planner.ui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.plaf.synth.Region;

import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.scene.control.TextField;
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

/**
 * The UI component that is responsible for displaying the possible auto complete text inputs
 * under the CommandBox UI component.
 */
public class AutoCompleteBox extends UiPart<Region> {

    private static final String FXML = "AutoCompleteBox.fxml";
    private static final int MAX_ROWS = 5;

    private static TextField commandTextField;

    public static Set<String> commandKeywordsSet =
            new HashSet<>(Arrays.asList(AddCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD,
                    DeleteCommandByDateEntry.COMMAND_WORD, EditCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD,
                    ExportExcelCommand.COMMAND_WORD, FindCommand.COMMAND_WORD, FindTagCommand.COMMAND_WORD,
                    HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, LimitCommand.COMMAND_WORD,
                    ListCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD,
                    SortCommand.COMMAND_WORD, SummaryCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD));

    public static Set<String> sortKeywordsSet = Stream.concat(SortCommand.ORDER_SET.stream(),
            SortCommand.CATEGORY_SET.stream()).collect(Collectors.toSet());

    public static SuggestionProvider<String> suggestionProvider = SuggestionProvider.create(commandKeywordsSet);

    public AutoCompleteBox(TextField commandTextField) {
        super(FXML);
        this.commandTextField = commandTextField;
        new CustomAutoCompletionTextFieldBinding<>(commandTextField, suggestionProvider).setVisibleRowCount(MAX_ROWS);
    }

}
