package seedu.address.model.medhistory;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Model of medical history of a patient.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class MedHistory {

    // Data fields
    private final MedHistDate medHistDate;
    private final Allergy allergy;
    private final PrevCountry prevCountry;

    public MedHistory(MedHistDate medHistDate, Allergy allergy, PrevCountry prevCountry) {
        requireAllNonNull(medHistDate, allergy, prevCountry);
        this.medHistDate = medHistDate;
        this.allergy = allergy;
        this.prevCountry = prevCountry;
    }

    public MedHistDate getMedHistDate() {
        return medHistDate;
    }
    public Allergy getAllergy() {
        return allergy;
    }
    public PrevCountry getPrevCountry() {
        return prevCountry;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MedHistory)) {
            return false;
        }

        MedHistory otherPerson = (MedHistory) other;
        return otherPerson.getMedHistDate().equals(getMedHistDate())
                && otherPerson.getAllergy().equals(getAllergy())
                && otherPerson.getPrevCountry().equals(getPrevCountry());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(medHistDate, allergy, prevCountry);
    }

    @Override
    public String toString() {
        return medHistDate.toString() + " \n" + allergy.toString() + " \n" + prevCountry.toString();
    }
}
