package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.timeidentifiedclass.shopday.Reminder;

/**
 * This command allows us to view all reminders in the active shop day that are due.
 */

public class ViewDueRemindersCommand extends Command {

    public static final String COMMAND_WORD = "duereminders";
    public static final String MESSAGE_USAGE = ": Shows the reminders that are past their times.";
    public static final String NO_REMINDERS_SET = "No reminders due yet.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        ArrayList<Reminder> reminders = model.getDueRemindersInActiveShopDay();

        if (reminders.size() == 0) {
            return new CommandResult(NO_REMINDERS_SET);
        }

        StringBuilder allReminders = new StringBuilder();

        allReminders.append("Reminders due as of now today:\n");

        for (Reminder reminder : reminders) {
            allReminders.append(reminder.getTime() + "\t\t" + reminder.getMessage() + "\n");
        }

        return new CommandResult(allReminders.toString());
    }

}
