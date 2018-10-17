package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.ModelStub;


public class AddEventCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() throws Exception {
        Event validEvent = new EventBuilder().build();
        AddEventCommand addEventCommand = new AddEventCommand(validEvent);
        ModelStub modelStub = new ModelStubWithEvent(validEvent);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddEventCommand.MESSAGE_DUPLICATE_EVENT);
        addEventCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Event alice = new EventBuilder().withEventName("Lecture").build();
        Event bob = new EventBuilder().withEventName("Tutorial").build();
        AddEventCommand addLectureCommand = new AddEventCommand(alice);
        AddEventCommand addTutorialCommand = new AddEventCommand(bob);

        // same object -> returns true
        assertTrue(addLectureCommand.equals(addLectureCommand));

        // same values -> returns true
        AddEventCommand addLectureCommandCopy = new AddEventCommand(alice);
        assertTrue(addLectureCommand.equals(addLectureCommandCopy));

        // different types -> returns false
        assertFalse(addLectureCommand.equals(1));

        // null -> returns false
        assertFalse(addLectureCommand.equals(null));

        // different person -> returns false
        assertFalse(addLectureCommand.equals(addTutorialCommand));
    }


    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithEvent extends ModelStub {
        private final Event event;

        ModelStubWithEvent(Event event) {
            requireNonNull(event);
            this.event = event;
        }

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return this.event.isSameEvent(event);
        }
    }

}
