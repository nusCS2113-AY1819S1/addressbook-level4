package seedu.address.model.group;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalAddGroups.ADD_GROUP_1;
import static seedu.address.testutil.TypicalAddGroups.ADD_GROUP_2;
import static seedu.address.testutil.TypicalAddGroups.ADD_GROUP_3;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.getSingleTypicalPersonIndicesSet;
import static seedu.address.testutil.TypicalIndexes.getTypicalPersonIndicesSet;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.testutil.Assert;

public class AddGroupTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddGroup(null,null));
    }

    @Test
    public void constructor_invalidIndex_throwsIndexOutOfBoundsException() {
        String invalidGroupIndex = "0";
        Assert.assertThrows(IndexOutOfBoundsException.class, () -> new AddGroup(Index.fromOneBased(Integer.parseInt(invalidGroupIndex)),
                getSingleTypicalPersonIndicesSet()));
    }

    @Test
    public void constructor_invalidIndex_throwsNumberFormatException() {
        String invalidGroupIndex = "e";
        Assert.assertThrows(NumberFormatException.class, () -> new AddGroup(Index.fromOneBased(Integer.parseInt(invalidGroupIndex)),
                getSingleTypicalPersonIndicesSet()));
    }

    @Test
    public void equals() {
        // same values -> returns true
        AddGroup addGroup1Copy = new AddGroup(INDEX_FIRST_GROUP,getTypicalPersonIndicesSet());
        assertTrue(ADD_GROUP_1.equals(addGroup1Copy));

        // same object -> returns true
        assertTrue(ADD_GROUP_1.equals(ADD_GROUP_1));

        // null -> returns false
        assertFalse(ADD_GROUP_1.equals(null));

        // different type -> returns false
        assertFalse(ADD_GROUP_1.equals(5));

        // different addGroup -> returns false
        assertFalse(ADD_GROUP_1.equals(ADD_GROUP_3));

        // different group index -> returns false
        assertFalse(ADD_GROUP_1.equals(ADD_GROUP_2));

        // different person indices -> returns false
        assertFalse(ADD_GROUP_1.equals(ADD_GROUP_3));
    }


}
