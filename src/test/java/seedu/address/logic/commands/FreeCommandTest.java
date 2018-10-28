package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;

public class FreeCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new FreeCommand(null);
    }

    @Test
    public void equals() {
        Collection<Index> containsOne = new ArrayList<>();
        containsOne.add(INDEX_FIRST_PERSON);

        Collection<Index> containsOneAndTwo = new ArrayList<>();
        containsOneAndTwo.add(INDEX_FIRST_PERSON);
        containsOneAndTwo.add(INDEX_SECOND_PERSON);

        FreeCommand freeOne = new FreeCommand(containsOne);
        FreeCommand freeOneAndTwo = new FreeCommand(containsOneAndTwo);

        // same object -> returns true
        assertTrue(freeOne.equals(freeOne));

        // same values -> returns true
        FreeCommand freeOneCopy = new FreeCommand(containsOne);
        assertTrue(freeOne.equals(freeOneCopy));

        // different types -> returns false
        assertFalse(freeOne.equals(1));

        // null -> returns false
        assertFalse(freeOne.equals(null));

        // different indices -> returns false
        assertFalse(freeOne.equals(freeOneAndTwo));
    }

    // TODO: Execution tests
}
