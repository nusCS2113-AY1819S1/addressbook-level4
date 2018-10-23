package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_MAXMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_WEIGHTAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.gradebook.Gradebook;
import seedu.address.model.gradebook.GradebookManager;

/**
 * Adds a gradebook component to the address book.
 */
public class GradebookAddCommand extends Command {

    public static final String COMMAND_WORD = "gradebook add";
    public static final String MESSAGE_ADD_SUCCESS = "\nSuccessfully Added! \nModule Code: %1$s"
            + "\nGradebook Component Name: %2$s" + "\nMaximum Marks: %3$s" + "\nWeightage: %4$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a gradebook component to Trajectory. "
            + "\nParameters: "
            + PREFIX_MODULE_CODE + "MODULE CODE  "
            + PREFIX_GRADEBOOK_ITEM + "ITEM "
            + PREFIX_GRADEBOOK_MAXMARKS + "[MAX MARKS] "
            + PREFIX_GRADEBOOK_WEIGHTAGE + "[WEIGHTAGE] "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2113 "
            + PREFIX_GRADEBOOK_ITEM + "Assignment 1 "
            + PREFIX_GRADEBOOK_MAXMARKS + "60 "
            + PREFIX_GRADEBOOK_WEIGHTAGE + "50";

    private final Gradebook toAddGradebookItem;

    public GradebookAddCommand (Gradebook gradebookComponent) {
        this.toAddGradebookItem = gradebookComponent;
    }

    @Override
    public CommandResult execute (Model model, CommandHistory history) throws CommandException {
        GradebookManager gradebookManager = new GradebookManager();
        gradebookManager.addGradebookComponent(toAddGradebookItem);
        gradebookManager.saveGradebookList();

        return new CommandResult(String.format(
                MESSAGE_ADD_SUCCESS,
                toAddGradebookItem.getModuleCode(),
                toAddGradebookItem.getGradeComponentName(),
                toAddGradebookItem.getGradeComponentMaxMarks(),
                toAddGradebookItem.getGradeComponentWeightage()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof GradebookAddCommand //instanceof handles nulls
                && toAddGradebookItem.equals(((GradebookAddCommand) other).toAddGradebookItem));
    }
}
