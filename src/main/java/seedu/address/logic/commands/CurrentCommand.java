package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WORKOUTS;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import org.jsoup.nodes.Element;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToRecommendListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ProfileWindowManager;
import seedu.address.model.tag.Tag;
import seedu.address.model.workout.Calories;
import seedu.address.model.workout.Difficulty;
import seedu.address.model.workout.Duration;
import seedu.address.model.workout.Equipment;
import seedu.address.model.workout.Instruction;
import seedu.address.model.workout.Muscle;
import seedu.address.model.workout.Name;
import seedu.address.model.workout.Type;
import seedu.address.model.workout.Workout;

/**
 * Changes a workout to be the current workout in the workout book.
 */
public class CurrentCommand extends Command {

    public static final String COMMAND_WORD = "current";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a workout to be the current workout identified "
            + "by the index number used in the displayed workout list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_CURRENT_WORKOUT_SUCCESS = "Current Workout: %1$s";
    public static final String MESSAGE_CURRENT_WORKOUT_FAILURE = "Fail to make the workout current.";
    public static final String MESSAGE_MULTIPLE_CURRENT_WORKOUT = "There is already a current workout. Complete that "
            + "before attempting again.";
    public static final String MESSAGE_MORE_DIFFICULT = "This workout is more difficult than your indicated workout "
            + "difficulty.\n";
    public static final String MESSAGE_HIGHER_CALORIES = "This workout requires more calories to be burnt than your "
            + "preferred calories.\n";
    public static final String MESSAGE_HIGHER_DURATION = "This workout will take longer than your preferred duration."
            + "\n";
    public static final String MESSAGE_CONTINUE = "Do you still want to make this workout current?";

    private static boolean currentWorkout;

    private static boolean success = true;
    private static Index targetIndex;
    private static Index currentWorkoutIndex;

    /**
     * @param targetIndex of the person in the filtered workout list to edit the state tag
     */
    public CurrentCommand(Index targetIndex) {
        requireNonNull(targetIndex);

        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Workout> filteredWorkoutList = model.getFilteredWorkoutList();
        Tag current = new Tag("current");

        for (int i = 0; i < model.getFilteredWorkoutList().size(); i++) {
            Workout workout = filteredWorkoutList.get(i);
            Set<Tag> tagList = workout.getTags();
            if (tagList.contains(current)) {
                setCurrentWorkout(true);
                currentWorkoutIndex = Index.fromZeroBased(i);
                EventsCenter.getInstance().post(new JumpToRecommendListRequestEvent(currentWorkoutIndex));
                throw new CommandException(MESSAGE_MULTIPLE_CURRENT_WORKOUT);
            }
        }

        try {
            Workout workoutToEdit = filteredWorkoutList.get(targetIndex.getZeroBased());
            Workout editedWorkout = createEditedWorkout(workoutToEdit);
            if (success) {
                model.updateWorkout(workoutToEdit, editedWorkout);
                model.updateFilteredWorkoutList(PREDICATE_SHOW_ALL_WORKOUTS);
                setCurrentWorkout(true);
                model.commitModel();
                return new CommandResult(String.format(MESSAGE_CURRENT_WORKOUT_SUCCESS, editedWorkout));
            } else {
                return new CommandResult(MESSAGE_CURRENT_WORKOUT_FAILURE);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);
        }
    }

