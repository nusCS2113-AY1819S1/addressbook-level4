package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ExpenseBook;
import seedu.address.model.ReadOnlyExpenseBook;
import seedu.address.model.expense.Expense;

/**
 * An Immutable ExpenseBook that is serializable to XML format
 */
@XmlRootElement(name = "expensebook")
public class XmlSerializableExpenseBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Expenses list contains duplicate expense(s).";

    @XmlElement
    private List<XmlAdaptedExpense> expenses;

    /**
     * Creates an empty XmlSerializableExpenseBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableExpenseBook() {
        expenses = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableExpenseBook(ReadOnlyExpenseBook src) {
        this();
        expenses.addAll(src.getExpenseList().stream().map(XmlAdaptedExpense::new).collect(Collectors.toList()));
    }

    /**
     * Converts this expensebook into the model's {@code ExpenseBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated
     */
    public ExpenseBook toModelType() throws IllegalValueException {
        ExpenseBook expenseBook = new ExpenseBook();
        for (XmlAdaptedExpense p : expenses) {
            Expense expense = p.toModelType();
            expenseBook.addExpense(expense);
        }
        return expenseBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableExpenseBook)) {
            return false;
        }
        return expenses.equals(((XmlSerializableExpenseBook) other).expenses);
    }
}
