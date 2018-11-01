package seedu.address.logic.commands.EventCommand;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.Events.Event;

import seedu.address.commons.core.LogsCenter;
import java.util.logging.Logger;

/**
 * add event to the club book.
 */
public class AddEventCommand extends Command {

    private final Logger logger = LogsCenter.getLogger(AddEventCommand.class);

    public static final String COMMAND_WORD = "addEvent";

    public static final String COMMAND_ALIAS = "aE";

    public static final String COMMAND_USAGE = COMMAND_WORD
            + ":add an event to the event\n"
            + "parameters: "
            + PREFIX_NAME + "EVENT_NAME "
            + PREFIX_VENUE + "EVENT_VENUE "
            + PREFIX_DESCRIPTION + "EVENT_DESCRIPTION "
            + PREFIX_DATE + "EVENT_DATE\n"
            +  "Example: " + COMMAND_WORD + " n/Basketball training v/MPSH3 d/Bring your own basketball D/01/11\n ";;

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";

    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book";

    private final Event NewEvent;

    public AddEventCommand(Event event) {
        requireNonNull(event);
        NewEvent = event;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(NewEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.addEvent(NewEvent);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS, NewEvent));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && NewEvent.equals(((AddEventCommand) other).NewEvent));
    }
}