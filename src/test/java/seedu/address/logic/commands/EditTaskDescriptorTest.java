//@@author XiaoYunhan
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DATE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_MODULE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_PRIORITY_TUTORIAL;

import org.junit.Test;

import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditTaskCommand.EditTaskDescriptor descriptorWithSameValues =
                new EditTaskCommand.EditTaskDescriptor(DESC_ASSIGNMENT);
        assertTrue(DESC_ASSIGNMENT.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ASSIGNMENT.equals(DESC_ASSIGNMENT));

        // null -> returns false
        assertFalse(DESC_ASSIGNMENT == null);

        // different types -> returns false
        assertFalse(DESC_ASSIGNMENT.equals(5));

        // different values -> returns false
        assertFalse(DESC_ASSIGNMENT.equals(DESC_TUTORIAL));

        // different name -> returns false
        EditTaskCommand.EditTaskDescriptor editedAssignment = new EditTaskDescriptorBuilder(DESC_ASSIGNMENT)
                .withName(VALID_TASK_NAME_TUTORIAL).build();
        assertFalse(DESC_ASSIGNMENT.equals(editedAssignment));

        // different date -> returns false
        editedAssignment = new EditTaskDescriptorBuilder(DESC_ASSIGNMENT).withDate(VALID_TASK_DATE_TUTORIAL).build();
        assertFalse(DESC_ASSIGNMENT.equals(editedAssignment));

        // different module -> returns false
        editedAssignment = new EditTaskDescriptorBuilder(DESC_ASSIGNMENT)
                .withModule(VALID_TASK_MODULE_TUTORIAL).build();
        assertFalse(DESC_ASSIGNMENT.equals(editedAssignment));

        // different priority -> returns false
        editedAssignment = new EditTaskDescriptorBuilder(DESC_ASSIGNMENT)
                .withPriority(VALID_TASK_PRIORITY_TUTORIAL).build();
        assertFalse(DESC_ASSIGNMENT.equals(editedAssignment));

    }
}
