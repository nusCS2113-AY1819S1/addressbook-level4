package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.clubbudget.FinalClubBudget;
import seedu.address.model.clubbudget.UniqueClubBudgetList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class FinalBudgetsBook implements ReadOnlyFinalBudgetBook {
    private final UniqueClubBudgetList clubBudgets;
    {
        clubBudgets = new UniqueClubBudgetList();
    }

    public FinalBudgetsBook() {}

    /**
     * Creates an FinalBudgetsBook using the FinalClubBudget in the {@code toBeCopied}
     */
    public FinalBudgetsBook(ReadOnlyFinalBudgetBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the final budgets list with {@code final budget}.
     * {@code final budgets} must not contain duplicate final budgets.
     */
    public void setFinalBudgets(List<FinalClubBudget> clubBudgets) {
        this.clubBudgets.setFinalClubBudget(clubBudgets);
    }

    /**
     * Resets the existing data of this {@code FinalBudgetsBook} with {@code newData}.
     */
    public void resetData(ReadOnlyFinalBudgetBook newData) {
        requireNonNull(newData);
        setFinalBudgets(newData.getClubBudgetsList());
    }

    //// final budget -level operations
    /**
     * Returns true if a club budget with the same identity as {@code clubBudget} exists in the final budgets book.
     */
    public boolean hasClubBudget(FinalClubBudget clubBudget) {
        requireNonNull(clubBudget);
        return clubBudgets.contains(clubBudget);
    }

    /**
     * Adds a club budget to the address book.
     * The club budget must not already exist in the final budgets book.
     */
    public void addClubBudget(FinalClubBudget c) {
        clubBudgets.add(c);
    }

    //// util methods
    @Override
    public ObservableList<FinalClubBudget> getClubBudgetsList() {
        return clubBudgets.asUnmodifiableObservableList(); }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FinalBudgetsBook // instanceof handles nulls
                && clubBudgets.equals(((FinalBudgetsBook) other).clubBudgets));
    }

    @Override
    public int hashCode() {
        return clubBudgets.hashCode();
    }
}
