package seedu.address.model.event;

import java.util.function.Predicate;

/**
 * Tests that an {@code Event} matches the singular displayed event
 */
public class EventSingleDisplayPredicate implements Predicate<Event> {
    private final EventName eventName;
    private final EventDate eventDate;
    private final Location location;
    private final StartTime startTime;
    private final EndTime endTime;

    public EventSingleDisplayPredicate(Event event) {
        this.eventName = event.getEventName();
        this.eventDate = event.getDate();
        this.location = event.getLocation();
        this.startTime = event.getStartTime();
        this.endTime = event.getEndTime();
    }

    @Override
    public boolean test(Event event) {
        return event.getEventName().equals(eventName)
                && event.getDate().equals(eventDate)
                && event.getLocation().equals(location)
                && event.getStartTime().equals(startTime)
                && event.getEndTime().equals(endTime);
    }

    @Override
    public boolean equals(Object other) {
        // Split up for readability
        if (other == this) { // return true if same object
            return true;
        }
        if (!(other instanceof EventSingleDisplayPredicate)) { // instanceof handles nulls
            return false;
        }

        return eventName.equals(((EventSingleDisplayPredicate) other).eventName)
                && eventDate.equals(((EventSingleDisplayPredicate) other).eventDate)
                && location.equals(((EventSingleDisplayPredicate) other).location)
                && startTime.equals(((EventSingleDisplayPredicate) other).startTime)
                && endTime.equals(((EventSingleDisplayPredicate) other).endTime);
    }

}
