package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_ALLERGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_ALLERGY2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_COUNTRY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_COUNTRY2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_DATE2;

import seedu.address.model.medhistory.MedHistory;
/**
 * A utility class containing a list of {@code MedicalReport} objects to be used in tests.
 */
public class TypicalMedHistory {
    public static final MedHistory MEDHISTORY1 = new MedHistoryBuilder().withMedHistDate(VALID_HISTORY_DATE)
            .withAllergy(VALID_HISTORY_ALLERGY).withPrevCountry(VALID_HISTORY_COUNTRY).build();
    public static final MedHistory MEDHISTORY2 = new MedHistoryBuilder().withMedHistDate(VALID_HISTORY_DATE2)
            .withAllergy(VALID_HISTORY_ALLERGY2).withPrevCountry(VALID_HISTORY_COUNTRY2).build();
    public static final MedHistory EMPTY_MEDHISTORY = new MedHistoryBuilder().withMedHistDate("").withAllergy("")
            .withPrevCountry("").build();
}
