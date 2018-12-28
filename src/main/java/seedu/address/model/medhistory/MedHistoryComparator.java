package seedu.address.model.medhistory;

import java.util.Comparator;

//@@author xShaivan
/**
 * Comparator for TreeSet to sort MedHistory entries by date.
 * If dates are similar, sort by allergy.
 */
public class MedHistoryComparator implements Comparator<MedHistory> {
    @Override
    public int compare(MedHistory hist1, MedHistory hist2) {
        if (!hist1.getMedHistDate().value.equals(hist2.getMedHistDate().value)) {
            return hist1.getMedHistDate().value.compareTo(hist2.getMedHistDate().value);
        } else {
            return hist1.getAllergy().toString().compareTo(hist2.getAllergy().toString());
        }
    }
}
