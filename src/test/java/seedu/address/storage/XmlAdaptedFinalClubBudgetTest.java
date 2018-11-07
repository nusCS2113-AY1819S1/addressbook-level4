package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedFinalClubBudget.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalFinalClubBudget.COMPUTING_CLUB;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.budgetelements.ClubName;
import seedu.address.testutil.Assert;

public class XmlAdaptedFinalClubBudgetTest {
    private static final String INVALID_CLUB_NAME = "Comput!ing";

    private static final String VALID_CLUB_NAME = COMPUTING_CLUB.getClubName().toString();
    private static final String VALID_ALLOCATED_BUDGET = Integer.toString(COMPUTING_CLUB.getAllocatedBudget());

    @Test
    public void toModelType_validFinalClubBudgetDetails_returnsFinalClubBudget() throws Exception {
        XmlAdaptedFinalClubBudget budget = new XmlAdaptedFinalClubBudget(COMPUTING_CLUB);
        assertEquals(COMPUTING_CLUB, budget.toModelType());
    }

    @Test
    public void toModelType_invalidClubName_throwsIllegalValueException() {
        XmlAdaptedFinalClubBudget budget =
                new XmlAdaptedFinalClubBudget(INVALID_CLUB_NAME, VALID_ALLOCATED_BUDGET);
        String expectedMessage = ClubName.MESSAGE_CLUB_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullClubName_throwsIllegalValueException() {
        XmlAdaptedFinalClubBudget budget = new XmlAdaptedFinalClubBudget(null, VALID_ALLOCATED_BUDGET);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ClubName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

}
