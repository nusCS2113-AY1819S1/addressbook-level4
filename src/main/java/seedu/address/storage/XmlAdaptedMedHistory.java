package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medhistory.Allergy;
import seedu.address.model.medhistory.MedHistDate;
import seedu.address.model.medhistory.MedHistory;
import seedu.address.model.medhistory.PrevCountry;


/**
 * JAXB-friendly version of the Medical History.
 */

public class XmlAdaptedMedHistory {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "MedHistory's %s field is missing!";

    @XmlValue
    private String medHistDate;
    @XmlValue
    private String allergy;
    @XmlValue
    private String prevCountry;

    /**
     * Constructs an XmlAdaptedMedHistory.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMedHistory() {}

    /**
     * Constructs an {@code XmlAdaptedMedHistory} with the given medical history details.
     */
    public XmlAdaptedMedHistory(String medHistDate, String allergy, String prevCountry) {
        this.medHistDate = medHistDate;
        this.allergy = allergy;
        this.prevCountry = prevCountry;
    }

    /**
     * Converts a given medical history into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedMedHistory
     */

    public XmlAdaptedMedHistory(MedHistory source) {
        medHistDate = source.getMedHistDate().toString();
        allergy = source.getAllergy().toString();
        prevCountry = source.getPrevCountry().toString();
    }

    /**
     * Converts this jaxb-friendly adapted medical history object into the model's medical history object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted medical history
     */
    public MedHistory toModelType() throws IllegalValueException {
        if (medHistDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MedHistory.class.getSimpleName()));
        }
        final MedHistDate modelMedHistDate = new MedHistDate(medHistDate);
        if (allergy == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MedHistory.class.getSimpleName()));
        }
        final Allergy modelAllergy = new Allergy(allergy);
        if (prevCountry == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MedHistory.class.getSimpleName()));
        }
        final PrevCountry modelPrevCountry = new PrevCountry(prevCountry);

        return new MedHistory(modelMedHistDate, modelAllergy, modelPrevCountry);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMedHistory)) {
            return false;
        }

        XmlAdaptedMedHistory otherMedHistory = (XmlAdaptedMedHistory) other;
        return Objects.equals(medHistDate, otherMedHistory.medHistDate)
                && Objects.equals(allergy, otherMedHistory.allergy)
                && Objects.equals(prevCountry, otherMedHistory.prevCountry);
    }

}
