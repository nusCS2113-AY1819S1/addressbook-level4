package seedu.planner.ui;

import java.util.logging.Logger;

import com.fasterxml.jackson.databind.deser.impl.PropertyValue;
import com.google.common.eventbus.Subscribe;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.ui.ShowSummaryTableEvent;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.summary.Summary;

/**
 * This UI component is responsible for displaying the summary requested by the user
 */
public class SummaryDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    private static final String FXML = "SummaryDisplay.fxml";

    private static final TableView<SummaryDisplayEntry> table = new TableView();

    @FXML
    private AnchorPane summaryDisplay;

    @FXML
    private TableColumn dateColumn;

    @FXML
    private TableColumn totalIncomeColumn;

    @FXML
    private TableColumn totalExpensesColumn;

    @FXML
    private TableColumn totalColumn;

    public SummaryDisplay() {
        super(FXML);
        init();
        hide();
        registerAsAnEventHandler(this);
    }

    /**
     * This function links up all the columns of {@code TableView} with the parameters of {@code SummaryDisplayEntry}
     */
    private void init() {
        dateColumn.setCellFactory(new PropertyValueFactory<>("date"));
        totalIncomeColumn.setCellFactory(new PropertyValueFactory<>("totalIncome"));
        totalExpensesColumn.setCellFactory(new PropertyValueFactory<>("totalExpenses"));
        totalColumn.setCellFactory(new PropertyValueFactory<>("total"));
    }

    private void show() {
        getRoot().toFront();
        summaryDisplay.setVisible(true);
    }

    private void hide() {
        getRoot().toBack();
        summaryDisplay.setVisible(false);
    }

    @Subscribe
    public void handleShowSummaryTableEvent(ShowSummaryTableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        show();
    }

    private class SummaryDisplayEntry {

        private final SimpleStringProperty date;
        private final SimpleStringProperty totalIncome;
        private final SimpleStringProperty totalExpense;
        private final SimpleStringProperty total;

        public SummaryDisplayEntry(Date date, MoneyFlow totalIncome, MoneyFlow totalExpenses, MoneyFlow total) {
            this.date = new SimpleStringProperty(date.toString());
            this.totalIncome = new SimpleStringProperty((totalIncome.toString()));
            this.totalExpense = new SimpleStringProperty((totalExpenses.toString()));
            this.total = new SimpleStringProperty((total.toString()));
        }

        public SummaryDisplayEntry(Summary summary) {
            this.date = new SimpleStringProperty(summary.getDate().toString());
            this.totalIncome = new SimpleStringProperty((summary.getTotalIncome().toString()));
            this.totalExpense = new SimpleStringProperty((summary.getTotalExpense().toString()));
            this.total = new SimpleStringProperty((summary.getTotal().toString()));
        }

        public String getDate() {
            return date.get();
        }

        public void setDate(String date) {
            this.date.set(date);
        }

        public String getTotalIncome() {
            return totalIncome.get();
        }

        public void setTotalIncome(String totalIncome) {
            this.totalIncome.set(totalIncome);
        }

        public String getTotalExpenses() {
            return totalExpense.get();
        }

        public void setTotalExpenses(String totalExpenses) {
            this.totalExpense.set(totalExpenses);
        }

        public String getTotal() {
            return total.get();
        }

        public void setTotal(String total) {
            this.total.set(total);
        }

    }
}
