package seedu.address.model;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.ThreadDueRemindersCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * This class defines a thread which is responsible for showing due reminders.
 * NOT USED AS OF YET TO FACILITATE REMINDERS - CURRENTLY USING TIMELINE IN COMMANDBOX [THIS IS FOR v2.0]
 */
public class ReminderRunnable implements Runnable {

    private static LogicManager logic;

    public ReminderRunnable(LogicManager logic) {
        setLogicManager(logic);
    }

    /**
     * The following is the execution routine of the thread when it is active.
     */
    private void executeThreadDueReminders() {
        try {
            logic.execute(ThreadDueRemindersCommand.COMMAND_WORD);
        } catch (CommandException e) {
            return;
        } catch (ParseException e) {
            return;
        }
    }

    public void setLogicManager(LogicManager logic) {
        requireNonNull(logic);
        this.logic = logic;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                executeThreadDueReminders();
            }
        }
    }
}
