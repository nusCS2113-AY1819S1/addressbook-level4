package seedu.address.logic;

import static org.junit.Assert.assertSame;

import org.junit.Test;

public class CommandSuggestionTest {
    private CommandSuggestion commandSuggestion = new CommandSuggestion();

    @Test
    public void returnSuggestion() {
        assertSame(commandSuggestion.returnSuggestion(), CommandSuggestion.SUGGESTION_NOT_IMPLEMENTED);
    }

}
