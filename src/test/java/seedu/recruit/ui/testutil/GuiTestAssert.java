package seedu.recruit.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.recruit.model.candidate.Candidate;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedCandidate}.
     */
    public static void assertCardDisplaysPerson(Candidate expectedCandidate, PersonCardHandle actualCard) {
        assertEquals(expectedCandidate.getName().fullName, actualCard.getName());
        assertEquals(expectedCandidate.getPhone().value, actualCard.getPhone());
        assertEquals(expectedCandidate.getEmail().value, actualCard.getEmail());
        assertEquals(expectedCandidate.getAddress().value, actualCard.getAddress());
        assertEquals(expectedCandidate.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code candidates} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Candidate... candidates) {
        for (int i = 0; i < candidates.length; i++) {
            personListPanelHandle.navigateToCard(i);
            assertCardDisplaysPerson(candidates[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code candidates} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Candidate> candidates) {
        assertListMatching(personListPanelHandle, candidates.toArray(new Candidate[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
