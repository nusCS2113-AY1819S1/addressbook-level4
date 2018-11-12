package seedu.address.model.workout;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.workout.exceptions.DuplicateParameterException;
import seedu.address.model.workout.exceptions.ParameterNotFoundException;

/**
 * A list of parameters that enforces uniqueness between its elements and does not allow nulls.
 * A parameter is considered unique by comparing using {@code Parameter#equals(Object)}. As such, adding and
 * updating of parameters uses Parameter#equals(Object) for equality so as to ensure that the parameter being added
 * or updated is unique in terms of identity in the UniqueParameterList. The removal of a parameter also uses
 * Parameter#equals(Object) so as to ensure that the parameter with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Parameter#equals(Object)
 */
public class UniqueParameterList implements Iterable<Parameter> {

    private final ObservableList<Parameter> internalList = FXCollections.observableArrayList();

    public List<Parameter> getFilteredInternalList (Prefix prefix) {
        return internalList.stream()
                .filter(w -> w.getPrefix().getPrefix().contains(prefix.getPrefix()))
                .collect(Collectors.toList());
    }

    public List<Parameter> getFilteredInternalList (String value) {
        return internalList.stream()
                .filter(w -> w.getValue().contains(value))
                .collect(Collectors.toList());
    }

    /**
     * Returns true if the list contains an equivalent parameter as the given argument.
     */
    public boolean contains(Parameter toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a parameter to the list.
     * The parameter must not already exist in the list.
     */
    public void add(Parameter toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateParameterException();
        }
        internalList.add(toAdd);
    }

    /**
     * Sorts the parameters in the list
     */
    public void sort() {
        internalList.sort(new Comparator<Parameter>() {
            @Override
            public int compare(Parameter o1, Parameter o2) {
                return o1.getPrefix().getPrefix().compareTo(o2.getPrefix().getPrefix());
            }
        });
    }

    /**
     * Removes the equivalent parameter from the list.
     * The parameter must exist in the list.
     */
    public void remove(Parameter toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ParameterNotFoundException();
        }
    }

    public void setParameters(UniqueParameterList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code parameters}.
     * {@code parameters} must not contain duplicate parameters.
     */
    public void setParameters(List<Parameter> parameters) {
        requireAllNonNull(parameters);
        if (!parametersAreUnique(parameters)) {
            throw new DuplicateParameterException();
        }

        internalList.setAll(parameters);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Parameter> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Parameter> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueParameterList // instanceof handles nulls
                && internalList.equals(((UniqueParameterList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code parameters} contains only unique parameters.
     */
    private boolean parametersAreUnique(List<Parameter> parameters) {
        for (int i = 0; i < parameters.size() - 1; i++) {
            for (int j = i + 1; j < parameters.size(); j++) {
                if (parameters.get(i).equals(parameters.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
