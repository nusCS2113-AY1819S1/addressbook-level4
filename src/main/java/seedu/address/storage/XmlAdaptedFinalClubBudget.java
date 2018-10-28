package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.budgetelements.ClubName;
import seedu.address.model.clubbudget.FinalClubBudget;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedFinalClubBudget {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Final Budget's %s field is missing!";

    @XmlElement(required = true)
    private String clubName;
    @XmlElement(required = true)
    private String allocatedBudget;

    /**
     * Constructs an XmlAdaptedFinalBudget.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedFinalClubBudget() {}

    /**
     * Constructs an {@code XmlAdaptedFinalBudget} with the given person details.
     */
    public XmlAdaptedFinalClubBudget(String clubName, String allocatedBudget) {
        this.clubName = clubName;
        this.allocatedBudget = allocatedBudget;
    }

    /**
     * Converts a given FinalClubBudget into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedFinalClubBudget
     */
    public XmlAdaptedFinalClubBudget(FinalClubBudget source) {
        clubName = source.getClubName().toString();
        allocatedBudget = Integer.toString(source.getAllocatedBudget());
    }

    /**
     * Converts this jaxb-friendly adapted final club budget object into the model's FinalClubBudget object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted final club budget
     */
    public FinalClubBudget toModelType() throws IllegalValueException {
        if (clubName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ClubName.class.getSimpleName()));
        }
        if (!ClubName.isValidClubName(clubName)) {
            throw new IllegalValueException(ClubName.MESSAGE_CLUB_NAME_CONSTRAINTS);
        }
        final ClubName modelClubName = new ClubName(clubName);

        if (allocatedBudget == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT));
        }
        final int modelAllocatedBudget = Integer.parseInt(allocatedBudget);
        return new FinalClubBudget(modelClubName, modelAllocatedBudget);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedFinalClubBudget)) {
            return false;
        }

        XmlAdaptedFinalClubBudget otherFinalClubBudget = (XmlAdaptedFinalClubBudget) other;
        return Objects.equals(clubName, otherFinalClubBudget.clubName);
    }
}
