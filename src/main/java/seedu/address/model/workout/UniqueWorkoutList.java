package seedu.address.model.workout;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.RecommendArguments;
import seedu.address.model.workout.exceptions.DuplicateWorkoutException;
import seedu.address.model.workout.exceptions.WorkoutNotFoundException;

/**
 * A list of workouts that enforces uniqueness between its elements and does not allow nulls.
 * A workout is considered unique by comparing using {@code Workout#isSameWorkout(Workout)}. As such, adding and
 * updating of workouts uses Workout#isSameWorkout(Workout) for equality so as to ensure that the workout being added
 * or updated is unique in terms of identity in the UniqueWorkoutList. However, the removal of a workout uses
 * Workout#equals(Object) so as to ensure that the workout with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Workout#isSameWorkout(Workout)
 */
public class UniqueWorkoutList implements Iterable<Workout> {

    private final ObservableList<Workout> internalList = FXCollections.observableArrayList();

    /**
     * Returns the filtered list with the greatest match with the respective calories, difficulty and duration.
     * @param recommendArguments
     * @return Final filtered list.
     */
    public List<Workout> getFinalFilteredInternalList (RecommendArguments recommendArguments) {

        if (isOneOfCaloriesDifficultyDurationNull(recommendArguments)) {
            return getFilteredInternalList(recommendArguments,
                    new ArrayList<>(List.of(true, true, true)));
        }

        List<Workout> finalFilteredInternalList;
        ArrayList<Boolean> optionalsList = recommendArguments.getOptionalsList();

        int totalOptionals = recommendArguments.getTotalOptionals();

        finalFilteredInternalList = getFilteredInternalList(recommendArguments,
                new ArrayList<>(List.of(true, true, true)));

        // 3 Choose 2 (3 Optionals)
        filterThreeChooseTwo(recommendArguments, finalFilteredInternalList, optionalsList, totalOptionals);

        // 3 Choose 1 (2 Optionals, 3 Optionals)
        filterThreeChooseOne(recommendArguments, finalFilteredInternalList, optionalsList, totalOptionals);

        // 3 Choose 0 (1 Optionals, 2 Optionals, 3 Optionals)
        filterThreeChooseZero(recommendArguments, finalFilteredInternalList, optionalsList, totalOptionals);

        return finalFilteredInternalList;
    }

    /**
     *  Returns true if at least one of `Calories`, `Difficulty` and `Duration` is null.
     * @param recommendArguments
     * @return true or false
     */
    private boolean isOneOfCaloriesDifficultyDurationNull(RecommendArguments recommendArguments) {
        return recommendArguments.isDurationNull() || recommendArguments.isDifficultyNull()
                || recommendArguments.isCaloriesNull();
    }

    /**
     * Choose zero out of `Calories`, `Difficulty` and `Duration` to filter.
     * @param recommendArguments
     * @param finalFilteredInternalList
     * @param optionalsList
     * @param totalOptionals
     */
    private void filterThreeChooseZero(RecommendArguments recommendArguments, List<Workout> finalFilteredInternalList,
                                       ArrayList<Boolean> optionalsList, int totalOptionals) {
        if (finalFilteredInternalList.isEmpty() && totalOptionals >= 1) {
            finalFilteredInternalList.addAll(getFilteredInternalList(recommendArguments,
                    new ArrayList<>(List.of(!optionalsList.get(0), !optionalsList.get(1), !optionalsList.get(2)))));
        }
    }

    /**
     * Choose one out of `Calories`, `Difficulty` and `Duration` to filter.
     * @param recommendArguments
     * @param finalFilteredInternalList
     * @param optionalsList
     * @param totalOptionals
     */
    private void filterThreeChooseOne(RecommendArguments recommendArguments, List<Workout> finalFilteredInternalList,
                                      ArrayList<Boolean> optionalsList, int totalOptionals) {
        if (finalFilteredInternalList.isEmpty() && totalOptionals >= 2) {
            for (int i = 0; i < optionalsList.size(); i++) {
                ArrayList<Boolean> conditionsList = new ArrayList<>(List.of(!optionalsList.get(0),
                        !optionalsList.get(1), !optionalsList.get(2)));
                if (optionalsList.get(i)) {
                    conditionsList.set(i, true);
                    finalFilteredInternalList.addAll(getFilteredInternalList(recommendArguments, conditionsList));
                }
            }
        }
    }

