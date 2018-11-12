package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelTypes;
import seedu.address.model.addressbook.AddressBook;
import seedu.address.model.expenses.ExpensesList;
import seedu.address.model.recruitment.RecruitmentList;
import seedu.address.model.schedule.ScheduleList;

/**
 * The {@code ClearCommand} class is used for clearing all storage.
 * The entire schedule, addressbook, expenses, recruitment storage will be reset to a clean state.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book, Schedule list, "
            + "expenses list, and recruitment list have been cleared!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all employees, schedules, recruitment posts"
            + " and expenses.";

    /**
     * ClearCommand execution.
     * <p>
     *     Checks if individual storage has data to be cleared, if so, clear it.
     *     Takes note of all storage that are cleared, places the type of storage cleared into a set
     *     and commit it. Important for undo and redo command to work properly.
     * </p>
     * @param model {@code Model} which the command will operate on the model.
     * @param history {@code CommandHistory} which the command history will be added.
     * @return CommandResult, String success feedback to the user.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        Set<ModelTypes> set = new HashSet<>();
        set.add(ModelTypes.ADDRESS_BOOK);
        model.resetAddressBookData(new AddressBook());

        model.updateFilteredExpensesList(model.PREDICATE_SHOW_ALL_EXPENSES);
        if (model.getFilteredExpensesList().size() > 0) {
            model.resetDataExpenses(new ExpensesList());
            set.add(ModelTypes.EXPENSES_LIST);
        }

        model.updateFilteredRecruitmentList(model.PREDICATE_SHOW_ALL_RECRUITMENT);
        if (model.getFilteredRecruitmentList().size() > 0) {
            model.resetRecruitmentListData(new RecruitmentList());
            set.add(ModelTypes.RECRUITMENT_LIST);
        }

        model.updateFilteredScheduleList(model.PREDICATE_SHOW_ALL_SCHEDULES);
        if (model.getFilteredScheduleList().size() > 0) {
            model.resetScheduleListData(new ScheduleList());
            set.add(ModelTypes.SCHEDULES_LIST);
        }

        model.commitMultipleLists(set);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
