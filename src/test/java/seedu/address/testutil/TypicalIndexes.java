package seedu.address.testutil;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import static seedu.address.testutil.TypicalRecords.getTypicalAddressBook;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalIndexes {
    private static Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    public static final Index INDEX_FIRST_RECORD = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_RECORD = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_RECORD = Index.fromOneBased(3);
    public static final Index INDEX_LAST_RECORD = Index.fromOneBased(model.getFilteredRecordList().size());
}
