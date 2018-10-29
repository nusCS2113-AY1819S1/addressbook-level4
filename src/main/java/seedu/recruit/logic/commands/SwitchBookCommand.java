package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.ui.SwitchBookRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;

/**
 * Switches view between Candidate Book and Company Book
 */
public class SwitchBookCommand extends Command {
    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_SUCCESSFULLY_SWITCHED_TO_CANDIDATE_BOOK =
            "Switched to Candidate Book successfully.";

    public static final String MESSAGE_SUCCESSFULLY_SWITCHED_TO_COMPANY_BOOK =
            "Switched to Company Book successfully.";

    private static String MESSAGE_SUCCESS = "";

    public static void setMessage(String message) {
        MESSAGE_SUCCESS = message;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        EventsCenter.getInstance().post(new SwitchBookRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
