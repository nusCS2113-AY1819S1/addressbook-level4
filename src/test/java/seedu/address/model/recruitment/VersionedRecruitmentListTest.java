package seedu.address.model.recruitment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalRecruitments.RECRUITMENT_EXAMPLE;
import static seedu.address.testutil.TypicalRecruitments.RECRUITMENT_EXAMPLE1;
import static seedu.address.testutil.TypicalRecruitments.RECRUITMENT_EXAMPLE2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.recruitment.RecruitmentListBuilder;

public class VersionedRecruitmentListTest {

    private final ReadOnlyRecruitmentList recruitmentListWithExample =
            new RecruitmentListBuilder().withRecruitment(RECRUITMENT_EXAMPLE).build();
    private final ReadOnlyRecruitmentList recruitmentListWithExample1 =
            new RecruitmentListBuilder().withRecruitment(RECRUITMENT_EXAMPLE1).build();
    private final ReadOnlyRecruitmentList recruitmentListWithExample2 =
            new RecruitmentListBuilder().withRecruitment(RECRUITMENT_EXAMPLE2).build();
    private final ReadOnlyRecruitmentList emptyRecruitmentList = new RecruitmentListBuilder().build();

    @Test
    public void commit_singleRecruitmentList_noStatesRemovedCurrentStateSaved() {
        VersionedRecruitmentList versionedRecruitmentList = prepareRecruitmentList(emptyRecruitmentList);

        versionedRecruitmentList.commit();
        assertRecruitmentListListStatus(versionedRecruitmentList,
                Collections.singletonList(emptyRecruitmentList),
                emptyRecruitmentList,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleRecruitmentListPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedRecruitmentList versionedRecruitmentList = prepareRecruitmentList(
                emptyRecruitmentList, recruitmentListWithExample, recruitmentListWithExample1);

        versionedRecruitmentList.commit();
        assertRecruitmentListListStatus(versionedRecruitmentList,
                Arrays.asList(emptyRecruitmentList, recruitmentListWithExample, recruitmentListWithExample1),
                recruitmentListWithExample1,
                Collections.emptyList());
    }

    @Test
    public void commit_multiplRecruitmentListPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedRecruitmentList versionedRecruitmentList = prepareRecruitmentList(
                emptyRecruitmentList, recruitmentListWithExample, recruitmentListWithExample1);
        shiftCurrentStatePointerLeftwards(versionedRecruitmentList, 2);

        versionedRecruitmentList.commit();
        assertRecruitmentListListStatus(versionedRecruitmentList,
                Collections.singletonList(emptyRecruitmentList),
                emptyRecruitmentList,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleRecruitmentListPointerAtEndOfStateList_returnsTrue() {
        VersionedRecruitmentList versionedRecruitmentList = prepareRecruitmentList(
                emptyRecruitmentList, recruitmentListWithExample, recruitmentListWithExample1);

        assertTrue(versionedRecruitmentList.canUndo());
    }

    @Test
    public void canUndo_multipleRecruitmentListPointerAtStartOfStateList_returnsTrue() {
        VersionedRecruitmentList versionedRecruitmentList = prepareRecruitmentList(
                emptyRecruitmentList, recruitmentListWithExample, recruitmentListWithExample1);
        shiftCurrentStatePointerLeftwards(versionedRecruitmentList, 1);

        assertTrue(versionedRecruitmentList.canUndo());
    }

    @Test
    public void canUndo_singleRecruitmentList_returnsFalse() {
        VersionedRecruitmentList versionedRecruitmentList = prepareRecruitmentList(emptyRecruitmentList);

        assertFalse(versionedRecruitmentList.canUndo());
    }

    @Test
    public void canUndo_multipleRecruitmentListPointerAtStartOfStateList_returnsFalse() {
        VersionedRecruitmentList versionedRecruitmentList = prepareRecruitmentList(
                emptyRecruitmentList, recruitmentListWithExample, recruitmentListWithExample1);
        shiftCurrentStatePointerLeftwards(versionedRecruitmentList, 2);

        assertFalse(versionedRecruitmentList.canUndo());
    }

    @Test
    public void canRedo_multipleRecruitmentListPointerNotAtEndOfStateList_returnsTrue() {
        VersionedRecruitmentList versionedRecruitmentList = prepareRecruitmentList(
                emptyRecruitmentList, recruitmentListWithExample, recruitmentListWithExample1);
        shiftCurrentStatePointerLeftwards(versionedRecruitmentList, 1);

        assertTrue(versionedRecruitmentList.canRedo());
    }

    @Test
    public void canRedo_multipleRecruitmentListPointerAtStartOfStateList_returnsTrue() {
        VersionedRecruitmentList versionedRecruitmentList = prepareRecruitmentList(
                emptyRecruitmentList, recruitmentListWithExample, recruitmentListWithExample1);
        shiftCurrentStatePointerLeftwards(versionedRecruitmentList, 2);

        assertTrue(versionedRecruitmentList.canRedo());
    }

    @Test
    public void canRedo_singleRecruitmentList_returnsFalse() {
        VersionedRecruitmentList versionedRecruitmentList = prepareRecruitmentList(emptyRecruitmentList);

        assertFalse(versionedRecruitmentList.canRedo());
    }

    @Test
    public void canRedo_multipleRecruitmentListPointerAtEndOfStateList_returnsFalse() {
        VersionedRecruitmentList versionedRecruitmentList = prepareRecruitmentList(
                emptyRecruitmentList, recruitmentListWithExample, recruitmentListWithExample1);

        assertFalse(versionedRecruitmentList.canRedo());
    }

    @Test
    public void undo_multipleRecruitmentListPointerAtEndOfStateList_success() {
        VersionedRecruitmentList versionedRecruitmentList = prepareRecruitmentList(
                emptyRecruitmentList, recruitmentListWithExample, recruitmentListWithExample1);

        versionedRecruitmentList.undo();
        assertRecruitmentListListStatus(versionedRecruitmentList,
                Collections.singletonList(emptyRecruitmentList),
                recruitmentListWithExample,
                Collections.singletonList(recruitmentListWithExample1));
    }

    @Test
    public void undo_multipleRecruitmentListPointerNotAtStartOfStateList_success() {
        VersionedRecruitmentList versionedRecruitmentList = prepareRecruitmentList(
                emptyRecruitmentList, recruitmentListWithExample, recruitmentListWithExample1);
        shiftCurrentStatePointerLeftwards(versionedRecruitmentList, 1);

        versionedRecruitmentList.undo();
        assertRecruitmentListListStatus(versionedRecruitmentList,
                Collections.emptyList(),
                emptyRecruitmentList,
                Arrays.asList(recruitmentListWithExample, recruitmentListWithExample1));
    }

    @Test
    public void undo_singleRecruitmentList_throwsNoUndoableStateException() {
        VersionedRecruitmentList versionedRecruitmentList = prepareRecruitmentList(emptyRecruitmentList);

        assertThrows(VersionedRecruitmentList.NoUndoableStateException.class, versionedRecruitmentList::undo);
    }

    @Test
    public void undo_multipleRecruitmentListPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedRecruitmentList versionedRecruitmentList = prepareRecruitmentList(
                emptyRecruitmentList, recruitmentListWithExample, recruitmentListWithExample1);
        shiftCurrentStatePointerLeftwards(versionedRecruitmentList, 2);

        assertThrows(VersionedRecruitmentList.NoUndoableStateException.class, versionedRecruitmentList::undo);
    }

    @Test
    public void redo_multipleRecruitmentListPointerNotAtEndOfStateList_success() {
        VersionedRecruitmentList versionedRecruitmentList = prepareRecruitmentList(
                emptyRecruitmentList, recruitmentListWithExample, recruitmentListWithExample1);
        shiftCurrentStatePointerLeftwards(versionedRecruitmentList, 1);

        versionedRecruitmentList.redo();
        assertRecruitmentListListStatus(versionedRecruitmentList,
                Arrays.asList(emptyRecruitmentList, recruitmentListWithExample),
                recruitmentListWithExample1,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleRecruitmentListPointerAtStartOfStateList_success() {
        VersionedRecruitmentList versionedRecruitmentList = prepareRecruitmentList(
                emptyRecruitmentList, recruitmentListWithExample, recruitmentListWithExample1);
        shiftCurrentStatePointerLeftwards(versionedRecruitmentList, 2);

        versionedRecruitmentList.redo();
        assertRecruitmentListListStatus(versionedRecruitmentList,
                Collections.singletonList(emptyRecruitmentList),
                recruitmentListWithExample,
                Collections.singletonList(recruitmentListWithExample1));
    }

    @Test
    public void redo_singleRecruitmentList_throwsNoRedoableStateException() {
        VersionedRecruitmentList versionedRecruitmentList = prepareRecruitmentList(emptyRecruitmentList);
        assertThrows(VersionedRecruitmentList.NoRedoableStateException.class, versionedRecruitmentList::redo);
    }

    @Test
    public void redo_multipleRecruitmentListPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedRecruitmentList versionedRecruitmentList = prepareRecruitmentList(
                emptyRecruitmentList, recruitmentListWithExample, recruitmentListWithExample1);

        assertThrows(VersionedRecruitmentList.NoRedoableStateException.class, versionedRecruitmentList::redo);
    }

    @Test
    public void equals() {
        VersionedRecruitmentList versionedRecruitmentList = prepareRecruitmentList(recruitmentListWithExample,
                recruitmentListWithExample1);

        // same values -> returns true
        VersionedRecruitmentList copy = prepareRecruitmentList(recruitmentListWithExample, recruitmentListWithExample1);
        assertTrue(versionedRecruitmentList.equals(copy));

        // same object -> returns true
        assertTrue(versionedRecruitmentList.equals(versionedRecruitmentList));

        // null -> returns false
        assertFalse(versionedRecruitmentList.equals(null));

        // different posts -> returns false
        assertFalse(versionedRecruitmentList.equals(1));

        // different state list -> returns false
        VersionedRecruitmentList differentRecruitmentList = prepareRecruitmentList(recruitmentListWithExample1,
                recruitmentListWithExample2);
        assertFalse(versionedRecruitmentList.equals(differentRecruitmentList));

        // different current pointer index -> returns false
        VersionedRecruitmentList differentCurrentStatePointer = prepareRecruitmentList(
                recruitmentListWithExample, recruitmentListWithExample1);
        shiftCurrentStatePointerLeftwards(versionedRecruitmentList, 1);
        assertFalse(versionedRecruitmentList.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedRecruitmentList} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedRecruitmentList#currentStatePointer} is equal to {@code
     * expectedStatesBeforePointer},
     * and states after {@code versionedRecruitmentList#currentStatePointer} is equal to
     * {@code expectedStatesAfterPointer}.
     */
    private void assertRecruitmentListListStatus(VersionedRecruitmentList versionedRecruitmentList,
                                                 List<ReadOnlyRecruitmentList> expectedStatesBeforePointer,
                                                 ReadOnlyRecruitmentList expectedCurrentState,
                                                 List<ReadOnlyRecruitmentList> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new RecruitmentList(versionedRecruitmentList), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedRecruitmentList.canUndo()) {
            versionedRecruitmentList.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyRecruitmentList expectedRecruitmentList : expectedStatesBeforePointer) {
            assertEquals(expectedRecruitmentList, new RecruitmentList(versionedRecruitmentList));
            versionedRecruitmentList.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyRecruitmentList expectedRecruitmentList : expectedStatesAfterPointer) {
            versionedRecruitmentList.redo();
            assertEquals(expectedRecruitmentList, new RecruitmentList(versionedRecruitmentList));
        }

        // check that there are no more states after pointer
        assertFalse(versionedRecruitmentList.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedRecruitmentList.undo());
    }

    /**
     * Creates and returns a {@code versionedRecruitmentList} with the {@code recruitmentListStates} added into it,
     * and the
     * {@code versionedRecruitmentList#currentStatePointer} at the end of list.
     */
    private VersionedRecruitmentList prepareRecruitmentList(ReadOnlyRecruitmentList... recruitmentListStates) {
        assertFalse(recruitmentListStates.length == 0);

        VersionedRecruitmentList versionedRecruitmentList = new VersionedRecruitmentList(recruitmentListStates[0]);
        for (int i = 1; i < recruitmentListStates.length; i++) {
            versionedRecruitmentList.resetData(recruitmentListStates[i]);
            versionedRecruitmentList.commit();
        }

        return versionedRecruitmentList;
    }

    /**
     * Shifts the {@code versionedRecruitmentList#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedRecruitmentList versionedRecruitmentList, int count) {
        for (int i = 0; i < count; i++) {
            versionedRecruitmentList.undo();
        }
    }

}
