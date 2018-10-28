package seedu.recruit.model.candidate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.recruit.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.recruit.testutil.TypicalPersons.ALICE;
import static seedu.recruit.testutil.TypicalPersons.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recruit.testutil.CandidateBuilder;

public class CandidateTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Candidate candidate = new CandidateBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        candidate.getTags().remove(0);
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSameCandidate(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameCandidate(null));

        // different phone and email -> returns false
        Candidate editedAlice = new CandidateBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                                    .build();
        assertFalse(ALICE.isSameCandidate(editedAlice));

        // different name -> returns false
        editedAlice = new CandidateBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameCandidate(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new CandidateBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCandidate(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new CandidateBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCandidate(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new CandidateBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCandidate(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Candidate aliceCopy = new CandidateBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different candidate -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Candidate editedAlice = new CandidateBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new CandidateBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new CandidateBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different recruit -> returns false
        editedAlice = new CandidateBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new CandidateBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
