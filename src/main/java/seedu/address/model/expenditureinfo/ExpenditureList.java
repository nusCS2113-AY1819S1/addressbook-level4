package seedu.address.model.expenditureinfo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.expenditureinfo.exceptions.DuplicateExpenditureException;
import seedu.address.model.expenditureinfo.exceptions.ExpenditureNotFoundException;


/**
 * A list of expenditures.
 * The removal of a person uses Expenditure#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 */
public class ExpenditureList implements Iterable<Expenditure> {

    private final ObservableList<Expenditure> internalList = FXCollections.observableArrayList();

    public boolean contains(Expenditure toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameExpenditure);
    }

    /**
     * Adds an expenditure to the list.
     */


    public void add(Expenditure toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */

    public void setExpenditures(ExpenditureList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void remove(Expenditure toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ExpenditureNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code expenditures}.
     */

    public void setExpenditures(List<Expenditure> expenditures) {
        requireAllNonNull(expenditures);
        internalList.setAll(expenditures);
    }

    public void setExpenditures(Expenditure target, Expenditure editedExpenditure) {
        requireAllNonNull(target, editedExpenditure);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ExpenditureNotFoundException();
        }

        if (!target.isSameExpenditure(editedExpenditure) && contains(editedExpenditure)) {
            throw new DuplicateExpenditureException();
        }

        internalList.set(index, editedExpenditure);
    }


    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Expenditure> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Expenditure> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenditureList // instanceof handles nulls
                && internalList.equals(((ExpenditureList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    private boolean expendituresAreUnique(List<Expenditure> expenditures) {
        for (int i = 0; i < expenditures.size() - 1; i++) {
            for (int j = i + 1; j < expenditures.size(); j++) {
                if (expenditures.get(i).isSameExpenditure(expenditures.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
