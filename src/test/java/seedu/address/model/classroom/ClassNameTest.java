package seedu.address.model.classroom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ClassNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ClassName(null));
    }

    @Test
    public void constructor_invalidClassName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ClassName(invalidName));
    }

    @Test
    public void isValidClassName(){
        // null className
        Assert.assertThrows(NullPointerException.class, () -> ClassName.isValidClassName(null));

        // invalid className
        assertFalse(ClassName.isValidClassName("")); // empty string
        assertFalse(ClassName.isValidClassName(" ")); // spaces only
        assertFalse(ClassName.isValidClassName("^")); // only non-alphanumeric characters
        assertFalse(ClassName.isValidClassName("t16*")); // contains non-alphanumeric characters
        assertFalse(ClassName.isValidClassName("t161")); // contains more than 3 alphanumeric characters

        // valid className
        assertTrue(ClassName.isValidClassName("T16")); // alphanumeric
        assertTrue(ClassName.isValidClassName("ALL")); // letters only
        assertTrue(ClassName.isValidClassName("1")); // single number only
        assertTrue(ClassName.isValidClassName("999")); // numbers only
    }

    @Test
    public void equals() {
        ClassName className = new ClassName("T16");
        // same object -> returns true
        assertTrue(className.equals(className));
        // same values -> returns true
        ClassName classNameCopy = new ClassName(className.getValue());
        assertTrue(className.equals(classNameCopy));
        // different types -> returns false
        assertFalse(className.equals(1));
        // null -> returns false
        assertFalse(className.equals(null));
        // different classname -> returns false
        ClassName differentClassName = new ClassName("16T");
        assertFalse(className.equals(differentClassName));
    }
}
