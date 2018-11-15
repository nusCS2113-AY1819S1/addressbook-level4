package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedClubBudgetElements.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalClubBudgetElements.COMPUTING_CLUB;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.budgetelements.ClubName;
import seedu.address.model.budgetelements.ExpectedTurnout;
import seedu.address.model.budgetelements.NumberOfEvents;

import seedu.address.testutil.Assert;

public class XmlAdaptedClubBudgetElementsTest {
    private static final String INVALID_CLUB_NAME = "Comput!ing";
    private static final String INVALID_EXPECTED_TURNOUT = "20a";
    private static final String INVALID_NUMBER_OF_EVENTS = "1a";

    private static final String VALID_CLUB_NAME = COMPUTING_CLUB.getClubName().toString();
    private static final String VALID_EXPECTED_TURNOUT = COMPUTING_CLUB.getExpectedTurnout().toString();
    private static final String VALID_NUMBER_OF_EVENTS = COMPUTING_CLUB.getNumberOfEvents().toString();

    @Test
    public void toModelType_validClubBudgetElementsDetails_returnsClubBudgetElements() throws Exception {
        XmlAdaptedClubBudgetElements club = new XmlAdaptedClubBudgetElements(COMPUTING_CLUB);
        assertEquals(COMPUTING_CLUB, club.toModelType());
    }

    @Test
    public void toModelType_invalidClubName_throwsIllegalValueException() {
        XmlAdaptedClubBudgetElements club =
                new XmlAdaptedClubBudgetElements(INVALID_CLUB_NAME, VALID_EXPECTED_TURNOUT, VALID_NUMBER_OF_EVENTS);
        String expectedMessage = ClubName.MESSAGE_CLUB_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, club::toModelType);
    }

    @Test
    public void toModelType_nullClubName_throwsIllegalValueException() {
        XmlAdaptedClubBudgetElements club = new XmlAdaptedClubBudgetElements(null, VALID_EXPECTED_TURNOUT,
                VALID_NUMBER_OF_EVENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ClubName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, club::toModelType);
    }

    @Test
    public void toModelType_invalidExpectedTurnout_throwsIllegalValueException() {
        XmlAdaptedClubBudgetElements club =
                new XmlAdaptedClubBudgetElements(VALID_CLUB_NAME, INVALID_EXPECTED_TURNOUT, VALID_NUMBER_OF_EVENTS);
        String expectedMessage = ExpectedTurnout.MESSAGE_EXPECTED_TURNOUT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, club::toModelType);
    }

    @Test
    public void toModelType_nullExpectedTurnout_throwsIllegalValueException() {
        XmlAdaptedClubBudgetElements club = new XmlAdaptedClubBudgetElements(VALID_CLUB_NAME, null,
                VALID_NUMBER_OF_EVENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ExpectedTurnout.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, club::toModelType);
    }

    @Test
    public void toModelType_invalidNumberOfEvents_throwsIllegalValueException() {
        XmlAdaptedClubBudgetElements club =
                new XmlAdaptedClubBudgetElements(VALID_CLUB_NAME, VALID_EXPECTED_TURNOUT, INVALID_NUMBER_OF_EVENTS);
        String expectedMessage = NumberOfEvents.MESSAGE_NUMBER_OF_EVENTS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, club::toModelType);
    }

    @Test
    public void toModelType_nullNumberOfEvents_throwsIllegalValueException() {
        XmlAdaptedClubBudgetElements club = new XmlAdaptedClubBudgetElements(VALID_CLUB_NAME, VALID_EXPECTED_TURNOUT,
                null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, NumberOfEvents.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, club::toModelType);
    }

}
