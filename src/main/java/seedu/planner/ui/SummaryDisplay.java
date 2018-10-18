package seedu.planner.ui;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private ObservableList<SummaryEntry> data = FXCollections.emptyObservableList();

    @FXML
    private TableView<SummaryEntry> table;

    @FXML
    private AnchorPane summaryDisplay;

    @FXML
    private TableColumn dateColumn;

    @FXML
    private TableColumn totalIncomeColumn;

    @FXML
    private TableColumn totalExpenseColumn;

    @FXML
    private TableColumn totalColumn;

    public SummaryDisplay() {
        super(FXML);
        init();
        hide();
        registerAsAnEventHandler(this);
    }

    /**
     * This function links up all the columns of {@code TableView} with the parameters of {@code SummaryEntry}
     */
    private void init() {
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<SummaryEntry, String>("date"));
        totalIncomeColumn.setCellValueFactory(
                new PropertyValueFactory<SummaryEntry, String>("totalIncome"));
        totalExpenseColumn.setCellValueFactory(
                new PropertyValueFactory<SummaryEntry, String>("totalExpense"));
        totalColumn.setCellValueFactory(
                new PropertyValueFactory<SummaryEntry, String>("total"));
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
        table.getItems().clear();
        data = FXCollections.observableList(event.data.stream().map(
            s -> new SummaryEntry(s)).collect(Collectors.toList()));
        table.setItems(data);
        show();
    }

    /**
     * This represents an entry for the summary table
     */
    public class SummaryEntry {

        private final SimpleStringProperty date;
        private final SimpleStringProperty totalIncome;
        private final SimpleStringProperty totalExpense;
        private final SimpleStringProperty total;

        public SummaryEntry(Date date, MoneyFlow totalIncome, MoneyFlow totalExpense, MoneyFlow total) {
            this.date = new SimpleStringProperty(date.toString());
            this.totalIncome = new SimpleStringProperty((totalIncome.toString()));
            this.totalExpense = new SimpleStringProperty((totalExpense.toString()));
            this.total = new SimpleStringProperty((total.toString()));
        }

        public SummaryEntry(Summary summary) {
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

    }
}
