package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GB_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GB_COMPONENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GB_MAXMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GB_WEIGHTAGE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.gradebook.Component;

/**
 * Adds a gradebook component to the address book.
 */
public class GradebookAddCommand extends Command {

    public static final String COMMAND_WORD = "gradebook add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a gradebook component to Trajectory. "
            + "Parameters: "
            + PREFIX_GB_MODULE + "MODULE NAME "
            + PREFIX_GB_COMPONENT + "COMPONENT "
            + PREFIX_GB_MAXMARKS + "MAX. MARKS "
            + PREFIX_GB_WEIGHTAGE + "WEIGHTAGE IN PERCENT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GB_MODULE + "CS2113 "
            + PREFIX_GB_COMPONENT + "Assignment 1 "
            + PREFIX_GB_MAXMARKS + "10 "
            + PREFIX_GB_WEIGHTAGE + "10 ";

    public static final String MESSAGE_SUCCESS = "New gradebook added: %1$s";
    public static final String MESSAGE_DUPLICATE_COMPONENT = "Grade component already exist in Trajectory.";

    private final Component addGBComponent;

    public GradebookAddCommand (Component component) {
        requireNonNull(component);
        addGBComponent = component;
    }

    @Override
    public CommandResult execute (Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if(model.hasComponent(addGBComponent)) {
            throw new CommandException(MESSAGE_DUPLICATE_COMPONENT);
        }

        model.addComponent(addGBComponent);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, addGBComponent));
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof GradebookAddCommand //instanceof handles nulls
                && addGBComponent.equals(((GradebookAddCommand) other).addGBComponent));
    }
}
