package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CALORIES_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.DIFFICULTY_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.EQUIPMENT_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.MUSCLE_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_MORNING;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALORIES_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EQUIPMENT_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MUSCLE_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MORNING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOB_WORKOUT;
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
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.TrackCommand;
import seedu.address.model.workout.Parameter;

public class TrackCommandParserTest {
    private TrackCommandParser parser = new TrackCommandParser();

    @Test
    public void parse_compulsoryFieldsPresent_returnsTrackCommand() {
        String subcommand = "start";

        // test for name
        String userInput = subcommand + " " + PREFIX_NAME + "Bob";
        TrackCommand expectedCommand = new TrackCommand(subcommand,
                new Parameter(PREFIX_NAME, "Bob"));
        assertParseSuccess(parser, userInput, expectedCommand);

        // test for type
        userInput = subcommand + " " + TYPE_DESC_BOB_WORKOUT;
        expectedCommand = new TrackCommand(subcommand,
                new Parameter(PREFIX_TYPE, VALID_TYPE_BOB_WORKOUT));
        assertParseSuccess(parser, userInput, expectedCommand);

        // test for duration
        userInput = subcommand + " " + DURATION_DESC_BOB_WORKOUT;
        expectedCommand = new TrackCommand(subcommand,
                new Parameter(PREFIX_DURATION, VALID_DURATION_BOB_WORKOUT));
        assertParseSuccess(parser, userInput, expectedCommand);

        // test for difficulty
        userInput = subcommand + " " + DIFFICULTY_DESC_BOB_WORKOUT;
        expectedCommand = new TrackCommand(subcommand,
                new Parameter(PREFIX_DIFFICULTY, VALID_DIFFICULTY_BOB_WORKOUT));
        assertParseSuccess(parser, userInput, expectedCommand);

        // test for equipment
        userInput = subcommand + " " + EQUIPMENT_DESC_BOB_WORKOUT;
        expectedCommand = new TrackCommand(subcommand,
                new Parameter(PREFIX_EQUIPMENT, VALID_EQUIPMENT_BOB_WORKOUT));
        assertParseSuccess(parser, userInput, expectedCommand);

        // test for muscle
        userInput = subcommand + " " + MUSCLE_DESC_BOB_WORKOUT;
        expectedCommand = new TrackCommand(subcommand,
                new Parameter(PREFIX_MUSCLE, VALID_MUSCLE_BOB_WORKOUT));
        assertParseSuccess(parser, userInput, expectedCommand);

        // test for calories
        userInput = subcommand + " " + CALORIES_DESC_BOB_WORKOUT;
        expectedCommand = new TrackCommand(subcommand,
                new Parameter(PREFIX_CALORIES, VALID_CALORIES_BOB_WORKOUT));
        assertParseSuccess(parser, userInput, expectedCommand);

        // test for instruction
        userInput = subcommand + " " + PREFIX_INSTRUCTION + " bicep";
        expectedCommand = new TrackCommand(subcommand,
                new Parameter(PREFIX_INSTRUCTION, "bicep"));
        assertParseSuccess(parser, userInput, expectedCommand);

        // test for tag
        userInput = subcommand + " " + TAG_DESC_MORNING;
        expectedCommand = new TrackCommand(subcommand,
                new Parameter(PREFIX_TAG, VALID_TAG_MORNING));
        assertParseSuccess(parser, userInput, expectedCommand);

        // test for remark
        userInput = subcommand + " " + PREFIX_REMARK + "bicep";
        expectedCommand = new TrackCommand(subcommand,
                new Parameter(PREFIX_REMARK, "bicep"));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_parameterValueHasSpaces_throwsParseException() {
        String subcommand = "start";
        String userInput = subcommand + NAME_DESC_BOB_WORKOUT;
        String expectedMessage = String.format(TrackCommand.MESSAGE_VALUE_CONSTRAINTS);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingCompulsoryField_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrackCommand.MESSAGE_USAGE);

        // no subcommand
        assertParseFailure(parser, MUSCLE_DESC_BOB_WORKOUT, expectedMessage);

        // no parameter
        assertParseFailure(parser, "start", expectedMessage);

        // only command word
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_multipleParameters_throwsParseException() {
        String userInput = NAME_DESC_BOB_WORKOUT + " " + MUSCLE_DESC_BOB_WORKOUT;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrackCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
