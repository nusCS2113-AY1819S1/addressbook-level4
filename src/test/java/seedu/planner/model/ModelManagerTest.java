package seedu.planner.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.planner.model.Model.PREDICATE_SHOW_ALL_RECORDS;
import static seedu.planner.testutil.TypicalRecords.CAIFAN;
import static seedu.planner.testutil.TypicalRecords.INDO;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.planner.model.record.NameContainsKeywordsPredicate;
import seedu.planner.testutil.FinancialPlannerBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasRecord_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasRecord(null);
    }

    @Test
    public void hasRecord_recordNotInFinancialPlanner_returnsFalse() {
        assertFalse(modelManager.hasRecord(INDO));
    }

    @Test
    public void hasRecord_recordInFinancialPlanner_returnsTrue() {
        modelManager.addRecord(INDO);
        assertTrue(modelManager.hasRecord(INDO));
    }

    @Test
    public void getFilteredRecordList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredRecordList().remove(0);
    }

    @Test
    public void equals() {
        FinancialPlanner financialPlanner = new FinancialPlannerBuilder().withRecord(INDO).withRecord(CAIFAN).build();
        FinancialPlanner differentFinancialPlanner = new FinancialPlanner();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(financialPlanner, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(financialPlanner, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different financialPlanner -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentFinancialPlanner, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = INDO.getName().fullName.split("\\s+");
        modelManager.updateFilteredRecordList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(financialPlanner, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORDS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setFinancialPlannerFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(financialPlanner, differentUserPrefs)));
    }
}
