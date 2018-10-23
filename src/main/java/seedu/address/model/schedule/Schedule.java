package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Schedule {
    private final TreeMap<Date, ArrayList<Activity>> schedule = new TreeMap<>();

    public Schedule() {
    }

    public void setSchedule(List<Activity> activities) {
        requireNonNull(activities);
        schedule.clear();
        for (Activity activity : activities) {
            add(activity);
        }
    }

    public ObservableList<Activity> getActivities() {
        ObservableList<Activity> activities = FXCollections.observableArrayList();
        for (Date date : schedule.keySet()) {
            activities.addAll(schedule.get(date));
        }
        return FXCollections.unmodifiableObservableList(activities);
    }

    public TreeMap<Date, ArrayList<Activity>> getSchedule() {
        return schedule;
    }

    public void add(Activity activity) {
        Date date = activity.getDate();
        if (!contains(date)) {
            schedule.put(date, new ArrayList<>());
        }
        schedule.get(date).add(activity);
    }

    public void delete(Activity activity) {
        schedule.get(activity.getDate()).remove(activity);
        if (schedule.get(activity.getDate()).isEmpty()) {
            schedule.remove(activity.getDate());
        }
    }

    private boolean contains(Date date) {
        if (schedule.containsKey(date)) {
            return true;
        }
        return false;
    }


}
