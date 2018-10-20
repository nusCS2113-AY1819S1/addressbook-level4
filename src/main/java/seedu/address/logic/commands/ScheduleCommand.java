package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.COMMAND_SCHEDULE;

import seedu.address.commons.util.FileEncryptor;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Activity;
import seedu.address.model.schedule.Schedule;

public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = CliSyntax.COMMAND_SCHEDULE;

    //TODO update this message
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Updates your schedule";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK= "This task already exists in the schedule";

    private final Activity toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public ScheduleCommand(Activity activity) {
        requireNonNull(activity);
        toAdd = activity;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        UserPrefs userPref = new UserPrefs();
        FileEncryptor fe = new FileEncryptor(userPref.getAddressBookFilePath().toString());

        if (fe.isLocked()) {
            throw new CommandException(FileEncryptor.MESSAGE_ADDRESS_BOOK_LOCKED);
        }
        model.addSchedule(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleCommand // instanceof handles nulls
                && toAdd.equals(((ScheduleCommand) other).toAdd));
    }
}