    /**
     * Creates and returns a {@code Workout} with the details of {@code workoutToEdit}
     * edited with {@code editWorkoutDescriptor}.
     */
    public static Workout createEditedWorkout(Workout workoutToEdit) throws CommandException {
        success = true;
        assert workoutToEdit != null;
        Name updatedName = workoutToEdit.getName();
        Type updatedType = workoutToEdit.getType();
        Duration updatedDuration = workoutToEdit.getDuration();
        Difficulty updatedDifficulty = workoutToEdit.getDifficulty();
        Equipment updatedEquipment = workoutToEdit.getEquipment();
        Muscle updatedMuscle = workoutToEdit.getMuscle();
        Calories updatedCalories = workoutToEdit.getCalories();
        Instruction updatedInstruction = workoutToEdit.getInstruction();
        Set<Tag> originalTags = workoutToEdit.getTags();
        Set<Tag> updatedTags = new HashSet<>();
        for (Tag entry: originalTags) {
            updatedTags.add(entry);
        }

        boolean moreDifficult = false;
        boolean higherCalories = false;
        boolean higherDuration = false;

        ProfileWindowManager profileWindowManager;
        try {
            profileWindowManager = ProfileWindowManager.getInstance();
            Element divCalories = profileWindowManager.getCalories();
            Element divDuration = profileWindowManager.getDuration();
            Element divDifficulty = profileWindowManager.getDifficulty();
            String userDifficulty = profileWindowManager.trimmedDifficulty(divDifficulty.ownText());
            String calories = profileWindowManager.trimmedCalories(divCalories.ownText());
            String duration = profileWindowManager.trimmedFullDuration(divDuration.ownText());
            if (profileWindowManager.isMoreDifficult(updatedDifficulty.toString(), userDifficulty)) {
                moreDifficult = true;
            }
            if (!calories.matches("any")) {
                if (profileWindowManager.isHigherCalories(profileWindowManager.convertStringIntoInt(profileWindowManager
                                .trimmedCalories(updatedCalories.toString())),
                        profileWindowManager.convertStringIntoInt(calories))) {
                    higherCalories = true;
                }
            }
            if (!duration.matches("any")) {
                if (profileWindowManager.isHigherDuration(profileWindowManager.convertStringIntoInt(profileWindowManager
                                .trimmedFullDuration(updatedDuration.toString())),
                        profileWindowManager.convertStringIntoInt(duration))) {
                    higherDuration = true;
                }
            }
            if ((moreDifficult || higherCalories || higherDuration)) {
                String message = popUpMessage(moreDifficult, higherCalories, higherDuration);
                int reply = JOptionPane.showConfirmDialog(null, message,
                        "Making this workout current", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.NO_OPTION) {
                    success = false;
                    EventsCenter.getInstance().post(new JumpToRecommendListRequestEvent(targetIndex));
                    return new Workout(updatedName, updatedType, updatedDuration, updatedDifficulty, updatedEquipment,
                            updatedMuscle, updatedCalories, updatedInstruction, originalTags, null);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Tag future = new Tag("future");
        Tag current = new Tag("current");
        Tag completed = new Tag("completed");

        if (originalTags.contains(current)) {
            throw new CommandException(MESSAGE_MULTIPLE_CURRENT_WORKOUT);
        }

        if (originalTags.contains(future)) {
            updatedTags.remove(future);
        }
        if (originalTags.contains(completed)) {
            updatedTags.remove(completed);
        }
        updatedTags.add(current);

        EventsCenter.getInstance().post(new JumpToRecommendListRequestEvent(targetIndex));
        return new Workout(updatedName, updatedType, updatedDuration, updatedDifficulty, updatedEquipment,
                updatedMuscle, updatedCalories, updatedInstruction, updatedTags, null);
    }

    /**
     * Gives the appropriate pop up message depending on the difficulty, calories and duration
     *
     * @param difficulty boolean to check if the user's difficulty is harder than the workout's difficulty
     * @param calories boolean to check if the user's indicated calories is higher than the workout's calories
     * @param duration boolean to check if the user's indicated duration is higher than the workout's duration
     * @return the appropriate pop up message
     */
    public static String popUpMessage(boolean difficulty, boolean calories, boolean duration) {
        if ((!(difficulty || calories)) && duration) {
            return MESSAGE_HIGHER_DURATION + MESSAGE_CONTINUE;
        } else if ((!(difficulty || duration)) && calories) {
            return MESSAGE_HIGHER_CALORIES + MESSAGE_CONTINUE;
        } else if ((!(calories || duration)) && difficulty) {
            return MESSAGE_MORE_DIFFICULT + MESSAGE_CONTINUE;
        } else if ((difficulty && calories) && (!duration)) {
            return "This workout is more difficult than your indicated workout difficulty and requires more calories to"
                    + " be burnt than your preferred calories.\n" + MESSAGE_CONTINUE;
        } else if ((difficulty && duration) && (!calories)) {
            return "This workout is more difficult than your indicated workout difficulty and will take longer than "
                    + "your preferred duration.\n" + MESSAGE_CONTINUE;
        } else if ((calories && duration) && (!difficulty)) {
            return "This workout requires more calories to be burnt than your preferred calories and will take longer "
                    + "than your preferred duration.\n" + MESSAGE_CONTINUE;
        } else {
            return "This workout is more difficult than your indicated workout difficulty, requires more calories to"
                    + " be burnt than your preferred calories and will take longer than your preferred duration.\n"
                    + MESSAGE_CONTINUE;
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CurrentCommand)) {
            return false;
        }

        // state check
        CurrentCommand e = (CurrentCommand) other;
        return targetIndex.equals(e.targetIndex);
    }

    public static void setCurrentWorkout(boolean b) {
        currentWorkout = b;
    }
}
