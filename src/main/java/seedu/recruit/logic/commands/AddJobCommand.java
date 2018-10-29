package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.ui.ShowCompanyBookRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.ui.MainWindow;

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
        if (!MainWindow.getDisplayedBook().equals("companybook")) {
            EventsCenter.getInstance().post(new ShowCompanyBookRequestEvent());
        }
        LogicManager.setLogicState("AddJobDetails");
        return new CommandResult(AddJobDetailsCommand.MESSAGE_USAGE);
    }



}
