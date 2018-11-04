package seedu.address.logic.commands.eventCommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Events.Event;
import seedu.address.model.Model;

/**
 * add event to the club book.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_ALIAS = "aE";
    public static final String COMMAND_WORD = "addEvent";

    public static final String COMMAND_USAGE = COMMAND_WORD
            + ":add an event to the event\n"
            + "parameters: "
            + PREFIX_NAME + "EVENT_NAME "
            + PREFIX_VENUE + "EVENT_VENUE "
            + PREFIX_DESCRIPTION + "EVENT_DESCRIPTION "
            + PREFIX_DATE + "EVENT_DATE\n"
            + "Example: "
            + COMMAND_WORD + " n/Basketball training v/MPSH3 D/Bring your own basketball d/01/11\n ";;

    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";

    private final Event newEvent;
    private final Logger logger = LogsCenter.getLogger(AddEventCommand.class);

    public AddEventCommand(Event event) {
        requireNonNull(event);
        newEvent = event;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(newEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.addEvent(newEvent);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS, newEvent));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && newEvent.equals(((AddEventCommand) other).newEvent));
    }
}
