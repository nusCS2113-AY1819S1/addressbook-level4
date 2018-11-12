package seedu.address.logic.suggestions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.TodoCommand;
import seedu.address.logic.commands.UndoCommand;

public class WrongCommandSuggestionTest {
    private Suggestion wrongCommandSuggestion = new WrongCommandSuggestion();
    private ArrayList<String> outputCommandList;

    @Before
    public void setup() {
        outputCommandList = new ArrayList<>();
    }

    @Test
    public void getSuggestion_noSuggestions() {
        // "abcDEFGH" input is not similar to any command
        assertNull(wrongCommandSuggestion.getSuggestions("abcDEFGH"));
    }

    @Test
    public void getSuggestion_oneSuggestion() {
        // "histary" input is similar to history command and has only one suggestion available
        outputCommandList.add(HistoryCommand.COMMAND_WORD);
        assertEquals(outputCommandList, wrongCommandSuggestion.getSuggestions("histary"));
    }

    @Test
    public void getSuggestion_multipleSuggestions() {
        outputCommandList.add(RedoCommand.COMMAND_WORD);
        outputCommandList.add(TodoCommand.COMMAND_WORD);
        outputCommandList.add(UndoCommand.COMMAND_WORD);
        assertEquals(outputCommandList, wrongCommandSuggestion.getSuggestions("wido"));
    }

}
