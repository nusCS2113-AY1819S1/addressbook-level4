package seedu.address.model.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME;
import static seedu.address.testutil.TypicalUsers.ALICE;
import static seedu.address.testutil.TypicalUsers.BOB;

import org.junit.Test;

import seedu.address.testutil.UserBuilder;

public class UserTest {

    @Test
    public void equals() {
        // same values -> returns true
        User aliceCopy = new UserBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different event -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        User editedAlice = new UserBuilder(ALICE).withUsername(VALID_USERNAME).build();
        assertFalse(ALICE.equals(editedAlice));

        // different contact -> returns false
        editedAlice = new UserBuilder(ALICE).withPassword(VALID_PASSWORD).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
