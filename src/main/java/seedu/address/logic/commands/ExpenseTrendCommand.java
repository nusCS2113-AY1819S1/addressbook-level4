package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.YearMonth;
import java.util.TreeMap;

import javafx.collections.ObservableList;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DisplayExpenseTrendEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.expense.Expense;

/**
 * Display a new window for the trend of expense for the past 6 months
 */
public class ExpenseTrendCommand extends Command {

    public static final String COMMAND_WORD = "expenseTrend";

    public static final String MESSAGE_SUCCESS = "Display expense trend for past 6 months";

    public static final double INITIAL_EXPENSE_VALUE = 0.0;

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        ObservableList<Expense> expenseList = model.getFilteredExpenseList();
        TreeMap<String, Double> expenseTrendData = getExpenseTrendData(expenseList);
        EventsCenter.getInstance().post(new DisplayExpenseTrendEvent(expenseTrendData));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    private TreeMap<String, Double> getExpenseTrendData(ObservableList<Expense> expenseList) {
        YearMonth currentMonth = YearMonth.now();
        TreeMap<String, Double> expenseTrendData = new TreeMap<>();
        for (int i = 0; i < 6; i++) {
            YearMonth selectedMonth = currentMonth.minusMonths(i);
            expenseTrendData.put(selectedMonth.toString(), INITIAL_EXPENSE_VALUE);
            String formattedMonth =
                    selectedMonth.toString().substring(5) + "/" + selectedMonth.toString().substring(0, 4);
            for (Expense expense : expenseList) {
                if (expense.getExpenseDate().toString().contains(formattedMonth)) {
                    double storedValue = expenseTrendData.get(selectedMonth.toString());
                    double expenseValue = Double.parseDouble(expense.getExpenseValue().toString());
                    expenseTrendData.put(selectedMonth.toString(), storedValue + expenseValue);
                }
            }
        }
        return expenseTrendData;
    }

}
