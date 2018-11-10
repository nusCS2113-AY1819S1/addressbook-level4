package seedu.address.model.request;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.events.model.RequestPanelChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;

/**
 * Lists all persons in the BookInventory to the user.
 */
public class ToggleRequestCommand extends CommandSecondary {

    public static final String COMMAND_WORD = "togglerequests";

    public static final String MESSAGE_SUCCESS = "Request panel toggled.";

    @Override
    public CommandResult execute(RequestModel requestModel, CommandHistory history) {
        requireNonNull(requestModel);
        raise(new RequestPanelChangedEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
