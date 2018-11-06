package seedu.planner.ui;

import javafx.beans.property.SimpleStringProperty;
import seedu.planner.model.summary.Summary;

//@@author tenvinc
/**
 * This represents a UI friendly summary entry and methods to convert a summary into a summary entry.
 */
public class SummaryEntry {

    private final SimpleStringProperty identifier;
    private final SimpleStringProperty totalIncome;
    private final SimpleStringProperty totalExpense;
    private final SimpleStringProperty total;

    public SummaryEntry(String timeStamp, String totalIncome, String totalExpense, String total) {
        this.identifier = new SimpleStringProperty(timeStamp);
        this.totalIncome = new SimpleStringProperty(totalIncome);
        this.totalExpense = new SimpleStringProperty(totalExpense);
        this.total = new SimpleStringProperty(total);
    }

    /**
     * Converts each {@code Summary} to a UI friendly counterpart for display
     */
    public static SummaryEntry convertToUiFriendly(Summary summary) {
        return new SummaryEntry(summary.getIdentifier().toString(), summary.getTotalIncome().toString(),
                summary.getTotalExpense().toString(), summary.getTotal().toString());
    }

    public String getIdentifier() {
        return identifier.get();
    }

    public String getTotalIncome() {
        return totalIncome.get();
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome.set(totalIncome);
    }

    public String getTotalExpense() {
        return totalExpense.get();
    }

    public void setTotalExpense(String totalExpense) {
        this.totalExpense.set(totalExpense);
    }

    public String getTotal() {
        return total.get();
    }

    public void setTotal(String total) {
        this.total.set(total);
    }

    public void setIdentifier(String identifier) {
        this.identifier.set(identifier);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SummaryEntry // instanceof handles nulls
                && identifier.toString().equals(((SummaryEntry) other).identifier.toString())
                && totalIncome.toString().equals(((SummaryEntry) other).totalIncome.toString())
                && totalExpense.toString().equals(((SummaryEntry) other).totalExpense.toString())
                && total.toString().equals(((SummaryEntry) other).total.toString()));
    }
}
