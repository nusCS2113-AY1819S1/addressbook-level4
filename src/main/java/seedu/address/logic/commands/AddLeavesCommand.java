package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_DATE;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Date;
import seedu.address.model.schedule.DateComparator;
import seedu.address.model.schedule.EmployeeIdComparator;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Type;


/**
 * The {@code AddLeavesCommand} class is used for scheduling multiple employees with leave schedules.
 * All the observable employees on the employees list panel before or after find/filter/list
 * will be scheduled leaves based on the set of dates specified by the user.
 *
 * @see seedu.address.logic.parser.AddLeavesCommandParser class for the parser.
 */
public class AddLeavesCommand extends Command {

    public static final String COMMAND_WORD = "addLeaves";
    public static final String COMMAND_ALIAS = "al";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": schedule leave for all observable employees "
            + "in the list by specifying the date of leave to take. "
            + "\nParameters: "
            + PREFIX_SCHEDULE_DATE + "[DD/MM/YYYY]  .... *You can specify more than 1 date prefix to schedule*"
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_SCHEDULE_DATE + "02/02/2019";

    public static final String MESSAGE_SUCCESS_SOME_ADDED = "New leave schedules added for SOME of"
            + " the observable employees whom are not yet added date: %1$s";

    public static final String MESSAGE_SUCCESS_ALL_ADDED = "New leave schedules added for ALL of"
            + " the observable employees wom are not yet added with date: %1$s";

    public static final String MESSAGE_NO_PERSON_OBSERVED =
            "No observable employees found in list! "
                    + "\nTry using %1$s/%2$s/%3$s commands get the employees you want to schedule work for.";

    public static final String MESSAGE_PERSON_ALL_ADDED_LEAVE = "Every observable employees in the list has "
            + "already been added with leave on %1$s !";

    public static final String MESSAGE_PERSON_ALL_ADDED_LEAVE_NOT_ON_WORK = "Every observable employees "
            + "whom are not on work in the list has already been added with leave on %1$s !";

    public static final String MESSAGE_PERSON_ALL_HAS_WORK_SAME_DATE = "Unable to schedule work for the following "
            + "employees below whom are on work:";

    public static final String MESSAGE_EMPLOYEE_ON_WORK = "Employee Id: %1$s Has work on: %2$s";

    private final Set<Date> setOfDates = new HashSet<>();

    /**
     * AddLeavesCommand
     * @param date Set of dates containing the date of leaves to schedule.
     */
    public AddLeavesCommand(Set<Date> date) {
        requireAllNonNull(date);
        this.setOfDates.addAll(date);
    }

