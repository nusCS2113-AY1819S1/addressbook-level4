package seedu.address.model.budgetelements;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_TURNOUT_ECE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMBER_OF_EVENTS_ECE;
import static seedu.address.testutil.TypicalClubBudgetElements.COMPUTING_CLUB;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.model.budgetelements.exceptions.DuplicateClubException;
import seedu.address.testutil.ClubBudgetElementsBuilder;

public class UniqueClubsListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueClubsList uniqueClubsList = new UniqueClubsList();

    @Test
    public void contains_nullClubBudgetElements_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueClubsList.contains(null);
    }

    @Test
    public void contains_clubNotInList_returnsFalse() {
        assertFalse(uniqueClubsList.contains(COMPUTING_CLUB));
    }

    @Test
    public void contains_clubInList_returnsTrue() {
        uniqueClubsList.add(COMPUTING_CLUB);
        assertTrue(uniqueClubsList.contains(COMPUTING_CLUB));
    }

    @Test
    public void contains_clubWithSameIdentityFieldsInList_returnsTrue() {
        uniqueClubsList.add(COMPUTING_CLUB);
        ClubBudgetElements editedComputingClub = new ClubBudgetElementsBuilder(COMPUTING_CLUB)
                .withExpectedTurnout(VALID_EXPECTED_TURNOUT_ECE).withNumberOfEvents(VALID_NUMBER_OF_EVENTS_ECE)
                .build();
        assertTrue(uniqueClubsList.contains(editedComputingClub));
    }

    @Test
    public void add_nullClub_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueClubsList.add(null);
    }

    @Test
    public void add_duplicateClub_throwsDuplicateClubException() {
        uniqueClubsList.add(COMPUTING_CLUB);
        thrown.expect(DuplicateClubException.class);
        uniqueClubsList.add(COMPUTING_CLUB);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueClubsList.asUnmodifiableObservableList().remove(0);
    }
}
