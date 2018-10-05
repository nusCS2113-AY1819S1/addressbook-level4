package seedu.planner.testutil;

<<<<<<< HEAD:src/test/java/seedu/address/testutil/TypicalIndexes.java
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import static seedu.address.testutil.TypicalRecords.getTypicalAddressBook;
=======
import seedu.planner.commons.core.index.Index;
>>>>>>> 936a266304811392cda80acfbf3d1820aac87fed:src/test/java/seedu/planner/testutil/TypicalIndexes.java

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
