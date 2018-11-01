package seedu.address.testutil;

import seedu.address.model.budgetelements.ClubBudgetElements;
import seedu.address.model.budgetelements.ClubName;
import seedu.address.model.budgetelements.ExpectedTurnout;
import seedu.address.model.budgetelements.NumberOfEvents;


/**
 * A utility class to help with building ClubBudgetElements objects.
 */
public class ClubBudgetElementsBuilder {

    public static final String DEFAULT_CLUB_NAME = "Computing Club";
    public static final String DEFAULT_EXPECTED_TURNOUT = "200";
    public static final String DEFAULT_NUMBER_OF_EVENTS = "5";

    private ClubName clubName;
    private ExpectedTurnout expectedTurnout;
    private NumberOfEvents numberOfEvents;

    public ClubBudgetElementsBuilder() {
        clubName = new ClubName(DEFAULT_CLUB_NAME);
        expectedTurnout = new ExpectedTurnout(DEFAULT_EXPECTED_TURNOUT);
        numberOfEvents = new NumberOfEvents(DEFAULT_NUMBER_OF_EVENTS);
    }

    /**
     * Initializes the ClubBudgetElementsBuilder with the data of {@code clubToCopy}.
     */
    public ClubBudgetElementsBuilder(ClubBudgetElements clubToCopy) {
        clubName = clubToCopy.getClubName();
        expectedTurnout = clubToCopy.getExpectedTurnout();
        numberOfEvents = clubToCopy.getNumberOfEvents();
    }

    /**
     * Sets the {@code ClubName} of the {@code ClubBudgetElements} that we are building.
     */
    public ClubBudgetElementsBuilder withClubName(String clubName) {
        this.clubName = new ClubName(clubName);
        return this;
    }

    /**
     * Sets the {@code ExpectedTurnout} of the {@code ClubBudgetElements} that we are building.
     */
    public ClubBudgetElementsBuilder withExpectedTurnout(String expectedTurnout) {
        this.expectedTurnout = new ExpectedTurnout(expectedTurnout);
        return this;
    }

    /**
     * Sets the {@code NumberOfEvents} of the {@code ClubBudgetElements} that we are building.
     */
    public ClubBudgetElementsBuilder withNumberOfEvents(String numberOfEvents) {
        this.numberOfEvents = new NumberOfEvents(numberOfEvents);
        return this;
    }

    public ClubBudgetElements build() {
        return new ClubBudgetElements(clubName, expectedTurnout, numberOfEvents);
    }

}
