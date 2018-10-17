package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalIndexes {
    public static final Index INDEX_FIRST_PERSON = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_PERSON = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_PERSON = Index.fromOneBased(3);
    public static final Index INDEX_FIRST_GROUP = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_GROUP = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_GROUP = Index.fromOneBased(3);

    public static Set<Index> getSingleTypicalPersonIndicesSet() {
        Set<Index> index = new HashSet<>();
        index.add(INDEX_FIRST_PERSON);
        return index;
    }

    public static Set<Index> getTypicalPersonIndicesSet() {
        Set<Index> index = new HashSet<>();
        index.add(INDEX_FIRST_PERSON);
        index.add(INDEX_SECOND_PERSON);
        index.add(INDEX_THIRD_PERSON);
        return index;
    }

}
