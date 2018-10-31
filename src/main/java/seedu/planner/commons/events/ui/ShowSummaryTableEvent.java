package seedu.planner.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.planner.commons.events.BaseEvent;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.ui.SummaryEntry;
//@@author tenvinc
/**
 * An event that requests to show the summary display
 */
public class ShowSummaryTableEvent extends BaseEvent {

    public final ObservableList<SummaryEntry> data;
    public final MoneyFlow total;
    public final MoneyFlow totalIncome;
    public final MoneyFlow totalExpense;
    public final String totalLabel;

    public ShowSummaryTableEvent(ObservableList<SummaryEntry> data, MoneyFlow totalExpense, MoneyFlow totalIncome,
                                 MoneyFlow total, String totalLabel) {
        this.data = data;
        this.totalExpense = totalExpense;
        this.totalIncome = totalIncome;
        this.total = total;
        this.totalLabel = totalLabel;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
