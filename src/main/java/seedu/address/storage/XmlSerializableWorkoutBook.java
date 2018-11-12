package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyWorkoutBook;
import seedu.address.model.WorkoutBook;
import seedu.address.model.workout.Workout;

/**
 * An Immutable WorkoutBook that is serializable to XML format
 */
@XmlRootElement(name = "workoutbook")
public class XmlSerializableWorkoutBook {

    public static final String MESSAGE_DUPLICATE_WORKOUT = "Workouts list contains duplicate workout(s).";

    @XmlElement
    private List<XmlAdaptedWorkout> workouts;

    /**
     * Creates an empty XmlSerializableWorkoutBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableWorkoutBook() {
        workouts = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableWorkoutBook(ReadOnlyWorkoutBook src) {
        this();
        workouts.addAll(src.getWorkoutList().stream().map(XmlAdaptedWorkout::new).collect(Collectors.toList()));
    }

    /**
     * Converts this workoutbook into the model's {@code WorkoutBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedWorkout}.
     */
    public WorkoutBook toModelType() throws IllegalValueException {
        WorkoutBook workoutBook = new WorkoutBook();
        for (XmlAdaptedWorkout p : workouts) {
            Workout workout = p.toModelType();
            if (workoutBook.hasWorkout(workout)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_WORKOUT);
            }
            workoutBook.addWorkout(workout);
        }
        return workoutBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableWorkoutBook)) {
            return false;
        }
        return workouts.equals(((XmlSerializableWorkoutBook) other).workouts);
    }
}
