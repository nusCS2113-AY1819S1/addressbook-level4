package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.logic.commands.CommandList;

public class SimilarityParserTest {

    private final SimilarityParser parser = new SimilarityParser();
    private CommandList commandList = new CommandList();
    private ArrayList<String> commands = commandList.getCommandList();
    //Spelling Errors are accepted in DiceCoefficient algorithm.
    @Test
    public void performSimilarityCheck_validSpellingError_addCommand() {
        assertEquals(parser.performSimilarityCheck("ad", commands), "add");
    }

    @Test
    public void performSimilarityCheck_validSpellingError_editCommand() {
        assertEquals(parser.performSimilarityCheck("edi", commands), "edit");
    }

    //Applies for owners.
    @Test
    public void performSimilarityCheck_validSpellingError_toggleRequestsCommand() {
        assertEquals(parser.performSimilarityCheck("togglereq", commands), "togglerequests");
    }

    //Applies for accountants.
    @Test
    public void performSimilarityCheck_validSpellingError_viewStatisticsCommand() {
        assertEquals(parser.performSimilarityCheck("stat", commands), "stats");
    }

    //Invalid in such scenario is acceptable because the command will be accepted by the BookInventoryParser beforehand.
    @Test
    public void performSimilarityCheck_invalid_similarCommand() {
        assertNotEquals(parser.performSimilarityCheck("togglerequests", commands), "togglerequests");
    }

    //Too much spelling error. Not detected. Returns empty string.
    @Test
    public void performSimilarityCheck_invalid_toggleRequestsCommand() {
        assertEquals(parser.performSimilarityCheck("toggReq", commands), "");
    }

    //Random input. Returns empty string.
    @Test
    public void performSimilarityCheck_invalid_randomCommand() {
        assertEquals(parser.performSimilarityCheck("t141n", commands), "");
    }
}
