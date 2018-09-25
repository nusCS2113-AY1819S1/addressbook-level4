package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalWorkouts.AMY_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.BOB_WORKOUT;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.workout.Workout;
import seedu.address.model.workout.Name;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.WorkoutBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Workout expectedWorkout = new WorkoutBuilder(BOB_WORKOUT).withTags(VALID_TAG_NIGHT).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT
                + INSTRUCTION_DESC_BOB_WORKOUT
                + TAG_DESC_NIGHT, new AddCommand(expectedWorkout));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY_WORKOUT + NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT
                + INSTRUCTION_DESC_BOB_WORKOUT
                + TAG_DESC_NIGHT, new AddCommand(expectedWorkout));

        // multiple tags - all accepted
        Workout expectedWorkoutMultipleTags = new WorkoutBuilder(BOB_WORKOUT).withTags(VALID_TAG_MORNING, VALID_TAG_NIGHT)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT
                + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT
                + INSTRUCTION_DESC_BOB_WORKOUT
                + TAG_DESC_MORNING + TAG_DESC_NIGHT, new AddCommand(expectedWorkoutMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Workout expectedWorkout = new WorkoutBuilder(AMY_WORKOUT).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY_WORKOUT + TYPE_DESC_AMY_WORKOUT + DURATION_DESC_AMY_WORKOUT
                        + DIFFICULTY_DESC_AMY_WORKOUT + EQUIPMENT_DESC_AMY_WORKOUT + MUSCLE_DESC_AMY_WORKOUT + CALORIES_DESC_AMY_WORKOUT
                        + INSTRUCTION_DESC_AMY_WORKOUT, new AddCommand(expectedWorkout));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                        + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT
                        + INSTRUCTION_DESC_BOB_WORKOUT, expectedMessage);

        // missing type prefix
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + VALID_TYPE_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT
                        + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT
                        + INSTRUCTION_DESC_BOB_WORKOUT, expectedMessage);

        // missing duration prefix
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + VALID_DURATION_BOB_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT
                + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT
                + INSTRUCTION_DESC_BOB_WORKOUT, expectedMessage);

        // missing difficulty prefix
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT + VALID_DIFFICULTY_BOB_WORKOUT
                + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT
                + INSTRUCTION_DESC_BOB_WORKOUT, expectedMessage);

        // missing equipment prefix
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT
                + VALID_EQUIPMENT_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT
                + INSTRUCTION_DESC_BOB_WORKOUT, expectedMessage);

        // missing muscle prefix
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT
                + EQUIPMENT_DESC_BOB_WORKOUT + VALID_MUSCLE_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT
                + INSTRUCTION_DESC_BOB_WORKOUT, expectedMessage);

        // missing calories prefix
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT
                + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT + VALID_CALORIES_BOB_WORKOUT
                + INSTRUCTION_DESC_BOB_WORKOUT, expectedMessage);

        // missing instruction prefix
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT
                + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT
                + VALID_INSTRUCTION_BOB_WORKOUT, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB_WORKOUT + VALID_TYPE_BOB_WORKOUT + VALID_DURATION_BOB_WORKOUT + VALID_DIFFICULTY_BOB_WORKOUT
                + VALID_EQUIPMENT_BOB_WORKOUT + VALID_MUSCLE_BOB_WORKOUT + VALID_CALORIES_BOB_WORKOUT
                + VALID_INSTRUCTION_BOB_WORKOUT, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT +  MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT
                + INSTRUCTION_DESC_BOB_WORKOUT
                + TAG_DESC_MORNING + TAG_DESC_NIGHT, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT
                + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT
                + INSTRUCTION_DESC_BOB_WORKOUT
                + INVALID_TAG_DESC + VALID_TAG_NIGHT, Tag.MESSAGE_TAG_CONSTRAINTS);

        /**
         * Work in Progress
         */
        /*
        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);
        */

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT
                + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT
                        + TAG_DESC_MORNING + TAG_DESC_NIGHT, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