    /**
     * Choose two out of `Calories`, `Difficulty` and `Duration` to filter.
     * @param recommendArguments
     * @param finalFilteredInternalList
     * @param optionalsList
     * @param totalOptionals
     */
    private void filterThreeChooseTwo(RecommendArguments recommendArguments, List<Workout> finalFilteredInternalList,
                                      ArrayList<Boolean> optionalsList, int totalOptionals) {
        if (finalFilteredInternalList.isEmpty() && totalOptionals == 3) {
            for (int i = 0; i < optionalsList.size(); i++) {
                ArrayList<Boolean> conditionsList = new ArrayList<>(List.of(!optionalsList.get(0),
                        !optionalsList.get(1), !optionalsList.get(2)));
                conditionsList.set(i, true);
                for (int j = i + 1; j < optionalsList.size(); j++) {
                    conditionsList.set(j, true);
                    finalFilteredInternalList.addAll(getFilteredInternalList(recommendArguments, conditionsList));
                }
            }
        }
    }

    private List<Workout> getFilteredInternalList(RecommendArguments recommendArguments,
                                                  ArrayList<Boolean> conditionsList) {
        List<Workout> filteredInternalList = internalList.stream()
                .filter((!recommendArguments.isCaloriesNull() && conditionsList.get(0)) ? w -> w.getCalories()
                        .toString().equals(recommendArguments.getCalories().toString()) : w -> w != null)
                .filter((!recommendArguments.isDifficultyNull() && conditionsList.get(1)) ? w -> w.getDifficulty()
                        .toString().equals(recommendArguments.getDifficulty().toString()) : w -> w != null)
                .filter((!recommendArguments.isDurationNull() && conditionsList.get(2))
                        ? w -> w.getDuration().toString()
                        .equals(recommendArguments.getDuration().toString()) : w -> w != null)
                .collect(Collectors.toList());
        return filteredInternalList;
    }


    /**
     * Returns true if the list contains an equivalent workout as the given argument.
     */
    public boolean contains(Workout toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameWorkout);
    }

    /**
     * Adds a workout to the list.
     * The workout must not already exist in the list.
     */
    public void add(Workout toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateWorkoutException();
        }
        internalList.add(toAdd);
    }

    /**
     * Sorts the workout in the list
     */
    public void sort() {
        internalList.sort(new Comparator<Workout>() {
            @Override
            public int compare(Workout o1, Workout o2) {
                return o1.getName().fullName.compareTo(o2.getName().fullName);
            }
        });
    }


    /**
     * Replaces the workout {@code target} in the list with {@code editedWorkout}.
     * {@code target} must exist in the list.
     * The workout identity of {@code editedWorkout} must not be the same as another existing workout in the list.
     */
    public void setWorkout(Workout target, Workout editedWorkout) {
        requireAllNonNull(target, editedWorkout);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new WorkoutNotFoundException();
        }

        if (!target.isSameWorkout(editedWorkout) && contains(editedWorkout)) {
            throw new DuplicateWorkoutException();
        }

        internalList.set(index, editedWorkout);
    }

    /**
     * Removes the equivalent workout from the list.
     * The workout must exist in the list.
     */
    public void remove(Workout toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new WorkoutNotFoundException();
        }
    }

    public void setWorkouts(UniqueWorkoutList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code workouts}.
     * {@code workouts} must not contain duplicate workouts.
     */
    public void setWorkouts(List<Workout> workouts) {
        requireAllNonNull(workouts);
        if (!workoutsAreUnique(workouts)) {
            throw new DuplicateWorkoutException();
        }

        internalList.setAll(workouts);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Workout> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Workout> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueWorkoutList // instanceof handles nulls
                && internalList.equals(((UniqueWorkoutList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code workouts} contains only unique workouts.
     */
    private boolean workoutsAreUnique(List<Workout> workouts) {
        for (int i = 0; i < workouts.size() - 1; i++) {
            for (int j = i + 1; j < workouts.size(); j++) {
                if (workouts.get(i).isSameWorkout(workouts.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
