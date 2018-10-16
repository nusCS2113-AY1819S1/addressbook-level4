package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.Assert;

//@@author jitwei98
public class FiletypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Filetype(null));
    }

    @Test
    public void constructor_invalidFiletype_throwsIllegalArgumentException() {
        String invalidFiletype = "ccc";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Filetype(invalidFiletype));
    }

    @Test
    public void isValidFiletype() {
        // null filetype
        Assert.assertThrows(NullPointerException.class, () -> Filetype.isValidFiletype(null));

        // invalid filetype
        assertFalse(Filetype.isValidFiletype("")); // empty string
        assertFalse(Filetype.isValidFiletype(" ")); // spaces only
        assertFalse(Filetype.isValidFiletype("coconut")); // not "csv" or "vcf"

        // valid filetype
        assertTrue(Filetype.isValidFiletype("csv"));
        assertTrue(Filetype.isValidFiletype("vcf"));
    }
}
