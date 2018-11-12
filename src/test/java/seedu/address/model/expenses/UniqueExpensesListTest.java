package seedu.address.model.expenses;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPLOYEEID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPLOYEEID_CARL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICALEXPENSES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MISCELLANEOUSEXPENSES_CARL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRAVELEXPENSES_AMY;
import static seedu.address.testutil.expenses.TypicalExpenses.ALICE_CLAIM;
import static seedu.address.testutil.expenses.TypicalExpenses.BENSON_CLAIM;
import static seedu.address.testutil.expenses.TypicalExpenses.CARL_CLAIM;
import static seedu.address.testutil.expenses.TypicalExpenses.DANIEL_CLAIM;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.expenses.exceptions.DuplicateExpensesException;
import seedu.address.model.expenses.exceptions.ExpensesNotFoundException;
import seedu.address.testutil.Assert;
import seedu.address.testutil.expenses.ExpensesBuilder;


public class UniqueExpensesListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueExpensesList uniqueExpensesList = new UniqueExpensesList();

    @Test
    public void contains_nullExpenses_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpensesList.contains(null);
    }

    @Test
    public void contains_expensesNotInList_returnsFalse() {
        assertFalse(uniqueExpensesList.contains(ALICE_CLAIM));
    }

    @Test
    public void contains_expensesInList_returnsTrue() {
        uniqueExpensesList.add(ALICE_CLAIM);
        assertTrue(uniqueExpensesList.contains(ALICE_CLAIM));
    }

    @Test
    public void contains_personWithDifferentIdentityFieldsInList_returnsFalse() {
        uniqueExpensesList.add(ALICE_CLAIM);
        Expenses editedAlice = new ExpensesBuilder().withEmployeeId(VALID_EMPLOYEEID_AMY).withExpensesAmount(
                VALID_TRAVELEXPENSES_AMY, VALID_MEDICALEXPENSES_BOB, VALID_MISCELLANEOUSEXPENSES_CARL)
                .withTravelExpenses(VALID_TRAVELEXPENSES_AMY).withMedicalExpenses(VALID_MEDICALEXPENSES_BOB)
                .withMiscellaneousExpenses(VALID_MISCELLANEOUSEXPENSES_CARL).build();
        assertFalse(uniqueExpensesList.contains(editedAlice));
    }

    @Test
    public void add_nullExpenses_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpensesList.add(null);
    }

    @Test
    public void add_duplicateExpenses_throwsDuplicateExpensesException() {
        uniqueExpensesList.add(BENSON_CLAIM);
        thrown.expect(DuplicateExpensesException.class);
        uniqueExpensesList.add(BENSON_CLAIM);
    }

    @Test
    public void setExpenses_nullTargetExpenses_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpensesList.setExpenses(null, CARL_CLAIM);
    }

    @Test
    public void setExpenses_nullEditedExpenses_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpensesList.setExpenses(ALICE_CLAIM, null);
    }

    @Test
    public void setExpenses_targetExpensesNotInList_throwsExpensesNotFoundException() {
        thrown.expect(ExpensesNotFoundException.class);
        uniqueExpensesList.setExpenses(ALICE_CLAIM, ALICE_CLAIM);
    }

    @Test
    public void setExpenses_editedExpensesIsSameExpenses_success() {
        uniqueExpensesList.add(DANIEL_CLAIM);
        uniqueExpensesList.setExpenses(DANIEL_CLAIM, DANIEL_CLAIM);
        UniqueExpensesList expectedUniqueExpensesList = new UniqueExpensesList();
        expectedUniqueExpensesList.add(DANIEL_CLAIM);
        assertEquals(expectedUniqueExpensesList, uniqueExpensesList);
    }

    @Test
    public void setExpenses_editedExpensesHasSameExpenses_success() {
        uniqueExpensesList.add(ALICE_CLAIM);
        Expenses editedAlice = new ExpensesBuilder().withEmployeeId(VALID_EMPLOYEEID_CARL).withExpensesAmount(
                VALID_TRAVELEXPENSES_AMY, VALID_MEDICALEXPENSES_BOB, VALID_MISCELLANEOUSEXPENSES_CARL)
                .withTravelExpenses(VALID_TRAVELEXPENSES_AMY).withMedicalExpenses(VALID_MEDICALEXPENSES_BOB)
                .withMiscellaneousExpenses(VALID_MISCELLANEOUSEXPENSES_CARL).build();
        uniqueExpensesList.setExpenses(ALICE_CLAIM, editedAlice);
        UniqueExpensesList expectedUniqueExpensesList = new UniqueExpensesList();
        expectedUniqueExpensesList.add(editedAlice);
        assertEquals(expectedUniqueExpensesList, uniqueExpensesList);
    }

    @Test
    public void setExpenses_editedExpensesHasDifferentExpenses_success() {
        uniqueExpensesList.add(ALICE_CLAIM);
        uniqueExpensesList.setExpenses(ALICE_CLAIM, BENSON_CLAIM);
        UniqueExpensesList expectedUniqueExpensesList = new UniqueExpensesList();
        expectedUniqueExpensesList.add(BENSON_CLAIM);
        assertEquals(expectedUniqueExpensesList, uniqueExpensesList);
    }

    @Test
    public void setExpenses_editedExpensesHasNonUniqueIdentity_throwsDuplicateExpensesException() {
        uniqueExpensesList.add(ALICE_CLAIM);
        uniqueExpensesList.add(BENSON_CLAIM);
        thrown.expect(DuplicateExpensesException.class);
        uniqueExpensesList.setExpenses(ALICE_CLAIM, BENSON_CLAIM);
    }

    @Test
    public void remove_nullExpenses_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpensesList.remove(null);
    }

    @Test
    public void remove_expensesDoesNotExist_throwsExpensesNotFoundException() {
        thrown.expect(ExpensesNotFoundException.class);
        uniqueExpensesList.remove(ALICE_CLAIM);
    }

    @Test
    public void remove_existingExpenses_removesExpenses() {
        uniqueExpensesList.add(ALICE_CLAIM);
        uniqueExpensesList.remove(ALICE_CLAIM);
        UniqueExpensesList expectedUniqueExpensesList = new UniqueExpensesList();
        assertEquals(expectedUniqueExpensesList, uniqueExpensesList);
    }

    @Test
    public void setExpenses_nullUniqueExpensesList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpensesList.setMultiExpenses((UniqueExpensesList) null);
    }

    @Test
    public void setExpenses_uniqueExpensesList_replacesOwnListWithProvidedUniqueExpensesList() {
        uniqueExpensesList.add(ALICE_CLAIM);
        UniqueExpensesList expectedUniqueExpensesList = new UniqueExpensesList();
        expectedUniqueExpensesList.add(BENSON_CLAIM);
        uniqueExpensesList.setMultiExpenses(expectedUniqueExpensesList);
        assertEquals(expectedUniqueExpensesList, uniqueExpensesList);
    }

    @Test
    public void setExpenses_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpensesList.setMultiExpenses((List<Expenses>) null);
    }

    @Test
    public void setExpenses_list_replacesOwnListWithProvidedList() {
        uniqueExpensesList.add(ALICE_CLAIM);
        List<Expenses> expensesList = Collections.singletonList(BENSON_CLAIM);
        uniqueExpensesList.setMultiExpenses(expensesList);
        UniqueExpensesList expectedUniqueExpensesList = new UniqueExpensesList();
        expectedUniqueExpensesList.add(BENSON_CLAIM);
        assertEquals(expectedUniqueExpensesList, uniqueExpensesList);
    }

    @Test
    public void setExpenses_listWithDuplicateExpenses_throwsDuplicateExpensesException() {
        List<Expenses> listWithDuplicateExpenses = Arrays.asList(ALICE_CLAIM, ALICE_CLAIM);
        thrown.expect(DuplicateExpensesException.class);
        uniqueExpensesList.setMultiExpenses(listWithDuplicateExpenses);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueExpensesList.asUnmodifiableObservableList().remove(0);
    }

    @Test
    public void hashCode_validExpenses_correctHashCodeRepresentation() {
        uniqueExpensesList.add(ALICE_CLAIM);
        Expenses sameAlice = new ExpensesBuilder(ALICE_CLAIM).build();
        List<Expenses> expected = Arrays.asList(sameAlice);
        assertEquals(uniqueExpensesList.hashCode(), expected.hashCode());
    }


    //Iterator has been tested by java, we will just run a few test to check if iterator is returned from the method
    @Test
    public void iterator_emptyUniqueExpensesList_hasNoNextExpenses() {
        assertFalse(uniqueExpensesList.iterator().hasNext());
    }

    @Test
    public void iterator_nextExpensesEmptyUniqueExpensesList_throwsNoSuchElementException() {
        Assert.assertThrows(NoSuchElementException.class, () -> uniqueExpensesList.iterator().next());
    }
}
