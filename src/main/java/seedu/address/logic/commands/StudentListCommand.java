package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.student.StudentManager;


/**
 * Lists all persons in the address book to the user.
 */
public class StudentListCommand extends Command {

    public static final String COMMAND_WORD = "student list";

    public static final String MESSAGE_SUCCESS = "Listed all students";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {

        // TO RETAIN BACKWARDS COMPATIBILITY
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        requireNonNull(model);

        return new CommandResult(MESSAGE_SUCCESS, StudentManager.getInstance().getTableRepresentation());
    }
}
