package seedu.address.model.schedule;

import java.util.Date;

public class Activity {
    private final Date date;
    private final String activity;

    public Activity(Date date, String activity){
            this.date = date;
            this.activity = activity;
    }

    public Date getDate() {
        return date;
    }

    public String getActivity() {
        return activity;
    }
}
