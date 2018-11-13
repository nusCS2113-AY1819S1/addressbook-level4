//@@author SHININGGGG
package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalExpenditures.CHICKEN;
import static seedu.address.testutil.TypicalExpenditures.getTypicalExpenditureTracker;

import java.util.Collection;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.expenditureinfo.Expenditure;

public class ExpenditureTrackerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ExpenditureTracker expenditureTracker = new ExpenditureTracker();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), expenditureTracker.getExpenditureList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenditureTracker.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyExpenditureTracker_replacesData() {
        ExpenditureTracker newData = getTypicalExpenditureTracker();
        expenditureTracker.resetData(newData);
        assertEquals(newData, expenditureTracker);
    }

    @Test
    public void hasExpenditure_nullExpenditure_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenditureTracker.hasExpenditure(null);
    }

    @Test
    public void hasExpenditure_expenditureNotInExpenditureTracker_returnsFalse() {
        assertFalse(expenditureTracker.hasExpenditure(CHICKEN));
    }

    @Test
    public void hasExpenditure_expenditureInExpenditureTracker_returnsTrue() {
        expenditureTracker.addExpenditure(CHICKEN);
        assertTrue(expenditureTracker.hasExpenditure(CHICKEN));
    }

    @Test
    public void getExpenditureList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        expenditureTracker.getExpenditureList().remove(0);
    }

    /**
     * A stub ReadOnlyExpenditureTracker whose expenditures list can violate interface constraints.
     */
    private static class ExpenditureTrackerStub implements ReadOnlyExpenditureTracker {
        private final ObservableList<Expenditure> expenditures = FXCollections.observableArrayList();

        ExpenditureTrackerStub(Collection<Expenditure> tasks) {
            this.expenditures.setAll(tasks);
        }

        @Override
        public ObservableList<Expenditure> getExpenditureList() {
            return expenditures;
        }
    }
}
