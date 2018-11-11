package seedu.recruit.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.logic.commands.CommandTestUtil.DESC_ALFA;
import static seedu.recruit.logic.commands.CommandTestUtil.DESC_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_ADDRESS_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_EMAIL_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_NAME_BMW;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_PHONE_BMW;

import org.junit.Test;

import seedu.recruit.logic.commands.EditCompanyCommand.EditCompanyDescriptor;
import seedu.recruit.testutil.EditCompanyDescriptorBuilder;

public class EditCompanyDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCompanyDescriptor descriptorWithSameValues = new EditCompanyDescriptor(DESC_ALFA);
        assertTrue(DESC_ALFA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ALFA.equals(DESC_ALFA));

        // null -> returns false
        assertFalse(DESC_ALFA.equals(null));

        // different types -> returns false
        assertFalse(DESC_ALFA.equals(5));

        // different values -> returns false
        assertFalse(DESC_ALFA.equals(DESC_BMW));

        // different company name -> returns false
        EditCompanyDescriptor editedAlfa =
                new EditCompanyDescriptorBuilder(DESC_ALFA).withCompanyName(VALID_NAME_BMW).build();
        assertFalse(DESC_ALFA.equals(editedAlfa));

        // different address -> returns false
        editedAlfa = new EditCompanyDescriptorBuilder(DESC_ALFA).withAddress(VALID_ADDRESS_BMW).build();
        assertFalse(DESC_ALFA.equals(editedAlfa));

        // different email -> returns false
        editedAlfa = new EditCompanyDescriptorBuilder(DESC_ALFA).withEmail(VALID_EMAIL_BMW).build();
        assertFalse(DESC_ALFA.equals(editedAlfa));

        // different phone -> returns false
        editedAlfa = new EditCompanyDescriptorBuilder(DESC_ALFA).withPhone(VALID_PHONE_BMW).build();
        assertFalse(DESC_ALFA.equals(editedAlfa));

    }
}
