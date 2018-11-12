package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ModeTest {
    @Test
    public void equals() {
        Mode mode = new Mode("in");

        // same object -> returns true
        assertTrue(mode.equals(mode));

        // same values -> returns true
        Mode modeCopy = new Mode(mode.value);
        assertTrue(mode.equals(modeCopy));

        // different types -> returns false
        assertFalse(mode.equals(1));

        // null -> returns false
        assertFalse(mode.equals(null));

        // different remark -> returns false
        Mode differentMode = new Mode("out");
        assertFalse(mode.equals(differentMode));
    }

    @Test
    public void hashCode_sameObject_equals() {
        Mode mode = new Mode("out");
        int expectedHash = mode.hashCode();
        assertEquals(mode.hashCode(), expectedHash);
    }

    @Test
    public void hashCode_differentObject_equals() {
        Mode mode = new Mode("out");
        int expectedHash = mode.hashCode();
        Mode sameMode = new Mode("out");
        assertEquals(sameMode.hashCode(), expectedHash);
    }

    @Test
    public void hashCode_differentValues_notEquals() {
        Mode mode = new Mode("out");
        int expectedHash = mode.hashCode();
        Mode differentMode = new Mode("in");
        assertNotEquals(differentMode.hashCode(), expectedHash);
    }
}
