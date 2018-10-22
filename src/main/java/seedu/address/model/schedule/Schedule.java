package seedu.address.model.schedule;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Schedule {
    private final TreeMap<Date, ArrayList<Activity>> schedule = new TreeMap<>();

    public Schedule () {
    }

    public void setSchedule(List<Activity> activities) {
        requireAllNonNull(activities);
        schedule.clear();
        for(Activity activity : activities){
            add(activity);
        }
    }

    public ObservableList<Activity> getActivities() {
        ObservableList<Activity> activities = FXCollections.observableArrayList();
        for (Date date : schedule.keySet()){
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

    //TODO convert index to 1
    public void delete(Activity activity){
        schedule.get(activity.getDate()).remove(activity);
        if (schedule.get(activity.getDate()).isEmpty()){
            schedule.remove(activity.getDate());
        }
    }

    private boolean contains(Date date) {
        if (schedule.containsKey(date)){
            return true;
        }
        return false;
    }


}
