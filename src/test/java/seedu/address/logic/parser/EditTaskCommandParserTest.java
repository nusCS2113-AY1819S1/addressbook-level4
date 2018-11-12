//@@author XiaoYunhan
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_MODULE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DATE_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DATE_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.TASK_MODULE_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.TASK_MODULE_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.TASK_NAME_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.TASK_PRIORITY_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.TASK_PRIORITY_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DATE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DATE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_MODULE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_MODULE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_PRIORITY_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_PRIORITY_TUTORIAL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskModule;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);

    private EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parseMissingPartsFailure() {
        // no index specified
        assertParseFailure(parser, VALID_TASK_NAME_ASSIGNMENT, MESSAGE_INVALID_FORMAT);

        // no field specified
        //assertParseFailure(parser, "1", EditTaskCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseInvalidPreambleFailure() {
        // negative index
        assertParseFailure(parser, "-5" + TASK_NAME_DESC_ASSIGNMENT, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TASK_NAME_DESC_ASSIGNMENT, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseInvalidValueFailure() {
        // invalid name
        //assertParseFailure(parser, "1" + INVALID_TASK_NAME_DESC, TaskName.MESSAGE_NAME_CONSTRAINTS);
        // invalid date
        assertParseFailure(parser, "1" + INVALID_TASK_DATE_DESC, TaskDate.MESSAGE_DATE_CONSTRAINTS);
        // invalid module
        assertParseFailure(parser, "1" + INVALID_TASK_MODULE_DESC, TaskModule.MESSAGE_MODULE_CONSTRAINTS);
        // invalid priority
        //assertParseFailure(parser, "1" + INVALID_TASK_PRIORITY_DESC, TaskPriority.MESSAGE_PRIORITY_CONSTRAINTS);

        // invalid date followed by valid module
        assertParseFailure(parser, "1" + INVALID_TASK_DATE_DESC + TASK_MODULE_DESC_ASSIGNMENT,
                TaskDate.MESSAGE_DATE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + TASK_DATE_DESC_TUTORIAL + INVALID_TASK_DATE_DESC,
                TaskDate.MESSAGE_DATE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        //assertParseFailure(parser, "1" + INVALID_TASK_NAME_DESC + INVALID_TASK_MODULE_DESC
        // + VALID_TASK_PRIORITY_ASSIGNMENT + VALID_TASK_DATE_ASSIGNMENT, TaskName.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parseAllFieldsSpecifiedSuccess() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + TASK_DATE_DESC_TUTORIAL
                + TASK_MODULE_DESC_ASSIGNMENT + TASK_PRIORITY_DESC_ASSIGNMENT + TASK_NAME_DESC_ASSIGNMENT;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withName(VALID_TASK_NAME_ASSIGNMENT).withDate(VALID_TASK_DATE_TUTORIAL)
                .withModule(VALID_TASK_MODULE_ASSIGNMENT).withPriority(VALID_TASK_PRIORITY_ASSIGNMENT).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseSomeFieldsSpecifiedSuccess() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + TASK_DATE_DESC_TUTORIAL + TASK_MODULE_DESC_ASSIGNMENT;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDate(VALID_TASK_DATE_TUTORIAL).withModule(VALID_TASK_MODULE_ASSIGNMENT).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseOneFieldSpecifiedSuccess() {
        // name
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + TASK_NAME_DESC_ASSIGNMENT;
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withName(VALID_TASK_NAME_ASSIGNMENT).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + TASK_DATE_DESC_ASSIGNMENT;
        descriptor = new EditTaskDescriptorBuilder().withDate(VALID_TASK_DATE_ASSIGNMENT).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // module
        userInput = targetIndex.getOneBased() + TASK_MODULE_DESC_ASSIGNMENT;
        descriptor = new EditTaskDescriptorBuilder().withModule(VALID_TASK_MODULE_ASSIGNMENT).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // priority
        userInput = targetIndex.getOneBased() + TASK_PRIORITY_DESC_ASSIGNMENT;
        descriptor = new EditTaskDescriptorBuilder().withPriority(VALID_TASK_PRIORITY_ASSIGNMENT).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /*
    @Test
    public void parseMultipleRepeatedFieldsAcceptsLast() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + TASK_DATE_DESC_ASSIGNMENT + TASK_PRIORITY_DESC_ASSIGNMENT
                + TASK_MODULE_DESC_ASSIGNMENT + TAG_DESC_FRIEND + TASK_DATE_DESC_ASSIGNMENT
                + TASK_PRIORITY_DESC_ASSIGNMENT + TASK_MODULE_DESC_ASSIGNMENT + TAG_DESC_FRIEND
                + TASK_DATE_DESC_TUTORIAL + TASK_PRIORITY_DESC_TUTORIAL + TASK_MODULE_DESC_TUTORIAL;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDate(VALID_TASK_DATE_TUTORIAL)
                .withModule(VALID_TASK_MODULE_TUTORIAL).withPriority(VALID_TASK_PRIORITY_TUTORIAL).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    */

    @Test
    public void parseInvalidValueFollowedByValidValueSuccess() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + INVALID_TASK_DATE_DESC + TASK_DATE_DESC_TUTORIAL;
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDate(VALID_TASK_DATE_TUTORIAL).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + TASK_MODULE_DESC_TUTORIAL + INVALID_TASK_DATE_DESC
                + TASK_PRIORITY_DESC_TUTORIAL + TASK_DATE_DESC_TUTORIAL;
        descriptor = new EditTaskDescriptorBuilder().withDate(VALID_TASK_DATE_TUTORIAL)
                .withModule(VALID_TASK_MODULE_TUTORIAL).withPriority(VALID_TASK_PRIORITY_TUTORIAL).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
