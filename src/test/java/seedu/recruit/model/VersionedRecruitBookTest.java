package seedu.recruit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.recruit.testutil.TypicalPersons.AMY;
import static seedu.recruit.testutil.TypicalPersons.BOB;
import static seedu.recruit.testutil.TypicalPersons.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.recruit.testutil.RecruitBookBuilder;

public class VersionedRecruitBookTest {

    private final RecruitBook recruitBookWithAmy = new
            RecruitBookBuilder().withCandidate(AMY).buildRecruitBook();
    private final RecruitBook recruitBookWithBob = new
            RecruitBookBuilder().withCandidate(BOB).buildRecruitBook();
    private final RecruitBook recruitBookWithCarl = new
            RecruitBookBuilder().withCandidate(CARL).buildRecruitBook();
    private final RecruitBook emptyRecruitBook = new RecruitBookBuilder().buildRecruitBook();

    @Test
    public void commit_singleAddressBook_noStatesRemovedCurrentStateSaved() {
        VersionedRecruitBook versionedAddressBook = prepareRecruitBookList(emptyRecruitBook);

        versionedAddressBook.commit();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.singletonList(emptyRecruitBook),
                emptyRecruitBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedRecruitBook versionedAddressBook = prepareRecruitBookList(
                emptyRecruitBook, recruitBookWithAmy, recruitBookWithBob);

        versionedAddressBook.commit();
        assertAddressBookListStatus(versionedAddressBook,
                Arrays.asList(emptyRecruitBook, recruitBookWithAmy, recruitBookWithBob),
                recruitBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedRecruitBook versionedAddressBook = prepareRecruitBookList(
                emptyRecruitBook, recruitBookWithAmy, recruitBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        versionedAddressBook.commit();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.singletonList(emptyRecruitBook),
                emptyRecruitBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtEndOfStateList_returnsTrue() {
        VersionedRecruitBook versionedAddressBook = prepareRecruitBookList(
                emptyRecruitBook, recruitBookWithAmy, recruitBookWithBob);

        assertTrue(versionedAddressBook.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedRecruitBook versionedAddressBook = prepareRecruitBookList(
                emptyRecruitBook, recruitBookWithAmy, recruitBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        assertTrue(versionedAddressBook.canUndo());
    }

    @Test
    public void canUndo_singleAddressBook_returnsFalse() {
        VersionedRecruitBook versionedAddressBook = prepareRecruitBookList(emptyRecruitBook);

        assertFalse(versionedAddressBook.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsFalse() {
        VersionedRecruitBook versionedAddressBook = prepareRecruitBookList(
                emptyRecruitBook, recruitBookWithAmy, recruitBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        assertFalse(versionedAddressBook.canUndo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedRecruitBook versionedAddressBook = prepareRecruitBookList(
                emptyRecruitBook, recruitBookWithAmy, recruitBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        assertTrue(versionedAddressBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedRecruitBook versionedAddressBook = prepareRecruitBookList(
                emptyRecruitBook, recruitBookWithAmy, recruitBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        assertTrue(versionedAddressBook.canRedo());
    }

    @Test
    public void canRedo_singleAddressBook_returnsFalse() {
        VersionedRecruitBook versionedAddressBook = prepareRecruitBookList(emptyRecruitBook);

        assertFalse(versionedAddressBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtEndOfStateList_returnsFalse() {
        VersionedRecruitBook versionedAddressBook = prepareRecruitBookList(
                emptyRecruitBook, recruitBookWithAmy, recruitBookWithBob);

        assertFalse(versionedAddressBook.canRedo());
    }

    @Test
    public void undo_multipleAddressBookPointerAtEndOfStateList_success() {
        VersionedRecruitBook versionedAddressBook = prepareRecruitBookList(
                emptyRecruitBook, recruitBookWithAmy, recruitBookWithBob);

        versionedAddressBook.undo();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.singletonList(emptyRecruitBook),
                recruitBookWithAmy,
                Collections.singletonList(recruitBookWithBob));
    }

    @Test
    public void undo_multipleAddressBookPointerNotAtStartOfStateList_success() {
        VersionedRecruitBook versionedAddressBook = prepareRecruitBookList(
                emptyRecruitBook, recruitBookWithAmy, recruitBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        versionedAddressBook.undo();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.emptyList(),
                emptyRecruitBook,
                Arrays.asList(recruitBookWithAmy, recruitBookWithBob));
    }

    @Test
    public void undo_singleAddressBook_throwsNoUndoableStateException() {
        VersionedRecruitBook versionedAddressBook = prepareRecruitBookList(emptyRecruitBook);

        assertThrows(VersionedRecruitBook.NoUndoableStateException.class, versionedAddressBook::undo);
    }

    @Test
    public void undo_multipleAddressBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedRecruitBook versionedAddressBook = prepareRecruitBookList(
                emptyRecruitBook, recruitBookWithAmy, recruitBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        assertThrows(VersionedRecruitBook.NoUndoableStateException.class, versionedAddressBook::undo);
    }

    @Test
    public void redo_multipleAddressBookPointerNotAtEndOfStateList_success() {
        VersionedRecruitBook versionedAddressBook = prepareRecruitBookList(
                emptyRecruitBook, recruitBookWithAmy, recruitBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        versionedAddressBook.redo();
        assertAddressBookListStatus(versionedAddressBook,
                Arrays.asList(emptyRecruitBook, recruitBookWithAmy),
                recruitBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleAddressBookPointerAtStartOfStateList_success() {
        VersionedRecruitBook versionedAddressBook = prepareRecruitBookList(
                emptyRecruitBook, recruitBookWithAmy, recruitBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        versionedAddressBook.redo();
        assertAddressBookListStatus(versionedAddressBook,
                Collections.singletonList(emptyRecruitBook),
                recruitBookWithAmy,
                Collections.singletonList(recruitBookWithBob));
    }

    @Test
    public void redo_singleAddressBook_throwsNoRedoableStateException() {
        VersionedRecruitBook versionedAddressBook = prepareRecruitBookList(emptyRecruitBook);

        assertThrows(VersionedRecruitBook.NoRedoableStateException.class, versionedAddressBook::redo);
    }

    @Test
    public void redo_multipleAddressBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedRecruitBook versionedAddressBook = prepareRecruitBookList(
                emptyRecruitBook, recruitBookWithAmy, recruitBookWithBob);

        assertThrows(VersionedRecruitBook.NoRedoableStateException.class, versionedAddressBook::redo);
    }

    @Test
    public void equals() {
        VersionedRecruitBook versionedAddressBook = prepareRecruitBookList(recruitBookWithAmy, recruitBookWithBob);

        // same values -> returns true
        VersionedRecruitBook copy = prepareRecruitBookList(recruitBookWithAmy, recruitBookWithBob);
        assertTrue(versionedAddressBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedAddressBook.equals(versionedAddressBook));

        // null -> returns false
        assertFalse(versionedAddressBook.equals(null));

        // different types -> returns false
        assertFalse(versionedAddressBook.equals(1));

        // different state list -> returns false
        VersionedRecruitBook differentAddressBookList = prepareRecruitBookList(recruitBookWithBob,
                recruitBookWithCarl);
        assertFalse(versionedAddressBook.equals(differentAddressBookList));

        // different current pointer index -> returns false
        VersionedRecruitBook differentCurrentStatePointer = prepareRecruitBookList(
                recruitBookWithAmy, recruitBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);
        assertFalse(versionedAddressBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedRecruitBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedRecruitBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedRecruitBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertAddressBookListStatus(VersionedRecruitBook versionedRecruitBook,
                                             List<RecruitBook> expectedStatesBeforePointer,
                                             RecruitBook expectedCurrentState,
                                             List<RecruitBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new RecruitBook(versionedRecruitBook.getCandidateBook(),
                versionedRecruitBook.getCompanyBook()), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedRecruitBook.canUndo()) {
            versionedRecruitBook.undo();
        }

        // check states before pointer are correct
        for (RecruitBook expectedAddressBook : expectedStatesBeforePointer) {
            assertEquals(expectedAddressBook,
                    new RecruitBook(versionedRecruitBook.getCandidateBook(), versionedRecruitBook.getCompanyBook()));
            versionedRecruitBook.redo();
        }

        // check states after pointer are correct
        for (RecruitBook expectedAddressBook : expectedStatesAfterPointer) {
            versionedRecruitBook.redo();
            assertEquals(expectedAddressBook, new RecruitBook(versionedRecruitBook.getCandidateBook(),
                    versionedRecruitBook.getCompanyBook()));
        }

        // check that there are no more states after pointer
        assertFalse(versionedRecruitBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedRecruitBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedRecruitBook} with the {@code recruitBookStates} added into it, and the
     * {@code VersionedRecruitBook#currentStatePointer} at the end of list.
     */
    private VersionedRecruitBook prepareRecruitBookList(RecruitBook... recruitBookStates) {
        assertFalse(recruitBookStates.length == 0);

        VersionedRecruitBook versionedRecruitBook =
            new VersionedRecruitBook(recruitBookStates[0].getCandidateBook(), recruitBookStates[0].getCompanyBook());
        for (int i = 1; i < recruitBookStates.length; i++) {
            versionedRecruitBook
                    .resetData(recruitBookStates[i].getCandidateBook(), recruitBookStates[i].getCompanyBook());
            versionedRecruitBook.commit();
        }

        return versionedRecruitBook;
    }

    /**
     * Shifts the {@code versionedRecruitBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedRecruitBook versionedRecruitBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedRecruitBook.undo();
        }
    }
}
