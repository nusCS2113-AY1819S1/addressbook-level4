package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.expenses.EmployeeIdExpensesContainsKeywordsPredicate;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PositionContainsKeywordsPredicate;
import seedu.address.model.schedule.EmployeeIdScheduleContainsKeywordsPredicate;

/**
 * Filters and lists all persons in address book whose department and/or position contains any of the argument keywords.
 * Keyword matching is case insensitive.
 * The list is sorted either in ascending or descending name order based on the user's input
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all persons whose department or position "
            + "contain any of the specified keywords (case-insensitive) and displays them as a sorted list in either "
            + "ascending or descending order with index numbers.\nParameters: ORDER " + PREFIX_DEPARTMENT
            + "DEPARTMENT AND/OR " + PREFIX_POSITION + "POSITION\n"
            + "Example: " + COMMAND_WORD + " dsc " + PREFIX_DEPARTMENT + "Human Resource" + " "
            + PREFIX_POSITION + "Intern";

    public static final String ASCENDING = "asc";
    public static final String DESCENDING = "dsc";

    private final String sortOrder;
    private DepartmentContainsKeywordsPredicate departmentPredicate;
    private PositionContainsKeywordsPredicate positionPredicate;

    private boolean isDepartmentPrefixPresent;
    private boolean isPositionPrefixPresent;

    /**
     * Creates a FilterCommand to filter out the employees based on the specified {@code departmentPredicate} or
     * {@code positionPredicate} or both and list them out in ascending or descending name order based on
     * {@code sortOrder}.
     * @param departmentPredicate The predicate that holds the matched department(s)
     * @param positionPredicate The predicate that holds the matched position(s)
     * @param sortOrder The sortOrder indicated by the user (either ascending or descending)
     */
    public FilterCommand(DepartmentContainsKeywordsPredicate departmentPredicate,
                         PositionContainsKeywordsPredicate positionPredicate, String sortOrder) {
        this.departmentPredicate = departmentPredicate;
        this.positionPredicate = positionPredicate;
        this.sortOrder = sortOrder;
    }

    public void setIsDepartmentPrefixPresent(boolean isDepartmentPrefixPresent) {
        this.isDepartmentPrefixPresent = isDepartmentPrefixPresent;
    }

    public void setIsPositionPrefixPresent(boolean isPositionPrefixPresent) {
        this.isPositionPrefixPresent = isPositionPrefixPresent;
    }

    public void setDepartmentPredicate(DepartmentContainsKeywordsPredicate departmentPredicate) {
        this.departmentPredicate = departmentPredicate;
    }

    public void setPositionPredicate(PositionContainsKeywordsPredicate positionPredicate) {
        this.positionPredicate = positionPredicate;
    }

    /**
     * Execution of the command will be carried out after the checks for the presence of department and position
     * prefixes are completed. The command will filter the person, schedule and expenses list to only show employees of
     * the input department(s) and/or position(s).
     * @param model The actual model
     * @param history The actual history
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        String allAvailableDepartments = listAvailableDepartments(model);
        String allAvailablePositions = listAvailablePositions(model);

        if (isDepartmentPrefixPresent && !isPositionPrefixPresent) {
            model.updateFilteredPersonList(departmentPredicate, sortOrder);
        } else if (isPositionPrefixPresent && !isDepartmentPrefixPresent) {
            model.updateFilteredPersonList(positionPredicate, sortOrder);
        } else if (isDepartmentPrefixPresent && isPositionPrefixPresent) {
            model.updateFilteredPersonList(departmentPredicate.and(positionPredicate), sortOrder);
        }

        EmployeeIdExpensesContainsKeywordsPredicate expensesPredicate = generateEmployeeIdExpensesPredicate(model);
        EmployeeIdScheduleContainsKeywordsPredicate schedulePredicate = generateEmployeeIdSchedulePredicate(model);
        model.updateFilteredExpensesList(expensesPredicate);
        model.updateFilteredScheduleList(schedulePredicate);

        return new CommandResult(feedbackToUser(model, allAvailableDepartments, allAvailablePositions));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && (departmentPredicate.and(positionPredicate).equals(((FilterCommand) other)
                .departmentPredicate.and(positionPredicate))
                || (departmentPredicate.equals(((FilterCommand) other).departmentPredicate))
                || (positionPredicate.equals(((FilterCommand) other).positionPredicate)))); // state check
    }

    /**
     * Creates and returns a {@code String} that holds all the currently available departments to filter by
     * in the Address Book.
     * @param model The actual model
     */
    public String listAvailableDepartments(Model model) {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        List<Person> getFullList = model.getFilteredPersonList();
        Set<String> allDepartments = new HashSet<>();

        for (Person department : getFullList) {
            allDepartments.add(department.getDepartment().toString().toUpperCase());
        }

        String availableDepartments = String.join(", ", allDepartments);

        return "\nAvailable Departments: " + availableDepartments;
    }

    /**
     * Creates and returns a {@code String} that holds all the currently available positions to filter by
     * in the Address Book.
     * @param model The actual model
     */
    public String listAvailablePositions(Model model) {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        List<Person> getFullList = model.getFilteredPersonList();
        Set<String> allPositions = new HashSet<>();

        for (Person position : getFullList) {
            allPositions.add(position.getPosition().toString().toUpperCase());
        }

        String availablePositions = String.join(", ", allPositions);

        return "\nAvailable Positions: " + availablePositions;
    }

    /**
     * Creates and returns a {@code EmployeeIdExpensesContainsKeywordsPredicate} that contains all the employee ID(s)
     * that matches the matched persons' employee ID.
     * @param model The actual model
     */
    public EmployeeIdExpensesContainsKeywordsPredicate generateEmployeeIdExpensesPredicate(Model model) {
        List<Person> getFilteredList = model.getFilteredPersonList();
        List<String> matchedEmployeeIds = new ArrayList<>();

        for (Person person : getFilteredList) {
            matchedEmployeeIds.add(person.getEmployeeId().value);
        }

        return new EmployeeIdExpensesContainsKeywordsPredicate(matchedEmployeeIds);
    }

    /**
     * Creates and returns a {@code EmployeeIdScheduleContainsKeywordsPredicate} that contains all the employee ID(s)
     * that matches the matched persons' employee ID.
     * @param model The actual model
     */
    public EmployeeIdScheduleContainsKeywordsPredicate generateEmployeeIdSchedulePredicate(Model model) {
        List<Person> getFilteredList = model.getFilteredPersonList();
        List<String> matchedEmployeeIds = new ArrayList<>();

        for (Person person : getFilteredList) {
            matchedEmployeeIds.add(person.getEmployeeId().value);
        }

        return new EmployeeIdScheduleContainsKeywordsPredicate(matchedEmployeeIds);
    }

    /**
     * Creates and return a {@code String} that contains the feedback to be printed to the user on the CLI.
     * @param model The actual model
     * @param allAvailableDepartments The available department(s) in the address book to filter by
     * @param allAvailablePositions The available position(s) in the address book to filter by
     */
    public String feedbackToUser(Model model, String allAvailableDepartments, String allAvailablePositions) {
        String toBeConcatenated = "";

        if (model.getFilteredPersonList().isEmpty() && isDepartmentPrefixPresent && !isPositionPrefixPresent) {
            toBeConcatenated = allAvailableDepartments;
        } else if (model.getFilteredPersonList().isEmpty() && !isDepartmentPrefixPresent && isPositionPrefixPresent) {
            toBeConcatenated = allAvailablePositions;
        } else if (model.getFilteredPersonList().isEmpty() && isPositionPrefixPresent && isPositionPrefixPresent) {
            toBeConcatenated = allAvailableDepartments + allAvailablePositions;
        }

        return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size())
                + toBeConcatenated;
    }
}
