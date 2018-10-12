package seedu.address.testutil;

import seedu.address.model.expenditureinfo.Category;
import seedu.address.model.expenditureinfo.Date;
import seedu.address.model.expenditureinfo.Expenditure;
import seedu.address.model.expenditureinfo.Money;

/**
 * A utility class to help with building Expenditure objects.
 */
public class ExpenditureBuilder {

    public static final String DEFAULT_CATEGORY = "Chicken rice";
    public static final String DEFAULT_DATE = "01-01-2018";
    public static final String DEFAULT_MONEY = "10";

    private Category category;
    private Date date;
    private Money money;

    public ExpenditureBuilder() {
        category = new Category(DEFAULT_CATEGORY);
        date = new Date(DEFAULT_DATE);
        money = new Money(DEFAULT_MONEY);
    }

    /**
     * Initializes the ExpenditureBuilder with the data of {@code expenditureToCopy}.
     */
    public ExpenditureBuilder(Expenditure expenditureToCopy) {
        category = expenditureToCopy.getCategory();
        date = expenditureToCopy.getDate();
        money = expenditureToCopy.getMoney();
    }

    /**
     * Sets the {@code Category} of the {@code Expenditure} that we are building.
     */
    public ExpenditureBuilder withCategory(String category) {
        this.category = new Category(category);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Expenditure} that we are building.
     */
    public ExpenditureBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Money} of the {@code Expenditure} that we are building.
     */
    public ExpenditureBuilder withMoney(String money) {
        this.money = new Money(money);
        return this;
    }

    public Expenditure build() {
        return new Expenditure(date, money, category);
    }

}
