package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_TURNOUT_ECE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMBER_OF_EVENTS_ECE;
import static seedu.address.testutil.TypicalClubBudgetElements.COMPUTING_CLUB;
import static seedu.address.testutil.TypicalClubBudgetElements.getTypicalClubBudgetElementsBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.budgetelements.ClubBudgetElements;
import seedu.address.model.budgetelements.exceptions.DuplicateClubException;
import seedu.address.testutil.ClubBudgetElementsBuilder;

public class ClubBudgetElementsBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ClubBudgetElementsBook clubBudgetElementsBook = new ClubBudgetElementsBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), clubBudgetElementsBook.getClubsList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clubBudgetElementsBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyClubBudgetElementsBook_replacesData() {
        ClubBudgetElementsBook newData = getTypicalClubBudgetElementsBook();
        clubBudgetElementsBook.resetData(newData);
        assertEquals(newData, clubBudgetElementsBook);
    }

    @Test
    public void resetData_withDuplicateClubBudgetElements_throwsDuplicateClubException() {
        // Two club budget elements with the same identity fields
        ClubBudgetElements editedClub1 = new ClubBudgetElementsBuilder(COMPUTING_CLUB)
                .withExpectedTurnout(VALID_EXPECTED_TURNOUT_ECE).withNumberOfEvents(VALID_NUMBER_OF_EVENTS_ECE).build();
        List<ClubBudgetElements> newClubs = Arrays.asList(COMPUTING_CLUB, editedClub1);
        ClubBudgetElementsBookStub newData = new ClubBudgetElementsBookStub(newClubs);

        thrown.expect(DuplicateClubException.class);
        clubBudgetElementsBook.resetData(newData);
    }

    @Test
    public void hasClubBudgetElements_nullClubBudgetElements_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clubBudgetElementsBook.hasClub(null);
    }

    @Test
    public void hasClubBudgetElements_clubBudgetElementsNotInClubBudgetElementsBook_returnsFalse() {
        assertFalse(clubBudgetElementsBook.hasClub(COMPUTING_CLUB));
    }

    @Test
    public void hasClubBudgetElements_clubBudgetElementsInClubBudgetElementsBook_returnsTrue() {
        clubBudgetElementsBook.addClub(COMPUTING_CLUB);
        assertTrue(clubBudgetElementsBook.hasClub(COMPUTING_CLUB));
    }

    @Test
    public void hasClubBudgetElements_clubBudgetElementsWithSameIdentityFieldsInClubBudgetElementsBook_returnsTrue() {
        clubBudgetElementsBook.addClub(COMPUTING_CLUB);
        ClubBudgetElements editedClub1 = new ClubBudgetElementsBuilder(COMPUTING_CLUB)
                .withExpectedTurnout(VALID_EXPECTED_TURNOUT_ECE).withNumberOfEvents(VALID_NUMBER_OF_EVENTS_ECE)
                .build();
        assertTrue(clubBudgetElementsBook.hasClub(editedClub1));
    }

    @Test
    public void getClubsList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        clubBudgetElementsBook.getClubsList().remove(0);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class ClubBudgetElementsBookStub implements ReadOnlyClubBudgetElementsBook {
        private final ObservableList<ClubBudgetElements> clubs = FXCollections.observableArrayList();

        ClubBudgetElementsBookStub(Collection<ClubBudgetElements> clubs) {
            this.clubs.setAll(clubs);
        }

        @Override
        public ObservableList<ClubBudgetElements> getClubsList() {
            return clubs;
        }
    }
}
