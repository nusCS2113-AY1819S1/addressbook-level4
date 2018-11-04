package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.saleshistory.SalesHistory;
import seedu.address.model.timeidentifiedclass.Reminder;

/**
 * This command displays all the reminders in the {@link SalesHistory}.
 */
public class ViewAllRemindersCommand extends Command {

    public static final String COMMAND_WORD = "allreminders";
    public static final String MESSAGE_USAGE = ": Shows all reminders for a given user";
    public static final String NO_REMINDERS_MESSAGE = "No reminders set yet";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        ArrayList<Reminder> reminders = model.getAllReminders();

        if (reminders.size() == 0) {
            return new CommandResult(NO_REMINDERS_MESSAGE);
        }

        StringBuilder allReminders = new StringBuilder();

        allReminders.append("All reminders:\n");

        for (Reminder reminder : reminders) {
            allReminders.append(reminder.getReminderTime() + "\t\t" + reminder.getReminderMessage() + "\n");
        }

        return new CommandResult(allReminders.toString());
    }
}
