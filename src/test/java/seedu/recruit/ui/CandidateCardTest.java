package seedu.recruit.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.testutil.PersonBuilder;

public class CandidateCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Candidate candidateWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(candidateWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, candidateWithNoTags, 1);

        // with tags
        Candidate candidateWithTags = new PersonBuilder().build();
        personCard = new PersonCard(candidateWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, candidateWithTags, 2);
    }

    @Test
    public void equals() {
        Candidate candidate = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(candidate, 0);

        // same candidate, same index -> returns true
        PersonCard copy = new PersonCard(candidate, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different candidate, same index -> returns false
        Candidate differentCandidate = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentCandidate, 0)));

        // same candidate, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(candidate, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedCandidate} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, Candidate expectedCandidate, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify candidate details are displayed correctly
        assertCardDisplaysPerson(expectedCandidate, personCardHandle);
    }
}
