package seedu.address.model.clubbudget;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of final club budgets that enforces uniqueness between its elements and does not allow nulls.
 * A club budget is considered unique by comparing using {@code FinalClubBudget#isSameFinalClubBudget(FinalClubBudge)}.
 *
 * Supports a minimal set of list operations.
 *
 * @see FinalClubBudget#isSameFinalClubBudget(FinalClubBudget)
 */
public class UniqueClubBudgetList implements Iterable<FinalClubBudget> {

    private final ObservableList<FinalClubBudget> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent club as the given argument.
     */
    public boolean contains(FinalClubBudget toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameFinalClubBudget);
    }

    /**
     * Adds a club budget to the list.
     * The club must not already exist in the list.
     */
    public void add(FinalClubBudget toAdd) {
        requireNonNull(toAdd);

        if (contains(toAdd)) {
            //throw new DuplicateFinalClubBudgetException();
        }

        internalList.add(toAdd);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<FinalClubBudget> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<FinalClubBudget> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueClubBudgetList // instanceof handles nulls
                && internalList.equals(((UniqueClubBudgetList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only clubs' budgets.
     */
    private boolean clubsAreUnique(List<FinalClubBudget> clubBudgets) {
        for (int i = 0; i < clubBudgets.size() - 1; i++) {
            for (int j = i + 1; j < clubBudgets.size(); j++) {
                if (clubBudgets.get(i).isSameFinalClubBudget(clubBudgets.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
