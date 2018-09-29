package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.ui.AttendanceStage;

/**
 * Generate a attendance list for all the people in the address book
 */
public class GenListCommand extends Command {

    public static final String COMMAND_WORD = "genlist";
    public static final String COMMAND_WORD_2 = "gl";

    public static final String MESSAGE_GENERATE_ATTENDANCE_LIST = "Attendance list has been generated";
    public static final String MESSAGE_EMPTY_LIST = "The list is currently empty";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        AttendanceStage stage;
        ObservableList<Person> lastShownList = model.getFilteredPersonList();

        if (lastShownList.isEmpty())
            throw new CommandException(MESSAGE_EMPTY_LIST);

        stage = new AttendanceStage(lastShownList);
        stage.generateAttendance();
        stage.printResizedTable();

        return new CommandResult(String.format(MESSAGE_GENERATE_ATTENDANCE_LIST));
    }
}
