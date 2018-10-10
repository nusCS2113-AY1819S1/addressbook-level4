package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_MODULE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.StorageController;
import seedu.address.model.gradebook.GradebookComponent;

/**
 * Find all gradebook components for module in Trajectory to the user.
 */
public class GradebookFindCommand extends Command {

    public static final String COMMAND_WORD = "gradebook find";
    public static final String MESSAGE_SUCCESS = "Successfully Found!";
    public static final String MESSAGE_FAIL = "Module Not Found!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds a gradebook component to module code in"
            + " Trajectory. "
            + "Parameters: "
            + PREFIX_GRADEBOOK_MODULE + "MODULE_CODE  "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GRADEBOOK_MODULE + "CS2113 ";

    private final GradebookComponent toFindGradebookComponent;

    public GradebookFindCommand(GradebookComponent gradebookComponent) {
        toFindGradebookComponent = gradebookComponent;
    }

    @Override
    public CommandResult execute (Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        StorageController.retrieveData();
        StringBuilder sb = new StringBuilder();
        String result = MESSAGE_FAIL;

        for (GradebookComponent gc : StorageController.getGradebookStorage()) {
            if (toFindGradebookComponent.getModuleCode().equals(gc.getModuleCode())) {
                sb.append("Module Code: ");
                sb.append(gc.getModuleCode() + "\n");
                sb.append("Grade Component: ");
                sb.append(gc.getGradeItemName() + "\n");
                result = MESSAGE_SUCCESS;
            }
        }
        return new CommandResult ("\n" + result + "\n" + sb.toString());
    }
}
