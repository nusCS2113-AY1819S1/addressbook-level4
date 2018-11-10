package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Person> PREDICATE_MATCHING_NO_PERSONS = unused -> false;
    private static final Predicate<Event> PREDICATE_MATCHING_NO_EVENTS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Person> toDisplay) {
        Optional<Predicate<Person>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredPersonList(predicate.orElse(PREDICATE_MATCHING_NO_PERSONS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Person... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Person} equals to {@code other}.
     */
    private static Predicate<Person> getPredicateMatching(Person other) {
        return person -> person.equals(other);
    }

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setEventFilteredList(Model model, List<Event> toDisplay) {
        Optional<Predicate<Event>> predicate =
                toDisplay.stream().map(ModelHelper::getEventPredicateMatching).reduce(Predicate::or);
        model.updateFilteredEventList(predicate.orElse(PREDICATE_MATCHING_NO_EVENTS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setEventFilteredList(Model model, Event... toDisplay) {
        setEventFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Event} equals to {@code other}.
     */
    private static Predicate<Event> getEventPredicateMatching(Event other) {
        return event -> event.equals(other);
    }
}
