package seedu.address.testutil.expenses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.ExpensesList;

/**
 * A utility class containing a list of {@code expenses} objects to be used in tests.
 */
public class TypicalExpenses {

    public static final String VALID_EMPLOYEEID_ALICE = "000001";
    public static final String VALID_EXPENSES_AMOUNT_ALICE = "369.00";
    public static final String VALID_TRAVEL_EXPENSES_ALICE = "123.00";
    public static final String VALID_MEDICAL_EXPENSES_ALICE = "123.00";
    public static final String VALID_MISCELLANEOUS_EXPENSES_ALICE = "123.00";

    public static final Expenses ALICE_CLAIM = new ExpensesBuilder().withEmployeeId("000001")
            .withExpensesAmount("123.00", "123.00", "123.00")
            .withTravelExpenses("123.00").withMedicalExpenses("123.00").withMiscellaneousExpenses("123.00").build();
    public static final Expenses BENSON_CLAIM = new ExpensesBuilder().withEmployeeId("000002")
            .withExpensesAmount("123", "123", "123")
            .withTravelExpenses("123").withMedicalExpenses("123").withMiscellaneousExpenses("123").build();
    public static final Expenses CARL_CLAIM = new ExpensesBuilder().withEmployeeId("000003")
            .withExpensesAmount("123", "123", "123")
            .withTravelExpenses("123").withMedicalExpenses("123").withMiscellaneousExpenses("123").build();
    public static final Expenses DANIEL_CLAIM = new ExpensesBuilder().withEmployeeId("000004")
            .withExpensesAmount("123", "123", "123")
            .withTravelExpenses("123").withMedicalExpenses("123").withMiscellaneousExpenses("123").build();
    public static final Expenses ELLE_CLAIM = new ExpensesBuilder().withEmployeeId("000005")
            .withExpensesAmount("123", "123", "123")
            .withTravelExpenses("123").withMedicalExpenses("123").withMiscellaneousExpenses("123").build();
    public static final Expenses FIONA_CLAIM = new ExpensesBuilder().withEmployeeId("000006")
            .withExpensesAmount("123", "123", "123")
            .withTravelExpenses("123").withMedicalExpenses("123").withMiscellaneousExpenses("123").build();
    public static final Expenses GEORGE_CLAIM = new ExpensesBuilder().withEmployeeId("000007")
            .withExpensesAmount("123", "123", "123")
            .withTravelExpenses("123").withMedicalExpenses("123").withMiscellaneousExpenses("123").build();

    // Manually added - Expenses's details found in {@code CommandTestUtil}
    public static final Expenses BENSON_CLAIM_COPY = new ExpensesBuilder().withEmployeeId("000002")
            .withExpensesAmount("123", "123", "123")
            .withTravelExpenses("123").withMedicalExpenses("123").withMiscellaneousExpenses("123").build();
    public static final Expenses AMY_CLAIM = new ExpensesBuilder().withEmployeeId("000010")
            .withExpensesAmount("234", "345", "456")
            .withTravelExpenses("234").withMedicalExpenses("345").withMiscellaneousExpenses("456").build();
    public static final Expenses BOB_CLAIM = new ExpensesBuilder().withEmployeeId("000011")
            .withExpensesAmount("345", "456", "567")
            .withTravelExpenses("345").withMedicalExpenses("456").withMiscellaneousExpenses("567").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalExpenses() {} // prevents instantiation
    /**
     * Returns an {@code ExpensesList} with all the typical expenses.
     */
    public static ExpensesList getTypicalExpensesList() {
        ExpensesList el = new ExpensesList();
        for (Expenses expenses : getTypicalExpenses()) {
            el.addExpenses(expenses);
        }
        return el;
    }

    public static List<Expenses> getTypicalExpenses() {
        return new ArrayList<>(Arrays.asList(ALICE_CLAIM, BENSON_CLAIM, CARL_CLAIM, DANIEL_CLAIM,
                ELLE_CLAIM, FIONA_CLAIM, GEORGE_CLAIM));
    }

}
