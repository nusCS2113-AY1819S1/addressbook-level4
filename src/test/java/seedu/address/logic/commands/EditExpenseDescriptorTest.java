package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MRT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_CATEGORY_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_DATE_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_VALUE_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TAOBAO;

import org.junit.Test;

import seedu.address.logic.commands.EditExpenseCommand.EditExpenseDescriptor;
import seedu.address.testutil.EditExpenseDescriptorBuilder;

public class EditExpenseDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditExpenseDescriptor descriptorWithSameValues = new EditExpenseDescriptor(DESC_MRT);
        assertTrue(DESC_MRT.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_MRT.equals(DESC_MRT));

        // null -> returns false
        assertFalse(DESC_MRT.equals(null));

        // different types -> returns false
        assertFalse(DESC_MRT.equals(5));

        // different values -> returns false
        assertFalse(DESC_MRT.equals(DESC_SHOPPING));

        // different name -> returns false
        EditExpenseDescriptor editedShopping = new EditExpenseDescriptorBuilder(DESC_SHOPPING)
                .withExpenseCategory(VALID_EXPENSE_CATEGORY_SHOPPING).build();
        assertFalse(DESC_MRT.equals(editedShopping));

        // different phone -> returns false
        editedShopping = new EditExpenseDescriptorBuilder(DESC_SHOPPING)
                .withExpenseDate(VALID_EXPENSE_DATE_SHOPPING).build();
        assertFalse(DESC_MRT.equals(editedShopping));

        // different email -> returns false
        editedShopping = new EditExpenseDescriptorBuilder(DESC_SHOPPING)
                .withExpenseValue(VALID_EXPENSE_VALUE_SHOPPING).build();
        assertFalse(DESC_MRT.equals(editedShopping));

        // different tags -> returns false
        editedShopping = new EditExpenseDescriptorBuilder(DESC_SHOPPING).withTags(VALID_TAG_TAOBAO).build();
        assertFalse(DESC_MRT.equals(editedShopping));
    }
}
