package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_YEAR;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SCHEDULES;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.EmployeeIdScheduleContainsKeywordsPredicate;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Type;
import seedu.address.model.schedule.Year;

/**
 * The {@code CalculateLeavesCommand} class is used for calculating
 * total number of leaves scheduled by an employee given a specified year.
 *
 * @see seedu.address.logic.parser.CalculateLeavesCommandParser class for the parser.
 */
public class CalculateLeavesCommand extends Command {

    public static final String COMMAND_WORD = "calculateLeaves";
    public static final String COMMAND_ALIAS = "cl";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Calculate total leaves schedule for the year "
            + "by specifying the Employee number and year. "
            + "\nParameters: "
            + PREFIX_EMPLOYEEID + "EMPLOYEEID "
            + PREFIX_SCHEDULE_YEAR + "YYYY"
            + "\nExample: "
            + COMMAND_WORD + " "
            + PREFIX_EMPLOYEEID + "000001 "
            + PREFIX_SCHEDULE_YEAR + "2019";

    public static final String MESSAGE_SUCCESS = "Number of leaves scheduled for Employee %1$s year %2$s is: %3$s.";

    public static final String MESSAGE_NO_SCHEDULE_FOUND = "No leaves found for the employee in that year!";
    public static final String MESSAGE_EMPLOYEE_ID_NOT_FOUND = "Employee Id not found in system!";

    private final Year year;
    private final EmployeeId employeeId;
    private Person toCheckEmployeeId;

    /**
     * CalculateLeavesCommand
     * @param id    Employee id
     * @param year  Year to calculate leaves taken by the employee
     */
    public CalculateLeavesCommand(EmployeeId id, Year year) {
        requireAllNonNull(id);
        requireAllNonNull(year);
        this.employeeId = id;
        this.year = year;
        toCheckEmployeeId = new Person(id);
    }

    /**
     * CalculateLeavesCommand execution.
     * <p>
     *     Calculates total number of leaves scheduled for an employee
     *     for the entire specified year in the schedule list.
     * </p>
     * @param model {@code Model} which the command will operate on the model.
     * @param history {@code CommandHistory} which the command history will be added.
     * @return CommandResult, String success feedback to the user.
     * @throws CommandException  String failure feedback to the user if error in execution.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        int numLeaves = 0;

        if (!model.hasEmployeeId(toCheckEmployeeId)) {
            throw new CommandException(MESSAGE_EMPLOYEE_ID_NOT_FOUND);
        }

        List<String> employeeIdList = new ArrayList<>();
        employeeIdList.add(employeeId.value);
        EmployeeIdScheduleContainsKeywordsPredicate employeeIdPredicate =
                new EmployeeIdScheduleContainsKeywordsPredicate(employeeIdList);
        model.updateFilteredScheduleList(employeeIdPredicate);

        if (model.getFilteredScheduleList().size() == 0) {
            throw new CommandException(MESSAGE_NO_SCHEDULE_FOUND);
        }

        for (Schedule schedule : model.getFilteredScheduleList()) {
            if (schedule.getScheduleYear().equals(year.toString())
                    && schedule.getType().toString().equals(Type.LEAVE)) {
                numLeaves++;
            }
        }

        model.updateFilteredExpensesList(PREDICATE_SHOW_ALL_EXPENSES);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);

        return new CommandResult(String.format(MESSAGE_SUCCESS, employeeId, year, numLeaves));
    }

    /**
     * Compares if both objects are equal.
     * @param other similar object type to be compared with.
     * @return Boolean, True if both objects are equal based on the defined conditions.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CalculateLeavesCommand // instanceof handles nulls
                && year.equals(((CalculateLeavesCommand) other).year)
                && employeeId.equals(((CalculateLeavesCommand) other).employeeId));
    }
}

