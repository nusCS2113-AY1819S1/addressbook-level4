package seedu.address.model.medhistory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author xShaivan
public class AllergyTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Allergy(null));
    }

    @Test
    public void constructor_invalidAllergy_throwsIllegalArgumentException() {
        String invalidAllergy = "%^&";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Allergy(invalidAllergy));
    }

    @Test
    public void isValidAllergy() {
        // null allergy
        Assert.assertThrows(NullPointerException.class, () -> Allergy.isValidAllergy(null));

        // invalid allergy
        assertFalse(Allergy.isValidAllergy("^")); // only non-alphanumeric characters
        assertFalse(Allergy.isValidAllergy("cancer*")); // contains non-alphanumeric characters

        // valid allergy
        assertTrue(Allergy.isValidAllergy("flower pollen")); // alphabets only
        assertTrue(Allergy.isValidAllergy("8123")); // numbers only
        assertTrue(Allergy.isValidAllergy("5 pollen")); // alphanumeric characters
        assertTrue(Allergy.isValidAllergy("POLLEN")); // with capital letters
        assertTrue(Allergy.isValidAllergy("there once was a pollen that came from poland")); // long names
    }
}
