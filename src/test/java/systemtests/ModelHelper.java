package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.divelog.model.Model;
import seedu.divelog.model.dive.DiveSession;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<DiveSession> PREDICATE_MATCHING_NO_DIVES = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<DiveSession> toDisplay) {
        Optional<Predicate<DiveSession>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredDiveList(predicate.orElse(PREDICATE_MATCHING_NO_DIVES));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, DiveSession... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code DiveSession} equals to {@code other}.
     */
    private static Predicate<DiveSession> getPredicateMatching(DiveSession other) {
        return diveSession -> diveSession.equals(other);
    }
}
