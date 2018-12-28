package seedu.address.model.medicalreport;

import java.util.Comparator;

//@@author chewkahmeng
/**
 * A comparator class to compare medical reports according to date and title
 */
public class ReportComparator implements Comparator<MedicalReport> {

    @Override
    public int compare(MedicalReport o1, MedicalReport o2) {
        if (!o1.getDate().fullDate.equals(o2.getDate().fullDate)) {
            return o1.getDate().fullDate.compareTo(o2.getDate().fullDate);
        } else {
            return o1.getTitle().fullTitle.compareTo(o2.getTitle().fullTitle);
        }
    }
}
