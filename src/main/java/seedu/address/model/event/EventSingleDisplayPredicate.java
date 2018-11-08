package seedu.address.model.event;

import java.util.function.Predicate;

/**
 * Tests that an {@code Event} matches the singular displayed event
 */
public class EventSingleDisplayPredicate implements Predicate<Event> {
    private final Event event;

    public EventSingleDisplayPredicate(Event event) {
        this.event = event;
    }

    @Override
    public boolean test(Event event) {
        return event.equals(this.event);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventSingleDisplayPredicate // instanceof handles nulls
                && event.equals(((EventSingleDisplayPredicate) other).event)); // state check
    }

}
