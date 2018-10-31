package seedu.address.testutil;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventList;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
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

