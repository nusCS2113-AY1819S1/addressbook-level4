package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONEYFLOW_EXPENSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalRecords.INDO;
import static seedu.address.testutil.TypicalRecords.getTypicalFinancialPlanner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.record.Record;
import seedu.address.model.record.exceptions.DuplicateRecordException;
import seedu.address.testutil.RecordBuilder;

public class FinancialPlannerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final FinancialPlanner financialPlanner = new FinancialPlanner();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), financialPlanner.getRecordList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        financialPlanner.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyFinancialPlanner_replacesData() {
        FinancialPlanner newData = getTypicalFinancialPlanner();
        financialPlanner.resetData(newData);
        assertEquals(newData, financialPlanner);
    }

    @Test
    public void resetData_withDuplicateRecords_throwsDuplicateRecordException() {
        // Two records with the same identity fields
        Record editedIndo = new RecordBuilder(INDO).withMoneyFlow(VALID_MONEYFLOW_EXPENSE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        List<Record> newRecords = Arrays.asList(INDO, editedIndo);
        FinancialPlannerStub newData = new FinancialPlannerStub(newRecords);

        thrown.expect(DuplicateRecordException.class);
        financialPlanner.resetData(newData);
    }

    @Test
    public void hasRecord_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        financialPlanner.hasRecord(null);
    }

    @Test
    public void hasRecord_recordNotInFinancialPlanner_returnsFalse() {
        assertFalse(financialPlanner.hasRecord(INDO));
    }

    @Test
    public void hasRecord_recordInFinancialPlanner_returnsTrue() {
        financialPlanner.addRecord(INDO);
        assertTrue(financialPlanner.hasRecord(INDO));
    }

    @Test
    public void hasRecord_recordWithSameIdentityFieldsInFinancialPlanner_returnsTrue() {
        financialPlanner.addRecord(INDO);
        Record editedIndo = new RecordBuilder(INDO).withMoneyFlow(VALID_MONEYFLOW_EXPENSE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(financialPlanner.hasRecord(editedIndo));
    }

    @Test
    public void getRecordList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        financialPlanner.getRecordList().remove(0);
    }

    /**
     * A stub ReadOnlyFinancialPlanner whose records list can violate interface constraints.
     */
    private static class FinancialPlannerStub implements ReadOnlyFinancialPlanner {
        private final ObservableList<Record> records = FXCollections.observableArrayList();

        FinancialPlannerStub(Collection<Record> records) {
            this.records.setAll(records);
        }

        @Override
        public ObservableList<Record> getRecordList() {
            return records;
        }
    }

}
