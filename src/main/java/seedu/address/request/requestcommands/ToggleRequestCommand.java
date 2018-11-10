package seedu.address.request.requestcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.events.model.RequestPanelChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.request.requestmodel.RequestModel;

/**
 * Toggles the request panel.
 * If request panel initially shows data, data will be hidden afterwards, and vice versa.
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
