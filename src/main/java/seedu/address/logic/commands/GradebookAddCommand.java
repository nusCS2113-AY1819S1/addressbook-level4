package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_MODULE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.StorageController;
import seedu.address.model.gradebook.GradebookComponent;

/**
 * Adds a gradebook component to the address book.
 */
public class GradebookAddCommand extends Command {

    public static final String COMMAND_WORD = "gradebook add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a gradebook component to Trajectory. "
            + "Parameters: "
            + PREFIX_GRADEBOOK_MODULE + "MODULE_CODE  "
            + PREFIX_GRADEBOOK_ITEM + "ITEM "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GRADEBOOK_MODULE + "CS2113 "
            + PREFIX_GRADEBOOK_ITEM + "Assignment 1";

    public static final String MESSAGE_SUCCESS = "\nSuccessfully Added! \nModule Code: %2$s \nGradebook Item: %1$s";
    public static final String MESSAGE_DUPLICATE_COMPONENT = "This grade component already exist in Trajectory.";

    private final GradebookComponent toAddGradebookItem;

    public GradebookAddCommand (GradebookComponent gradebookComponent) {
        toAddGradebookItem = gradebookComponent;
    }

    @Override
    public CommandResult execute (Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        StorageController.retrieveData();
        StorageController.getGradebookStorage().add(new GradebookComponent(toAddGradebookItem.getGradeItemName(),
                toAddGradebookItem.getModuleCode()));
        StorageController.storeData();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddGradebookItem.getGradeItemName(),
                toAddGradebookItem.getModuleCode()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof GradebookAddCommand //instanceof handles nulls
                && toAddGradebookItem.equals(((GradebookAddCommand) other).toAddGradebookItem));
    }
}
