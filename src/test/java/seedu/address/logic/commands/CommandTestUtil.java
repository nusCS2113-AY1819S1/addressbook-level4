package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EQUIPMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTRUCTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.WorkoutBook;
import seedu.address.model.workout.NameContainsKeywordsPredicate;
import seedu.address.model.workout.Workout;
import seedu.address.testutil.EditWorkoutDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String VALID_NAME_AMY_WORKOUT = "Amy Bee's workout";
    public static final String VALID_NAME_BOB_WORKOUT = "Bob Choo's workout";
    public static final String VALID_NAME_JOHN_WORKOUT = "John Pauline's workout";
    public static final String VALID_TYPE_AMY_WORKOUT = "cardio";
    public static final String VALID_TYPE_BOB_WORKOUT = "strength";
    public static final String VALID_TYPE_JOHN_WORKOUT = "cardio";
    public static final String VALID_DURATION_AMY_WORKOUT = "20m";
    public static final String VALID_DURATION_BOB_WORKOUT = "25m";
    public static final String VALID_DURATION_JOHN_WORKOUT = "45m";
    public static final String VALID_DIFFICULTY_AMY_WORKOUT = "beginner";
    public static final String VALID_DIFFICULTY_BOB_WORKOUT = "intermediate";
    public static final String VALID_DIFFICULTY_JOHN_WORKOUT = "intermediate";
    public static final String VALID_EQUIPMENT_AMY_WORKOUT = "yoga mat";
    public static final String VALID_EQUIPMENT_BOB_WORKOUT = "dumbbell";
    public static final String VALID_EQUIPMENT_JOHN_WORKOUT = "NIL";
    public static final String VALID_MUSCLE_AMY_WORKOUT = "legs";
    public static final String VALID_MUSCLE_BOB_WORKOUT = "biceps";
    public static final String VALID_MUSCLE_JOHN_WORKOUT = "legs";
    public static final String VALID_CALORIES_AMY_WORKOUT = "180";
    public static final String VALID_CALORIES_BOB_WORKOUT = "150";
    public static final String VALID_CALORIES_JOHN_WORKOUT = "300";
    public static final String VALID_INSTRUCTION_AMY_WORKOUT = "set1: flutter kick reps: 5-7";
    public static final String VALID_INSTRUCTION_BOB_WORKOUT = "set1: bicep curl reps: 4-6";
    public static final String VALID_INSTRUCTION_JOHN_WORKOUT = "45 minutes slow jog";
    public static final String VALID_REMARK_AMY_WORKOUT = "This workout trains your legs";
    public static final String VALID_REMARK_BOB_WORKOUT = "This workout trains your biceps";
    public static final String VALID_REMARK_JOHN_WORKOUT = "This workout trains legs";
    public static final String VALID_USERNAME = "gsj";
    public static final String VALID_GENDER = "male";
    public static final String VALID_HEIGHT = "1.83";
    public static final String VALID_WEIGHT = "91.2";
    public static final String VALID_DIFFICULTY = "beginner";
    public static final String VALID_CALORIES = "150";
    public static final String VALID_DURATION = "20m";

    public static final String VALID_TAG_MORNING = "morning";
    public static final String VALID_TAG_NIGHT = "night";
    public static final String VALID_TAG_FUTURE = "future";
    public static final String VALID_TAG_CURRENT = "current";
    public static final String VALID_TAG_COMPLETED = "completed";

    public static final String NAME_DESC_AMY_WORKOUT = " " + PREFIX_NAME + VALID_NAME_AMY_WORKOUT;
    public static final String NAME_DESC_BOB_WORKOUT = " " + PREFIX_NAME + VALID_NAME_BOB_WORKOUT;

    public static final String TYPE_DESC_AMY_WORKOUT = " " + PREFIX_TYPE + VALID_TYPE_AMY_WORKOUT;
    public static final String TYPE_DESC_BOB_WORKOUT = " " + PREFIX_TYPE + VALID_TYPE_BOB_WORKOUT;
    public static final String DURATION_DESC_AMY_WORKOUT = " " + PREFIX_DURATION + VALID_DURATION_AMY_WORKOUT;
    public static final String DURATION_DESC_BOB_WORKOUT = " " + PREFIX_DURATION + VALID_DURATION_BOB_WORKOUT;
    public static final String DIFFICULTY_DESC_AMY_WORKOUT = " " + PREFIX_DIFFICULTY + VALID_DIFFICULTY_AMY_WORKOUT;
    public static final String DIFFICULTY_DESC_BOB_WORKOUT = " " + PREFIX_DIFFICULTY + VALID_DIFFICULTY_BOB_WORKOUT;
    public static final String EQUIPMENT_DESC_AMY_WORKOUT = " " + PREFIX_EQUIPMENT + VALID_EQUIPMENT_AMY_WORKOUT;
    public static final String EQUIPMENT_DESC_BOB_WORKOUT = " " + PREFIX_EQUIPMENT + VALID_EQUIPMENT_BOB_WORKOUT;
    public static final String MUSCLE_DESC_AMY_WORKOUT = " " + PREFIX_MUSCLE + VALID_MUSCLE_AMY_WORKOUT;
    public static final String MUSCLE_DESC_BOB_WORKOUT = " " + PREFIX_MUSCLE + VALID_MUSCLE_BOB_WORKOUT;
    public static final String REMARK_DESC_AMY_WORKOUT = " " + PREFIX_REMARK + VALID_REMARK_AMY_WORKOUT;
    public static final String REMARK_DESC_BOB_WORKOUT = " " + PREFIX_REMARK + VALID_REMARK_BOB_WORKOUT;
    public static final String CALORIES_DESC_AMY_WORKOUT = " " + PREFIX_CALORIES + VALID_CALORIES_AMY_WORKOUT;
    public static final String CALORIES_DESC_BOB_WORKOUT = " " + PREFIX_CALORIES + VALID_CALORIES_BOB_WORKOUT;
    public static final String INSTRUCTION_DESC_AMY_WORKOUT = " " + PREFIX_INSTRUCTION + VALID_INSTRUCTION_AMY_WORKOUT;
    public static final String INSTRUCTION_DESC_BOB_WORKOUT = " " + PREFIX_INSTRUCTION + VALID_INSTRUCTION_BOB_WORKOUT;
    public static final String TAG_DESC_MORNING = " " + PREFIX_TAG + VALID_TAG_MORNING;
    public static final String TAG_DESC_NIGHT = " " + PREFIX_TAG + VALID_TAG_NIGHT;
    public static final String TAG_DESC_FUTURE = " " + PREFIX_TAG + VALID_TAG_FUTURE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James's workout&"; // '&' not allowed in names
    public static final String INVALID_TYPE_DESC = " " + PREFIX_TYPE + "strength + cardio"; // '+' not allowed in types
    public static final String INVALID_DURATION_DESC = " " + PREFIX_DURATION + "5 minutes";
    public static final String INVALID_DIFFICULTY_DESC = " " + PREFIX_DIFFICULTY + "difficult";
    public static final String INVALID_EQUIPMENT_DESC = " " + PREFIX_EQUIPMENT + "dumbbell + mat";
    public static final String INVALID_MUSCLE_DESC = " " + PREFIX_MUSCLE + "bicep + tricep";
    public static final String INVALID_CALORIES_DESC = " " + PREFIX_CALORIES + "123 calories";
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "morning*"; // '*' not allowed in tags
    public static final String INVALID_TAG_CURRENT = " " + PREFIX_TAG + "current"; // 'current' not allowed in edit tags
    public static final String INVALID_USERNAME = "gs_j@a";
    public static final String INVALID_GENDER = "femal";
    public static final String INVALID_HEIGHT = "183";
    public static final String INVALID_WEIGHT = "911";
    public static final String INVALID_DIFFICULTY = "easy";
    public static final String INVALID_DURATION = "20";
    public static final String INVALID_CALORIES = "0";


    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditWorkoutDescriptor DESC_AMY_WORKOUT;
    public static final EditCommand.EditWorkoutDescriptor DESC_BOB_WORKOUT;

    static {
        DESC_AMY_WORKOUT = new EditWorkoutDescriptorBuilder().withName(VALID_NAME_AMY_WORKOUT)
                        .withType(VALID_TYPE_AMY_WORKOUT).withDuration(VALID_DURATION_AMY_WORKOUT)
                        .withDifficulty(VALID_DIFFICULTY_AMY_WORKOUT)
                        .withEquipment(VALID_EQUIPMENT_AMY_WORKOUT).withMuscle(VALID_MUSCLE_AMY_WORKOUT)
                        .withCalories(VALID_CALORIES_AMY_WORKOUT).withInstruction(VALID_INSTRUCTION_AMY_WORKOUT)
                        .withTags(VALID_TAG_MORNING).build();
        DESC_BOB_WORKOUT = new EditWorkoutDescriptorBuilder().withName(VALID_NAME_BOB_WORKOUT)
                        .withType(VALID_TYPE_BOB_WORKOUT).withDuration(VALID_DURATION_BOB_WORKOUT)
                        .withDifficulty(VALID_DIFFICULTY_BOB_WORKOUT)
                        .withEquipment(VALID_EQUIPMENT_BOB_WORKOUT).withMuscle(VALID_MUSCLE_BOB_WORKOUT)
                        .withCalories(VALID_CALORIES_BOB_WORKOUT).withInstruction(VALID_INSTRUCTION_BOB_WORKOUT)
                        .withTags(VALID_TAG_NIGHT).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     * - the {@code actualAttribute} matches {@code expectedAttribute}
     */
    public static void assertModifyCommandSuccess(Command command, ArrayList<String> actualAttribute, Model actualModel,
                                                  CommandHistory actualCommandHistory, String expectedMessage,
                                                  ArrayList<String> expectedAttribute) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedCommandHistory, actualCommandHistory);
            assertEquals(expectedAttribute, actualAttribute);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the workout book and the filtered workout list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        WorkoutBook expectedWorkoutBook = new WorkoutBook(actualModel.getWorkoutBook());
        List<Workout> expectedFilteredList = new ArrayList<>(actualModel.getFilteredWorkoutList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedWorkoutBook, actualModel.getWorkoutBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredWorkoutList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the workout at the given {@code targetIndex} in the
     * {@code model}'s workout book.
     */
    public static void showWorkoutAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredWorkoutList().size());

        Workout workout = model.getFilteredWorkoutList().get(targetIndex.getZeroBased());
        final String[] splitName = workout.getName().fullName.split("\\s+");
        model.updateFilteredWorkoutList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredWorkoutList().size());
    }

    /**
     * Deletes the first workout in {@code model}'s filtered list from {@code model}'s workout book.
     */
    public static void deleteFirstWorkout(Model model) {
        Workout firstWorkout = model.getFilteredWorkoutList().get(0);
        model.deleteWorkout(firstWorkout);
        model.commitModel();
    }

}
