package seedu.address.testutil;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.record.Date;

import static seedu.address.testutil.TypicalRecords.getTypicalAddressBook;

public class TypicalDates {
    private static Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    public static final Date DATE_FIRST_INDEX_DATE = model.getFilteredRecordList().get(TypicalIndexes.INDEX_FIRST_RECORD.getZeroBased()).getDate();
    public static final Date DATE_LAST_INDEX_DATE = model.getFilteredRecordList().get(TypicalIndexes.INDEX_SECOND_RECORD.getZeroBased()).getDate();
    public static final Date DATE_THIRD_INDEX_DATE = model.getFilteredRecordList().get(TypicalIndexes.INDEX_THIRD_RECORD.getZeroBased()).getDate();

}
