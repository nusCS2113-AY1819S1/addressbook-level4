package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_REQUEST1;
import static seedu.address.logic.commands.CommandTestUtil.DESC_REQUEST2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPROVAL_REQUEST2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_REQUEST3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITYLEVEL_AMY;

import org.junit.Test;

import seedu.address.logic.commands.EditLeaveCommand.EditLeaveDescriptor;
import seedu.address.testutil.EditLeaveDescriptorBuilder;

//@@author Hafizuddin-NUS
public class EditLeaveDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditLeaveDescriptor descriptorWithSameValues =
                new EditLeaveDescriptor(DESC_REQUEST1);

        assertTrue(DESC_REQUEST1.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_REQUEST1.equals(DESC_REQUEST1));

        // null -> returns false
        assertFalse(DESC_REQUEST1.equals(null));

        // different types -> returns false
        assertFalse(DESC_REQUEST1.equals(5));

        // different values -> returns false
        assertFalse(DESC_REQUEST1.equals(DESC_REQUEST2));

        // different nric -> returns false
        EditLeaveDescriptor editedRequest = new EditLeaveDescriptorBuilder(DESC_REQUEST1)
                .withNric(VALID_NRIC_BOB).build();

        assertFalse(DESC_REQUEST1.equals(editedRequest));

        // different date -> returns false
        editedRequest = new EditLeaveDescriptorBuilder(DESC_REQUEST1).withDate(VALID_DATE_REQUEST3).build();
        assertFalse(DESC_REQUEST1.equals(editedRequest));

        // different approval -> returns false
        editedRequest = new EditLeaveDescriptorBuilder(DESC_REQUEST1).withApproval(VALID_APPROVAL_REQUEST2).build();

        // different priority level -> returns false
        editedRequest = new EditLeaveDescriptorBuilder(DESC_REQUEST1)
                .withPriorityLevel(VALID_PRIORITYLEVEL_AMY).build();
        assertFalse(DESC_REQUEST1.equals(editedRequest));
    }
}
