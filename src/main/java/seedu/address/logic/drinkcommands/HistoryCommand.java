package seedu.address.logic.drinkcommands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.model.DrinkModel;

/**
 * Lists all the commands entered by user from the start of app launch.
 */
public class HistoryCommand extends DrinkCommand {

    public static final String COMMAND_WORD = "history";
    public static final String MESSAGE_SUCCESS = "Entered commands (from most recent to earliest):\n%1$s";
    public static final String MESSAGE_NO_HISTORY = "You have not yet entered any commands.";

    @Override
    public DrinkCommandResult execute(DrinkModel model, CommandHistory history) {
        requireNonNull(history);
        List<String> previousCommands = history.getHistory();

        if (previousCommands.isEmpty()) {
            return new DrinkCommandResult(MESSAGE_NO_HISTORY);
        }

        Collections.reverse(previousCommands);
        return new DrinkCommandResult(String.format(MESSAGE_SUCCESS, String.join("\n", previousCommands)));
    }

}
