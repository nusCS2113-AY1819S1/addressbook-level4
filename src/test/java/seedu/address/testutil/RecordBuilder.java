package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.record.Date;
import seedu.address.model.record.Expense;
import seedu.address.model.record.Income;
import seedu.address.model.record.MoneyFlow;
import seedu.address.model.record.Name;
import seedu.address.model.record.Record;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Record objects.
 */
public class RecordBuilder {

    public static final String DEFAULT_NAME = "Income";
    public static final String DEFAULT_DATE = "01-01-2001";
    public static final String DEFAULT_MONEYFLOW = "+11.30";

    private Name name;
    private Date date;
    private MoneyFlow moneyFlow;
    private Set<Tag> tags;

    public RecordBuilder() {
        name = new Name(DEFAULT_NAME);
        date = new Date(DEFAULT_DATE);
        moneyFlow = new Income(DEFAULT_MONEYFLOW);
        tags = new HashSet<>();
    }

    /**
     * Initializes the RecordBuilder with the data of {@code recordToCopy}.
     */
    public RecordBuilder(Record recordToCopy) {
        name = recordToCopy.getName();
        date = recordToCopy.getDate();
        moneyFlow = recordToCopy.getMoneyFlow();
        tags = new HashSet<>(recordToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Record} that we are building.
     */
    public RecordBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Record} that we are building.
     */
    public RecordBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code MoneyFlow} of the {@code Record} that we are building.
     */
    public RecordBuilder withMoneyFlow(String moneyFlow) {
        if (Expense.isValidExpense(moneyFlow)) {
            this.moneyFlow = new Expense(moneyFlow);
        } else if (Income.isValidIncome(moneyFlow)) {
            this.moneyFlow = new Income(moneyFlow);
        }
        return this;
    }

    /**
     * Sets the {@code date} of the {@code Record} that we are building.
     */
    public RecordBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    public Record build() {
        return new Record(name, date, moneyFlow, tags);
    }

}
