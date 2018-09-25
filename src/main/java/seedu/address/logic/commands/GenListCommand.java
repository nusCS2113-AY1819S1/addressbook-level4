package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.ui.AttendanceStage;

/**
 * Generate a attendance list for all the people in the address book
 */
public class GenListCommand extends Command {

    public static final String COMMAND_WORD = "genlist";
    public static final String COMMAND_WORD_2 = "gl";

    private AttendanceStage stage;

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        ObservableList<Person> lastShownList = model.getFilteredPersonList();
        stage=new AttendanceStage(lastShownList);
        stage.generateAttendance();
        stage.printAttendanceList();
        return new CommandResult(String.format(Messages.MESSAGE_GENERATE_ATTENDANCE_LIST));
    }
}
