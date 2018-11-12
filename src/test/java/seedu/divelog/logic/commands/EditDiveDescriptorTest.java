package seedu.divelog.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.divelog.logic.commands.CommandTestUtil.DESC_DAY_BALI;
import static seedu.divelog.logic.commands.CommandTestUtil.DESC_DAY_TIOMAN;

import org.junit.Test;

import seedu.divelog.logic.commands.EditCommand.EditDiveDescriptor;

public class EditDiveDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditDiveDescriptor descriptorWithSameValues = new EditDiveDescriptor(DESC_DAY_BALI);
        assertTrue(DESC_DAY_BALI.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_DAY_BALI.equals(DESC_DAY_BALI));

        // null -> returns false
        assertFalse(DESC_DAY_BALI.equals(null));

        // different types -> returns false
        assertFalse(DESC_DAY_BALI.equals(5));

        // different values -> returns false
        assertFalse(DESC_DAY_BALI.equals(DESC_DAY_TIOMAN));

    }
}
