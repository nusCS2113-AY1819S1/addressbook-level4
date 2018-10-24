package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_CATEGORY_HII;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_CATEGORY_YOO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_DATE_HII;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_DATE_YOO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_VALUE_HII;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_VALUE_YOO;

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
            .withExpenseDate("12/30/2018").withExpenseValue("1.00")
            .withTags("lunch").build();
    public static final Expense TRANSPORT = new ExpenseBuilder().withExpenseCategory("transport")
            .withExpenseDate("12/30/2018").withExpenseValue("11.00")
            .withTags("grab").build();
    public static final Expense DATE = new ExpenseBuilder().withExpenseCategory("date")
            .withExpenseDate("12/30/2018").withExpenseValue("11.10").build();
    public static final Expense HOTEL = new ExpenseBuilder().withExpenseCategory("hotel")
            .withExpenseDate("12/30/2018").withExpenseValue("11.11").withTags("friends").build();
    public static final Expense TRAVEL = new ExpenseBuilder().withExpenseCategory("travel")
            .withExpenseDate("12/30/2018").withExpenseValue("11.10").build();
    public static final Expense SCHOOLFEE = new ExpenseBuilder().withExpenseCategory("date")
            .withExpenseDate("12/30/2018").withExpenseValue("11.10").build();
    public static final Expense GST = new ExpenseBuilder().withExpenseCategory("date")
            .withExpenseDate("12/30/2018").withExpenseValue("11.10").build();

    // Manually added
    public static final Expense JD = new ExpenseBuilder().withExpenseCategory("jd")
            .withExpenseDate("11/11/2018").withExpenseValue("11.11").build();
    public static final Expense TAOBAO = new ExpenseBuilder().withExpenseCategory("taobao")
            .withExpenseDate("11/11/2018").withExpenseValue("11.11").build();

    // Manually added - Expense's details found in {@code CommandTestUtil}
    public static final Expense YOO = new ExpenseBuilder().withExpenseCategory(VALID_EXPENSE_CATEGORY_YOO)
            .withExpenseDate(VALID_EXPENSE_DATE_YOO).withExpenseValue(VALID_EXPENSE_VALUE_YOO).build();
    public static final Expense HII = new ExpenseBuilder().withExpenseCategory(VALID_EXPENSE_CATEGORY_HII)
            .withExpenseDate(VALID_EXPENSE_DATE_HII).withExpenseValue(VALID_EXPENSE_VALUE_HII).build();

    public static final String KEYWORD_MATCHING_FOOD = "food"; // A keyword that matches FOOD

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
