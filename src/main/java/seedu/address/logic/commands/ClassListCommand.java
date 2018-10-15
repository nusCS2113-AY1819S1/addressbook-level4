package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.StorageController;
import seedu.address.model.classroom.Classroom;

/**
 * Lists all persons in the address book to the user.
 */
public class ClassListCommand extends Command {

    public static final String COMMAND_WORD = "class list";

    public static final String MESSAGE_SUCCESS = "Listed all classes";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        StorageController.retrieveData();

        final StringBuilder builder = new StringBuilder();

        for (Classroom c : StorageController.getClassesStorage()) {
            builder.append(c.toString());
            builder.append("\n");
        }
        return new CommandResult(MESSAGE_SUCCESS + "\n" + builder.toString());
    }
}
