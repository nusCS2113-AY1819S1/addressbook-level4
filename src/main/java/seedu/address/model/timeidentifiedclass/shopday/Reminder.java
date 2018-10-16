package seedu.address.model.timeidentifiedclass.shopday;

/**
 * todo
 */
public class Reminder {
    private String time;
    private String reminderMessage;

    public Reminder(String time, String reminderMessage) {
        this.time = time;
        this.reminderMessage = reminderMessage;
    }

    public String getMessage() {
        return reminderMessage;
    }

    public String getTime() {
        return time;
    }

    public void changeTime(String time) {
        this.time = time;
    }

    public void changeMessage(String newMessage) {
        this.reminderMessage = newMessage;
    }

}
