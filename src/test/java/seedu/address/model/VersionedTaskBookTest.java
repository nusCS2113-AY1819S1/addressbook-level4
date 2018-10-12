package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalTasks.AMY;
import static seedu.address.testutil.TypicalTasks.BOB;
import static seedu.address.testutil.TypicalTasks.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.AddressBookBuilder;

public class VersionedTaskBookTest {

    private final ReadOnlyTaskBook addressBookWithAmy = new AddressBookBuilder().withPerson(AMY).build();
    private final ReadOnlyTaskBook addressBookWithBob = new AddressBookBuilder().withPerson(BOB).build();
    private final ReadOnlyTaskBook addressBookWithCarl = new AddressBookBuilder().withPerson(CARL).build();
    private final ReadOnlyTaskBook emptyAddressBook = new AddressBookBuilder().build();

    @Test
    public void commit_singleAddressBook_noStatesRemovedCurrentStateSaved() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(emptyAddressBook);

        versionedTaskBook.commit();
        assertAddressBookListStatus(versionedTaskBook,
                Collections.singletonList(emptyAddressBook),
                emptyAddressBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        versionedTaskBook.commit();
        assertAddressBookListStatus(versionedTaskBook,
                Arrays.asList(emptyAddressBook, addressBookWithAmy, addressBookWithBob),
                addressBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 2);

        versionedTaskBook.commit();
        assertAddressBookListStatus(versionedTaskBook,
                Collections.singletonList(emptyAddressBook),
                emptyAddressBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtEndOfStateList_returnsTrue() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertTrue(versionedTaskBook.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 1);

        assertTrue(versionedTaskBook.canUndo());
    }

    @Test
    public void canUndo_singleAddressBook_returnsFalse() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(emptyAddressBook);

        assertFalse(versionedTaskBook.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsFalse() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 2);

        assertFalse(versionedTaskBook.canUndo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 1);

        assertTrue(versionedTaskBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 2);

        assertTrue(versionedTaskBook.canRedo());
    }

    @Test
    public void canRedo_singleAddressBook_returnsFalse() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(emptyAddressBook);

        assertFalse(versionedTaskBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtEndOfStateList_returnsFalse() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertFalse(versionedTaskBook.canRedo());
    }

    @Test
    public void undo_multipleAddressBookPointerAtEndOfStateList_success() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        versionedTaskBook.undo();
        assertAddressBookListStatus(versionedTaskBook,
                Collections.singletonList(emptyAddressBook),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));
    }

    @Test
    public void undo_multipleAddressBookPointerNotAtStartOfStateList_success() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 1);

        versionedTaskBook.undo();
        assertAddressBookListStatus(versionedTaskBook,
                Collections.emptyList(),
                emptyAddressBook,
                Arrays.asList(addressBookWithAmy, addressBookWithBob));
    }

    @Test
    public void undo_singleAddressBook_throwsNoUndoableStateException() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(emptyAddressBook);

        assertThrows(VersionedTaskBook.NoUndoableStateException.class, versionedTaskBook::undo);
    }

    @Test
    public void undo_multipleAddressBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 2);

        assertThrows(VersionedTaskBook.NoUndoableStateException.class, versionedTaskBook::undo);
    }

    @Test
    public void redo_multipleAddressBookPointerNotAtEndOfStateList_success() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 1);

        versionedTaskBook.redo();
        assertAddressBookListStatus(versionedTaskBook,
                Arrays.asList(emptyAddressBook, addressBookWithAmy),
                addressBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleAddressBookPointerAtStartOfStateList_success() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 2);

        versionedTaskBook.redo();
        assertAddressBookListStatus(versionedTaskBook,
                Collections.singletonList(emptyAddressBook),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));
    }

    @Test
    public void redo_singleAddressBook_throwsNoRedoableStateException() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(emptyAddressBook);

        assertThrows(VersionedTaskBook.NoRedoableStateException.class, versionedTaskBook::redo);
    }

    @Test
    public void redo_multipleAddressBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertThrows(VersionedTaskBook.NoRedoableStateException.class, versionedTaskBook::redo);
    }

    @Test
    public void equals() {
        VersionedTaskBook versionedTaskBook = prepareAddressBookList(addressBookWithAmy, addressBookWithBob);

        // same values -> returns true
        VersionedTaskBook copy = prepareAddressBookList(addressBookWithAmy, addressBookWithBob);
        assertTrue(versionedTaskBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedTaskBook.equals(versionedTaskBook));

        // null -> returns false
        assertFalse(versionedTaskBook.equals(null));

        // different types -> returns false
        assertFalse(versionedTaskBook.equals(1));

        // different state list -> returns false
        VersionedTaskBook differentAddressBookList = prepareAddressBookList(addressBookWithBob, addressBookWithCarl);
        assertFalse(versionedTaskBook.equals(differentAddressBookList));

        // different current pointer index -> returns false
        VersionedTaskBook differentCurrentStatePointer = prepareAddressBookList(
                addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 1);
        assertFalse(versionedTaskBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedTaskBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedTaskBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedTaskBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertAddressBookListStatus(VersionedTaskBook versionedTaskBook,
                                             List<ReadOnlyTaskBook> expectedStatesBeforePointer,
                                             ReadOnlyTaskBook expectedCurrentState,
                                             List<ReadOnlyTaskBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new AddressBook(versionedTaskBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedTaskBook.canUndo()) {
            versionedTaskBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyTaskBook expectedAddressBook : expectedStatesBeforePointer) {
            assertEquals(expectedAddressBook, new AddressBook(versionedTaskBook));
            versionedTaskBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyTaskBook expectedAddressBook : expectedStatesAfterPointer) {
            versionedTaskBook.redo();
            assertEquals(expectedAddressBook, new AddressBook(versionedTaskBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedTaskBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedTaskBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedTaskBook} with the {@code addressBookStates} added into it, and the
     * {@code VersionedTaskBook#currentStatePointer} at the end of list.
     */
    private VersionedTaskBook prepareAddressBookList(ReadOnlyTaskBook... addressBookStates) {
        assertFalse(addressBookStates.length == 0);

        VersionedTaskBook versionedTaskBook = new VersionedTaskBook(addressBookStates[0]);
        for (int i = 1; i < addressBookStates.length; i++) {
            versionedTaskBook.resetData(addressBookStates[i]);
            versionedTaskBook.commit();
        }

        return versionedTaskBook;
    }

    /**
     * Shifts the {@code versionedTaskBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedTaskBook versionedTaskBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedTaskBook.undo();
        }
    }
}
