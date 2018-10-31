package seedu.address.ui;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;

/**
 * The UI component that is responsible for displaying graph.
 */
public class GradeGraphDisplay {
    private static final double DISPLAY_WIDTH = 600;
    private static final double DISPLAY_HEIGHT = 600;
    private static final String X_AXIS_LABEL = "Marks";
    private static final String Y_AXIS_LABEL = "No. of Students";
    private static final String CHART_TITLE = "No. of Students";

    private Scene scene;
    private LineChart<Number, Number> lineChart;

    /**
     * This method sets up stage to display line chart.
     */
    public void showStage () {
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method sets up line chart configuration.
     */
    public void configureLineChart () {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(X_AXIS_LABEL);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(Y_AXIS_LABEL);

        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(CHART_TITLE);
    }

    public LineChart<Number, Number> getLineChart() {
        return this.lineChart;
    }

    /**
     * This method sets up scene configuration for line chart.
     */
    public void initGraph() {
        scene = new Scene(lineChart, DISPLAY_WIDTH, DISPLAY_HEIGHT);
    }

    /**
     * This method gets up scene configuration for line chart.
     */
    public Scene getScene() {
        return this.scene;
    }
}
