package seedu.address.model.schedule;

import java.util.Date;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Schedule {
    private final ObservableMap<Date, ArrayList<Activity>> schedule = FXCollections.observableHashMap();

    public Schedule () {

    }

    public void add(Activity activity){
        Date date = activity.getDate();
        if (!contains(date)) {
            schedule.put(date, new ArrayList<>());
        }
        schedule.get(date).add(activity);
    }

    private boolean contains(Date date) {
        if (schedule.containsKey(date)){
            return true;
        }
        return false;
    }


}
