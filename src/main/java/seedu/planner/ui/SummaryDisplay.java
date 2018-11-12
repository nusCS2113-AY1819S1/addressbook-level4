package seedu.planner.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.planner.model.summary.SummaryList;
//@@author tenvinc
/**
 * This UI component is responsible for displaying the summary requested by the user
 */
public class SummaryDisplay extends UiPart<Region> {

    private static final String FXML = "SummaryDisplay.fxml";

    private ObservableList<SummaryEntry> data = FXCollections.emptyObservableList();

    @FXML
    private TableView<SummaryEntry> summaryTable;

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

    public SummaryDisplay(SummaryList toDisplay, String totalLabel) {
        super(FXML);
        init(toDisplay);
        summaryTable.getItems().add(new SummaryEntry(totalLabel, toDisplay.getTotalIncome().toString(),
                toDisplay.getTotalExpense().toString(),
                toDisplay.getTotal().toString()));
    }

    /**
     * This function links up all the columns of {@code TableView} with the parameters of {@code SummaryEntry}
     */
    private void init(SummaryList toDisplay) {
        dateColumn.setText(toDisplay.getIdentifierName().toUpperCase());
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<SummaryEntry, String>("identifier"));
        totalIncomeColumn.setCellValueFactory(
                new PropertyValueFactory<SummaryEntry, String>("totalIncome"));
        totalExpenseColumn.setCellValueFactory(
                new PropertyValueFactory<SummaryEntry, String>("totalExpense"));
        totalColumn.setCellValueFactory(
                new PropertyValueFactory<SummaryEntry, String>("total"));
        summaryTable.setItems(toDisplay.getSummaryList());
    }
}
