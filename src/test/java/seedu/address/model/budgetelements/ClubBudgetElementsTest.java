package seedu.address.model.budgetelements;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLUB_NAME_ECE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_TURNOUT_ECE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMBER_OF_EVENTS_ECE;
import static seedu.address.testutil.TypicalClubBudgetElements.COMPUTING_CLUB;
import static seedu.address.testutil.TypicalClubBudgetElements.ECE_CLUB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.ClubBudgetElementsBuilder;

public class ClubBudgetElementsTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameClubBudgetElements() {
        // same object -> returns true
        assertTrue(COMPUTING_CLUB.isSameClub(COMPUTING_CLUB));

        // null -> returns false
        assertFalse(COMPUTING_CLUB.isSameClub(null));

        // different name -> returns false
        ClubBudgetElements editedComputingClub = new ClubBudgetElementsBuilder(COMPUTING_CLUB)
                .withClubName(VALID_CLUB_NAME_ECE).build();
        assertFalse(COMPUTING_CLUB.isSameClub(editedComputingClub));

        // same club name, same expected turnout, different number of events -> returns true
        editedComputingClub = new ClubBudgetElementsBuilder(COMPUTING_CLUB)
                .withNumberOfEvents(VALID_NUMBER_OF_EVENTS_ECE).build();
        assertTrue(COMPUTING_CLUB.isSameClub(editedComputingClub));

        // same club name, same number of events, different expected turnout -> returns true
        editedComputingClub = new ClubBudgetElementsBuilder(COMPUTING_CLUB)
                .withExpectedTurnout(VALID_EXPECTED_TURNOUT_ECE).build();
        assertTrue(COMPUTING_CLUB.isSameClub(editedComputingClub));

    }

    @Test
    public void equals() {
        // same values -> returns true
        ClubBudgetElements computingClubCopy = new ClubBudgetElementsBuilder(COMPUTING_CLUB).build();
        assertTrue(COMPUTING_CLUB.equals(COMPUTING_CLUB));

        // same object -> returns true
        assertTrue(COMPUTING_CLUB.equals(COMPUTING_CLUB));

        // null -> returns false
        assertFalse(COMPUTING_CLUB.equals(null));

        // different type -> returns false
        assertFalse(COMPUTING_CLUB.equals(5));

        // different club -> returns false
        assertFalse(COMPUTING_CLUB.equals(ECE_CLUB));

        // different name -> returns false
        ClubBudgetElements editedComputingClub = new ClubBudgetElementsBuilder(COMPUTING_CLUB)
                .withClubName(VALID_CLUB_NAME_ECE).build();
        assertFalse(COMPUTING_CLUB.equals(editedComputingClub));

        // different expected turnout -> returns true
        editedComputingClub = new ClubBudgetElementsBuilder(COMPUTING_CLUB)
                .withExpectedTurnout(VALID_EXPECTED_TURNOUT_ECE).build();
        assertTrue(COMPUTING_CLUB.equals(editedComputingClub));

        // different number of events -> returns true
        editedComputingClub = new ClubBudgetElementsBuilder(COMPUTING_CLUB)
                .withNumberOfEvents(VALID_NUMBER_OF_EVENTS_ECE).build();
        assertTrue(COMPUTING_CLUB.equals(editedComputingClub));

    }
}