    /**
     * AddLeavesCommand execution.
     * <p>
     *     Each date specified by the user will be checked with every observable employee for the possibility
     *     of scheduling leave. No schedules will be created if existing work or leave is found on that date.
     * </p>
     * @param model {@code Model} which the command will operate on the model.
     * @param history {@code CommandHistory} which the command history will be added.
     * @return CommandResult, String success feedback to the user.
     * @throws CommandException  String failure feedback to the user if error in execution.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Type leave = new Type(Type.LEAVE);
        Type work = new Type(Type.WORK);
        Multimap<EmployeeId, Date> employeeIdMapToWorks = TreeMultimap.create(
                new EmployeeIdComparator(), new DateComparator());
        boolean commit = false;

        //model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        if (model.getFilteredPersonList().size() == 0) {
            throw new CommandException(String.format(MESSAGE_NO_PERSON_OBSERVED,
                    FindCommand.COMMAND_WORD, ListCommand.COMMAND_WORD, FilterCommand.COMMAND_WORD));
        }

        for (Date date : setOfDates) {
            for (Person person : model.getFilteredPersonList()) {
                Schedule toWorkSchedule = new Schedule(person.getEmployeeId(), work, date);
                Schedule toLeaveSchedule = new Schedule(person.getEmployeeId(), leave, date);

                if (model.hasSchedule(toWorkSchedule)) {
                    employeeIdMapToWorks.put(person.getEmployeeId(), date);

                } else if (!model.hasSchedule(toLeaveSchedule)) {
                    model.addSchedule(toLeaveSchedule);
                    commit = true;
                }
            }
        }

        String textFeedbackToUser = getUserInteractionFeedback(employeeIdMapToWorks, commit, setOfDates);
        if (!commit) {
            throw new CommandException(String.format(textFeedbackToUser));
        }

        model.commitScheduleList();
        return new CommandResult(String.format(textFeedbackToUser));
    }

    /**
     * Compares if both objects are equal.
     * @param other similar object type to be compared with.
     * @return Boolean, True if both objects are equal based on the defined conditions.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddLeavesCommand // instanceof handles nulls
                && setOfDates.equals(((AddLeavesCommand) other).setOfDates));
    }

    /**
     * User interaction feedback generator.
     * <p>
     *     There are 4 possibilities.
     *     1) No leave schedule is committed because everyone has already been scheduled leave and
     *     no employees found with work.
     *
     *     2) No leave schedule is committed either because the employees have been scheduled leave or
     *     has work on that day.
     *
     *     3) Some leave schedules is committed because the employees are not yet scheduled leave, or
     *      not committed because the employee has work on that day.
     *
     *     4) Leave schedule is committed for all employees.
     * </p>
     * @param employeeIdMapToWorks EmployeeId mapped to date of working schedules
     * @param commit Boolean whether has a schedule been committed or not.
     * @param setOfDates Set of dates to schedule
     * @return String to feedback to user.
     */
    public static String getUserInteractionFeedback (Multimap<EmployeeId, Date> employeeIdMapToWorks, Boolean commit,
                                               Set<Date> setOfDates) {
        String noneCommitted;
        StringBuilder someCommitted;
        String allCommitted;
        String textFeedbackToUser;

        //No schedule committed and no employees found with work
        if ((!commit) && (employeeIdMapToWorks.isEmpty())) {
            noneCommitted = String.format(MESSAGE_PERSON_ALL_ADDED_LEAVE, setOfDates);
            textFeedbackToUser = noneCommitted;

        //Not Committed and some employees found with work
        } else if ((!commit) && (!employeeIdMapToWorks.isEmpty())) {
            someCommitted = new StringBuilder();
            someCommitted.append(String.format(MESSAGE_PERSON_ALL_ADDED_LEAVE_NOT_ON_WORK, setOfDates) + "\n");
            someCommitted.append(MESSAGE_PERSON_ALL_HAS_WORK_SAME_DATE);
            someCommitted.append("\n");
            for (EmployeeId employeeId : employeeIdMapToWorks.keySet()) {
                someCommitted.append(
                        String.format(MESSAGE_EMPLOYEE_ON_WORK, employeeId, employeeIdMapToWorks.get(employeeId)));
                someCommitted.append("\n");
            }
            textFeedbackToUser = someCommitted.toString();

        //Committed and some employees found with work
        } else if ((commit) && (!employeeIdMapToWorks.isEmpty())) {
            someCommitted = new StringBuilder(String.format(MESSAGE_SUCCESS_SOME_ADDED, setOfDates) + "\n");
            someCommitted.append(MESSAGE_PERSON_ALL_HAS_WORK_SAME_DATE);
            someCommitted.append("\n");
            for (EmployeeId employeeId : employeeIdMapToWorks.keySet()) {
                someCommitted.append(
                        String.format(MESSAGE_EMPLOYEE_ON_WORK, employeeId, employeeIdMapToWorks.get(employeeId)));
                someCommitted.append("\n");
            }
            textFeedbackToUser = someCommitted.toString();

        } else {
            allCommitted = String.format(MESSAGE_SUCCESS_ALL_ADDED, setOfDates);
            textFeedbackToUser = allCommitted;

        }

        return textFeedbackToUser;
    }
}

