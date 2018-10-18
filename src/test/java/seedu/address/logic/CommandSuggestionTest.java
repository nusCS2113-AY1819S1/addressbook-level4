package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class CommandSuggestionTest {
    private CommandSuggestion commandSuggestion = new CommandSuggestion();

    @Test
    public void getSuggestion() {
        // "abcDEF" command is not similar to "test" command
        assertSame(commandSuggestion.getSuggestion("abcDEF"), CommandSuggestion.NO_SUGGESTION);

        // "test" command is similar to "test" command, returns edit distance 0
        assertEquals(commandSuggestion.getSuggestion("test"),
                String.format(CommandSuggestion.SUGGESTION_HEADER, 0));

        // "tes" command is similar to "test" command, returns edit distance 1
        assertEquals(commandSuggestion.getSuggestion("tes"),
                String.format(CommandSuggestion.SUGGESTION_HEADER, 1));
    }

}
