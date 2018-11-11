package seedu.recruit.model.company;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.recruit.testutil.Assert;

public class CompanyNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CompanyName(null));
    }

    @Test
    public void constructor_invalidCompanyName_throwsIllegalArgumentException() {
        String invalidCompanyName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new CompanyName(invalidCompanyName));
    }

    @Test
    public void isValidCompanyName() {
        // null company y name
        Assert.assertThrows(NullPointerException.class, () -> CompanyName.isValidCompanyName(null));

        // invalid company  name
        assertFalse(CompanyName.isValidCompanyName("")); // empty string

        // valid company  name
        assertTrue(CompanyName.isValidCompanyName("McDonald's"));
        assertTrue(CompanyName.isValidCompanyName("KFC@Suntec City"));

    }
}
