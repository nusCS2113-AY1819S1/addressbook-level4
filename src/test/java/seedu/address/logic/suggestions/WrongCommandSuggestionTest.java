package seedu.address.logic.suggestions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import seedu.address.logic.commands.ExportAllCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ScheduleCommand;

public class WrongCommandSuggestionTest {
    private WrongCommandSuggestion wrongCommandSuggestion = new WrongCommandSuggestion();

    @Test
    public void getSuggestion() {
        // "abcDEF" input is not similar to any command
        assertSame(wrongCommandSuggestion.getSuggestion("abcDEF"), WrongCommandSuggestion.NO_SUGGESTION);

        // "hist" input is similar to "list" command
        assertEquals(wrongCommandSuggestion.getSuggestion("hist"),
            String.format(WrongCommandSuggestion.SUGGESTION_HEADER, ListCommand.COMMAND_WORD));

        // "shedule" input is similar to "schedule" command
        assertEquals(wrongCommandSuggestion.getSuggestion("shedule"),
            String.format(WrongCommandSuggestion.SUGGESTION_HEADER, ScheduleCommand.COMMAND_WORD));

        // "exportalll" input is similar to "exportall" command
        assertEquals(wrongCommandSuggestion.getSuggestion("exportalll"),
            String.format(WrongCommandSuggestion.SUGGESTION_HEADER, ExportAllCommand.COMMAND_WORD));
    }

}
