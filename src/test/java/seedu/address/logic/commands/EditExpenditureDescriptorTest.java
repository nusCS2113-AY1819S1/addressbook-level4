package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CLOTHES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENDITURE_CATEGORY_CLOTHES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENDITURE_DATE_CLOTHES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENDITURE_DESCRIPTION_CLOTHES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENDITURE_MONEY_CLOTHES;

import org.junit.Test;

import seedu.address.testutil.EditExpenditureDescriptorBuilder;

public class EditExpenditureDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditExpenditureCommand.EditExpenditureDescriptor descriptorWithSameValues = 
                new EditExpenditureCommand.EditExpenditureDescriptor(DESC_IPHONE);
        assertTrue(DESC_IPHONE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_IPHONE.equals(DESC_IPHONE));

        // null -> returns false
        assertFalse(DESC_IPHONE == null);

        // different types -> returns false
        assertFalse(DESC_IPHONE.equals(5));

        // different values -> returns false
        assertFalse(DESC_IPHONE.equals(DESC_CLOTHES));

        // different description -> returns false
        EditExpenditureCommand.EditExpenditureDescriptor editediPhone =
                new EditExpenditureDescriptorBuilder(DESC_IPHONE)
                        .withDescription(VALID_EXPENDITURE_DESCRIPTION_CLOTHES).build();
        assertFalse(DESC_IPHONE.equals(editediPhone));

        // different date -> returns false
        editediPhone = new EditExpenditureDescriptorBuilder(DESC_IPHONE)
                .withDate(VALID_EXPENDITURE_DATE_CLOTHES).build();
        assertFalse(DESC_IPHONE.equals(editediPhone));

        // different category -> returns false
        editediPhone = new EditExpenditureDescriptorBuilder(DESC_IPHONE)
                .withCategory(VALID_EXPENDITURE_CATEGORY_CLOTHES).build();
        assertFalse(DESC_IPHONE.equals(editediPhone));

        // different money -> returns false
        editediPhone = new EditExpenditureDescriptorBuilder(DESC_IPHONE)
                .withMoney(VALID_EXPENDITURE_MONEY_CLOTHES).build();
        assertFalse(DESC_IPHONE.equals(editediPhone));
    }
}
