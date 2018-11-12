package seedu.address.model.expenses;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPLOYEEID_CARL;
import static seedu.address.testutil.expenses.TypicalExpenses.ALICE_CLAIM;
import static seedu.address.testutil.expenses.TypicalExpenses.VALID_EMPLOYEEID_ALICE;
import static seedu.address.testutil.expenses.TypicalExpenses.VALID_EXPENSES_AMOUNT_ALICE;
import static seedu.address.testutil.expenses.TypicalExpenses.VALID_MEDICAL_EXPENSES_ALICE;
import static seedu.address.testutil.expenses.TypicalExpenses.VALID_MISCELLANEOUS_EXPENSES_ALICE;
import static seedu.address.testutil.expenses.TypicalExpenses.VALID_TRAVEL_EXPENSES_ALICE;

import java.util.Objects;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.testutil.Assert;
import seedu.address.testutil.expenses.ExpensesBuilder;

public class ExpensesTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Expenses(null, null, null, null, null));
    }

    @Test
    public void isSameExpenses_sameExpenses_true() {
        // same object -> returns true
        assertTrue(ALICE_CLAIM.isSameExpensesRequest(ALICE_CLAIM));
    }

    @Test
    public void isSameExpenses_null_false() {
        // null object -> returns false
        assertFalse(ALICE_CLAIM.isSameExpensesRequest(null));
    }

    @Test
    public void isSameExpenses_differentEmployeeId_false() {
        // different employee id -> returns false
        Expenses editedAlice = new ExpensesBuilder().withEmployeeId(VALID_EMPLOYEEID_CARL)
                .withExpensesAmount(VALID_TRAVEL_EXPENSES_ALICE, VALID_MEDICAL_EXPENSES_ALICE,
                        VALID_MISCELLANEOUS_EXPENSES_ALICE).withTravelExpenses(VALID_TRAVEL_EXPENSES_ALICE)
                .withMedicalExpenses(VALID_MEDICAL_EXPENSES_ALICE).withMiscellaneousExpenses(
                        VALID_MISCELLANEOUS_EXPENSES_ALICE).build();
        assertFalse(ALICE_CLAIM.isSameExpensesRequest(editedAlice));
    }
    @Test
    public void isSameExpenses_sameAttributes_true() {
        // same employee id, travel expenses, medical expenses, miscellaneous expenses -> returns true
        Expenses editedAlice = new ExpensesBuilder().withEmployeeId(VALID_EMPLOYEEID_ALICE)
                .withExpensesAmount(VALID_TRAVEL_EXPENSES_ALICE, VALID_MEDICAL_EXPENSES_ALICE,
                        VALID_MISCELLANEOUS_EXPENSES_ALICE).withTravelExpenses(VALID_TRAVEL_EXPENSES_ALICE)
                .withMedicalExpenses(VALID_MEDICAL_EXPENSES_ALICE).withMiscellaneousExpenses(
                        VALID_MISCELLANEOUS_EXPENSES_ALICE).build();
        assertTrue(ALICE_CLAIM.isSameExpensesRequest(editedAlice));
    }

    @Test
    public void hashCode_validExpenses_correctHashCodeRepresentation() {
        // same employee id, travel expenses, medical expenses, miscellaneous expenses -> returns true
        Expenses editedAlice = new ExpensesBuilder().withEmployeeId(VALID_EMPLOYEEID_ALICE)
                .withExpensesAmount(VALID_TRAVEL_EXPENSES_ALICE, VALID_MEDICAL_EXPENSES_ALICE,
                        VALID_MISCELLANEOUS_EXPENSES_ALICE).withTravelExpenses(VALID_TRAVEL_EXPENSES_ALICE)
                .withMedicalExpenses(VALID_MEDICAL_EXPENSES_ALICE).withMiscellaneousExpenses(
                        VALID_MISCELLANEOUS_EXPENSES_ALICE).build();
        assertEquals(editedAlice.hashCode(), Objects.hash(VALID_EXPENSES_AMOUNT_ALICE, VALID_TRAVEL_EXPENSES_ALICE,
                VALID_MEDICAL_EXPENSES_ALICE, VALID_MISCELLANEOUS_EXPENSES_ALICE));
    }


}
