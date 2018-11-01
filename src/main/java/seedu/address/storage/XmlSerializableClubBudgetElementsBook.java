package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ClubBudgetElementsBook;
import seedu.address.model.ReadOnlyClubBudgetElementsBook;
import seedu.address.model.budgetelements.ClubBudgetElements;

/**
 * An Immutable ClubBudgetElementsBook that is serializable to XML format
 */
@XmlRootElement(name = "budgetelementsbook")
public class XmlSerializableClubBudgetElementsBook {

    public static final String MESSAGE_DUPLICATE_CLUB_BUDGET_ELEMENTS = "Club Budget Elements list contains"
            + "duplicate club budget elements.";
    @XmlElement
    private List<XmlAdaptedClubBudgetElements> clubs;

    /**
     * Creates an empty XmlSerializableClubBudgetElementsBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableClubBudgetElementsBook() {
        clubs = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableClubBudgetElementsBook(ReadOnlyClubBudgetElementsBook src) {
        this();
        clubs.addAll(src.getClubsList().stream().map(XmlAdaptedClubBudgetElements::new).collect(Collectors.toList()));
    }

    /**
     * Converts this club budget elements book into the model's {@code ClubBudgetElementsBook} object.
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     *{@code XmlAdaptedClubBudgetElements}.
     */
    public ClubBudgetElementsBook toModelType() throws IllegalValueException {
        ClubBudgetElementsBook clubBudgetElementsBook = new ClubBudgetElementsBook();
        for (XmlAdaptedClubBudgetElements c : clubs) {
            ClubBudgetElements clubBudgetElements = c.toModelType();
            if (clubBudgetElementsBook.hasClub(clubBudgetElements)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CLUB_BUDGET_ELEMENTS);
            }
            clubBudgetElementsBook.addClub(clubBudgetElements);
        }
        return clubBudgetElementsBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableClubBudgetElementsBook)) {
            return false;
        }
        return clubs.equals(((XmlSerializableClubBudgetElementsBook) other).clubs);
    }
}
