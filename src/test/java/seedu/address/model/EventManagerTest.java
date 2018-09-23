package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class EventManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EventManager eventManager = new EventManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), eventManager.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventManager.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        EventManager newData = getTypicalAddressBook();
        eventManager.resetData(newData);
        assertEquals(newData, eventManager);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two events with the same identity fields
        Event editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Event> newEvents = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newEvents);

        thrown.expect(DuplicatePersonException.class);
        eventManager.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventManager.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(eventManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        eventManager.addPerson(ALICE);
        assertTrue(eventManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        eventManager.addPerson(ALICE);
        Event editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(eventManager.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        eventManager.getPersonList().remove(0);
    }

    /**
     * A stub ReadOnlyAddressBook whose events list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Event> events = FXCollections.observableArrayList();

        AddressBookStub(Collection<Event> events) {
            this.events.setAll(events);
        }

        @Override
        public ObservableList<Event> getPersonList() {
            return events;
        }
    }

}
