package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

public class PasswordCommand extends Command {
    private String message;
    public static final String COMMAND_WORD = "password";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Hashes password together with username as salting.\n" +
            "Parameters: KEYWORD USERNAME PASSWORD...\\n" +
            "Example: " + COMMAND_WORD + " myUsername myPassword";

//    public static final String MESSAGE_SUCCESS = "Command executed";

    public PasswordCommand (String message) {
        this.message = message;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {

        return new CommandResult(this.message);
    }


}
