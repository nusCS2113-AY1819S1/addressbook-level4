package seedu.address.logic.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.CommandHistory;



public class DifferentiatingParserTest {

    private final DifferentiatingParser parser = new DifferentiatingParser();

    private String [] string = new String[]{"default"};

    private CommandHistory commandHistory;

    private String prev = "random";

    //User input is correct for RequestList.
    @Test
    public void parseInput_valid_toggleRequestCommand() {
        string[0] = "togglerequest";
        assertTrue(parser.parseInput(string, prev, commandHistory));
    }

    @Test
    public void parseInput_valid_deleteRequestCommand() {
        string[0] = "deleterequest";
        assertTrue(parser.parseInput(string, prev, commandHistory));
    }

    //User input is correct for RequestList, accepted by DiceCoefficient algo range.
    @Test
    public void parseInput_validSpellingError_requestCommand() {
        string[0] = "reques";
        assertTrue(parser.parseInput(string, prev, commandHistory));
    }

    @Test
    public void parseInput_validSpellingError_deleteRequestCommand() {
        string[0] = "deletereques";
        assertTrue(parser.parseInput(string, prev, commandHistory));
    }

    @Test
    public void parseInput_validSpellingError_toggleRequestCommand() {
        string[0] = "togglereques";
        assertTrue(parser.parseInput(string, prev, commandHistory));
    }

    //User input is independent of command history.
    @Test
    public void parseInput_validArgs_nullCommandHistory() {
        string[0] = "request";
        assertTrue(parser.parseInput(string, prev, null));
    }

    //User input is independent of prev input unless command is Undo.
    @Test
    public void parseInput_toggleRequestCommand_changeInPrev() {
        string[0] = "togglerequest";
        prev = "request";
        assertTrue(parser.parseInput(string, prev, commandHistory));
    }

    @Test
    public void parseInput_undoCommand_prevIsRequest() {
        string[0] = "undo";
        prev = "request";
        assertTrue(parser.parseInput(string, prev, commandHistory));
    }

    @Test
    public void parseInput_undoCommand_prevIsNotRequest() {
        string[0] = "undo";
        prev = "edit";
        assertFalse(parser.parseInput(string, prev, commandHistory));
    }

    //Too much spelling error. Not detected.
    @Test
    public void parseInput_invalidSpellingError_toggleRequestCommand() {
        string[0] = "trequoggllee";
        assertFalse(parser.parseInput(string, prev, commandHistory));
    }


    @Test
    public void parseInput_invalidArgs_deleteCommand() {
        string[0] = "delete";
        assertFalse(parser.parseInput(string, prev, commandHistory));
    }

    @Test
    public void parseInput_invalidSpellingError_deleteCommand() {
        string[0] = "dellletee";
        assertFalse(parser.parseInput(string, prev, commandHistory));
    }

    @Test
    public void parseInput_invalidArgs_randomCommand() {
        string[0] = "shout";
        assertFalse(parser.parseInput(string, prev, commandHistory));
    }

}
