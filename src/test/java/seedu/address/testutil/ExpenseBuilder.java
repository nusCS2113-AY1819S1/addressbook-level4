package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.expense.Expense;
import seedu.address.model.expense.ExpenseCategory;
import seedu.address.model.expense.ExpenseDate;
import seedu.address.model.expense.ExpenseValue;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Expense objects.
 */
public class ExpenseBuilder {

    public static final String DEFAULT_CATEGORY = "food";
    public static final String DEFAULT_DATE = "11/11/2011";
    public static final String DEFAULT_VALUE = "11.11";

    private ExpenseCategory expenseCategory;
    private ExpenseDate expenseDate;
    private ExpenseValue expenseValue;
    private Set<Tag> tags;

    public ExpenseBuilder() {

        expenseCategory = new ExpenseCategory(DEFAULT_CATEGORY);
        expenseDate = new ExpenseDate(DEFAULT_DATE);
        expenseValue = new ExpenseValue(DEFAULT_VALUE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ExpenseBuilder with the data of {@code expenseToCopy}.
     */
    public ExpenseBuilder(Expense expenseToCopy) {

        expenseCategory = expenseToCopy.getExpenseCategory();
        expenseDate = expenseToCopy.getExpenseDate();
        expenseValue = expenseToCopy.getExpenseValue();
        tags = new HashSet<>(expenseToCopy.getTags());
    }

    /**
     * Sets the {@code ExpenseCategory} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withExpenseCategory(String expenseCategory) {
        this.expenseCategory = new ExpenseCategory(expenseCategory);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Expense} that we are building.
     */
    public ExpenseBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code ExpenseDate} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withExpenseDate(String expenseDate) {
        this.expenseDate = new ExpenseDate(expenseDate);
        return this;
    }

    /**
     * Sets the {@code ExpenseValue} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withExpenseValue(String expenseValue) {
        this.expenseValue = new ExpenseValue(expenseValue);
        return this;
    }

    public Expense build() {
        return new Expense(expenseCategory, expenseDate, expenseValue, tags);
    }

}
