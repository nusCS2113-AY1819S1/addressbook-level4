package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalAddGroups.ADD_GROUP_1;
import static seedu.address.testutil.TypicalAddGroups.ADD_GROUP_2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddGroupCommand.
 */
public class AddGroupCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullParam_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddGroupCommand(null);
    }
    @Test
    public void equals() {
        AddGroupCommand addGroupCommand1 = new AddGroupCommand(ADD_GROUP_1);
        AddGroupCommand addGroupCommand2 = new AddGroupCommand(ADD_GROUP_2);

        // same object -> returns true
        assertTrue(addGroupCommand1.equals(addGroupCommand1));

        // same values -> returns true
        AddGroupCommand addGroup1CommandCopy = new AddGroupCommand(ADD_GROUP_1);
        assertTrue(addGroupCommand1.equals(addGroup1CommandCopy));

        // different types -> returns false
        assertFalse(addGroupCommand1.equals(1));

        // null -> returns false
        assertFalse(addGroupCommand1.equals(null));

        // different param -> returns false
        assertFalse(addGroupCommand1.equals(addGroupCommand2));
    }

}
