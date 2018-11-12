package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRODUCT_INFO_APPLE;

import org.junit.Test;

import seedu.address.logic.commands.EditProductCommand.EditProductDescriptor;
import seedu.address.testutil.EditProductDescriptorBuilder;

public class EditProductDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditProductDescriptor descriptorWithSameValues = new EditProductDescriptor(DESC_APPLE);
        assertTrue(DESC_APPLE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_APPLE.equals(DESC_APPLE));

        // null -> returns false
        assertFalse(DESC_APPLE.equals(null));

        // different types -> returns false
        assertFalse(DESC_APPLE.equals(5));

        // different values -> returns false
        assertFalse(DESC_APPLE.equals(DESC_BANANA));

        // different Product info -> returns false
        EditProductDescriptor editedAmy =
                new EditProductDescriptorBuilder(DESC_APPLE).withAddress(VALID_PRODUCT_INFO_APPLE).build();
        assertFalse(DESC_APPLE.equals(editedAmy));

    }
}
