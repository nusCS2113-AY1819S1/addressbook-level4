package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalEvents.EVENT_1;
import static seedu.address.testutil.TypicalEvents.EVENT_2;
import static seedu.address.testutil.TypicalEvents.EVENT_3;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.TypicalEvents;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    // Pure AddressBook Model test methods

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    // Pure EventList Model test methods
    @Test
    public void hasEvent_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasEvent(null);
    }

    @Test
    public void hasEvent_eventNotInEventList_returnsFalse() {
        assertFalse(modelManager.hasEvent(EVENT_1));
    }

    @Test
    public void hasClash_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasClash(null, VALID_NAME_ALICE);
    }

    @Test
    public void hasClash_nullString_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasClash(EVENT_1, null);
    }


    @Test
    public void hasClash_eventClashWithEventList_returnsFalse() {
        modelManager.addEvent(TypicalEvents.eventwithAttendee());
        assertTrue(modelManager.hasClash(EVENT_3, VALID_NAME_ALICE));
    }

    @Test
    public void hasClash_eventDoesNotClashWithEventList_returnsFalse() {
        modelManager.addEvent(TypicalEvents.eventwithAttendee());
        assertFalse(modelManager.hasClash(EVENT_3, VALID_NAME_BOB));
    }

    @Test
    public void getFilteredEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredEventList().remove(0);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        EventList eventList = new EventList();
        eventList.addEvent(EVENT_1);
        eventList.addEvent(EVENT_2);
        EventList differentEventList = new EventList();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, eventList, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, eventList, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, eventList, userPrefs)));

        // different eventlist -> returns false
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentEventList, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, eventList, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, eventList, differentUserPrefs)));
    }
}
