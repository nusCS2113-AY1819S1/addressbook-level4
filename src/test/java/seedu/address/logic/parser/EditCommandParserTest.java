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
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_MORNING;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_NIGHT;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALORIES_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALORIES_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EQUIPMENT_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EQUIPMENT_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTRUCTION_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTRUCTION_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MUSCLE_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MUSCLE_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MORNING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NIGHT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOB_WORKOUT;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WORKOUT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_WORKOUT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_WORKOUT;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditWorkoutDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.workout.Calories;
import seedu.address.model.workout.Difficulty;
import seedu.address.model.workout.Duration;
import seedu.address.model.workout.Equipment;
import seedu.address.model.workout.Muscle;
import seedu.address.model.workout.Name;
import seedu.address.model.workout.Type;
import seedu.address.testutil.EditWorkoutDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY_WORKOUT, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY_WORKOUT, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY_WORKOUT, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    /**
     * To be uncommented once invalid test cases are thought of
     */
    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_TYPE_DESC, Type.MESSAGE_TYPE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_DURATION_DESC, Duration.MESSAGE_DURATION_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_DIFFICULTY_DESC, Difficulty.MESSAGE_DIFFICULTY_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_EQUIPMENT_DESC, Equipment.MESSAGE_EQUIPMENT_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_MUSCLE_DESC, Muscle.MESSAGE_MUSCLE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_CALORIES_DESC, Calories.MESSAGE_CALORIES_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        /**
         * To be uncommented once invalid test cases are thought of
         */
        //invalid type followed by valid duration
        //assertParseFailure(parser, "1" + INVALID_TYPE_DESC +DURATION_DESC_AMY_WORKOUT, Type.MESSAGE_TYPE_CONSTRAINTS);
        //valid type followed by invalid type. The test case for invalid type followed by valid type
        //is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        //assertParseFailure(parser, "1" + TYPE_DESC_BOB_WORKOUT + INVALID_TYPE_DESC, Type.MESSAGE_TYPE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Workout} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_MORNING + TAG_DESC_NIGHT + TAG_EMPTY,
                Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_MORNING + TAG_EMPTY + TAG_DESC_NIGHT,
                Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_MORNING + TAG_DESC_NIGHT,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        /**
         * To be uncommented once invalid test cases are thought of
         */
        // multiple invalid values, but only the first invalid value is captured
        //assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_TYPE_DESC + VALID_DURATION_AMY_WORKOUT
        //+ VALID_DIFFICULTY_AMY_WORKOUT + VALID_EQUIPMENT_AMY_WORKOUT + VALID_MUSCLE_AMY_WORKOUT
        //+ VALID_CALORIES_AMY_WORKOUT + VALID_INSTRUCTION_AMY_WORKOUT, Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_WORKOUT;
        String userInput = targetIndex.getOneBased() + TYPE_DESC_BOB_WORKOUT + TAG_DESC_NIGHT
                + NAME_DESC_AMY_WORKOUT + DURATION_DESC_AMY_WORKOUT + DIFFICULTY_DESC_AMY_WORKOUT
                + EQUIPMENT_DESC_AMY_WORKOUT + MUSCLE_DESC_AMY_WORKOUT + CALORIES_DESC_AMY_WORKOUT
                + INSTRUCTION_DESC_AMY_WORKOUT + TAG_DESC_MORNING;

        EditWorkoutDescriptor descriptor = new EditWorkoutDescriptorBuilder().withName(VALID_NAME_AMY_WORKOUT)
                .withType(VALID_TYPE_BOB_WORKOUT).withDuration(VALID_DURATION_AMY_WORKOUT)
                .withDifficulty(VALID_DIFFICULTY_AMY_WORKOUT).withEquipment(VALID_EQUIPMENT_AMY_WORKOUT)
                .withMuscle(VALID_MUSCLE_AMY_WORKOUT).withCalories(VALID_CALORIES_AMY_WORKOUT)
                .withInstruction(VALID_INSTRUCTION_AMY_WORKOUT).withTags(VALID_TAG_MORNING, VALID_TAG_NIGHT).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_WORKOUT;
        String userInput = targetIndex.getOneBased() + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_AMY_WORKOUT
                + EQUIPMENT_DESC_AMY_WORKOUT + MUSCLE_DESC_AMY_WORKOUT + CALORIES_DESC_AMY_WORKOUT
                + INSTRUCTION_DESC_AMY_WORKOUT;

        EditWorkoutDescriptor descriptor = new EditWorkoutDescriptorBuilder().withDuration(VALID_DURATION_BOB_WORKOUT)
                .withDifficulty(VALID_DIFFICULTY_AMY_WORKOUT).withEquipment(VALID_EQUIPMENT_AMY_WORKOUT)
                .withMuscle(VALID_MUSCLE_AMY_WORKOUT).withCalories(VALID_CALORIES_AMY_WORKOUT)
                .withInstruction(VALID_INSTRUCTION_AMY_WORKOUT).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_WORKOUT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY_WORKOUT;
        EditWorkoutDescriptor descriptor = new EditWorkoutDescriptorBuilder().withName(VALID_NAME_AMY_WORKOUT).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // type
        userInput = targetIndex.getOneBased() + TYPE_DESC_AMY_WORKOUT;
        descriptor = new EditWorkoutDescriptorBuilder().withType(VALID_TYPE_AMY_WORKOUT).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // duration
        userInput = targetIndex.getOneBased() + DURATION_DESC_AMY_WORKOUT;
        descriptor = new EditWorkoutDescriptorBuilder().withDuration(VALID_DURATION_AMY_WORKOUT).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // difficulty
        userInput = targetIndex.getOneBased() + DIFFICULTY_DESC_AMY_WORKOUT;
        descriptor = new EditWorkoutDescriptorBuilder().withDifficulty(VALID_DIFFICULTY_AMY_WORKOUT).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // equipment
        userInput = targetIndex.getOneBased() + EQUIPMENT_DESC_AMY_WORKOUT;
        descriptor = new EditWorkoutDescriptorBuilder().withEquipment(VALID_EQUIPMENT_AMY_WORKOUT).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // muscle
        userInput = targetIndex.getOneBased() + MUSCLE_DESC_AMY_WORKOUT;
        descriptor = new EditWorkoutDescriptorBuilder().withMuscle(VALID_MUSCLE_AMY_WORKOUT).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // calories
        userInput = targetIndex.getOneBased() + CALORIES_DESC_AMY_WORKOUT;
        descriptor = new EditWorkoutDescriptorBuilder().withCalories(VALID_CALORIES_AMY_WORKOUT).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // instruction
        userInput = targetIndex.getOneBased() + INSTRUCTION_DESC_AMY_WORKOUT;
        descriptor = new EditWorkoutDescriptorBuilder().withInstruction(VALID_INSTRUCTION_AMY_WORKOUT).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_MORNING;
        descriptor = new EditWorkoutDescriptorBuilder().withTags(VALID_TAG_MORNING).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_WORKOUT;
        String userInput = targetIndex.getOneBased() + TYPE_DESC_AMY_WORKOUT + DURATION_DESC_AMY_WORKOUT
                + DIFFICULTY_DESC_AMY_WORKOUT + EQUIPMENT_DESC_AMY_WORKOUT + MUSCLE_DESC_AMY_WORKOUT
                + CALORIES_DESC_AMY_WORKOUT + INSTRUCTION_DESC_AMY_WORKOUT + TAG_DESC_MORNING + TYPE_DESC_AMY_WORKOUT
                + DURATION_DESC_AMY_WORKOUT + DIFFICULTY_DESC_AMY_WORKOUT + EQUIPMENT_DESC_AMY_WORKOUT
                + MUSCLE_DESC_AMY_WORKOUT + CALORIES_DESC_AMY_WORKOUT + INSTRUCTION_DESC_AMY_WORKOUT + TAG_DESC_MORNING
                + TYPE_DESC_BOB_WORKOUT + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT
                + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT
                + INSTRUCTION_DESC_BOB_WORKOUT + TAG_DESC_NIGHT;

        EditWorkoutDescriptor descriptor = new EditWorkoutDescriptorBuilder().withType(VALID_TYPE_BOB_WORKOUT)
                .withDuration(VALID_DURATION_BOB_WORKOUT).withDifficulty(VALID_DIFFICULTY_BOB_WORKOUT)
                .withEquipment(VALID_EQUIPMENT_BOB_WORKOUT).withMuscle(VALID_MUSCLE_BOB_WORKOUT)
                .withCalories(VALID_CALORIES_BOB_WORKOUT).withInstruction(VALID_INSTRUCTION_BOB_WORKOUT)
                .withTags(VALID_TAG_MORNING, VALID_TAG_NIGHT).build();

        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * To be uncommented once invalid test cases are thought of
     */
    /* @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_WORKOUT;
        String userInput = targetIndex.getOneBased() + INVALID_TYPE_DESC + TYPE_DESC_BOB_WORKOUT;
        EditWorkoutDescriptor descriptor = new EditWorkoutDescriptorBuilder().withType(VALID_TYPE_BOB_WORKOUT).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DURATION_DESC_BOB_WORKOUT + INVALID_TYPE_DESC + TYPE_DESC_BOB_WORKOUT
            + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT + MUSCLE_DESC_BOB_WORKOUT
            + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT;

        descriptor = new EditWorkoutDescriptorBuilder().withType(VALID_TYPE_BOB_WORKOUT)
                .withDuration(VALID_DURATION_BOB_WORKOUT).withDifficulty(VALID_DIFFICULTY_BOB_WORKOUT)
                .withEquipment(VALID_EQUIPMENT_BOB_WORKOUT).withMuscle(VALID_MUSCLE_BOB_WORKOUT)
                .withCalories(VALID_CALORIES_BOB_WORKOUT).withInstruction(VALID_INSTRUCTION_BOB_WORKOUT).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }*/

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_WORKOUT;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditWorkoutDescriptor descriptor = new EditWorkoutDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
