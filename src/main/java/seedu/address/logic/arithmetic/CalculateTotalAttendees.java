package seedu.address.logic.arithmetic;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.model.budgetelements.ClubBudgetElements;

/**
 * Calculates the total number of attendees across all events, based on the club budget elements entered by club
 * treasure.
 */
public class CalculateTotalAttendees {
    private final List<ClubBudgetElements> listOfClubs;
    /**
     * @param listOfClubs is the filtered list with which to calculate totalAttendees
     */
    public CalculateTotalAttendees(List<ClubBudgetElements> listOfClubs) {
        requireNonNull(listOfClubs);
        this.listOfClubs = listOfClubs;
    }

    /**
     * @return the totalAttendees
     */
    public int arithmeticTotalAttendees() {

        int totalAttendees = 0;

        for (int i = 0; i < listOfClubs.size(); i++) {

            ClubBudgetElements currentClub = listOfClubs.get(i);

            int currentExpectedTurnout = Integer.parseInt(currentClub.getExpectedTurnout().toString());

            int currentNumberOfEvents = Integer.parseInt(currentClub.getNumberOfEvents().toString());

            totalAttendees += (currentExpectedTurnout * currentNumberOfEvents);

        }

        return totalAttendees;
    }
}
