package seedu.address.model.expenditureinfo;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Expenditure {

    // Data fields
    private final Description description;
    private final Date date;
    private final Money money;
    private final Category category;


    /**
     * Every field must be present and not null.
     */
    public Expenditure(Description description, Date date, Money money, Category category) {
        requireAllNonNull(description, date, money, category);
        this.description = description;
        this.date = date;
        this.money = money;
        this.category = category;
    }

    public Description getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public Money getMoney() {
        return money;
    }

    public Category getCategory() {
        return category;
    }


    /**
     * Returns true if both expenditures have the same identity fields.
     * This defines a weaker notion of equality between two expenditures.
     */
    public boolean isSameExpenditure(Expenditure otherExpenditure) {
        if (otherExpenditure == this) {
            return true;
        }

        return otherExpenditure != null
                && otherExpenditure.getDescription().equals(getDescription())
                && otherExpenditure.getDate().equals(getDate())
                && otherExpenditure.getMoney().equals(getMoney())
                && otherExpenditure.getCategory().equals(getCategory());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, money, category);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Description: ")
                .append(getDescription())
                .append(" Date: ")
                .append(getDate())
                .append(" Money: ")
                .append(getMoney())
                .append(" Category ")
                .append(getCategory());
        return builder.toString();
    }

}
