package seedu.address.logic.suggestions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportAllCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.TodoCommand;

public class InputCommandSuggestionTest {
    private InputCommandSuggestion ics;
    private ArrayList<String> suggestedCommands;

    @Before
    public void setUp() {
        ics = new InputCommandSuggestion();
    }

    @Test
    public void checkValidCharacter() {
        assertTrue(ics.checkValidCharacter('a'));
        assertTrue(ics.checkValidCharacter('d'));
        assertTrue(ics.checkValidCharacter('d'));
        assertFalse(ics.checkValidCharacter('s'));
        assertFalse(ics.checkValidCharacter('\r'));
    }

    @Test
    public void checkValidString() {
        assertTrue(ics.checkValidString("add"));
        assertTrue(ics.checkValidString("todo"));
        assertTrue(ics.checkValidString("schedule"));
        assertFalse(ics.checkValidString("adds"));
        assertFalse(ics.checkValidString("z"));
    }

    @Test
    public void removeSearchCharacter() {
        assertTrue(ics.removeSearchCharacter());
        ics.checkValidCharacter('a');
        assertTrue(ics.removeSearchCharacter());
        ics.checkValidCharacter('a');
        ics.checkValidCharacter('a');
        assertTrue(ics.removeSearchCharacter());
        assertTrue(ics.removeSearchCharacter());
    }

    @Test
    public void getSuggestedCommands() {
        suggestedCommands = new ArrayList<>();
        suggestedCommands.add("add");
        assertEquals(ics.getSuggestedCommands("a"), suggestedCommands);
        assertEquals(ics.getSuggestedCommands("a n/JOHN DOE"), suggestedCommands);
        assertNotEquals(ics.getSuggestedCommands("az n/JOHN DOE"), suggestedCommands);

        suggestedCommands = new ArrayList<>();
        suggestedCommands.add(EditCommand.COMMAND_WORD);
        suggestedCommands.add(ExportAllCommand.COMMAND_WORD);
        suggestedCommands.add(ExitCommand.COMMAND_WORD);
        assertEquals(ics.getSuggestedCommands("e"), suggestedCommands);
        assertNotEquals(ics.getSuggestedCommands("ex"), suggestedCommands);
    }

    @Test
    public void getCommandParameters() {
        assertEquals(ics.getCommandParameters("add"), AddCommand.COMMAND_PARAMETERS);
        assertEquals(ics.getCommandParameters("delete"), DeleteCommand.COMMAND_PARAMETERS);
        assertEquals(ics.getCommandParameters("edit"), EditCommand.COMMAND_PARAMETERS);
        assertEquals(ics.getCommandParameters("exportall"), ExportAllCommand.COMMAND_PARAMETERS);
        assertEquals(ics.getCommandParameters("export"), ExportCommand.COMMAND_PARAMETERS);
        assertEquals(ics.getCommandParameters("find"), FindCommand.COMMAND_PARAMETERS);
        assertEquals(ics.getCommandParameters("schedule"), ScheduleCommand.COMMAND_PARAMETERS);
        assertEquals(ics.getCommandParameters("select"), SelectCommand.COMMAND_PARAMETERS);
        assertEquals(ics.getCommandParameters("todo"), TodoCommand.COMMAND_PARAMETERS);
        assertEquals(ics.getCommandParameters("clear"), InputCommandSuggestion.NO_REQUIRED_PARAMETERS);
        assertEquals(ics.getCommandParameters("xx"), InputCommandSuggestion.INVALID_COMMAND_PARAMETERS);
    }
}
