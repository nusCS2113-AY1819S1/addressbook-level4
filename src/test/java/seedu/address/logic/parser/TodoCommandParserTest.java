package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_TASK1;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC_TASK2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_TASK1;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_TASK2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_TASK1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_TASK2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_TASK1;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalTodos.TASK1;

import org.junit.Test;

import seedu.address.logic.commands.TodoCommand;
import seedu.address.model.todo.Title;
import seedu.address.model.todo.Todo;
import seedu.address.testutil.TodoBuilder;

public class TodoCommandParserTest {
    private TodoCommandParser parser = new TodoCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Todo expectedTodo = new TodoBuilder(TASK1).withTitle(VALID_TITLE_TASK1).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_TASK1 + CONTENT_DESC_TASK1,
                new TodoCommand(expectedTodo));

        // multiple titles - last title accepted
        assertParseSuccess(parser, TITLE_DESC_TASK2 + CONTENT_DESC_TASK1 + TITLE_DESC_TASK1,
                new TodoCommand(expectedTodo));

        // multiple content - last content accepted
        assertParseSuccess(parser, TITLE_DESC_TASK1 + CONTENT_DESC_TASK2 + CONTENT_DESC_TASK1,
                new TodoCommand(expectedTodo));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TodoCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, VALID_TITLE_TASK1 + CONTENT_DESC_TASK1, expectedMessage);

        // missing content prefix
        assertParseFailure(parser, TITLE_DESC_TASK2 + VALID_CONTENT_TASK2, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_TASK1 + VALID_CONTENT_TASK1, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_TITLE_DESC + CONTENT_DESC_TASK1, Title.MESSAGE_TITLE_CONSTRAINTS);
    }
}
