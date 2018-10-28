package seedu.address.model.distributor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DistributorPhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DistributorPhone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DistributorPhone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> DistributorPhone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(DistributorPhone.isValidPhone("")); // empty string
        assertFalse(DistributorPhone.isValidPhone(" ")); // spaces only
        assertFalse(DistributorPhone.isValidPhone("91")); // less than 8 numbers
        assertFalse(DistributorPhone.isValidPhone("phone")); // non-numeric
        assertFalse(DistributorPhone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(DistributorPhone.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(DistributorPhone.isValidPhone("91177783")); // exactly 8 numbers
        assertTrue(DistributorPhone.isValidPhone("124293842033123")); // long phone numbers
    }
}
