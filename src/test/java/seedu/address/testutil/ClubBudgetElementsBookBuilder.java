package seedu.address.testutil;

import seedu.address.model.ClubBudgetElementsBook;
import seedu.address.model.budgetelements.ClubBudgetElements;


/**
 * A utility class to help with building ClubBudgetElementsBook objects.
 */
public class ClubBudgetElementsBookBuilder {

    private ClubBudgetElementsBook clubBudgetElementsBook;

    public ClubBudgetElementsBookBuilder() {
        clubBudgetElementsBook = new ClubBudgetElementsBook();
    }

    public ClubBudgetElementsBookBuilder(ClubBudgetElementsBook clubBudgetElementsBook) {
        this.clubBudgetElementsBook = clubBudgetElementsBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public ClubBudgetElementsBookBuilder withClubBudgetElements(ClubBudgetElements clubBudgetElements) {
        clubBudgetElementsBook.addClub(clubBudgetElements);
        return this;
    }

    public ClubBudgetElementsBook build() {
        return clubBudgetElementsBook;
    }
}
