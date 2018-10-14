package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_MODULE;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.gradebook.GradebookComponent;
import seedu.address.model.gradebook.GradebookModel;

/**
 * Finds gradebook component for module in Trajectory to the user.
 */
public class GradebookFindCommand extends Command {
    public static final String COMMAND_WORD = "gradebook find";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds a gradebook component to module in Trajectory. "
            + "Parameters: "
            + PREFIX_GRADEBOOK_MODULE + "MODULE_CODE  "
            + PREFIX_GRADEBOOK_ITEM + "ITEM "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GRADEBOOK_MODULE + "CS2113 "
            + PREFIX_GRADEBOOK_ITEM + "Assignment 1";

    private final GradebookComponent toFindGradebookComponent;

    public GradebookFindCommand(GradebookComponent gradebookComponent) {
        toFindGradebookComponent = gradebookComponent;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        CommandResult result = GradebookModel.findGradebookComponent(toFindGradebookComponent.getModuleCode(),
                toFindGradebookComponent.getGradeItemName());
        return result;
    }
}
