package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.record.Date;
import seedu.address.model.record.Expense;
import seedu.address.model.record.Income;
import seedu.address.model.record.Name;
import seedu.address.model.record.Record;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Record.
 */
public class XmlAdaptedRecord {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Record's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String income;
    @XmlElement(required = true)
    private String expense;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedRecord.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRecord() {}

    /**
     * Constructs an {@code XmlAdaptedRecord} with the given record details.
     */
    public XmlAdaptedRecord(String name, String date, String income, String expense, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.date = date;
        this.income = income;
        this.expense = expense;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Record into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedRecord
     */
    public XmlAdaptedRecord(Record source) {
        name = source.getName().fullName;
        date = source.getDate().value;
        income = source.getIncome().value;
        expense = source.getExpense().value;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted record object into the model's Record object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted record
     */
    public Record toModelType() throws IllegalValueException {
        final List<Tag> recordTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            recordTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (income == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Income.class.getSimpleName()));
        }
        if (!Income.isValidIncome(income)) {
            throw new IllegalValueException(Income.MESSAGE_INCOME_CONSTRAINTS);
        }
        final Income modelIncome = new Income(income);

        if (expense == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Expense.class.getSimpleName()));
        }
        if (!Expense.isValidExpense(expense)) {
            throw new IllegalValueException(Expense.MESSAGE_EXPENSE_CONSTRAINTS);
        }
        final Expense modelExpense = new Expense(expense);

        final Set<Tag> modelTags = new HashSet<>(recordTags);
        return new Record(modelName, modelDate, modelIncome, modelExpense, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedRecord)) {
            return false;
        }

        XmlAdaptedRecord otherRecord = (XmlAdaptedRecord) other;
        return Objects.equals(name, otherRecord.name)
                && Objects.equals(date, otherRecord.date)
                && Objects.equals(income, otherRecord.income)
                && Objects.equals(expense, otherRecord.expense)
                && tagged.equals(otherRecord.tagged);
    }
}
