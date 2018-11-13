//@@author SHININGGGG
package seedu.address.model.expenditureinfo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
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
     * Removes the equivalent expenditure from the list.
     * The expenditure must exist in the list.
     */
    public void remove(Expenditure toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ExpenditureNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */

    public void setExpenditures(ExpenditureList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }


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

    public Map getExpenditureRecords() {

        Map <String, Double> map = new HashMap <String, Double> ();
        int index = 0;
        String category;
        double money;
        //System.out.println("Now we are in expenditureList executing getExpenditureRecords.");
        while (index < internalList.size()) {
            category = internalList.get(index).getCategory().categoryName;
            money = Double.parseDouble(internalList.get(index).getMoney().addingMoney);
            if (!map.containsKey(category)) {
                map.put(category, 0.0);
            }
            map.put(category, map.get(category) + money);
            index++;
        }
        return map;
    }

    /**
     * Get the expenditure records on a particular day.
     */
    public String checkExpenditureRecordsOnParticularDay(String particularDate) {

        String records;
        String theDate;
        int index = 0;
        StringBuilder it = new StringBuilder();
        StringBuilder builder = new StringBuilder();
        boolean hasExpenditure = false;
        while (index < internalList.size()) {
            theDate = internalList.get(index).getDate().addingDate;
            if (theDate.equals(particularDate)) {
                System.out.println(theDate + "printing...");
                it.append("Expenditure description: " + internalList.get(index).getDescription().descriptionName + ", ")
                        .append("Category: " + internalList.get(index).getCategory().categoryName + ", ")
                        .append("Money: " + internalList.get(index).getMoney().addingMoney + ", ")
                        .append("Date: " + internalList.get(index).getDate().addingDate)
                        .append("\n");
                hasExpenditure = true;
            }
            index++;
        }
        if (hasExpenditure) {
            builder.append("Here's the expenditure record:\n")
                    .append(it.toString());
        } else {
            builder.append("There is no expenditure record on this day.\n");
        }
        records = builder.toString();
        return records;
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
}
