package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Event in the event list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event implements Comparable<Event> {

    public static final String MESSAGE_DESCRIPTION = " Description: ";
    public static final String MESSAGE_LOCATION = " Location: ";
    public static final String MESSAGE_DATE = " Date: ";
    public static final String MESSAGE_START_TIME = " Start time: ";
    public static final String MESSAGE_END_TIME = " End time: ";


    // Data fields
    private final EventName eventName;
    private final Description description;
    private final EventDate date;
    private final StartTime startTime;
    private final EndTime endTime;
    private final Location location;
    private Attendees attendees;

    /**
     * Every field besides Attendees must be present not null
     */

    public Event(EventName eventName, Description description,
                 EventDate date, StartTime startTime, EndTime endTime, Location location) {
        requireAllNonNull(eventName, description, startTime, endTime, location);

        this.eventName = eventName;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        attendees = new Attendees();

    }

    /**
     * Overloaded constructor to construct event with existing attendees
     */

    public Event(EventName eventName, Description description,
                 EventDate date, StartTime startTime, EndTime endTime, Location location, Attendees attendees) {
        requireAllNonNull(eventName, description, startTime, endTime, location);

        this.eventName = eventName;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.attendees = attendees;

    }

    public EventName getEventName() {
        return eventName;
    }

    public Description getDescription() {
        return description;
    }

    public EventDate getDate() {
        return date;
    }

    public StartTime getStartTime() {
        return startTime;
    }

    public EndTime getEndTime() {
        return endTime;
    }

    public Location getLocation() {
        return location;
    }

    public Attendees getAttendees() {
        return attendees;
    }


    /**
     * Returns true if both event are at the same location and clashes.
     * This defines a weaker notion of equality between two events, indicating that they overlap.
     */
    public boolean isSameEvent(Event event) {
        if (event == this) {
            return true;
        }
        return event != null
                && event.getLocation().equals(getLocation())
                && event.hasClash(this);
    }

    /**
     * Returns true if both events have the same identity and data fields.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getEventName().equals(getEventName())
                && otherEvent.getDescription().equals(getDescription())
                && otherEvent.getLocation().equals(getLocation())
                && otherEvent.getStartTime().equals(getStartTime())
                && otherEvent.getEndTime().equals(getEndTime())
                && otherEvent.getDate().equals(getDate())
                && otherEvent.getAttendees().equals(getAttendees());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(eventName, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEventName())
                .append("\n")
                .append(MESSAGE_DESCRIPTION)
                .append(getDescription())
                .append("\n")
                .append(MESSAGE_LOCATION)
                .append(getLocation())
                .append("\n")
                .append(MESSAGE_DATE)
                .append(getDate())
                .append("\n")
                .append(MESSAGE_START_TIME)
                .append(getStartTime())
                .append("\n")
                .append(MESSAGE_END_TIME)
                .append(getEndTime());
        return builder.toString();
    }

    /**
     * Create a new event with the new email added to the current Attendee.
     *
     * @param personEmail The person's email to be added to Attendee.
     * @return An updated event with the person's email added to the original Attendee.
     */
    public Event createEventWithUpdatedAttendee(String personEmail) {
        assert personEmail != null;
        assert !attendees.hasPerson(personEmail);
        Attendees updatedAttendee = attendees.createAttendeesWithAddedEmail(personEmail);
        return new Event(eventName, description, date, startTime, endTime, location, updatedAttendee);
    }


    /**
     * Create a new event with an existing email removed from the current Attendee.
     *
     * @param personEmail The person's email to be removed from Attendee.
     * @return An updated event with the person's email removed from Attendee.
     */
    public Event removePersonFromAttendee(String personEmail) {
        assert personEmail != null;
        assert attendees.hasPerson(personEmail);
        Attendees updatedAttendee = attendees.createAttendeesWithRemovedEmail(personEmail);
        return new Event(eventName, description, date, startTime, endTime, location, updatedAttendee);
    }

    /**
     * Check if personEmail is in attendees of Event.
     *
     * @param personEmail The person's email to be checked from Attendee.
     */
    public boolean hasAttendee(String personEmail) {
        assert personEmail != null;
        return attendees.hasPerson(personEmail);
    }


    /**
     * Check if Attendees of Event is empty.
     */
    public boolean isAttendeeEmpty() {
        return attendees.isSetEmpty();
    }

    /**
     * Returns true if {@code event} time and date clashes
     *
     */
    public boolean hasClash(Event event) {
        if (!event.date.equals(this.date)) {
            return false;
        }

        StartTime startTime1;
        StartTime startTime2;
        EndTime endTime1;
        EndTime endTime2;

        // Let suffix 1 be the time of event with earlier start time, and suffix 2 be the latter.
        if (event.startTime.compareTo(this.startTime) <= 0) {
            startTime1 = event.startTime;
            endTime1 = event.endTime;
            startTime2 = this.startTime;
            endTime2 = this.endTime;
        } else {
            startTime1 = this.startTime;
            endTime1 = this.endTime;
            startTime2 = event.startTime;
            endTime2 = event.endTime;
        }

        // Two sufficient and necessary conditions to prove overlap of intervals
        boolean doesEvent1BeginEarlierBeforeEvent2Ends = startTime1.compareTo(endTime2) < 0;
        boolean doesEvent2BeginBeforeEvent1Ends = startTime2.compareTo(endTime1) < 0;

        return doesEvent1BeginEarlierBeforeEvent2Ends && doesEvent2BeginBeforeEvent1Ends;
    }


    @Override
    public int compareTo(Event other) {
        int compareValue = this.getEventName().fullName.toLowerCase()
                .compareTo(other.getEventName().fullName.toLowerCase());
        if (compareValue == 0) {
            return this.getEventName().fullName.compareTo(other.getEventName().fullName);
        }
        return compareValue;
    }


    /**
     * Compare the date between the events
     * If both events have the same date then compare their start time
     **/
    public int compareDateTo(Event other) {
        int compareValue = this.getDate().eventDate.compareTo(other.getDate().eventDate);
        if (compareValue == 0) {
            return this.getStartTime().compareTo(other.getStartTime());
        }
        return compareValue;
    }

    /**
     * Compare the start time between the events
     * If both events have the same start time then compare their date
     **/
    public int compareStartTimeTo(Event other) {
        int compareValue = this.getStartTime().compareTo(other.getStartTime());
        if (compareValue == 0) {
            return this.getDate().eventDate.compareTo(other.getDate().eventDate);
        }
        return compareValue;
    }

    /**
     * Compare the end time between the events
     * If both events have the same start time then compare their date
     **/
    public int compareEndTimeTo(Event other) {
        int compareValue = this.getEndTime().compareTo(other.getEndTime());
        if (compareValue == 0) {
            return this.getDate().eventDate.compareTo(other.getDate().eventDate);
        }
        return compareValue;
    }
}
