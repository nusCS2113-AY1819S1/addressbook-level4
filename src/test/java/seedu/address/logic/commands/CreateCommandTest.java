package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreateCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void equals() {
        Event alice = new EventBuilder().withEventName("Lecture").build();
        Event bob = new EventBuilder().withEventName("Tutorial").build();
        CreateCommand addAliceCommand = new CreateCommand(alice);
        CreateCommand addBobCommand = new CreateCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        CreateCommand addAliceCommandCopy = new CreateCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }
}
