package seedu.address.ui;

import java.util.TreeMap;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a expense trend page
 */
public class ExpenseTrendWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(ExpenseTrendWindow.class);
    private static final String FXML = "ExpenseTrendWindow.fxml";

    @FXML
    private WebView browser;

    /**
     * Creates a new ExpenseTrendWindow.
     *
     * @param root Stage to use as the root of the ExpenseTrendWindow.
     */
    public ExpenseTrendWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new ExpenseTrendWindow.
     */
    public ExpenseTrendWindow() {
        this(new Stage());
    }


    public void setExpenseTrendData(TreeMap<String, Double> expenseTrendData) {
        displayExpenseTrendData(updateBarChart(expenseTrendData));
    }

    /**
     * updates the bar chart with the expense trend data
     * @param expenseTrendData the total value of monthly expense for past 6 months
     * @return
     */
    public BarChart updateBarChart(TreeMap<String, Double> expenseTrendData) {
        XYChart.Series<String, Double> series = new XYChart.Series();

        for (String key : expenseTrendData.keySet()) {
            series.getData().add(new XYChart.Data<>(key, expenseTrendData.get(key)));
        }

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        yAxis.setLabel("Monthly Expense ($)");
        series.setName("Expense");
        BarChart barChart = new BarChart(xAxis, yAxis);
        barChart.getData().add(series);
        return barChart;
    }

    /**
     * displays the expense trend data in a new window
     * @param barChart the bar chart of the expense trend
     */
    public void displayExpenseTrendData (BarChart barChart) {
        AnchorPane anchorPane = new AnchorPane(barChart);
        Scene scene = new Scene(anchorPane);
        getRoot().setScene(scene);
        getRoot().setMaxHeight(437);
        getRoot().setMinHeight(437);
        getRoot().setMinWidth(514);
        getRoot().setMaxWidth(514);
    }

    /**
     * Shows the expense trend window.
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
     * Returns true if the expense trend window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the expense trend window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
