package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Deadline;

/**
 * Contains helper methods for testing command parsers.
 */
public class CommandParserTestUtil {
    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertParseSuccess(Parser parser, String userInput, Command expectedCommand) {
        try {
            Command command = parser.parse(userInput);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }
    //@@author chelseyong
    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful
     * by setting the deadline
     * and the command created equals to {@code expectedCommand}.
     * Only applicable for AddTaskCommandParserTest
     */
    public static void assertParseSuccessWithDate(AddTaskCommandParserTest.ParserWithDate parser, Deadline date,
                                                  String userInput, Command expectedCommand) {
        try {
            Command command = parser.parse(userInput, date);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }
    //@@author
    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertParseFailure(Parser parser, String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
