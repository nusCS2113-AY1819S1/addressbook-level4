package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.FinalBudgetsBook;
import seedu.address.model.ReadOnlyFinalBudgetBook;
import seedu.address.model.clubbudget.FinalClubBudget;


/**
 * An Immutable FinalBudgetsBook that is serializable to XML format
 */
@XmlRootElement(name = "budgetsbook")
public class XmlSerializableFinalBudgetsBook {

    public static final String MESSAGE_DUPLICATE_FINAL_BUDGET = "Final Budgets list contains duplicate club(s).";

    @XmlElement
    private List<XmlAdaptedFinalClubBudget> budgets;

    /**
     * Creates an empty XmlSerializableFinalBudgetsBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableFinalBudgetsBook() {
        budgets = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableFinalBudgetsBook(ReadOnlyFinalBudgetBook src) {
        this();
        budgets.addAll(src.getClubBudgetsList().stream().map(XmlAdaptedFinalClubBudget::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this final budgets book into the model's {@code FinalBudgetsBook} object.
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedFinalClubBudget}.
     */
    public FinalBudgetsBook toModelType() throws IllegalValueException {
        FinalBudgetsBook finalBudgetsBook = new FinalBudgetsBook();
        for (XmlAdaptedFinalClubBudget b : budgets) {
            FinalClubBudget budget = b.toModelType();
            if (finalBudgetsBook.hasClubBudget(budget)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FINAL_BUDGET);
            }
            finalBudgetsBook.addClubBudget(budget);
        }
        return finalBudgetsBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableFinalBudgetsBook)) {
            return false;
        }
        return budgets.equals(((XmlSerializableFinalBudgetsBook) other).budgets);
    }
}
