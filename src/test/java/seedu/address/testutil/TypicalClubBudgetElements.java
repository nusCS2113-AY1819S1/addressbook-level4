package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CLUB_NAME_COMPUTING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLUB_NAME_ECE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_TURNOUT_COMPUTING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_TURNOUT_ECE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMBER_OF_EVENTS_COMPUTING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMBER_OF_EVENTS_ECE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.budgetelements.ClubBudgetElements;


/**
 * A utility class containing a list of {@code ClubBudgetElements} objects to be used in tests.
 */
public class TypicalClubBudgetElements {

    public static final ClubBudgetElements CLUB_1 = new ClubBudgetElementsBuilder().withClubName("Computing Club")
            .withExpectedTurnout("200").withNumberOfEvents("5").build();

    public static final ClubBudgetElements CLUB_2 = new ClubBudgetElementsBuilder().withClubName("ECE Club")
            .withExpectedTurnout("300").withNumberOfEvents("7").build();

    public static final ClubBudgetElements CLUB_3 = new ClubBudgetElementsBuilder().withClubName("Film Club")
            .withExpectedTurnout("150").withNumberOfEvents("3").build();

    public static final ClubBudgetElements CLUB_4 = new ClubBudgetElementsBuilder().withClubName("Photography Club")
            .withExpectedTurnout("250").withNumberOfEvents("2").build();

    public static final ClubBudgetElements CLUB_5 = new ClubBudgetElementsBuilder().withClubName("Dance Club")
            .withExpectedTurnout("400").withNumberOfEvents("3").build();

    // Manually added - Club's details found in {@code CommandTestUtil}
    public static final ClubBudgetElements COMPUTING_CLUB = new ClubBudgetElementsBuilder()
            .withClubName(VALID_CLUB_NAME_COMPUTING)
            .withExpectedTurnout(VALID_EXPECTED_TURNOUT_COMPUTING).withNumberOfEvents(VALID_NUMBER_OF_EVENTS_COMPUTING)
            .build();

    public static final ClubBudgetElements ECE_CLUB = new ClubBudgetElementsBuilder().withClubName(VALID_CLUB_NAME_ECE)
            .withExpectedTurnout(VALID_EXPECTED_TURNOUT_ECE).withNumberOfEvents(VALID_NUMBER_OF_EVENTS_ECE).build();

    private TypicalClubBudgetElements() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical clubs.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ClubBudgetElements club : getTypicalClubBudgetElements()) {
            ab.addClub(club);
        }
        return ab;
    }

    /**
     * Returns a list of all typical clubs
     */
    public static List<ClubBudgetElements> getTypicalClubBudgetElements() {
        return new ArrayList<>(Arrays.asList(CLUB_1, CLUB_2, CLUB_3, CLUB_4, CLUB_5));
    }

}
