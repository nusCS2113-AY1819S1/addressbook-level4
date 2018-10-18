package seedu.address.commons.core;

import java.util.Calendar;
import java.util.TimeZone;

import seedu.address.model.statistic.Statistic;


/**
 * Manages the statistics' version the app.
 */
public class StatisticCenter {

    private static Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
    private static StatisticCenter instance;
    private static final int month = localCalendar.get(Calendar.MONTH) + 1;
    private static final int year = localCalendar.get(Calendar.YEAR);
    private Statistic statistic;

    private StatisticCenter () { statistic = new Statistic(month, year); }

    public static StatisticCenter getInstance() {
        if (instance == null) {
            instance = new StatisticCenter();
        }
        return instance;
    }
    public Statistic getStatistic() {
        return statistic;
    }

    public void increaseRevenue(String amount) {
        statistic.increaseRevenue(amount);
    }
}
