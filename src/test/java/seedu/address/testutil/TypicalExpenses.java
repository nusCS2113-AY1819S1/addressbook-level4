package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ExpenseBook;
import seedu.address.model.expense.Expense;

/**
 * A utility class containing a list of {@code Expense} objects to be used in tests.
 */
public class TypicalExpenses {

    public static final Expense FOOD = new ExpenseBuilder().withExpenseCategory("food")
            .withExpenseDate("30/10/2018").withExpenseValue("5.00")
            .withTags("lunch").build();
    public static final Expense TRANSPORT = new ExpenseBuilder().withExpenseCategory("transport")
            .withExpenseDate("30/11/2018").withExpenseValue("2.00")
            .withTags("grab").build();
    public static final Expense DATE = new ExpenseBuilder().withExpenseCategory("date")
            .withExpenseDate("10/10/2018").withExpenseValue("233.00").build();
    public static final Expense HOTEL = new ExpenseBuilder().withExpenseCategory("hotel")
            .withExpenseDate("10/10/2018").withExpenseValue("111.11").withTags("friends").build();
    public static final Expense TRAVEL = new ExpenseBuilder().withExpenseCategory("travel")
            .withExpenseDate("11/11/2018").withExpenseValue("1500.00").build();
    public static final Expense SCHOOLFEE = new ExpenseBuilder().withExpenseCategory("fee")
            .withExpenseDate("17/08/2018").withExpenseValue("4198.95").build();
    public static final Expense GST = new ExpenseBuilder().withExpenseCategory("others")
            .withExpenseDate("11/09/2018").withExpenseValue("11.11").build();

    private TypicalExpenses() {} // prevents instantiation

    /**
     * Returns an {@code ExpenseBook} with all the typical expenses.
     */
    public static ExpenseBook getTypicalExpenseBook() {
        ExpenseBook eb = new ExpenseBook();
        for (Expense expense : getTypicalExpenses()) {
            eb.addExpense(expense);
        }
        return eb;
    }

    public static List<Expense> getTypicalExpenses() {
        return new ArrayList<>(Arrays.asList(FOOD, TRANSPORT, DATE, HOTEL, TRAVEL, SCHOOLFEE, GST));
    }
}
