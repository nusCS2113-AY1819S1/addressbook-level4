package seedu.address.model.expenses;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.expenses.TypicalExpenses.ALICE_CLAIM;
import static seedu.address.testutil.expenses.TypicalExpenses.BENSON_CLAIM;
import static seedu.address.testutil.expenses.TypicalExpenses.getTypicalExpensesList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.expenses.exceptions.DuplicateExpensesException;
import seedu.address.testutil.expenses.ExpensesBuilder;


public class ExpensesListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ExpensesList expensesList = new ExpensesList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), expensesList.getExpensesRequestList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expensesList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyExpensesList_replacesData() {
        ExpensesList newData = getTypicalExpensesList();
        expensesList.resetData(newData);
        assertEquals(newData, expensesList);
    }

    @Test
    public void resetData_withDuplicateExpenses_throwsDuplicateExpenseseException() {
        // Two expenses with the same identity fields
        Expenses editedAlice = new ExpensesBuilder(ALICE_CLAIM).build();
        List<Expenses> newExpenses = Arrays.asList(ALICE_CLAIM, editedAlice);
        ExpensesListStub newData = new ExpensesListStub(newExpenses);

        thrown.expect(DuplicateExpensesException.class);
        expensesList.resetData(newData);
    }

    @Test
    public void hasExpenses_nullExpenses_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expensesList.hasExpenses(null);
    }

    @Test
    public void hasExpenses_expensesNotInExpensesList_returnsFalse() {
        assertFalse(expensesList.hasExpenses(ALICE_CLAIM));
    }

    @Test
    public void hasExpenses_expensesInExpensesList_returnsTrue() {
        expensesList.addExpenses(ALICE_CLAIM);
        assertTrue(expensesList.hasExpenses(ALICE_CLAIM));
    }

    @Test
    public void hasExpenses_expensesWithSameIdentityFieldsInExpensesList_returnsTrue() {
        expensesList.addExpenses(ALICE_CLAIM);
        Expenses editedAlice = new ExpensesBuilder(ALICE_CLAIM).build();
        assertTrue(expensesList.hasExpenses(editedAlice));
    }

    @Test
    public void getExpensesList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        expensesList.getExpensesRequestList().remove(0);
    }

    @Test
    public void toString_validExpenses_returnsOneExpensesSize() {
        expensesList.addExpenses(ALICE_CLAIM);
        assertEquals(expensesList.toString(), "1 multiExpenses");
    }

    @Test
    public void hashCode_validExpenses_correctHashCodeRepresentation() {
        expensesList.addExpenses(ALICE_CLAIM);
        Expenses sameAlice = new ExpensesBuilder(ALICE_CLAIM).build();
        List<Expenses> expected = Arrays.asList(sameAlice);
        assertEquals(expensesList.hashCode(), expected.hashCode());
    }

    @Test
    public void updateExpenses_validExpenses_expensesUpdated() {
        expensesList.addExpenses(ALICE_CLAIM);
        expensesList.updateExpenses(ALICE_CLAIM, BENSON_CLAIM);
        assertTrue(expensesList.hasExpenses(BENSON_CLAIM));
    }

    /**
     * A stub ReadOnlyExpenses whose expenses list can violate interface constraints.
     */
    private static class ExpensesListStub implements ReadOnlyExpensesList {
        private final ObservableList<Expenses> expenses = FXCollections.observableArrayList();

        ExpensesListStub(Collection<Expenses> expenses) {
            this.expenses.setAll(expenses);
        }

        @Override
        public ObservableList<Expenses> getExpensesRequestList() {
            return expenses;
        }
    }
}
