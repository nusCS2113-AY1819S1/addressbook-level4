package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.workout.Parameter;
import seedu.address.model.workout.UniqueParameterList;

/**
 * Wraps all data at the tracked data list level, for each tracked parameter
 * Duplicates are not allowed (by .equals comparison)
 */
public class TrackedDataList implements ReadOnlyTrackedDataList {

    private final UniqueParameterList parameters;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        parameters = new UniqueParameterList();
    }

    public TrackedDataList() {}

    /**
     * Creates an TrackedDataList using the tracked parameters in {@code toBeCopied}
     */
    public TrackedDataList(ReadOnlyTrackedDataList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the TrackedDataList with {@code parameters}.
     * {@code workouts} must not contain duplicate workouts.
     */
    public void setParameters(List<Parameter> parameters) {
        this.parameters.setParameters(parameters);
    }

    public List<Parameter> getFilteredInternalList (Prefix prefix) {
        return parameters.getFilteredInternalList(prefix);
    }

    public List<Parameter> getFilteredInternalList (String value) {
        return parameters.getFilteredInternalList(value);
    }


    /**
     * Resets the existing data of this {@code TrackedDataList} with {@code newData}.
     */
    public void resetData(ReadOnlyTrackedDataList newData) {
        requireNonNull(newData);

        setParameters(newData.getTrackedDataList());
    }

    //// parameter-level operations

    /**
     * Returns true if a parameter with the same identity as {@code parameter} exists in the tracked data list.
     */
    public boolean hasParameter(Parameter parameter) {
        requireNonNull(parameter);
        return parameters.contains(parameter);
    }

    /**
     * Adds a parameter to the tracked data list.
     * The parameter must not already exist in the tracked data list.
     */
    public void addParameter(Parameter parameter) {
        parameters.add(parameter);
    }

    /**
     * Removes {@code key} from this {@code TrackedDataList}.
     * {@code key} must exist in the TrackedDataList.
     */
    public void removeParameter(Parameter key) {
        parameters.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return parameters.asUnmodifiableObservableList().size() + " parameters";
    }

    @Override
    public ObservableList<Parameter> getTrackedDataList() {
        return parameters.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TrackedDataList // instanceof handles nulls
                && parameters.equals(((TrackedDataList) other).parameters));
    }

    @Override
    public int hashCode() {
        return parameters.hashCode();
    }
}
