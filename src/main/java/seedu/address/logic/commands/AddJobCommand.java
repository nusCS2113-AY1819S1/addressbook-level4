package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 *  Starts the 2-step process of adding a job offer
 */

public class AddJobCommand extends Command {
    public static final String COMMAND_WORD = "addj";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a job offer to the RecruitBook. ";

    public AddJobCommand() {

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        LogicManager.setLogicState("AddJobDetails");
        return new CommandResult(AddJobDetailsCommand.MESSAGE_USAGE);
    };



}
