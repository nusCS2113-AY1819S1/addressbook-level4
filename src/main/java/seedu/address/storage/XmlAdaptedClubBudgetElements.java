package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.budgetelements.ClubBudgetElements;
import seedu.address.model.budgetelements.ClubName;
import seedu.address.model.budgetelements.ExpectedTurnout;
import seedu.address.model.budgetelements.NumberOfEvents;

/**
 * JAXB-friendly version of the Club Budget Elements.
 */
public class XmlAdaptedClubBudgetElements {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Club Budget Elements' %s field is missing!";

    @XmlElement(required = true)
    private String clubName;
    @XmlElement(required = true)
    private String expectedTurnout;
    @XmlElement(required = true)
    private String numberOfEvents;

    /**
     * Constructs an XmlAdaptedClubBudgetElements.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedClubBudgetElements() {}

    /**
     * Constructs an {@code XmlAdaptedClubBudgetElements} with the given person details.
     */
    public XmlAdaptedClubBudgetElements(String clubName, String expectedTurnout, String numberOfEvents) {
        this.clubName = clubName;
        this.expectedTurnout = expectedTurnout;
        this.numberOfEvents = numberOfEvents;
    }

    /**
     * Converts a given ClubBudgetElements into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedClubBudgetElements
     */
    public XmlAdaptedClubBudgetElements(ClubBudgetElements source) {
        clubName = source.getClubName().toString();
        expectedTurnout = source.getExpectedTurnout().toString();
        numberOfEvents = source.getNumberOfEvents().toString();
    }

    /**
     * Converts this jaxb-friendly adapted club budget elements object into the model's ClubBudgetElements object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted club budget elements
     */
    public ClubBudgetElements toModelType() throws IllegalValueException {

        if (clubName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ClubName.class.getSimpleName()));
        }
        if (!ClubName.isValidClubName(clubName)) {
            throw new IllegalValueException(ClubName.MESSAGE_CLUB_NAME_CONSTRAINTS);
        }
        final ClubName modelClubName = new ClubName(clubName);

        if (expectedTurnout == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExpectedTurnout.class.getSimpleName()));
        }
        if (!ExpectedTurnout.isValidExpectedTurnout(expectedTurnout)) {
            throw new IllegalValueException(ExpectedTurnout.MESSAGE_EXPECTED_TURNOUT_CONSTRAINTS);
        }
        final ExpectedTurnout modelExpectedTurnout = new ExpectedTurnout(expectedTurnout);

        if (numberOfEvents == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    NumberOfEvents.class.getSimpleName()));
        }
        if (!NumberOfEvents.isValidNumberOfEvents(numberOfEvents)) {
            throw new IllegalValueException(NumberOfEvents.MESSAGE_NUMBER_OF_EVENTS_CONSTRAINTS);
        }
        final NumberOfEvents modelNumberOfEvents = new NumberOfEvents(numberOfEvents);

        return new ClubBudgetElements(modelClubName, modelExpectedTurnout, modelNumberOfEvents);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedClubBudgetElements)) {
            return false;
        }

        XmlAdaptedClubBudgetElements otherClubBudgetElements = (XmlAdaptedClubBudgetElements) other;
        return Objects.equals(clubName, otherClubBudgetElements.clubName);
    }
}
