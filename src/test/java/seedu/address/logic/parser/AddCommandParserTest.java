package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_1ST_JAN;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_31ST_MARCH;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_2;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_LEVEL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_LEVEL_DESC_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_LEVEL_DESC_LOW;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LEVEL_LOW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_1;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static unrefactored.testutil.TypicalTasks.CS2113_TASK_1;
import static unrefactored.testutil.TypicalTasks.CS2113_TASK_2;

import org.junit.Test;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.PriorityLevel;
import seedu.address.model.task.Task;
import unrefactored.testutil.TaskBuilder;


public class AddCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder(CS2113_TASK_2).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DEADLINE_DESC_1ST_JAN + TITLE_DESC_2
                + DESCRIPTION_DESC_2 + PRIORITY_LEVEL_DESC_HIGH, new AddTaskCommand(expectedTask));

        // multiple deadlines - last deadline accepted
        assertParseSuccess(parser, DEADLINE_DESC_1ST_JAN + DEADLINE_DESC_31ST_MARCH
                + TITLE_DESC_2 + DESCRIPTION_DESC_2 + PRIORITY_LEVEL_DESC_HIGH, new AddTaskCommand(expectedTask));

        // multiple titles - last title accepted
        assertParseSuccess(parser, DEADLINE_DESC_1ST_JAN + TITLE_DESC_1 + TITLE_DESC_2
                + DESCRIPTION_DESC_2 + PRIORITY_LEVEL_DESC_HIGH, new AddTaskCommand(expectedTask));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, DEADLINE_DESC_1ST_JAN + TITLE_DESC_2
                + DESCRIPTION_DESC_1 + DESCRIPTION_DESC_2 + PRIORITY_LEVEL_DESC_HIGH,
                new AddTaskCommand(expectedTask));

        // multiple priorities - last priority accepted
        assertParseSuccess(parser, DEADLINE_DESC_1ST_JAN + TITLE_DESC_2
                + DESCRIPTION_DESC_2 + PRIORITY_LEVEL_DESC_LOW + PRIORITY_LEVEL_DESC_HIGH,
                new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, VALID_TITLE_1 + DESCRIPTION_DESC_1 + PRIORITY_LEVEL_DESC_LOW,
                expectedMessage);

        // missing description prefix
        assertParseFailure(parser, TITLE_DESC_1 + VALID_DESCRIPTION_1 + PRIORITY_LEVEL_DESC_LOW,
                expectedMessage);

        // missing priority prefix
        assertParseFailure(parser, TITLE_DESC_1 + DESCRIPTION_DESC_1 + VALID_PRIORITY_LEVEL_LOW,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid Priority Level
        assertParseFailure(parser, TITLE_DESC_1 + DESCRIPTION_DESC_1 + INVALID_PRIORITY_LEVEL_DESC,
                PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS);
        // invalid deadline
        // assertParseFailure(parser, INVALID_DEADLINE_DESC, Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TITLE_DESC_1 + DESCRIPTION_DESC_1
                + PRIORITY_LEVEL_DESC_LOW,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
    }
}
