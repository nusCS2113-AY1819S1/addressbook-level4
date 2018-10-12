package seedu.planner.storage.xml_jaxb;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.planner.model.summary.Summary;

/**
 * A JAXB-friendly version of the {@code Summary}
 */
public class XmlAdaptedSummary {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Summary's %s field is missing!";

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
