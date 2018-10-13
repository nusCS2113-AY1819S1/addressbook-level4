package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersonIndexs.PERSON_INDEX_1;
import static seedu.address.testutil.TypicalPersonIndexs.PERSON_INDEX_2;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PersonIndexTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PersonIndex(null));
    }

    @Test
    public void constructor_invalidPersonIndex_throwsIllegalArgumentException() {
        String invalidPersonIndex = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new PersonIndex(invalidPersonIndex));
    }

    @Test
    public void isValidPersonIndex() {
        // null person index
        Assert.assertThrows(NullPointerException.class, () -> PersonIndex.isValidPersonIndex(null));

        // invalid person index
        assertFalse(PersonIndex.isValidPersonIndex("")); // empty string
        assertFalse(PersonIndex.isValidPersonIndex(" ")); // spaces only
        assertFalse(PersonIndex.isValidPersonIndex("/")); // only non-alphanumeric characters
        assertFalse(PersonIndex.isValidPersonIndex("TUT[1]*")); // contains non-alphanumeric characters

        // valid person index
        assertTrue(PersonIndex.isValidPersonIndex("2")); // numbers only

    }

    @Test
    public void equals() {
        PersonIndex personIndex = PERSON_INDEX_1;
        // same object -> returns true
        assertTrue(personIndex.equals(personIndex));
        // same values -> returns true
        PersonIndex personIndexCopy = new PersonIndex(personIndex.personIndex);
        assertTrue(personIndex.equals(personIndexCopy));
        // different types -> returns false
        assertFalse(personIndex.equals(1));
        // null -> returns false
        assertFalse(personIndex.equals(null));
        // different remark -> returns false
        PersonIndex differentPersonIndex = PERSON_INDEX_2;
        assertFalse(personIndex.equals(differentPersonIndex));
    }
}


