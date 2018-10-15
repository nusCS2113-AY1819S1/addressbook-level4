package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventList;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;


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
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData, ReadOnlyEventList newEventList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        // Implemented EventList Methods go here

        @Override
        public ReadOnlyEventList getEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEvent(Event person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateEvent(Event target, Event editedEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortByName() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortByStartTime() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortByEndTime() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortByDate() {
            throw new AssertionError("This method should not be called.");
        }
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
