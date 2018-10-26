package seedu.address.commons.events.ui;

import java.util.Optional;

import javafx.util.Duration;
import seedu.address.commons.events.BaseEvent;


/**
 * Indicates that a new result is available.
 */
public class NewNotificationAvailableEvent extends BaseEvent {

    public final String title;
    public final String message;
    public final Duration duration;

    public NewNotificationAvailableEvent(String title, String message, Optional<Duration> duration) {
        this.title = title;
        this.message = message;
        this.duration = duration.orElse(Duration.seconds(5));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
