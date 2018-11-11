package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGENDA;


import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminder.Reminder;

//@@author junweiljw
/**
 * Adds reminder for next meeting
 */
public class ReminderCommand extends Command {
    public static final String COMMAND_WORD = "reminder";
    public static final String COMMAND_ALIAS = "rm";


    public static final String COMMAND_PARAMETERS = "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_DATE + "DATE(DDMMMYYYY) "
            + PREFIX_START_TIME + "TIME(24HRS) "
            + PREFIX_AGENDA + "AGENDA \n";

    public static final String COMMAND_EXAMPLE = "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "CS2113T Meeting "
            + PREFIX_DATE + "08122018 "
            + PREFIX_START_TIME + "1300 "
            + PREFIX_AGENDA + "Discuss Product Demo \n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a meeting reminder on the sidebar. \n"
            + COMMAND_PARAMETERS
            + COMMAND_EXAMPLE;

    public static final String MESSAGE_SUCCESS = "New reminder added: %1$s";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This reminder already exists!";
    public static final String MESSAGE_SAME_TIME = "A reminder has already been scheduled for a meeting at this timing!";

    private final Reminder reminder;

    /**
     * Creates a ReminderCommand to add the specified {@code Reminder}
     */
    public ReminderCommand(Reminder reminderMeeting) {
        requireNonNull(reminderMeeting);
        reminder = reminderMeeting;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasReminder(reminder)) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        }

        model.addReminder(reminder);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, reminder));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderCommand // instanceof handles nulls
                && reminder.equals(((ReminderCommand) other).reminder));
    }
}
