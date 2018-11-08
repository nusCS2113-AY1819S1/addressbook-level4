package seedu.address.logic.commands.ledger;


import static org.junit.Assert.assertEquals;

import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Events.Event;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.item.Item;
import seedu.address.model.ledger.Account;
import seedu.address.model.ledger.Ledger;
import seedu.address.model.member.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.LedgerBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public class AddLedgerCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullLedger_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddLedgerCommand(null);
    }

    @Test
    public void execute_ledgerAddedByModel_addSuccessful() throws Exception {

        ModelStubAcceptingLedgerAdded modelStub = new ModelStubAcceptingLedgerAdded();
        Ledger validLedger = new LedgerBuilder().build();

        CommandResult commandResult = new AddLedgerCommand(validLedger).execute(modelStub, commandHistory);
        assertEquals(String.format(AddLedgerCommand.MESSAGE_SUCCESS, validLedger), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validLedger), modelStub.ledgersAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    private class ModelStubAcceptingLedgerAdded extends ModelStub {
        final ArrayList<Ledger> ledgersAdded = new ArrayList<>();

        @Override
        public boolean hasLedger(Ledger ledger) {
            requireNonNull(ledger);
            return ledgersAdded.stream().anyMatch(ledger::isSameLedger);
        }

        @Override
        public void addLedger(Ledger ledger) {
            requireNonNull(ledger);
            ledgersAdded.add(ledger);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddMemberCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEvent(Event event) {

        }

        @Override
        public void updateEvent(Event target, Event editedEvent) {

        }

        @Override
        public void addLedger(Ledger ledger) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteLedger(Ledger ledger) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event event) {

        }

        @Override
        public void increaseAccount(Account account) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void decreaseAccount(Account account) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
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
        public boolean hasEvent(Event event) {
            return false;
        }

        @Override
        public boolean hasLedger(Ledger ledger) {
            return false;
        }

        @Override
        public boolean hasItem(Item item) {
            return false;
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
        public void updateLedger(Ledger target, Ledger editedLedger) {

        }

        @Override
        public void updateItem(Item target, Item editedItem) {

        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Ledger> getFilteredLedgerList() {
            return null;
        }

        @Override
        public ObservableSet<Ledger> getFilteredLedgerSet() {
            return null;
        }

        @Override
        public ObservableList<Item> getFilteredItemList() {
            return null;
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredLedgerList(Predicate<Ledger> predicate) {

        }

        @Override
        public void updateFilteredItemList(Predicate<Item> predicate) {

        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {

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

        @Override
        public void addItem(Item item) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteItem(Item item) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void undoAllAddressBook() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void redoAllAddressBook() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void deleteTag(Tag tag) {
            throw new AssertionError("This method should not be called");
        }
    }

}
