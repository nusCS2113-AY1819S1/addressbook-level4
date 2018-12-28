package seedu.address.model.person.addinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HeightTest {

    @Test
    public void isValidHeight() {
        assertTrue(Height.isValidHeight("180"));
        assertTrue(Height.isValidHeight("120.3"));

        assertFalse(Height.isValidHeight("ABC"));
        assertFalse(Height.isValidHeight("140.2.2"));
    }


    @Test
    public void heightToString() {
        Height height = new Height("170.2");
        assertEquals(height.toString(), "170.2");
    }
}
