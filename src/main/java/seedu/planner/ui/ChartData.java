package seedu.planner.ui;
//@@author tenvinc
/**
 * This class represents any type of chart data that can be split into 2 parameters, key and the value which is a double
 */
public class ChartData {

    public final String key;
    public final Double value;

    public ChartData(String key, Double value) {
        this.key = key;
        this.value = value;
    }
}
