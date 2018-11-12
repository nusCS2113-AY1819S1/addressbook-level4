package seedu.address.logic.parser;

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
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CALORIES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DIFFICULTY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DURATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EQUIPMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MUSCLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MUSCLE_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.MUSCLE_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FUTURE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_MORNING;
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
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FUTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MORNING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NIGHT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOB_WORKOUT;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalWorkouts.AMY_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.BOB_WORKOUT;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.workout.Calories;
import seedu.address.model.workout.Difficulty;
import seedu.address.model.workout.Duration;
import seedu.address.model.workout.Equipment;
import seedu.address.model.workout.Muscle;
import seedu.address.model.workout.Name;
import seedu.address.model.workout.Type;
import seedu.address.model.workout.Workout;
import seedu.address.testutil.WorkoutBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Workout expectedWorkout = new WorkoutBuilder(BOB_WORKOUT).withTags(VALID_TAG_NIGHT, VALID_TAG_FUTURE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT
                + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT
                + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT
                + TAG_DESC_NIGHT + TAG_DESC_FUTURE, new AddCommand(expectedWorkout));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY_WORKOUT + NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT
                + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT
                + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT
                + TAG_DESC_NIGHT + TAG_DESC_FUTURE, new AddCommand(expectedWorkout));

        // multiple types - last type accepted
        assertParseSuccess(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_AMY_WORKOUT + TYPE_DESC_BOB_WORKOUT
                + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT
                + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT
                + TAG_DESC_NIGHT + TAG_DESC_FUTURE, new AddCommand(expectedWorkout));

        // multiple durations - last duration accepted
        assertParseSuccess(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_AMY_WORKOUT
                + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT
                + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT
                + TAG_DESC_NIGHT + TAG_DESC_FUTURE, new AddCommand(expectedWorkout));

        // multiple difficulties - last difficulty accepted
        assertParseSuccess(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + DIFFICULTY_DESC_AMY_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT
                + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT
                + TAG_DESC_NIGHT + TAG_DESC_FUTURE, new AddCommand(expectedWorkout));

        // multiple equipments - last equipment accepted
        assertParseSuccess(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_AMY_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT
                + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT
                + TAG_DESC_NIGHT + TAG_DESC_FUTURE, new AddCommand(expectedWorkout));

        // multiple muscles - last muscle accepted
        assertParseSuccess(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_AMY_WORKOUT
                + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT
                + TAG_DESC_NIGHT + TAG_DESC_FUTURE, new AddCommand(expectedWorkout));

        // multiple calories - last calories accepted
        assertParseSuccess(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT
                + CALORIES_DESC_AMY_WORKOUT + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT
                + TAG_DESC_NIGHT + TAG_DESC_FUTURE, new AddCommand(expectedWorkout));

        // multiple instructions - last instruction accepted
        assertParseSuccess(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT
                + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_AMY_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT
                + TAG_DESC_NIGHT + TAG_DESC_FUTURE, new AddCommand(expectedWorkout));

        // multiple tags - all accepted
        Workout expectedWorkoutMultipleTags = new WorkoutBuilder(BOB_WORKOUT)
                .withTags(VALID_TAG_MORNING, VALID_TAG_NIGHT, VALID_TAG_FUTURE).build();
        assertParseSuccess(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT
                + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT + TAG_DESC_MORNING
                + TAG_DESC_NIGHT + TAG_DESC_FUTURE, new AddCommand(expectedWorkoutMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags except future tag
        Workout expectedWorkout = new WorkoutBuilder(AMY_WORKOUT).withTags(VALID_TAG_FUTURE).build();
        assertParseSuccess(parser, NAME_DESC_AMY_WORKOUT + TYPE_DESC_AMY_WORKOUT + DURATION_DESC_AMY_WORKOUT
                        + DIFFICULTY_DESC_AMY_WORKOUT + EQUIPMENT_DESC_AMY_WORKOUT + MUSCLE_DESC_AMY_WORKOUT
                        + CALORIES_DESC_AMY_WORKOUT + INSTRUCTION_DESC_AMY_WORKOUT + TAG_DESC_FUTURE,
                new AddCommand(expectedWorkout));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                        + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT
                        + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT, expectedMessage);

        // missing type prefix
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + VALID_TYPE_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                        + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT
                        + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT, expectedMessage);

        // missing duration prefix
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + VALID_DURATION_BOB_WORKOUT
                + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT
                + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT, expectedMessage);

        // missing difficulty prefix
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + VALID_DIFFICULTY_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT
                + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT, expectedMessage);

        // missing equipment prefix
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + DIFFICULTY_DESC_BOB_WORKOUT + VALID_EQUIPMENT_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT
                + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT, expectedMessage);

        // missing muscle prefix
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + VALID_MUSCLE_BOB_WORKOUT
                + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT, expectedMessage);

        // missing calories prefix
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT
                + VALID_CALORIES_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT, expectedMessage);

        // missing instruction prefix
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT
                + CALORIES_DESC_BOB_WORKOUT + VALID_INSTRUCTION_BOB_WORKOUT, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB_WORKOUT + VALID_TYPE_BOB_WORKOUT
                + VALID_DURATION_BOB_WORKOUT + VALID_DIFFICULTY_BOB_WORKOUT + VALID_EQUIPMENT_BOB_WORKOUT
                + VALID_MUSCLE_BOB_WORKOUT + VALID_CALORIES_BOB_WORKOUT
                + VALID_INSTRUCTION_BOB_WORKOUT, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT
                + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT + TAG_DESC_MORNING
                + TAG_DESC_NIGHT, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid type
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + INVALID_TYPE_DESC + DURATION_DESC_BOB_WORKOUT
                + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT
                + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT + TAG_DESC_MORNING
                + TAG_DESC_NIGHT, Type.MESSAGE_TYPE_CONSTRAINTS);

        // invalid duration
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + INVALID_DURATION_DESC
                + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT
                + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT + TAG_DESC_MORNING
                + TAG_DESC_NIGHT, Duration.MESSAGE_DURATION_CONSTRAINTS);

        // invalid difficulty
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + INVALID_DIFFICULTY_DESC + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT
                + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT + TAG_DESC_MORNING
                + TAG_DESC_NIGHT, Difficulty.MESSAGE_DIFFICULTY_CONSTRAINTS);

        // invalid equipment
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + DIFFICULTY_DESC_BOB_WORKOUT + INVALID_EQUIPMENT_DESC + MUSCLE_DESC_BOB_WORKOUT
                + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT + TAG_DESC_MORNING
                + TAG_DESC_NIGHT, Equipment.MESSAGE_EQUIPMENT_CONSTRAINTS);

        // invalid muscle
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + INVALID_MUSCLE_DESC
                + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT + TAG_DESC_MORNING
                + TAG_DESC_NIGHT, Muscle.MESSAGE_MUSCLE_CONSTRAINTS);

        // invalid calories
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT
                + INVALID_CALORIES_DESC + INSTRUCTION_DESC_BOB_WORKOUT + TAG_DESC_MORNING
                + TAG_DESC_NIGHT, Calories.MESSAGE_CALORIES_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT
                + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT + INVALID_TAG_DESC
                + VALID_TAG_NIGHT, Tag.MESSAGE_TAG_CONSTRAINTS);

        /**
         * Work in Progress
         */
        /*
        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);
        */

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT
                + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT
                + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT + TAG_DESC_MORNING
                + TAG_DESC_NIGHT, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
