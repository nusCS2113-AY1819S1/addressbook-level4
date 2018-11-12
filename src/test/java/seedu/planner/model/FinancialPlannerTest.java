package seedu.planner.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.planner.testutil.TypicalRecords.INDO;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.planner.model.autocomplete.DateMap;
import seedu.planner.model.autocomplete.RecordMap;
import seedu.planner.model.record.DateBasedLimitList;
import seedu.planner.model.record.Limit;
import seedu.planner.model.record.Record;
import seedu.planner.model.record.UniqueRecordList;
import seedu.planner.model.record.exceptions.DuplicateRecordException;
import seedu.planner.testutil.RecordBuilder;

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
        financialPlanner.resetData((UniqueRecordList) null);
    }

    @Test
    public void resetData_withValidReadOnlyFinancialPlanner_replacesData() {
        FinancialPlanner newData = getTypicalFinancialPlanner();
        financialPlanner.resetData(newData);
        assertEquals(newData, financialPlanner);
    }

    @Test
    public void resetData_withDuplicateRecords_throwsDuplicateRecordException() {
        // Two records with the same compulsory fields
        Record editedIndo = new RecordBuilder(INDO).withTags(VALID_TAG_HUSBAND).build();
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
    public void hasRecord_recordWithSameCompulsoryFieldsInFinancialPlanner_returnsTrue() {
        financialPlanner.addRecord(INDO);
        Record editedIndo = new RecordBuilder(INDO).withTags(VALID_TAG_HUSBAND).build();
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
        private final ObservableList<Limit> limits = FXCollections.observableArrayList();
        private final RecordMap recordMap = new RecordMap();
        private final DateMap limitsMap = new DateMap();

        FinancialPlannerStub(Collection<Record> records) {
            this.records.setAll(records);
        }

        @Override
        public ObservableList<Record> getRecordList() {
            return records;
        }

        @Override
        public ObservableList<Limit> getLimitList() {
            return limits;
        }

        @Override
        public RecordMap getRecordMap() {
            return recordMap;
        }

        @Override
        public HashMap<String, Integer> getLimitMap() {
            return limitsMap.getAsReadOnlyDateMap();
        }

        @Override
        public void setLimitList(DateBasedLimitList limitList) {} //see as above

    }

}
