package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.timeidentifiedclass.shopday.Reminder;

import java.util.ArrayList;

public class ViewDueRemindersCommand extends Command {

    public static final String COMMAND_WORD = "duereminders";
    public static final String MESSAGE_USAGE = ": Shows the reminders that are past their times.";
    public static final String NO_REMINDERS_SET = "No reminders set today.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        ArrayList<Reminder> reminders = model.getDueRemindersInActiveShopDay();

        if (reminders.size() == 0) {
            return new CommandResult(NO_REMINDERS_SET);
        }

        StringBuilder allReminders = new StringBuilder();

        allReminders.append("Reminders for today:\n");

        for (Reminder reminder : reminders) {
            allReminders.append(reminder.getTime() + "\t\t" + reminder.getMessage() + "\n");
        }

        return new CommandResult(allReminders.toString());
    }

}
