package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BODY_DESC_ASSIGN;
import static seedu.address.logic.commands.CommandTestUtil.BODY_DESC_QUIZ;
import static seedu.address.logic.commands.CommandTestUtil.END_DATETIME_DECS_ASSIGN;
import static seedu.address.logic.commands.CommandTestUtil.END_DATETIME_DECS_QUIZ;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_NAME_DESC;
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
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;
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

    @Test
    public void parse_invalidValue_failure() {
        // invalid taskName
        assertParseFailure(parser, INVALID_TASK_NAME_DESC + BODY_DESC_ASSIGN + START_DATETIME_DECS_ASSIGN
                + END_DATETIME_DECS_ASSIGN + PRIORITY_DESC_ASSIGN
                + TAG_DESC_HARDCOPY + TAG_DESC_IVLE, TaskName.MESSAGE_NAME_CONSTRAINTS);

        // invalid startDateTime
        assertParseFailure(parser, TASKNAME_DESC_ASSIGN + BODY_DESC_ASSIGN + INVALID_START_DATETIME_DESC
                + END_DATETIME_DECS_ASSIGN + PRIORITY_DESC_ASSIGN
                + TAG_DESC_HARDCOPY + TAG_DESC_IVLE, DateTime.MESSAGE_START_DATETIME_CONSTRAINTS);

        // invalid endDateTime
        assertParseFailure(parser, TASKNAME_DESC_ASSIGN + BODY_DESC_ASSIGN + START_DATETIME_DECS_ASSIGN
                + INVALID_END_DATETIME_DESC + PRIORITY_DESC_ASSIGN
                + TAG_DESC_HARDCOPY + TAG_DESC_IVLE, DateTime.MESSAGE_END_DATETIME_CONSTRAINTS);

        // invalid priority
        assertParseFailure(parser, TASKNAME_DESC_ASSIGN + BODY_DESC_ASSIGN + START_DATETIME_DECS_ASSIGN
                + END_DATETIME_DECS_ASSIGN + INVALID_PRIORITY_DESC
                + TAG_DESC_HARDCOPY + TAG_DESC_IVLE, Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, TASKNAME_DESC_ASSIGN + BODY_DESC_ASSIGN + START_DATETIME_DECS_ASSIGN
                + END_DATETIME_DECS_ASSIGN + PRIORITY_DESC_ASSIGN
                + INVALID_TAG_DESC + VALID_TAG_IVLE, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TASK_NAME_DESC + BODY_DESC_ASSIGN + START_DATETIME_DECS_ASSIGN
                        + END_DATETIME_DECS_ASSIGN + INVALID_PRIORITY_DESC,
                TaskName.MESSAGE_NAME_CONSTRAINTS);

    }

}
