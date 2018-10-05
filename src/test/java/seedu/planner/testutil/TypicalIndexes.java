package seedu.planner.testutil;

import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import seedu.planner.commons.core.index.Index;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.UserPrefs;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalIndexes {
    private static Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    public static final Index INDEX_FIRST_RECORD = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_RECORD = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_RECORD = Index.fromOneBased(3);
    public static final Index INDEX_LAST_RECORD = Index.fromOneBased(model.getFilteredRecordList().size());
}
