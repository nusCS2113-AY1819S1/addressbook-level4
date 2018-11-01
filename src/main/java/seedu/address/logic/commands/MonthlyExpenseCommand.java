package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;

import javafx.collections.ObservableList;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DisplayMonthlyExpenseEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.expense.Expense;

/**
 * Display a new window for the expense of the selected month.
 */
public class MonthlyExpenseCommand extends Command {

    public static final String COMMAND_WORD = "monthlyExpense";

    public static final String MESSAGE_SUCCESS = "Display monthly expense";

    public static final String MESSAGE_MONTHLY_EXPENSE_COMMAND_CONSTRAINTS =
            "Argument should be a valid month and in MM/YYYY format!";

    private final String selectedMonth;

    /**
     * Creates an MonthlyExpenseCommand to display the expense for the select month.
     */
    public MonthlyExpenseCommand(String selectedMonth) {
        requireNonNull(selectedMonth);
        this.selectedMonth = selectedMonth;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        requireNonNull(this.selectedMonth);
        ObservableList<Expense> expenseList = model.getFilteredExpenseList();
        HashMap<String, String> monthlyData = getMonthlyData(expenseList);
        EventsCenter.getInstance().post(new DisplayMonthlyExpenseEvent(monthlyData, this.selectedMonth));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    private HashMap<String, String> getMonthlyData(ObservableList<Expense> expenseList) {
        HashMap<String, String> monthlyData = new HashMap<>();

        for (Expense expense : expenseList) {
            if (expense.getExpenseDate().toString().contains(this.selectedMonth)) {
                if (monthlyData.containsKey(expense.getExpenseCategory().toString())) {
                    double storedValue = Double.parseDouble(monthlyData.get(expense.getExpenseCategory().toString()));
                    double expenseValue = Double.parseDouble(expense.getExpenseValue().toString());
                    String newValue = Double.toString(storedValue + expenseValue);
                    monthlyData.put(expense.getExpenseCategory().toString(), newValue);
                } else {
                    monthlyData.put(expense.getExpenseCategory().toString(), expense.getExpenseValue().toString());
                }
            }
        }
        return monthlyData;
    }
}
