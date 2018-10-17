package seedu.recruit.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Ignore;
import org.junit.Test;

import guitests.guihandles.CandidateCardHandle;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.testutil.PersonBuilder;

@Ignore
public class CandidateCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Candidate candidateWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        CandidateCard candidateCard = new CandidateCard(candidateWithNoTags, 1);
        uiPartRule.setUiPart(candidateCard);
        assertCardDisplay(candidateCard, candidateWithNoTags, 1);

        // with tags
        Candidate candidateWithTags = new PersonBuilder().build();
        candidateCard = new CandidateCard(candidateWithTags, 2);
        uiPartRule.setUiPart(candidateCard);
        assertCardDisplay(candidateCard, candidateWithTags, 2);
    }

    @Test
    public void equals() {
        Candidate candidate = new PersonBuilder().build();
        CandidateCard candidateCard = new CandidateCard(candidate, 0);

        // same candidate, same index -> returns true
        CandidateCard copy = new CandidateCard(candidate, 0);
        assertTrue(candidateCard.equals(copy));

        // same object -> returns true
        assertTrue(candidateCard.equals(candidateCard));

        // null -> returns false
        assertFalse(candidateCard.equals(null));

        // different types -> returns false
        assertFalse(candidateCard.equals(0));

        // different candidate, same index -> returns false
        Candidate differentCandidate = new PersonBuilder().withName("differentName").build();
        assertFalse(candidateCard.equals(new CandidateCard(differentCandidate, 0)));

        // same candidate, different index -> returns false
        assertFalse(candidateCard.equals(new CandidateCard(candidate, 1)));
    }

    /**
     * Asserts that {@code candidateCard} displays the details of {@code expectedCandidate} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(CandidateCard candidateCard, Candidate expectedCandidate, int expectedId) {
        guiRobot.pauseForHuman();

        CandidateCardHandle candidateCardHandle = new CandidateCardHandle(candidateCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", candidateCardHandle.getId());

        // verify candidate details are displayed correctly
        assertCardDisplaysPerson(expectedCandidate, candidateCardHandle);
    }
}
