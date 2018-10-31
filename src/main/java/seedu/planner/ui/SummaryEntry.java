package seedu.planner.ui;

import javafx.beans.property.SimpleStringProperty;

//@@author tenvinc
/**
 * This represents a UI friendly summary entry.
 */
public class SummaryEntry {

    private final SimpleStringProperty timeStamp;
    private final SimpleStringProperty totalIncome;
    private final SimpleStringProperty totalExpense;
    private final SimpleStringProperty total;

    public SummaryEntry(String timeStamp, String totalIncome, String totalExpense, String total) {
        this.timeStamp = new SimpleStringProperty(timeStamp);
        this.totalIncome = new SimpleStringProperty(totalIncome);
        this.totalExpense = new SimpleStringProperty(totalExpense);
        this.total = new SimpleStringProperty(total);
    }

    public String getTimeStamp() {
        return timeStamp.get();
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp.set(timeStamp);
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SummaryEntry // instanceof handles nulls
                && timeStamp.toString().equals(((SummaryEntry) other).timeStamp.toString())
                && totalIncome.toString().equals(((SummaryEntry) other).totalIncome.toString())
                && totalExpense.toString().equals(((SummaryEntry) other).totalExpense.toString())
                && total.toString().equals(((SummaryEntry) other).total.toString()));
    }
}
