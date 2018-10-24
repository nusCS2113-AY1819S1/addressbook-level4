package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.ExpenseCategory;
import seedu.address.model.expense.ExpenseDate;
import seedu.address.model.expense.ExpenseValue;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Expense.
 */
public class XmlAdaptedExpense {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Expense's %s field is missing!";

    @XmlElement(required = true)
    private String category;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String value;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedExpense.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedExpense() {}

    /**
     * Constructs an {@code XmlAdaptedExpense} with the given expense details.
     */
    public XmlAdaptedExpense(String category, String date, String value, List<XmlAdaptedTag> tagged) {
        this.category = category;
        this.date = date;
        this.value = value;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Expense into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedExpense
     */
    public XmlAdaptedExpense(Expense source) {
        category = source.getExpenseCategory().expenseCategory;
        date = source.getExpenseDate().expenseDate;
        value = source.getExpenseValue().expenseValue;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted Expense object into the model's Expense object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Expense
     */
    public Expense toModelType() throws IllegalValueException {
        final List<Tag> expenseTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            expenseTags.add(tag.toModelType());
        }

        if (category == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExpenseCategory.class.getSimpleName()));
        }
        if (!ExpenseCategory.isValidExpenseCategory(category)) {
            throw new IllegalValueException(ExpenseCategory.MESSAGE_EXPENSE_CATEGORY_CONSTRAINTS);
        }
        final ExpenseCategory modelCategory = new ExpenseCategory(category);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExpenseDate.class.getSimpleName()));
        }
        if (!ExpenseDate.isValidDate(date)) {
            throw new IllegalValueException(ExpenseDate.MESSAGE_EXPENSE_DATE_CONSTRAINTS);
        }
        final ExpenseDate modelDate = new ExpenseDate(date);

        if (value == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExpenseValue.class.getSimpleName()));
        }
        if (!ExpenseValue.isValidExpenseValue(value)) {
            throw new IllegalValueException(ExpenseValue.MESSAGE_EXPENSE_VALUE_CONSTRAINTS);
        }
        final ExpenseValue modelValue = new ExpenseValue(value);

        final Set<Tag> modelTags = new HashSet<>(expenseTags);
        return new Expense(modelCategory, modelDate, modelValue, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedExpense)) {
            return false;
        }

        XmlAdaptedExpense otherExpense = (XmlAdaptedExpense) other;
        return Objects.equals(category, otherExpense.category)
                && Objects.equals(date, otherExpense.date)
                && Objects.equals(value, otherExpense.value)
                && tagged.equals(otherExpense.tagged);
    }
}
