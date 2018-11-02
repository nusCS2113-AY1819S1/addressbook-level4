package seedu.recruit.logic.parser;

import static org.junit.Assert.assertEquals;

import seedu.recruit.logic.LogicState;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.SelectCandidateCommand;
import seedu.recruit.logic.commands.SelectCompanyCommand;
import seedu.recruit.logic.commands.SelectJobCommand;
import seedu.recruit.logic.commands.ShortlistCandidateCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;

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

    // ================================ SHORTLIST COMMAND ===================================== //
    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertSelectCompanyCommandParseSuccess(ShortlistParser parser, String userInput,
                                                                 Command expectedCommand) {
        try {
            Command command = parser.parseCommand(SelectCompanyCommand.COMMAND_WORD, userInput,
                    new LogicState(SelectCompanyCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST));
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertSelectCompanyCommandParseFailure(ShortlistParser parser, String userInput,
                                                                 String expectedMessage) {
        try {
            parser.parseCommand(SelectCompanyCommand.COMMAND_WORD, userInput,
                    new LogicState(SelectCompanyCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST));
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertSelectJobCommandParseSuccess(ShortlistParser parser, String userInput,
                                                              Command expectedCommand) {
        try {
            Command command = parser.parseCommand(SelectJobCommand.COMMAND_WORD, userInput,
                    new LogicState(SelectJobCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST));
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertSelectJobCommandParseFailure(ShortlistParser parser, String userInput,
                                                              String expectedMessage) {
        try {
            parser.parseCommand(SelectJobCommand.COMMAND_WORD, userInput,
                    new LogicState(SelectJobCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST));
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertSelectCandidateCommandParseSuccess(ShortlistParser parser, String userInput,
                                                          Command expectedCommand) {
        try {
            Command command = parser.parseCommand(SelectCandidateCommand.COMMAND_WORD, userInput,
                    new LogicState(SelectCandidateCommand.COMMAND_LOGIC_STATE));
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertSelectCandidateCommandParseFailure(ShortlistParser parser, String userInput,
                                                          String expectedMessage) {
        try {
            parser.parseCommand(SelectCandidateCommand.COMMAND_WORD, userInput,
                    new LogicState(SelectCandidateCommand.COMMAND_LOGIC_STATE));
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertShortlistCandidateCommandParseSuccess(ShortlistParser parser, String userInput,
                                                                Command expectedCommand) {
        try {
            Command command = parser.parseCommand(ShortlistCandidateCommand.COMMAND_WORD, userInput,
                    new LogicState(ShortlistCandidateCommand.COMMAND_LOGIC_STATE));
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertShortlistCandidateCommandParseFailure(ShortlistParser parser, String userInput,
                                                                String expectedMessage) {
        try {
            parser.parseCommand(ShortlistCandidateCommand.COMMAND_WORD, userInput,
                    new LogicState(ShortlistCandidateCommand.COMMAND_LOGIC_STATE));
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
