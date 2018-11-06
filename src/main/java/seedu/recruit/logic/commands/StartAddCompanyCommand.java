package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.ui.ShowCompanyBookRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;

/**
 *  Starts the add company interface
 *  Users can continue adding companies until in this interface until user inputs 'cancel'
 */

public class StartAddCompanyCommand extends Command {
    public static final String COMMAND_WORD = "addC";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Starts the add company interface. ";

    public StartAddCompanyCommand() {

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs) throws CommandException {
        requireNonNull(model);
        EventsCenter.getInstance().post(new ShowCompanyBookRequestEvent());

        LogicManager.setLogicState(AddCompanyCommand.COMMAND_WORD);
        return new CommandResult(AddCompanyCommand.MESSAGE_USAGE);
    }

}
