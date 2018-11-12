package seedu.address.model.schedule;

import static seedu.address.model.schedule.Date.DATE_PATTERN;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Comparator;

/**
 * The {@code DateComparator} class is used for comparing which date is larger.
 * In ascending normal order DD/MM/YYYY.
 */
public class DateComparator implements Comparator<Date> {

    /**
     * Compare in ascending order
     * @param o1 Date 1 to be compared
     * @param o2 Date 2 to be compared
     * @return Int, true if ascending
     */
    public int compare(Date o1, Date o2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDate s1;
        LocalDate s2;
        s1 = LocalDate.parse(o1.value, formatter);
        s2 = LocalDate.parse(o2.value, formatter);
        return s1.compareTo(s2);
    }
}
