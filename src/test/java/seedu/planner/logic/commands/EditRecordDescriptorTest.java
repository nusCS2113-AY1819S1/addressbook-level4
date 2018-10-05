package seedu.planner.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.planner.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_MONEYFLOW_EXPENSE_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Test;

import seedu.planner.logic.commands.EditCommand.EditRecordDescriptor;
import seedu.planner.testutil.EditRecordDescriptorBuilder;

public class EditRecordDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditRecordDescriptor descriptorWithSameValues = new EditCommand.EditRecordDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditCommand.EditRecordDescriptor editedAmy = new EditRecordDescriptorBuilder(DESC_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different day params -> returns false
        editedAmy = new EditRecordDescriptorBuilder(DESC_AMY).withDate(VALID_DATE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different money flow -> returns false
        editedAmy = new EditRecordDescriptorBuilder(DESC_AMY).withMoneyFlow(VALID_MONEYFLOW_EXPENSE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditRecordDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
