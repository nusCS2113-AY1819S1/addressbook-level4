package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalGroups.CS1010;
import static seedu.address.testutil.TypicalGroups.TUT_1;
import static seedu.address.testutil.TypicalPersonIndexs.getTypicalPersonIndexs;

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
        new AddGroupCommand(null,null);
    }
    @Test
    public void equals() {
        AddGroupCommand addGroupCommand1 = new AddGroupCommand(TUT_1.getGroupName(),getTypicalPersonIndexs());
        AddGroupCommand addGroupCommand2 = new AddGroupCommand(CS1010.getGroupName(), getTypicalPersonIndexs());

        // same object -> returns true
        assertTrue(addGroupCommand1.equals(addGroupCommand1));

        // same values -> returns true
        AddGroupCommand addGroup1CommandCopy = new AddGroupCommand(TUT_1.getGroupName(),getTypicalPersonIndexs());
        assertTrue(addGroupCommand1.equals(addGroup1CommandCopy));

        // different types -> returns false
        assertFalse(addGroupCommand1.equals(1));

        // null -> returns false
        assertFalse(addGroupCommand1.equals(null));

        // different param -> returns false
        assertFalse(addGroupCommand1.equals(addGroupCommand2));
    }

}
