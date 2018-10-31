package seedu.address.model.event;

import java.util.function.Predicate;

//@@author jieliangang
/**
 * Tests that a {@code Event}'s {@code EventDate} matches according to input
 * and {@code Attendee} contains the person name given.
 */
public class EventContainsAttendeeAndDatePredicate implements Predicate<Event> {
    public static final String DATE_VALIDATION_REGEX = "^(\\d{4})-(0[1-9]|1[012])$";

    private final String personName;
    private final String inputDate;
    private final TimeType type;

    public EventContainsAttendeeAndDatePredicate(String personName, String inputDate, TimeType type) {
        this.personName = personName;
        this.inputDate = inputDate;
        this.type = type;
    }

    @Override
    public boolean test(Event event) {
        boolean eventHasAttendee = event.hasAttendee(personName);
        boolean eventMatchesDate;

        String eventDate = event.getDate().eventDate;
        String[] eventTokens = eventDate.split("-", 3);

        switch (type) {
        case DAY:
            eventMatchesDate = eventDate.matches(inputDate);
            break;
        case MONTH:
            eventMatchesDate = eventTokens[1].matches(inputDate);
            break;
        case YEAR:
            eventMatchesDate = eventTokens[0].matches(inputDate);
            break;
        case MONTH_AND_YEAR:
            assert inputDate.matches(DATE_VALIDATION_REGEX);
            String[] tokens = inputDate.split("-", 2);
            eventMatchesDate = eventTokens[1].matches(tokens[1]) && eventTokens[0].matches(tokens[0]);
            break;
        default:
            eventMatchesDate = false;
        }
        return eventHasAttendee && eventMatchesDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventContainsAttendeeAndDatePredicate // instanceof handles nulls
                && personName.equals(((EventContainsAttendeeAndDatePredicate) other).personName) // state check
                && inputDate.equals(((EventContainsAttendeeAndDatePredicate) other).inputDate))
                && type.equals(((EventContainsAttendeeAndDatePredicate) other).type);
    }
}
