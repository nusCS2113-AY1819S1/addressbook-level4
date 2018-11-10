package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.logic.ChangeLogicStateEvent;
import seedu.recruit.commons.events.ui.ShowCandidateBookRequestEvent;
import seedu.recruit.logic.CommandHistory;

import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;

/**
 *  Starts the add candidate interface
 *  Users can continue adding candidates until in this interface until user inputs 'cancel'
 */

public class StartAddCandidateCommand extends Command {
    public static final String COMMAND_WORD = "addc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Starts the add candidate interface. ";

    public StartAddCandidateCommand() {

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs) throws CommandException {
        requireNonNull(model);
        EventsCenter.getInstance().post(new ShowCandidateBookRequestEvent());
        EventsCenter.getInstance().post(new ChangeLogicStateEvent(AddCandidateCommand.COMMAND_WORD));

        return new CommandResult(AddCandidateCommand.MESSAGE_USAGE);
    }
}
