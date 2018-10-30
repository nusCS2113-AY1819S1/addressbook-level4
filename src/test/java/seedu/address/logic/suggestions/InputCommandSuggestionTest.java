package seedu.address.logic.suggestions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class InputCommandSuggestionTest {
    private InputCommandSuggestion ics;

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
    public void removeSearchCharacter() {
        assertTrue(ics.removeSearchCharacter());
        ics.checkValidCharacter('a');
        assertTrue(ics.removeSearchCharacter());
        ics.checkValidCharacter('a');
        ics.checkValidCharacter('a');
        assertTrue(ics.removeSearchCharacter());
        assertTrue(ics.removeSearchCharacter());
    }
}
