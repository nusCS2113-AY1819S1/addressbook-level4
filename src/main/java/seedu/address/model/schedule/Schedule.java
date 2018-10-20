package seedu.address.model.schedule;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.model.person.Person;

public class Schedule {
    private final Map<Date, ArrayList<Activity>> schedule = new HashMap<>();


    public Schedule () {
    }

    public ArrayList<Activity> getActivities() {
        ArrayList<Activity> activities = new ArrayList<>();
        for (Date date : schedule.keySet()){
            activities.addAll(schedule.get(date));
        }
        return new ArrayList<>(activities);
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
