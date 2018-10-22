package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.COMMAND_SCHEDULE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileEncryptor;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Activity;
import seedu.address.model.schedule.Schedule;

import java.util.List;

public abstract class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = CliSyntax.COMMAND_SCHEDULE;
    public static final String COMMAND_WORD_ADD = "add";
    public static final String COMMAND_WORD_DELETE = "delete";
    public static final String COMMAND_WORD_EDIT = "edit";


    //TODO update this message
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Updates your schedule";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK= "This task already exists in the schedule";
    private final String MESSEGE_INVALID_INDEX = "Index is not valid";

    public abstract CommandResult updateSchedule(Model model) throws CommandException;

    public Activity getActivityFromIndex (Model model, Index index) throws CommandException{
        List<Activity> activities = model.getActivityList();
        if (index.getZeroBased() >= activities.size()) {
            throw new CommandException(MESSEGE_INVALID_INDEX);
        }
        return activities.get(index.getZeroBased());
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        UserPrefs userPref = new UserPrefs();
        FileEncryptor fe = new FileEncryptor(userPref.getAddressBookFilePath().toString());

        if (fe.isLocked()) {
            throw new CommandException(FileEncryptor.MESSAGE_ADDRESS_BOOK_LOCKED);
        }

        CommandResult result = updateSchedule(model);
        model.commitAddressBook();
        return result;
    }
}
