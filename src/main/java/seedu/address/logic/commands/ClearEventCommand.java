//@@author ian-tjahjono
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.EventBook;
import seedu.address.model.Model;


/**
 * Clears the event book.
 */
public class ClearEventCommand extends Command {

    public static final String COMMAND_WORD = "clearEvent";
    public static final String MESSAGE_SUCCESS = "Event book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new EventBook());
        model.commitEventBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
