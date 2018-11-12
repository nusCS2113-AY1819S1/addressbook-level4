package seedu.address.model.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Type(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidType = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Type(invalidType));
    }

    @Test
    public void isValidType_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> Type.isValidType(null));
    }

    @Test
    public void isValidType_emptyString_invalidType() {
        assertFalse(Type.isValidType("")); // empty string
    }

    @Test
    public void isValidType_extraChar_invalidType() {
        assertFalse(Type.isValidType("WORKs")); // middle chars wrong no match
    }

    @Test
    public void isValidType_validWorkStringType_validType() {
        //Case not sensitive
        assertTrue(Type.isValidType("WORK"));
        assertTrue(Type.isValidType("work"));
        assertTrue(Type.isValidType("WoRk"));
    }

    @Test
    public void isValidType_validLeaveStringType_validType() {
        //Case not sensitive
        assertTrue(Type.isValidType("LEAVE"));
        assertTrue(Type.isValidType("leave"));
        assertTrue(Type.isValidType("LeAvE"));
    }


    @Test
    public void typeToString_validString_correctStringRepresentation() {
        Type expectedType = new Type("LEAVE");
        assertEquals("LEAVE", expectedType.toString());
    }

    @Test
    public void typeComparable_validType_hashCodeIsCorrect() {
        Type expected = new Type("LEAVE");
        assertEquals("LEAVE".hashCode(), expected.hashCode());

    }
}
