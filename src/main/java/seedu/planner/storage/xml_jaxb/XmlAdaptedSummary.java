package seedu.planner.storage.xml_jaxb;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.summary.Summary;

/**
 * A JAXB-friendly version of the {@code Summary}
 */
public class XmlAdaptedSummary {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Summary's %s field is missing!";
    public static final String TOTAL_EXPENSE_SIMPLE_NAME = "Total Expense";
    public static final String TOTAL_INCOME_SIMPLE_NAME = "Total Income";
    public static final String TOTAL_SIMPLE_NAME = "Total Money";

    @XmlElement(required = true)
    private String date;

    @XmlElement(required = true)
    private String totalExpense;

    @XmlElement(required = true)
    private String totalIncome;

    @XmlElement(required = true)
    private String total;

    public XmlAdaptedSummary() {};

    /**
     * Constructs an {@XmlAdaptedSummary} with the given summary record details
     */
    public XmlAdaptedSummary(String date, String totalExpense, String totalIncome, String total) {
        this.date = date;
        this.totalExpense = totalExpense;
        this.totalIncome = totalIncome;
        this.total = total;
    }

    /**
     * Constructs an {@XmlAdaptedSummary} with the given summary object
     * @param summary
     */
    public XmlAdaptedSummary(Summary summary) {
        this.date = summary.getDate().value;
        this.totalExpense = summary.getTotalExpense().value;
        this.totalIncome = summary.getTotalIncome().value;
        this.total = summary.getTotal().value;
    }

    /**
     * Converts this JAXB-friendly object into the model's {@Summary} object
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted summary
     */
    public Summary toModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDateFormat(date)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (totalExpense == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TOTAL_EXPENSE_SIMPLE_NAME));
        }
        if (!MoneyFlow.isValidMoneyFlow(totalExpense)) {
            throw new IllegalValueException(MoneyFlow.MESSAGE_MONEY_FLOW_CONSTRAINTS);
        }
        final MoneyFlow modelTotalExpense = new MoneyFlow(totalExpense);

        if (totalIncome == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TOTAL_INCOME_SIMPLE_NAME));
        }
        if (!MoneyFlow.isValidMoneyFlow(totalIncome)) {
            throw new IllegalValueException(MoneyFlow.MESSAGE_MONEY_FLOW_CONSTRAINTS);
        }
        final MoneyFlow modelTotalIncome = new MoneyFlow(totalIncome);

        if (total == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TOTAL_SIMPLE_NAME));
        }
        if (!MoneyFlow.isValidMoneyFlow(date)) {
            throw new IllegalValueException(MoneyFlow.MESSAGE_MONEY_FLOW_CONSTRAINTS);
        }
        final MoneyFlow modelTotal = new MoneyFlow(total);

        return new Summary(modelDate, modelTotalExpense, modelTotalIncome, modelTotal);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedSummary)) {
            return false;
        }

        XmlAdaptedSummary otherSummary = (XmlAdaptedSummary) other;
        return Objects.equals(date, otherSummary.date)
                && Objects.equals(totalExpense, otherSummary.totalExpense)
                && Objects.equals(totalIncome, otherSummary.totalIncome)
                && Objects.equals(total, otherSummary.total);
    }
}
