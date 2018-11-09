//@@author cqinkai
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to send a reminder to the user
 * for the upcoming event with {@code eventName}
 */
public class SendEventReminder extends BaseEvent {

    public final String eventName;
    public final String eventDate;
    public final String timeTillEvent;

    public SendEventReminder(String eventName, String eventDate, String timeTillEvent) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.timeTillEvent = timeTillEvent;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
