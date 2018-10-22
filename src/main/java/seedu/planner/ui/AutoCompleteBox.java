package seedu.planner.ui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.plaf.synth.Region;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.logic.Logic;
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

public class AutoCompleteBox {

   // private static final String FXML = "AutoCompleteBox.fxml";

    private static Set<String> commandWordSet =
            new HashSet<>(Arrays.asList(AddCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD,
                    DeleteCommandByDateEntry.COMMAND_WORD, EditCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD,
                    ExportExcelCommand.COMMAND_WORD, FindCommand.COMMAND_WORD, FindTagCommand.COMMAND_WORD,
                    HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, LimitCommand.COMMAND_WORD,
                    ListCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD,
                    SortCommand.COMMAND_WORD, SummaryCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD));

    @FXML
    private AutoCompletionBinding<String> autoCompletionBinding;

    public AutoCompleteBox(TextField commandTextField) {
        //super(FXML);
        autoCompletionBinding = TextFields.bindAutoCompletion(commandTextField, commandWordSet);
    }

}
