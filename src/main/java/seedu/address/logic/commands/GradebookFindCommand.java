package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.gradebook.Gradebook;
import seedu.address.model.gradebook.GradebookManager;

/**
 * Finds gradebook component for module in Trajectory to the user.
 */
public class GradebookFindCommand extends Command {
    public static final String COMMAND_WORD = "gradebook find";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds a gradebook component to module in Trajectory. "
            + "Parameters: "
            + PREFIX_MODULECODE + "MODULE_CODE  "
            + PREFIX_GRADEBOOK_ITEM + "ITEM "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULECODE + "CS2113 "
            + PREFIX_GRADEBOOK_ITEM + "Assignment 1";

    private final Gradebook toFindGradebookComponent;
    public GradebookFindCommand (Gradebook gradebookComponent) {
        toFindGradebookComponent = gradebookComponent;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        String result = GradebookManager.findGradebookComponent(toFindGradebookComponent.getModuleCode(),
                toFindGradebookComponent.getGradeComponentName());
        return new CommandResult(result);
    }
}
