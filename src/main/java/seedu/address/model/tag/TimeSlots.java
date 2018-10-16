package seedu.address.model.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class TimeSlots {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String TAG_VALIDATION_REGEX = "\\p{Alnum}+";

    public static final String[] SET_VALUES = new String[]{"8am    ", "9am    ", "10am   ", "11am    ", "12am   ",
            "1pm    ", "2pm     ", "3pm     ", "4pm    ", "5pm     ", "6pm    ", "7pm"};
    private static final String[] mon = new String[]{"Mon8am", "Mon9am", "Mon10am", "Mon11am", "Mon12am"
            , "Mon1pm", "Mon2pm", "Mon3pm", "Mon4pm", "Mon5pm", "Mon6pm", "Mon7pm"};
    /*public static final String[] tue = new String[] { "Tue8am", "Tue9am", "Tue10am", "Tue11am", "Tue12am"
            ,"Tue1pm","Tue2pm","Tue3pm","Tue4pm","Tue5pm","Tue6pm","Tue7pm" };

    public static final String[] wed = new String[] { "Wed8am", "Wed9am", "Wed10am", "Wed11am", "Wed12am"
            ,"Wed1pm","Wed2pm","Wed3pm","Wed4pm","Wed5pm","Wed6pm","Wed7pm" };
    public static final String[] thu = new String[] { "Thu8am", "Thu9am", "Thu10am", "Thu11am", "Thu12am"
            ,"Thu1pm","Thu2pm","Thu3pm","Thu4pm","Thu5pm","Thu6pm","Thu7pm" };
    public static final String[] fri = new String[] { "Fri8am", "Fri9am", "Fri10am", "Fri11am", "Fri12am"
            ,"Fri1pm","Fri2pm","Fri3pm","Fri4pm","Fri5pm","Fri6pm","Fri7pm" };*/
    private static final String[] tue = new String[]{"CS2040c", "CS2040c", "ST2332", "ST2332", "Tue12am",
            "Tue1pm", "CS2107", "CS2107", "Tue4pm", "Tue5pm", "Tue6pm", "Tue7pm"};
    private static final String[] wed = new String[]{"CS2101", "CS2101", "Wed10am", "Wed11am", "GES1041",
            "GES1041", "Wed2pm", "Wed3pm", "CS2113", "CS2113", "Wed6pm", "Wed7pm"};
    private static final String[] thu = new String[]{"Thu8am", "Thu9am", "MA1521", "MA1521", "Thu12am",
            "Thu1pm", "CS2040c", "CS2040c", "Thu4pm", "Thu5pm", "Thu6pm", "Thu7pm"};
    private static final String[] fri = new String[]{"Fri8am", "Fri9am", "CS2107", "Fri11am", "Fri12am",
            "Fri1pm", "CS2101", "Fri3pm", "Fri4pm", "Fri5pm", "Fri6pm", "Fri7pm"};


    public String timeslot;


    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public TimeSlots(String timeslot) {
        requireNonNull(timeslot);
        this.timeslot = timeslot;
    }
    public static Map<String, List<TimeSlots>> sampleTimeSlots() {
        Map<String, List<TimeSlots>> sampletimeslot= new HashMap<>();
        sampletimeslot.put("mon", getMon());
        sampletimeslot.put("tue", getTue());
        sampletimeslot.put("wed", getWed());
        sampletimeslot.put("thu", getThu());
        sampletimeslot.put("fri", getMon());

        return sampletimeslot;
    }

    public static String[] getHeader() {
        return SET_VALUES;
    }

    public static List<TimeSlots> getMon() {
        List<TimeSlots> monTimeSlots = new ArrayList<>();
        for(String it: mon ){
            monTimeSlots.add(new TimeSlots(it));
        }
        return monTimeSlots;
    }
    public static List<TimeSlots> getTue() {
        List<TimeSlots> tueTimeSlots = new ArrayList<>();
        for(String it: tue ){
            tueTimeSlots.add(new TimeSlots(it));
        }
        return tueTimeSlots;
    }

    public static List<TimeSlots> getWed() {
        List<TimeSlots> wedTimeSlots = new ArrayList<>();
        for(String it: wed ){
            wedTimeSlots.add(new TimeSlots(it));
        }
        return wedTimeSlots;
    }

    public static List<TimeSlots> getThu() {
        List<TimeSlots> thuTimeSlots = new ArrayList<>();
        for(String it: thu ){
            thuTimeSlots.add(new TimeSlots(it));
        }
        return thuTimeSlots;
    }

    public static List<TimeSlots> getFri() {
        List<TimeSlots> friTimeSlots = new ArrayList<>();
        for(String it: fri ){
            friTimeSlots.add(new TimeSlots(it));
        }
        return friTimeSlots;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */

    @Override
    public int hashCode() {
        return timeslot.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return timeslot;
    }

}
