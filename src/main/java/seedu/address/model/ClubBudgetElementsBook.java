package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.budgetelements.ClubBudgetElements;
import seedu.address.model.budgetelements.UniqueClubsList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameClub comparison)
 */
public class ClubBudgetElementsBook implements ReadOnlyClubBudgetElementsBook {


    private final UniqueClubsList clubs;
    {
        clubs = new UniqueClubsList();
    }

    public ClubBudgetElementsBook() {}

    /**
     * Creates a ClubBudgetElementsBook using the ClubBudgetElements in the {@code toBeCopied}
     */
    public ClubBudgetElementsBook(ReadOnlyClubBudgetElementsBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the club budget elements list with {@code club budget elements}.
     * {@code club budget elements} must not contain duplicate club budget elements.
     */
    public void setClubs(List<ClubBudgetElements> clubs) {
        this.clubs.setClubs(clubs);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyClubBudgetElementsBook newData) {
        requireNonNull(newData);

        setClubs(newData.getClubsList());
    }

    //// club budget elements -level operations

    /**
     * Returns true if a club with the same identity as {@code club} exists in the address book.
     */
    public boolean hasClub(ClubBudgetElements club) {
        requireNonNull(club);
        return clubs.contains(club);
    }

    /**
     * Adds a club to the address book.
     * The club must not already exist in the address book.
     */
    public void addClub(ClubBudgetElements c) {
        clubs.add(c);
    }

    //// util methods

    @Override
    public ObservableList<ClubBudgetElements> getClubsList() {
        return clubs.asUnmodifiableObservableList(); }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClubBudgetElementsBook // instanceof handles nulls
                && clubs.equals(((ClubBudgetElementsBook) other).clubs));
    }

    @Override
    public int hashCode() {
        return clubs.hashCode();
    }
}
