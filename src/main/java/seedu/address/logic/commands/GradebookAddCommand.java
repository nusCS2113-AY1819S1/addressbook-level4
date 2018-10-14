package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_MAXMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_WEIGHTAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.gradebook.GradebookComponent;
import seedu.address.model.gradebook.GradebookModel;

/**
 * Adds a gradebook component to the address book.
 */
public class GradebookAddCommand extends Command {

    public static final String COMMAND_WORD = "gradebook add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a gradebook component to Trajectory. "
            + "\nParameters: "
            + PREFIX_MODULECODE + "MODULE_CODE  "
            + PREFIX_GRADEBOOK_ITEM + "ITEM "
            + PREFIX_GRADEBOOK_MAXMARKS + "[MAX MARKS] "
            + PREFIX_GRADEBOOK_WEIGHTAGE + "[WEIGHTAGE] "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_MODULECODE + "CS2113 "
            + PREFIX_GRADEBOOK_ITEM + "Assignment 1 "
            + PREFIX_GRADEBOOK_MAXMARKS + "60 "
            + PREFIX_GRADEBOOK_WEIGHTAGE + "50";

    private final GradebookComponent toAddGradebookItem;

    public GradebookAddCommand (GradebookComponent gradebookComponent) {
        toAddGradebookItem = gradebookComponent;
    }

    @Override
    public CommandResult execute (Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        CommandResult result = GradebookModel.addGradebookComponent(toAddGradebookItem.getModuleCode(),
                toAddGradebookItem.getGradeComponentName(), toAddGradebookItem.getGradeComponentMaxMarks(),
                toAddGradebookItem.getGradeComponentWeightage());
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof GradebookAddCommand //instanceof handles nulls
                && toAddGradebookItem.equals(((GradebookAddCommand) other).toAddGradebookItem));
    }
}
