package seedu.address.commons.core;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;

import seedu.address.model.statistic.Statistic;


/**
 * Manages the statistics' version the app.
 */
public class StatisticCenter implements Serializable {

    private static Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
    private static volatile StatisticCenter instance;
    private static final int month = localCalendar.get(Calendar.MONTH) + 1;
    private static final int year = localCalendar.get(Calendar.YEAR);
    private volatile Statistic statistic;

    private StatisticCenter () {
        //Prevent form the reflection api.
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        statistic = new Statistic(month, year);
    }

    public static StatisticCenter getInstance() {
        if (instance == null) { //if there is no instance available... create new one
            synchronized (StatisticCenter.class) {
                if (instance == null) instance = new StatisticCenter();
            }
        }
        return instance;
    }

    protected StatisticCenter readResolve() {
        return getInstance();
    }
    public Statistic getStatistic() {
        return statistic;
    }
}
