package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Adds an event to the student planner.
 */
public class AddEventCommand extends Command {
    public static final String COMMAND_WORD = "addEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the student planner. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_START + "EVENT DATE "
            + PREFIX_TIME + "EVENT TIME "
            + "[" + PREFIX_CONTACT + "CONTACT]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Watch Avengers 4 movie "
            + PREFIX_START + "26052019 "
            + PREFIX_TIME + "1700 "
            + PREFIX_CONTACT + "John";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the task list";

    private final Event toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddEventCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.addEvent(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }

}
