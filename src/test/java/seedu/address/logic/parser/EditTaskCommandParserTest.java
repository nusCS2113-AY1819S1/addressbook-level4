package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.HOURS_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.INDEX_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.INDEX_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMPTY_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HOURS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX_DESC_NEGATIVE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX_DESC_ZERO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PREAMBLE_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_LEVEL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_CG2271_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_CS2113_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_LEVEL_DESC_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_LEVEL_DESC_LOW;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_1_HOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CG2271;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2113;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LEVEL_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LEVEL_LOW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_2;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_EMPTY_DESCRIPTION;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_EMPTY_INDEX;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_EMPTY_TITLE;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_HOURS;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.model.task.ModuleCode.MESSAGE_MODULE_CODE_CONSTRAINTS;
import static seedu.address.model.task.PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import org.junit.Test;

import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.model.task.ModuleCode;
import seedu.address.model.task.PriorityLevel;
import seedu.address.testutil.EditTaskDescriptorBuilder;

//@@author emobeany
public class EditTaskCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);
    private EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parse_compulsoryFieldsMissing_failure() {
        // index not specified
        assertParseFailure(parser, VALID_TITLE_1, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, INDEX_DESC_1, EditTaskCommand.MESSAGE_NO_FIELD_PROVIDED);

        // no index and no fields specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndexAndPreamble_failure() {
        // negative index
        assertParseFailure(parser, INVALID_INDEX_DESC_NEGATIVE + VALID_TITLE_1, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, INVALID_INDEX_DESC_ZERO + VALID_TITLE_1, MESSAGE_INVALID_INDEX);

        // non integer-only index
        assertParseFailure(parser, INVALID_PREAMBLE_INDEX_DESC, MESSAGE_INVALID_INDEX);

        // empty index with prefix
        assertParseFailure(parser, INVALID_EMPTY_INDEX_DESC, MESSAGE_EMPTY_INDEX);

        // invalid preamble
        assertParseFailure(parser, "1" + INDEX_DESC_1, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidFieldValue_failure() {
        assertParseFailure(parser, INDEX_DESC_1 + INVALID_TITLE_DESC, MESSAGE_EMPTY_TITLE);
        assertParseFailure(parser, INDEX_DESC_1 + INVALID_DESCRIPTION_DESC, MESSAGE_EMPTY_DESCRIPTION);
        assertParseFailure(parser, INDEX_DESC_1 + INVALID_PRIORITY_LEVEL_DESC, MESSAGE_PRIORITY_CONSTRAINTS);
        assertParseFailure(parser, INDEX_DESC_1 + INVALID_MODULE_CODE_DESC, MESSAGE_MODULE_CODE_CONSTRAINTS);
        assertParseFailure(parser, INDEX_DESC_1 + INVALID_HOURS_DESC, MESSAGE_INVALID_HOURS);

        // invalid field followed by another valid field
        assertParseFailure(parser, INDEX_DESC_1 + INVALID_TITLE_DESC + DESCRIPTION_DESC_1, MESSAGE_EMPTY_TITLE);

        // valid field followed by another invalid field
        assertParseFailure(parser, INDEX_DESC_1 + DESCRIPTION_DESC_1 + INVALID_TITLE_DESC, MESSAGE_EMPTY_TITLE);

        // multiple invalid fields captures first invalid field
        assertParseFailure(parser, INDEX_DESC_1 + INVALID_TITLE_DESC + INVALID_DESCRIPTION_DESC
                + INVALID_HOURS_DESC, MESSAGE_EMPTY_TITLE);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = INDEX_DESC_1 + TITLE_DESC_1 + DESCRIPTION_DESC_1 + MODULE_CODE_CS2113_DESC
                + PRIORITY_LEVEL_DESC_HIGH + HOURS_DESC_1;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_1)
                .withDescription(VALID_DESCRIPTION_1).withModuleCode(new ModuleCode(VALID_MODULE_CODE_CS2113))
                .withPriorityLevel(new PriorityLevel(VALID_PRIORITY_LEVEL_HIGH))
                .withExpectedNumOfHours(Integer.parseInt(VALID_1_HOUR)).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(INDEX_FIRST_TASK, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = INDEX_DESC_1 + TITLE_DESC_1 + MODULE_CODE_CS2113_DESC + PRIORITY_LEVEL_DESC_HIGH;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_1)
                .withModuleCode(new ModuleCode(VALID_MODULE_CODE_CS2113))
                .withPriorityLevel(new PriorityLevel(VALID_PRIORITY_LEVEL_HIGH)).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(INDEX_FIRST_TASK, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // title
        String userInput = INDEX_DESC_1 + TITLE_DESC_1;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_1).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(INDEX_FIRST_TASK, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = INDEX_DESC_1 + DESCRIPTION_DESC_1;
        descriptor = new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_1).build();
        expectedCommand = new EditTaskCommand(INDEX_FIRST_TASK, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // module code
        userInput = INDEX_DESC_1 + MODULE_CODE_CS2113_DESC;
        descriptor = new EditTaskDescriptorBuilder().withModuleCode(new ModuleCode(VALID_MODULE_CODE_CS2113)).build();
        expectedCommand = new EditTaskCommand(INDEX_FIRST_TASK, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // priority level
        userInput = INDEX_DESC_1 + PRIORITY_LEVEL_DESC_HIGH;
        descriptor = new EditTaskDescriptorBuilder().withPriorityLevel(new PriorityLevel(VALID_PRIORITY_LEVEL_HIGH))
                .build();
        expectedCommand = new EditTaskCommand(INDEX_FIRST_TASK, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // expected number of hours
        userInput = INDEX_DESC_1 + HOURS_DESC_1;
        descriptor = new EditTaskDescriptorBuilder().withExpectedNumOfHours(Integer.parseInt(VALID_1_HOUR)).build();
        expectedCommand = new EditTaskCommand(INDEX_FIRST_TASK, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        String userInput = INDEX_DESC_1 + INDEX_DESC_2 + TITLE_DESC_1 + DESCRIPTION_DESC_1 + TITLE_DESC_2
                + MODULE_CODE_CS2113_DESC + PRIORITY_LEVEL_DESC_HIGH + MODULE_CODE_CG2271_DESC + HOURS_DESC_1
                + PRIORITY_LEVEL_DESC_LOW;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_2)
                .withDescription(VALID_DESCRIPTION_1).withModuleCode(new ModuleCode(VALID_MODULE_CODE_CG2271))
                .withPriorityLevel(new PriorityLevel(VALID_PRIORITY_LEVEL_LOW))
                .withExpectedNumOfHours(Integer.parseInt(VALID_1_HOUR)).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(INDEX_SECOND_TASK, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {

        // invalid index followed by valid index
        String userInput = INVALID_INDEX_DESC + INDEX_DESC_1 + TITLE_DESC_1;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_1).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(INDEX_FIRST_TASK, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // valid index with only one field specified
        userInput = INDEX_DESC_1 + INVALID_TITLE_DESC + TITLE_DESC_1;
        descriptor = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_1).build();
        expectedCommand = new EditTaskCommand(INDEX_FIRST_TASK, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // valid index with other valid fields specified
        userInput = INDEX_DESC_1 + INVALID_TITLE_DESC + TITLE_DESC_1 + DESCRIPTION_DESC_1 + PRIORITY_LEVEL_DESC_LOW;
        descriptor = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_1).withDescription(VALID_DESCRIPTION_1)
                .withPriorityLevel(new PriorityLevel(VALID_PRIORITY_LEVEL_LOW)).build();
        expectedCommand = new EditTaskCommand(INDEX_FIRST_TASK, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }
}
