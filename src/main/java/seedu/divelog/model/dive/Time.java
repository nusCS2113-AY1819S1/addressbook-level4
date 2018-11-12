package seedu.divelog.model.dive;

//@@author Cjunx
/**
 * This is the time class:
 */
public class Time {
    private final String value;

    public Time (String time) {
        this.value = time;
    }

    public String getTimeString() {
        return value;
    }

    /*
    * Given HHMM, returns in long
     */
    public long getTimeLong() {
        String[] arr = value.split("");
        long timeLong = Long.parseLong(arr[0]) * 1000
                + Long.parseLong(arr[1]) * 100
                + Long.parseLong(arr[2]) * 10
                + Long.parseLong(arr[3]);

        return timeLong;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Time)) {
            return false;
        }
        Time time = (Time) obj;
        return value.equals(time.getTimeString());
    }
}
