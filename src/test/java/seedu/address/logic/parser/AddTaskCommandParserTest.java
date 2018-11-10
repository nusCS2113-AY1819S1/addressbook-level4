package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BODY_DESC_ASSIGN;
import static seedu.address.logic.commands.CommandTestUtil.BODY_DESC_QUIZ;
import static seedu.address.logic.commands.CommandTestUtil.END_DATETIME_DECS_ASSIGN;
import static seedu.address.logic.commands.CommandTestUtil.END_DATETIME_DECS_QUIZ;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_ASSIGN;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_QUIZ;
import static seedu.address.logic.commands.CommandTestUtil.START_DATETIME_DECS_ASSIGN;
import static seedu.address.logic.commands.CommandTestUtil.START_DATETIME_DECS_QUIZ;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HARDCOPY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_IVLE;
import static seedu.address.logic.commands.CommandTestUtil.TASKNAME_DESC_ASSIGN;
import static seedu.address.logic.commands.CommandTestUtil.TASKNAME_DESC_QUIZ;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BODY_ASSIGN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATETIME_ASSIGN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_ASSIGN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATETIME_ASSIGN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HARDCOPY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_IVLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_ASSIGN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseTaskSuccess;
import static seedu.address.testutil.TypicalTasks.ASSIGNMENT5;
import static seedu.address.testutil.TypicalTasks.QUIZ9;

import org.junit.Test;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder(ASSIGNMENT5).withTags(VALID_TAG_IVLE).build();

        // whitespace only preamble
        assertParseTaskSuccess(parser, PREAMBLE_WHITESPACE + TASKNAME_DESC_ASSIGN + BODY_DESC_ASSIGN
                + START_DATETIME_DECS_ASSIGN + END_DATETIME_DECS_ASSIGN + PRIORITY_DESC_ASSIGN + TAG_DESC_IVLE);

        // multiple taskNames - last taskName accepted
        assertParseTaskSuccess(parser, TASKNAME_DESC_QUIZ + TASKNAME_DESC_ASSIGN + BODY_DESC_ASSIGN
                + START_DATETIME_DECS_ASSIGN + END_DATETIME_DECS_ASSIGN
                + PRIORITY_DESC_ASSIGN + TAG_DESC_IVLE);

        // multiple bodys - last body accepted
        assertParseTaskSuccess(parser, TASKNAME_DESC_ASSIGN + BODY_DESC_QUIZ + BODY_DESC_ASSIGN
                + START_DATETIME_DECS_ASSIGN + END_DATETIME_DECS_ASSIGN + PRIORITY_DESC_ASSIGN
                + TAG_DESC_IVLE);

        // multiple startDateTime - last startDateTime accepted
        assertParseTaskSuccess(parser, TASKNAME_DESC_ASSIGN + BODY_DESC_ASSIGN + START_DATETIME_DECS_QUIZ
                + START_DATETIME_DECS_ASSIGN + END_DATETIME_DECS_ASSIGN + PRIORITY_DESC_ASSIGN
                + TAG_DESC_IVLE);

        // multiple endDateTime - last endDateTime accepted
        assertParseTaskSuccess(parser, TASKNAME_DESC_ASSIGN + BODY_DESC_ASSIGN + START_DATETIME_DECS_ASSIGN
                + END_DATETIME_DECS_QUIZ + END_DATETIME_DECS_ASSIGN
                + PRIORITY_DESC_ASSIGN + TAG_DESC_IVLE);

        // multiple priority - last priority accepted
        assertParseTaskSuccess(parser, TASKNAME_DESC_ASSIGN + BODY_DESC_ASSIGN + START_DATETIME_DECS_ASSIGN
                + END_DATETIME_DECS_ASSIGN + PRIORITY_DESC_QUIZ
                + PRIORITY_DESC_ASSIGN + TAG_DESC_IVLE);

        // multiple tags - all accepted
        Task expectedTaskMultipleTags = new TaskBuilder(ASSIGNMENT5).withTags(VALID_TAG_IVLE, VALID_TAG_HARDCOPY)
                .build();
        assertParseTaskSuccess(parser, TASKNAME_DESC_ASSIGN + BODY_DESC_ASSIGN + START_DATETIME_DECS_ASSIGN
                + END_DATETIME_DECS_ASSIGN + PRIORITY_DESC_ASSIGN
                + TAG_DESC_HARDCOPY + TAG_DESC_IVLE);
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Task expectedTask = new TaskBuilder(QUIZ9).withTags().build();
        assertParseTaskSuccess(parser, TASKNAME_DESC_QUIZ + BODY_DESC_QUIZ + START_DATETIME_DECS_QUIZ
                        + END_DATETIME_DECS_QUIZ + PRIORITY_DESC_QUIZ);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_TASK_NAME_ASSIGN + BODY_DESC_ASSIGN + START_DATETIME_DECS_ASSIGN
                        + END_DATETIME_DECS_ASSIGN + PRIORITY_DESC_ASSIGN,
                expectedMessage);

        // missing body prefix
        assertParseFailure(parser, TASKNAME_DESC_ASSIGN + VALID_BODY_ASSIGN + START_DATETIME_DECS_ASSIGN
                        + END_DATETIME_DECS_ASSIGN + PRIORITY_DESC_ASSIGN,
                expectedMessage);

        // missing startDateTime prefix
        assertParseFailure(parser, TASKNAME_DESC_ASSIGN + BODY_DESC_ASSIGN + VALID_START_DATETIME_ASSIGN
                        + END_DATETIME_DECS_ASSIGN + PRIORITY_DESC_ASSIGN,
                expectedMessage);

        // missing endDateTime prefix
        assertParseFailure(parser, TASKNAME_DESC_ASSIGN + BODY_DESC_ASSIGN + START_DATETIME_DECS_ASSIGN
                        + VALID_END_DATETIME_ASSIGN + PRIORITY_DESC_ASSIGN,
                expectedMessage);

        // missing priority prefix
        assertParseFailure(parser, TASKNAME_DESC_ASSIGN + BODY_DESC_ASSIGN + START_DATETIME_DECS_ASSIGN
                        + END_DATETIME_DECS_ASSIGN + VALID_PRIORITY_ASSIGN,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TASK_NAME_ASSIGN + VALID_BODY_ASSIGN + VALID_START_DATETIME_ASSIGN
                        + VALID_END_DATETIME_ASSIGN + VALID_PRIORITY_ASSIGN,
                expectedMessage);
    }

    /*
    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }*/
}
