
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CALORIES_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.CALORIES_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.DIFFICULTY_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.DIFFICULTY_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.EQUIPMENT_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.EQUIPMENT_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.INSTRUCTION_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.INSTRUCTION_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_CURRENT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MUSCLE_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.MUSCLE_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_NIGHT;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALORIES_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EQUIPMENT_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTRUCTION_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MUSCLE_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOB_WORKOUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalWorkouts.ALICE_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.AMY_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.BOB_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.HOON_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.IDA_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.workout.Name;
import seedu.address.model.workout.Workout;
import seedu.address.testutil.WorkoutBuilder;
import seedu.address.testutil.WorkoutUtil;

public class AddCommandSystemTest extends WorkoutBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();


        //------------------------ Perform add operations on the shown unfiltered list ----------------------------

        /*Case: add a workout without tags to a non-empty workout book, command with leading spaces and trailing spaces
         -> added */




        Workout toAdd = AMY_WORKOUT;
        String command = AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY_WORKOUT + "  " + TYPE_DESC_AMY_WORKOUT
                + "   " + DURATION_DESC_AMY_WORKOUT + "   " + DIFFICULTY_DESC_AMY_WORKOUT + "   "
                + EQUIPMENT_DESC_AMY_WORKOUT + "   " + MUSCLE_DESC_AMY_WORKOUT + "   " + CALORIES_DESC_AMY_WORKOUT
                + "   " + INSTRUCTION_DESC_AMY_WORKOUT;
        assertCommandSuccess(command, toAdd);


        //Case: undo adding Amy to the list -> Amy deleted*/


        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);


        //Case: redo adding Amy to the list -> Amy added again

        command = RedoCommand.COMMAND_WORD;
        model.addWorkout(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);


        //Case: add to empty workout book -> added

        deleteAllWorkouts();
        assertCommandSuccess(ALICE_WORKOUT);


        //Case: add a workout with tags, command with parameters in random order -> added

        toAdd = BOB_WORKOUT;
        command = AddCommand.COMMAND_WORD + TAG_DESC_NIGHT + INSTRUCTION_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT
                + MUSCLE_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT
                + DURATION_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + NAME_DESC_BOB_WORKOUT;
        assertCommandSuccess(command, toAdd);



        //Case: add a workout, missing tags -> added



        assertCommandSuccess(HOON_WORKOUT);



        //-------------------------- Perform add operation on the shown filtered list ------------------------------






        //Case: filters the workout list before adding -> added



        showWorkoutsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA_WORKOUT);





        //----------------------------------- Perform invalid add operations ---------------------------------------






        //Case: add a duplicate workout -> rejected



        command = WorkoutUtil.getAddCommand(HOON_WORKOUT);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_WORKOUT);



        //Case: add a duplicate workout with all fields same as another workout, except name -> rejected



        toAdd = new WorkoutBuilder(AMY_WORKOUT).withName(VALID_NAME_BOB_WORKOUT).build();
        command = WorkoutUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_WORKOUT);



        //Case: add a duplicate workout except with different type -> rejected



        toAdd = new WorkoutBuilder(HOON_WORKOUT).withType(VALID_TYPE_BOB_WORKOUT).build();
        command = WorkoutUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_WORKOUT);



        //Case: add a duplicate workout except with different duration -> rejected



        toAdd = new WorkoutBuilder(HOON_WORKOUT).withDuration(VALID_DURATION_BOB_WORKOUT).build();
        command = WorkoutUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_WORKOUT);



        //Case: add a duplicate workout except with different difficulty -> rejected



        toAdd = new WorkoutBuilder(HOON_WORKOUT).withDifficulty(VALID_DIFFICULTY_BOB_WORKOUT).build();
        command = WorkoutUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_WORKOUT);



        //Case: add a duplicate workout except with different equipment -> rejected



        toAdd = new WorkoutBuilder(HOON_WORKOUT).withEquipment(VALID_EQUIPMENT_BOB_WORKOUT).build();
        command = WorkoutUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_WORKOUT);



        //Case: add a duplicate workout except with different muscle -> rejected



        toAdd = new WorkoutBuilder(HOON_WORKOUT).withMuscle(VALID_MUSCLE_BOB_WORKOUT).build();
        command = WorkoutUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_WORKOUT);



        //Case: add a duplicate workout except with different calories -> rejected



        toAdd = new WorkoutBuilder(HOON_WORKOUT).withCalories(VALID_CALORIES_BOB_WORKOUT).build();
        command = WorkoutUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_WORKOUT);



        //Case: add a duplicate workout except with different instruction -> rejected



        toAdd = new WorkoutBuilder(HOON_WORKOUT).withInstruction(VALID_INSTRUCTION_BOB_WORKOUT).build();
        command = WorkoutUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_WORKOUT);



        //Case: add a duplicate workout except with different tags -> rejected



        command = WorkoutUtil.getAddCommand(HOON_WORKOUT) + " " + PREFIX_TAG.getPrefix() + "night";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_WORKOUT);



        //Case: missing name -> rejected



        command = AddCommand.COMMAND_WORD + TYPE_DESC_AMY_WORKOUT + DURATION_DESC_AMY_WORKOUT
                + DIFFICULTY_DESC_AMY_WORKOUT + EQUIPMENT_DESC_AMY_WORKOUT + MUSCLE_DESC_AMY_WORKOUT
                + CALORIES_DESC_AMY_WORKOUT + INSTRUCTION_DESC_AMY_WORKOUT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));



        //Case: missing type -> rejected



        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY_WORKOUT + DURATION_DESC_AMY_WORKOUT
                + DIFFICULTY_DESC_AMY_WORKOUT + EQUIPMENT_DESC_AMY_WORKOUT + MUSCLE_DESC_AMY_WORKOUT
                + CALORIES_DESC_AMY_WORKOUT + INSTRUCTION_DESC_AMY_WORKOUT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));



        //Case: missing duration -> rejected



        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY_WORKOUT + TYPE_DESC_AMY_WORKOUT
                + DIFFICULTY_DESC_AMY_WORKOUT + EQUIPMENT_DESC_AMY_WORKOUT + MUSCLE_DESC_AMY_WORKOUT
                + CALORIES_DESC_AMY_WORKOUT + INSTRUCTION_DESC_AMY_WORKOUT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));



        //Case: missing difficulty -> rejected



        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY_WORKOUT + TYPE_DESC_AMY_WORKOUT
                + DURATION_DESC_AMY_WORKOUT + EQUIPMENT_DESC_AMY_WORKOUT + MUSCLE_DESC_AMY_WORKOUT
                + CALORIES_DESC_AMY_WORKOUT + INSTRUCTION_DESC_AMY_WORKOUT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));



        //Case: missing equipment -> rejected



        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY_WORKOUT + TYPE_DESC_AMY_WORKOUT + DURATION_DESC_AMY_WORKOUT
                + DIFFICULTY_DESC_AMY_WORKOUT + MUSCLE_DESC_AMY_WORKOUT + CALORIES_DESC_AMY_WORKOUT
                + INSTRUCTION_DESC_AMY_WORKOUT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));



        //Case: missing muscle -> rejected



        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY_WORKOUT + TYPE_DESC_AMY_WORKOUT + DURATION_DESC_AMY_WORKOUT
                + DIFFICULTY_DESC_AMY_WORKOUT + EQUIPMENT_DESC_AMY_WORKOUT + CALORIES_DESC_AMY_WORKOUT
                + INSTRUCTION_DESC_AMY_WORKOUT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));



        //Case: missing calories -> rejected



        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY_WORKOUT + TYPE_DESC_AMY_WORKOUT + DURATION_DESC_AMY_WORKOUT
                + DIFFICULTY_DESC_AMY_WORKOUT + EQUIPMENT_DESC_AMY_WORKOUT + MUSCLE_DESC_AMY_WORKOUT
                + INSTRUCTION_DESC_AMY_WORKOUT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));


        //Case: missing instruction -> rejected



        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY_WORKOUT + TYPE_DESC_AMY_WORKOUT + DURATION_DESC_AMY_WORKOUT
                + DIFFICULTY_DESC_AMY_WORKOUT + EQUIPMENT_DESC_AMY_WORKOUT + MUSCLE_DESC_AMY_WORKOUT
                + CALORIES_DESC_AMY_WORKOUT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));



        //Case: invalid keyword -> rejected



        command = "adds " + WorkoutUtil.getWorkoutDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);



        //Case: invalid name -> rejected



        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + TYPE_DESC_AMY_WORKOUT + DURATION_DESC_AMY_WORKOUT
                + DIFFICULTY_DESC_AMY_WORKOUT + EQUIPMENT_DESC_AMY_WORKOUT + MUSCLE_DESC_AMY_WORKOUT
                + CALORIES_DESC_AMY_WORKOUT + INSTRUCTION_DESC_AMY_WORKOUT;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);



        //Case: invalid tag -> rejected



        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY_WORKOUT + TYPE_DESC_AMY_WORKOUT + DURATION_DESC_AMY_WORKOUT
                + DIFFICULTY_DESC_AMY_WORKOUT + EQUIPMENT_DESC_AMY_WORKOUT + MUSCLE_DESC_AMY_WORKOUT
                + CALORIES_DESC_AMY_WORKOUT + INSTRUCTION_DESC_AMY_WORKOUT + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);



        //Case: invalid tag (current) -> rejected



        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY_WORKOUT + TYPE_DESC_AMY_WORKOUT + DURATION_DESC_AMY_WORKOUT
                + DIFFICULTY_DESC_AMY_WORKOUT + EQUIPMENT_DESC_AMY_WORKOUT + MUSCLE_DESC_AMY_WORKOUT
                + CALORIES_DESC_AMY_WORKOUT + INSTRUCTION_DESC_AMY_WORKOUT + INVALID_TAG_CURRENT;
        assertCommandFailure(command, AddCommand.MESSAGE_TAG_FAILURE);

    }



    /**
         * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
         * 1. Command box displays an empty string.<br>
         * 2. Command box has the default style class.<br>
         * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
         * {@code toAdd}.<br>
         * 4. {@code Storage} and {@code WorkoutListPanel} equal to the corresponding components in
         * the current model added with {@code toAdd}.<br>
         * 5. Browser url and selected card remain unchanged.<br>
         * 6. Status bar's sync status changes.<br>
         * Verifications 1, 3 and 4 are performed by
         * {@code WorkoutBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
         * @see WorkoutBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
         */

    private void assertCommandSuccess(Workout toAdd) {
        assertCommandSuccess(WorkoutUtil.getAddCommand(toAdd), toAdd);
    }



    /**
         * Performs the same verification as {@code assertCommandSuccess(Workout)}. Executes {@code command}
         * instead.
         * @see AddCommandSystemTest#assertCommandSuccess(Workout)
         */


    private void assertCommandSuccess(String command, Workout toAdd) {
        Model expectedModel = getModel();
        expectedModel.addWorkout(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }


    /**
         * Performs the same verification as {@code assertCommandSuccess(String, Workout)} except asserts that
         * the,<br>
         * 1. Result display box displays {@code expectedResultMessage}.<br>
         * 2. {@code Storage} and {@code WorkoutListPanel} equal to the corresponding components in
         * {@code expectedModel}.<br>
         * @see AddCommandSystemTest#assertCommandSuccess(String, Workout)
         */

    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }


    /**
         * Executes {@code command} and asserts that the,<br>
         * 1. Command box displays {@code command}.<br>
         * 2. Command box has the error style class.<br>
         * 3. Result display box displays {@code expectedResultMessage}.<br>
         * 4. {@code Storage} and {@code WorkoutListPanel} remain unchanged.<br>
         * 5. Browser url, selected card and status bar remain unchanged.<br>
         * Verifications 1, 3 and 4 are performed by
         * {@code WorkoutBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
         * @see WorkoutBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
         */

    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}

