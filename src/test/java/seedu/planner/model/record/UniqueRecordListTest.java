package seedu.planner.model.record;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_MONEYFLOW_EXPENSE_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.planner.testutil.TypicalRecords.BOB;
import static seedu.planner.testutil.TypicalRecords.INDO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.planner.model.record.exceptions.DuplicateRecordException;
import seedu.planner.model.record.exceptions.RecordNotFoundException;
import seedu.planner.testutil.RecordBuilder;

public class UniqueRecordListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueRecordList uniqueRecordList = new UniqueRecordList();

    @Test
    public void contains_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.contains(null);
    }

    @Test
    public void contains_recordNotInList_returnsFalse() {
        assertFalse(uniqueRecordList.contains(INDO));
    }

    @Test
    public void contains_recordInList_returnsTrue() {
        uniqueRecordList.add(INDO);
        assertTrue(uniqueRecordList.contains(INDO));
    }

    @Test
    public void contains_recordWithSameIdentityFieldsInList_returnsTrue() {
        uniqueRecordList.add(INDO);
        Record editedAlice = new RecordBuilder(INDO).withMoneyFlow(VALID_MONEYFLOW_EXPENSE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(uniqueRecordList.contains(editedAlice));
    }

    @Test
    public void add_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.add(null);
    }

    @Test
    public void add_duplicateRecord_throwsDuplicateRecordException() {
        uniqueRecordList.add(INDO);
        thrown.expect(DuplicateRecordException.class);
        uniqueRecordList.add(INDO);
    }

    @Test
    public void setRecord_nullTargetRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecord(null, INDO);
    }

    @Test
    public void setRecord_nullEditedRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecord(INDO, null);
    }

    @Test
    public void setRecord_targetRecordNotInList_throwsRecordNotFoundException() {
        thrown.expect(RecordNotFoundException.class);
        uniqueRecordList.setRecord(INDO, INDO);
    }

    @Test
    public void setRecord_editedRecordIsSameRecord_success() {
        uniqueRecordList.add(INDO);
        uniqueRecordList.setRecord(INDO, INDO);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        expectedUniqueRecordList.add(INDO);
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecord_editedRecordHasSameIdentity_success() {
        uniqueRecordList.add(INDO);
        Record editedAlice = new RecordBuilder(INDO).withMoneyFlow(VALID_MONEYFLOW_EXPENSE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        uniqueRecordList.setRecord(INDO, editedAlice);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        expectedUniqueRecordList.add(editedAlice);
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecord_editedRecordHasDifferentIdentity_success() {
        uniqueRecordList.add(INDO);
        uniqueRecordList.setRecord(INDO, BOB);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        expectedUniqueRecordList.add(BOB);
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecord_editedRecordHasNonUniqueIdentity_throwsDuplicateRecordException() {
        uniqueRecordList.add(INDO);
        uniqueRecordList.add(BOB);
        thrown.expect(DuplicateRecordException.class);
        uniqueRecordList.setRecord(INDO, BOB);
    }

    @Test
    public void remove_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.remove(null);
    }

    @Test
    public void remove_recordDoesNotExist_throwsRecordNotFoundException() {
        thrown.expect(RecordNotFoundException.class);
        uniqueRecordList.remove(INDO);
    }

    @Test
    public void remove_existingRecord_removesRecord() {
        uniqueRecordList.add(INDO);
        uniqueRecordList.remove(INDO);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecords_nullUniqueRecordList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecords((UniqueRecordList) null);
    }

    @Test
    public void setRecords_uniqueRecordList_replacesOwnListWithProvidedUniqueRecordList() {
        uniqueRecordList.add(INDO);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        expectedUniqueRecordList.add(BOB);
        uniqueRecordList.setRecords(expectedUniqueRecordList);
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecords_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecords((List<Record>) null);
    }

    @Test
    public void setRecords_list_replacesOwnListWithProvidedList() {
        uniqueRecordList.add(INDO);
        List<Record> recordList = Collections.singletonList(BOB);
        uniqueRecordList.setRecords(recordList);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        expectedUniqueRecordList.add(BOB);
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecords_listWithDuplicateRecords_throwsDuplicateRecordException() {
        List<Record> listWithDuplicateRecords = Arrays.asList(INDO, INDO);
        thrown.expect(DuplicateRecordException.class);
        uniqueRecordList.setRecords(listWithDuplicateRecords);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueRecordList.asUnmodifiableObservableList().remove(0);
    }
}
