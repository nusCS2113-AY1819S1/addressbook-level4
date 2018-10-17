package seedu.planner.model.record;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_MONEYFLOW_EXPENSE_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.planner.testutil.TypicalRecords.BOB;
import static seedu.planner.testutil.TypicalRecords.INDO;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.planner.testutil.RecordBuilder;

public class RecordTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Record record = new RecordBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        record.getTags().remove(0);
    }

    @Test
    public void isSameRecord() {
        // same object -> returns true
        assertTrue(INDO.isSameRecord(INDO));

        // null -> returns false
        assertFalse(INDO.isSameRecord(null));

        // different date parameter and income -> returns false
        Record editedIndo = new RecordBuilder(INDO).withDate(VALID_DATE_BOB)
                .withMoneyFlow(VALID_MONEYFLOW_EXPENSE_BOB).build();
        assertFalse(INDO.isSameRecord(editedIndo));

        // different name -> returns false
        editedIndo = new RecordBuilder(INDO).withName(VALID_NAME_BOB).build();
        assertFalse(INDO.isSameRecord(editedIndo));

        // same name, same date parameter, different attributes -> returns true
        editedIndo = new RecordBuilder(INDO).withMoneyFlow(VALID_MONEYFLOW_EXPENSE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(INDO.isSameRecord(editedIndo));

        // same name, same money flow, different attributes -> returns true
        editedIndo = new RecordBuilder(INDO).withDate(VALID_DATE_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(INDO.isSameRecord(editedIndo));

        // same name, same date parameter, same money flow parameter, different attributes -> returns true
        editedIndo = new RecordBuilder(INDO).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(INDO.isSameRecord(editedIndo));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Record aliceCopy = new RecordBuilder(INDO).build();
        assertTrue(INDO.equals(aliceCopy));

        // same object -> returns true
        assertTrue(INDO.equals(INDO));

        // null -> returns false
        assertFalse(INDO.equals(null));

        // different type -> returns false
        assertFalse(INDO.equals(5));

        // different record -> returns false
        assertFalse(INDO.equals(BOB));

        // different name -> returns false
        Record editedAlice = new RecordBuilder(INDO).withName(VALID_NAME_BOB).build();
        assertFalse(INDO.equals(editedAlice));

        // different day parameter -> returns false
        editedAlice = new RecordBuilder(INDO).withDate(VALID_DATE_BOB).build();
        assertFalse(INDO.equals(editedAlice));

        // different money flow -> returns false
        editedAlice = new RecordBuilder(INDO).withMoneyFlow(VALID_MONEYFLOW_EXPENSE_BOB).build();
        assertFalse(INDO.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new RecordBuilder(INDO).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(INDO.equals(editedAlice));
    }
}
