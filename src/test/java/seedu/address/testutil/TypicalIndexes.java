package seedu.address.testutil;

import seedu.address.commons.core.index.Index;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalIndexes {
    public static final Index INDEX_FIRST_TASK = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_TASK = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_TASK = Index.fromOneBased(3);
    public static final Index INDEX_FOURTH_TASK = Index.fromOneBased(4);
    public static final Index INDEX_INVALID_TASK = Index.fromOneBased(99);
    //TypicalTaskBook used for testing contains less than 10 tasks, hence 99 would be an invalid index
}
