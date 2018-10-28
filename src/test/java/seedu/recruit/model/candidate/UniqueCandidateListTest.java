package seedu.recruit.model.candidate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.recruit.testutil.TypicalPersons.ALICE;
import static seedu.recruit.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recruit.model.candidate.exceptions.CandidateNotFoundException;
import seedu.recruit.model.candidate.exceptions.DuplicateCandidateException;
import seedu.recruit.testutil.CandidateBuilder;

public class UniqueCandidateListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueCandidateList uniqueCandidateList = new UniqueCandidateList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCandidateList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueCandidateList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueCandidateList.add(ALICE);
        assertTrue(uniqueCandidateList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueCandidateList.add(ALICE);
        Candidate editedAlice = new CandidateBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueCandidateList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCandidateList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueCandidateList.add(ALICE);
        thrown.expect(DuplicateCandidateException.class);
        uniqueCandidateList.add(ALICE);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCandidateList.setCandidate(null, ALICE);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCandidateList.setCandidate(ALICE, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(CandidateNotFoundException.class);
        uniqueCandidateList.setCandidate(ALICE, ALICE);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueCandidateList.add(ALICE);
        uniqueCandidateList.setCandidate(ALICE, ALICE);
        UniqueCandidateList expectedUniqueCandidateList = new UniqueCandidateList();
        expectedUniqueCandidateList.add(ALICE);
        assertEquals(expectedUniqueCandidateList, uniqueCandidateList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueCandidateList.add(ALICE);
        Candidate editedAlice = new CandidateBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueCandidateList.setCandidate(ALICE, editedAlice);
        UniqueCandidateList expectedUniqueCandidateList = new UniqueCandidateList();
        expectedUniqueCandidateList.add(editedAlice);
        assertEquals(expectedUniqueCandidateList, uniqueCandidateList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueCandidateList.add(ALICE);
        uniqueCandidateList.setCandidate(ALICE, BOB);
        UniqueCandidateList expectedUniqueCandidateList = new UniqueCandidateList();
        expectedUniqueCandidateList.add(BOB);
        assertEquals(expectedUniqueCandidateList, uniqueCandidateList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueCandidateList.add(ALICE);
        uniqueCandidateList.add(BOB);
        thrown.expect(DuplicateCandidateException.class);
        uniqueCandidateList.setCandidate(ALICE, BOB);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCandidateList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(CandidateNotFoundException.class);
        uniqueCandidateList.remove(ALICE);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueCandidateList.add(ALICE);
        uniqueCandidateList.remove(ALICE);
        UniqueCandidateList expectedUniqueCandidateList = new UniqueCandidateList();
        assertEquals(expectedUniqueCandidateList, uniqueCandidateList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCandidateList.setCandidates((UniqueCandidateList) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueCandidateList.add(ALICE);
        UniqueCandidateList expectedUniqueCandidateList = new UniqueCandidateList();
        expectedUniqueCandidateList.add(BOB);
        uniqueCandidateList.setCandidates(expectedUniqueCandidateList);
        assertEquals(expectedUniqueCandidateList, uniqueCandidateList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCandidateList.setCandidates((List<Candidate>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueCandidateList.add(ALICE);
        List<Candidate> candidateList = Collections.singletonList(BOB);
        uniqueCandidateList.setCandidates(candidateList);
        UniqueCandidateList expectedUniqueCandidateList = new UniqueCandidateList();
        expectedUniqueCandidateList.add(BOB);
        assertEquals(expectedUniqueCandidateList, uniqueCandidateList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Candidate> listWithDuplicateCandidates = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateCandidateException.class);
        uniqueCandidateList.setCandidates(listWithDuplicateCandidates);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueCandidateList.asUnmodifiableObservableList().remove(0);
    }
}
