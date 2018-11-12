package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_DATE;

import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Date;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Type;


/**
 * The {@code DeleteLeavesCommand} class is used for deleting multiple employees with leave schedules.
 * All the observable employees on the employees list panel in the user interface will be deleted
 * leaves based on the dates specified by the user.
 *
 * @see seedu.address.logic.parser.DeleteLeavesCommandParser class for the parser.
 */
public class DeleteLeavesCommand extends Command {

    public static final String COMMAND_WORD = "deleteLeaves";
    public static final String COMMAND_ALIAS = "dl";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes leave schedules for all observable employees "
            + "in the list by specifying the date of leave to delete. "
            + "\nParameters: "
            + PREFIX_SCHEDULE_DATE + "[DD/MM/YYYY]  .... *You can specify more than 1 date prefix to schedule*"
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_SCHEDULE_DATE + "02/02/2019";

    public static final String MESSAGE_SUCCESS = "Leaves deleted for all observable employees that contain date : %1$s";

    public static final String MESSAGE_NO_PERSON_FOUND = "No observable employees found in list! "
            + "Try to list/find/filter the employees you want to delete leaves for";

    public static final String MESSAGE_PERSON_ALL_DELETED_LEAVE = "Every observable employees in the list"
            + " does not have leave on %1$s !";

    private final Set<Date> setOfDates;

    /**
     * DeleteLeavesCommand
     * @param date Set of dates containing the date of leaves to delete.
     */
    public DeleteLeavesCommand(Set<Date> date) {
        requireAllNonNull(date);
        this.setOfDates = date;
    }

    /**
     * DeleteLeavesCommand execution.
     * <p>
     *     Each date specified by the user will be checked with every observable employee for the possibility
     *     of deleting leave. Leave schedule will be deleted if found on that date.
     * </p>
     * @param model {@code Model} which the command will operate on the model.
     * @param history {@code CommandHistory} which the command history will be added.
     * @return CommandResult, String success feedback to the user.
     * @throws CommandException  String failure feedback to the user if error in execution.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Type leave = new Type(Type.LEAVE);
        boolean commit = false;

        if (model.getFilteredPersonList().size() == 0) {
            throw new CommandException(MESSAGE_NO_PERSON_FOUND);
        }
        for (Date date : setOfDates) {
            for (Person person : model.getFilteredPersonList()) {
                Schedule toDeleteSchedule = new Schedule(person.getEmployeeId(), leave , date);
                if (model.hasSchedule(toDeleteSchedule)) {
                    model.deleteSchedule(toDeleteSchedule);
                    commit = true;
                }
            }
        }

        if (!commit) {
            throw new CommandException(String.format(MESSAGE_PERSON_ALL_DELETED_LEAVE, setOfDates));
        }

        model.commitScheduleList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, setOfDates));
    }

    /**
     * Compares if both objects are equal.
     * @param other similar object type to be compared with.
     * @return Boolean, True if both objects are equal based on the defined conditions.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteLeavesCommand // instanceof handles nulls
                && setOfDates.equals(((DeleteLeavesCommand) other).setOfDates));
    }
}

