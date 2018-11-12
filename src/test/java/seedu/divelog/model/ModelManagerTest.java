package seedu.divelog.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.divelog.model.Model.PREDICATE_SHOW_ALL_DIVES;
import static seedu.divelog.testutil.TypicalDiveSessions.DIVE_AT_BALI;
import static seedu.divelog.testutil.TypicalDiveSessions.DIVE_AT_NIGHT;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.divelog.model.dive.LocationContainsKeywordPredicate;
import seedu.divelog.testutil.DiveLogBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void getFilteredDiveSessionList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredDiveList().remove(0);
    }

    @Test
    public void equals() {
        DiveLog diveLog = new DiveLogBuilder().withDive(DIVE_AT_BALI).withDive(DIVE_AT_NIGHT).build();
        DiveLog differentDiveLog = new DiveLog();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(diveLog, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(diveLog, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different diveLog -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentDiveLog, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = {"Bali"};
        modelManager.updateFilteredDiveList(new LocationContainsKeywordPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(diveLog, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredDiveList(PREDICATE_SHOW_ALL_DIVES);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setDiveLogBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(diveLog, differentUserPrefs)));
    }
}
