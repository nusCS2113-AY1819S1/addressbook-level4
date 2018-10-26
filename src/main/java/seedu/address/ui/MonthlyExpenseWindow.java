package seedu.address.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a monthly expense page
 */
public class MonthlyExpenseWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(MonthlyExpenseWindow.class);
    private static final String FXML = "MonthlyExpenseWindow.fxml";

    @FXML
    private WebView browser;

    /**
     * Creates a new MonthlyExpenseWindow.
     *
     * @param root Stage to use as the root of the MonthlyExpenseWindow.
     */
    public MonthlyExpenseWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new MonthlyExpenseWindow.
     */
    public MonthlyExpenseWindow() {
        this(new Stage());
    }

    public void setMonthlyData(HashMap<String, String> monthlyData) {
        displayMonthlyData(updatePieChart(monthlyData));
    }

    /**
     * updates the pie chart with the monthly expense data
     * @param monthlyData the values of each category for the selected month
     * @return
     */
    public PieChart updatePieChart(HashMap<String, String> monthlyData) {
        ArrayList<PieChart.Data> pieChartDataList = new ArrayList<>();
        for (HashMap.Entry<String, String> entry : monthlyData.entrySet()) {
            pieChartDataList.add(new PieChart.Data(entry.getKey() + ": $"
                    + entry.getValue(), Double.parseDouble(entry.getValue())));
        }
        ObservableList<PieChart.Data> observablePieChartDataList = FXCollections.observableList(pieChartDataList);
        PieChart pieChart = new PieChart(observablePieChartDataList);
        pieChart.setTitle("Monthly Expense");
        return pieChart;
    }

    /**
     * displays the monthly data in a new window
     * @param pieChart the pie chart of expense value for each category
     */
    public void displayMonthlyData (PieChart pieChart) {
        AnchorPane anchorPane = new AnchorPane(pieChart);
        Scene scene = new Scene(anchorPane);
        getRoot().setScene(scene);
    }

    /**
     * Shows the monthly expense window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing monthly expense window about the selected month.");
        getRoot().show();
    }

    /**
     * Returns true if the monthly expense window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the monthly expense window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
