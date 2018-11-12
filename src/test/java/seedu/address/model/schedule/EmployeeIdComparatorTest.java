package seedu.address.model.schedule;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.model.person.EmployeeId;

public class EmployeeIdComparatorTest {
    private final EmployeeIdComparator idCompare = new EmployeeIdComparator();

    @Test
    public void compare_validBigToSmall_bigGreaterThanSmallTrue() {
        EmployeeId small = new EmployeeId("000001");
        EmployeeId big = new EmployeeId("000002");
        int result = idCompare.compare(big, small);
        assertEquals(1, result);
    }

    @Test
    public void compare_validSmallToBig_smallGreaterThanBigFalse() {
        EmployeeId small = new EmployeeId("000001");
        EmployeeId big = new EmployeeId("000002");
        int result = idCompare.compare(small, big);
        assertEquals(-1, result);
    }

    @Test
    public void compare_validSameId_equal() {
        EmployeeId id = new EmployeeId("000001");
        int result = idCompare.compare(id, id);
        assertEquals(0, result);
    }
}
