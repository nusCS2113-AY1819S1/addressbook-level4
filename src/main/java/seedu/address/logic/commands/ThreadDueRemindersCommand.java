package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.timeidentifiedclass.shopday.Reminder;

/**
 * This command allows us to view all reminders in the active shop day that are due.
 */

public class ThreadDueRemindersCommand extends Command {

    public static final String COMMAND_WORD = "threadduereminders";
    public static final String MESSAGE_USAGE = ": Shows the reminders that are past their times.";
    public static final String NO_THREAD_REMINDERS = "No threaded reminders due yet.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        ArrayList<Reminder> reminders = model.getDueRemindersInActiveBusinessDayForThread();

        if (reminders.size() == 0) {
            throw new CommandException(NO_THREAD_REMINDERS);
        }

        StringBuilder allReminders = new StringBuilder();

        allReminders.append("Reminders due:\n");

        for (Reminder reminder : reminders) {
            allReminders.append(reminder.getTime() + "\t\t" + reminder.getMessage() + "\n");
        }

        return new CommandResult(allReminders.toString());
    }
}
