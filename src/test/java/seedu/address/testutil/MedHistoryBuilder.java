package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_ALLERGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_COUNTRY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_DATE;

import seedu.address.model.medhistory.Allergy;
import seedu.address.model.medhistory.MedHistDate;
import seedu.address.model.medhistory.MedHistory;
import seedu.address.model.medhistory.PrevCountry;

/**
 * A utility class to help with building MedHistory objects.
 */
public class MedHistoryBuilder {
    public static final String DEFAULT_MEDHISTDATE = VALID_HISTORY_DATE;
    public static final String DEFAULT_ALLERGY = VALID_HISTORY_ALLERGY;
    public static final String DEFAULT_PREVCOUNTRY = VALID_HISTORY_COUNTRY;

    private MedHistDate medHistDate;
    private Allergy allergy;
    private PrevCountry prevCountry;

    public MedHistoryBuilder() {
        medHistDate = new MedHistDate(DEFAULT_MEDHISTDATE);
        allergy = new Allergy(DEFAULT_ALLERGY);
        prevCountry = new PrevCountry(DEFAULT_PREVCOUNTRY);
    }
    /**
     * Initializes the ReportBuilder with the data of {@code medHistoryToCopy}.
     */
    public MedHistoryBuilder(MedHistory medHistoryToCopy) {
        medHistDate = medHistoryToCopy.getMedHistDate();
        allergy = medHistoryToCopy.getAllergy();
        prevCountry = medHistoryToCopy.getPrevCountry();
    }
    /**
     * Sets the {@code MedHistDate} of the {@code MedHistory} that we are building.
     */
    public MedHistoryBuilder withMedHistDate(String medHistDate) {
        this.medHistDate = new MedHistDate(medHistDate);
        return this;
    }
    /**
     * Sets the {@code Allergy} of the {@code MedHistory} that we are building.
     */
    public MedHistoryBuilder withAllergy(String allergy) {
        this.allergy = new Allergy(allergy);
        return this;
    }
    /**
     * Sets the {@code PrevCountry} of the {@code MedHistory} that we are building.
     */
    public MedHistoryBuilder withPrevCountry(String prevCountry) {
        this.prevCountry = new PrevCountry(prevCountry);
        return this;
    }

    public MedHistory build() {
        return new MedHistory(medHistDate, allergy, prevCountry);
    }
}
