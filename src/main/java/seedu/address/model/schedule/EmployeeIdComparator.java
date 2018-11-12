package seedu.address.model.schedule;

import java.util.Comparator;

import seedu.address.model.person.EmployeeId;

/**
 * The {@code EmployeeIdComparator} class is used for comparing which employee id is larger.
 * In ascending normal order.
 */
public class EmployeeIdComparator implements Comparator<EmployeeId> {

    /**
     * Compare in ascending order
     * @param o1 Employee 1 to be compared
     * @param o2 Employee 2 to be compared
     * @return Int, true if ascending
     */
    public int compare(EmployeeId o1, EmployeeId o2) {
        int s1 = Integer.parseInt(o1.value);
        int s2 = Integer.parseInt(o2.value);

        if (s1 == s2) {
            return 0;
        } else if (s1 > s2) {
            return 1;
        } else {
            return -1;
        }
    }
}
