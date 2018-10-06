package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds a job offer to the JobBook with the relevant fields
 */

public class AddJobDetailsCommand extends Command {

    public static final String COMMAND_WORD = "AddJobDetails";

    public static final String MESSAGE_USAGE = "Enter the following details of the job in the format:\n"
            + "c/COMPANY j/JOB_TITLE";

    public AddJobDetailsCommand() {
    };

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        LogicManager.setLogicState("Primary");
        return new CommandResult("Job Added");
    };
}
