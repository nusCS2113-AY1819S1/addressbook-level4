package seedu.planner.testutil;

import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.record.Date;

/**
 * A utility class containing a list of {@code Date} objects to be used in tests.
 */
public class TypicalDates {
    private static Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    public static final Date DATE_FIRST_INDEX_DATE = model.getFilteredRecordList().get(
            TypicalIndexes.INDEX_FIRST_RECORD.getZeroBased()).getDate();
    public static final Date DATE_SECOND_INDEX_DATE = model.getFilteredRecordList().get(
            TypicalIndexes.INDEX_SECOND_RECORD.getZeroBased()).getDate();
    public static final Date DATE_THIRD_INDEX_DATE = model.getFilteredRecordList().get(
            TypicalIndexes.INDEX_THIRD_RECORD.getZeroBased()).getDate();
    public static final Date DATE_LAST_INDEX_DATE = model.getFilteredRecordList().get(
            TypicalIndexes.INDEX_LAST_RECORD.getZeroBased()).getDate();
}
