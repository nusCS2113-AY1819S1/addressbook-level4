package seedu.address.model.budgetelements;

import static java.util.Objects.requireNonNull;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of clubs that enforces uniqueness between its elements and does not allow nulls.
 * A club is considered unique by comparing using {@code ClubBudgetElements#isSameClub(ClubBudgetElements)}. As such adding of
 * clubs uses CLubBudgetElements#isSameClub(ClubBudgetElements) for equality so as to ensure that the club being added is
 * unique in terms of identity in the UniqueClubsList.
 *
 * Supports a minimal set of list operations.
 *
 * @see ClubBudgetElements#isSameClub(ClubBudgetElements)
 */
public class UniqueClubsList implements Iterable<ClubBudgetElements> {

    private final ObservableList<ClubBudgetElements> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent club as the given argument.
     */
    public boolean contains(ClubBudgetElements toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameClub);
    }

    /**
     * Adds a club to the list.
     * The club must not already exist in the list.
     */
    public void add(ClubBudgetElements toAdd) {
        requireNonNull(toAdd);
        /**
        if (contains(toAdd)) {
            throw new DuplicateClubException();
        }
         */
        internalList.add(toAdd);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ClubBudgetElements> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<ClubBudgetElements> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueClubsList // instanceof handles nulls
                && internalList.equals(((UniqueClubsList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean clubsAreUnique(List<ClubBudgetElements> clubs) {
        for (int i = 0; i < clubs.size() - 1; i++) {
            for (int j = i + 1; j < clubs.size(); j++) {
                if (clubs.get(i).isSameClub(clubs.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
