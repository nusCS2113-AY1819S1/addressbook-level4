package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SCHEDULES;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelTypes;
import seedu.address.model.expenses.EmployeeIdExpensesContainsKeywordsPredicate;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.EmployeeIdScheduleContainsKeywordsPredicate;
import seedu.address.model.schedule.Schedule;

/**
 * The {@code DeleteCommand} class is used for deleting a person identified using it's
 * displayed index from the employee observable panel list.
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * DeleteCommand execution.
     * <p>
     *     Checks if schedule storage has schedule/expenses data containing the same employee id
     *     as the person to delete, if so, clear it.
     *     {@code Set<ModelTypes> set} takes note of the set of storage involved in clearing.
     *     Important for undo and redo command to work properly.
     * </p>
     * @param model {@code Model} which the command will operate on the model.
     * @param history {@code CommandHistory} which the command history will be added.
     * @return CommandResult, String success feedback to the user.
     * @throws CommandException  String failure feedback to the user if error in execution.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());

        Set<ModelTypes> set = new HashSet<>();
        set.add(ModelTypes.ADDRESS_BOOK);
        model.deletePerson(personToDelete);

        if (deleteAllSchedulesFromPerson(model, personToDelete)) {
            set.add(ModelTypes.SCHEDULES_LIST);
        }

        if (deleteAllExpensesFromPerson (model, personToDelete)) {
            set.add(ModelTypes.EXPENSES_LIST);
        }

        model.commitMultipleLists(set);

        model.updateFilteredExpensesList(PREDICATE_SHOW_ALL_EXPENSES);
        model.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    /**
     * Compares if both objects are equal.
     * @param other similar object type to be compared with.
     * @return Boolean, True if both objects are equal based on the defined conditions.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }

    /**
     * Deletes all expenses related to person
     * <p>
     *     Runs a O(n) loop on the expenses list to delete all expenses containing
     *     the employee id of the person to delete.
     * </p>
     * @param model which the command will operate on the model.
     * @param personToDelete Person to delete from the address book
     * @return True if at least 1 expenses is deleted
     */
    public boolean deleteAllExpensesFromPerson (Model model, Person personToDelete) {
        EmployeeIdExpensesContainsKeywordsPredicate predicatEmployeeId;
        List<String> employeeIdList = new ArrayList<>();
        List<Expenses> lastShownListExpenses;

        employeeIdList.add(personToDelete.getEmployeeId().value);
        predicatEmployeeId = new EmployeeIdExpensesContainsKeywordsPredicate(employeeIdList);
        model.updateFilteredExpensesList(predicatEmployeeId);
        lastShownListExpenses = model.getFilteredExpensesList();
        if (lastShownListExpenses.size() == 0) {
            return false;
        }

        while (lastShownListExpenses.size() != 0) {
            Expenses expenseToDelete = lastShownListExpenses.get(0);
            model.deleteExpenses(expenseToDelete);
        }
        return true;
    }

    /**
     * Deletes all schedules related to person
     * <p>
     *     Runs a O(n) loop on the schedule list to delete all schedules containing
     *     the employee id of the person to delete
     * </p>
     * @param model which the command will operate on the model.
     * @param personToDelete Person to delete from the address book
     * @return True if at least 1 expenses is deleted
     */
    public boolean deleteAllSchedulesFromPerson (Model model, Person personToDelete) {
        EmployeeIdScheduleContainsKeywordsPredicate predicatEmployeeId;
        List<String> employeeIdList = new ArrayList<>();
        List<Schedule> lastShownListSchedule;

        employeeIdList.add(personToDelete.getEmployeeId().value);
        predicatEmployeeId = new EmployeeIdScheduleContainsKeywordsPredicate(employeeIdList);
        model.updateFilteredScheduleList(predicatEmployeeId);
        lastShownListSchedule = model.getFilteredScheduleList();
        if (lastShownListSchedule.size() == 0) {
            return false;
        }

        while (lastShownListSchedule.size() != 0) {
            Schedule scheduleToDelete = lastShownListSchedule.get(0);
            model.deleteSchedule(scheduleToDelete);
        }
        return true;
    }
}
