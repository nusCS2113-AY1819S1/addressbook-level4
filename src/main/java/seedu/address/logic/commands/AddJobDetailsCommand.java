package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class AddJobDetailsCommand extends Command{

    public static final String COMMAND_WORD = "AddJobDetails";

    public AddJobDetailsCommand() {
    };

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        LogicManager.setLogicState("Primary");
        return new CommandResult("Job Added");
    };
}
