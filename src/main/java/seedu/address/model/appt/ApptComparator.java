package seedu.address.model.appt;

import java.util.Comparator;

//@@author brandonccm1996
/**
 * A comparator class to compare Appts according to start date and end date
 */
public class ApptComparator implements Comparator<Appt> {

    @Override
    public int compare(Appt o1, Appt o2) {
        if (!o1.getStart().value.equals(o2.getStart().value)) {
            return o1.getStart().value.compareTo(o2.getStart().value);
        } else {
            return o1.getEnd().value.compareTo(o2.getEnd().value);
        }
    }
}
