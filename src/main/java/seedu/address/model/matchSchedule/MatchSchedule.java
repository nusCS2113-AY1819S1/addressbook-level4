package seedu.address.model.matchSchedule;


import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.TheDate;
import seedu.address.model.person.Time;

/**
 * Represents a person selected to match schedule in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidMatchSchedulePerson(String)}
 */
public class MatchSchedule {

    public static final String MESSAGE_MATCH_SCHEDULE_CONSTRAINTS = "INDEX should be a person stored in JITHUB";
    public static final String TAG_VALIDATION_REGEX = "\\d";


    private final TheDate date;
    private final Time startTime;
    private final Time endTime;
    private final List<Index> matchSchedulePerson = new ArrayList<>();

    /**
     * Constructs a {@code MatchSchedule}.
     *
     * @param matchSchedulePerson A valid index of person.
     */
    public MatchSchedule(TheDate date, Time startTime, Time endTime, List<Index> matchSchedulePerson) {
        requireAllNonNull(date, startTime, endTime, matchSchedulePerson);
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.matchSchedulePerson.addAll(matchSchedulePerson);
    }

    public TheDate getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public List<Index> getMatchSchedulePerson() {
        return Collections.unmodifiableList(matchSchedulePerson);
    }

    /**
     * Returns true if a given string is a valid index of a person.
     */
    public static boolean isValidMatchSchedulePerson(String test) {
        return test.matches(TAG_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchSchedule // instanceof handles nulls
                && matchSchedulePerson.equals(((MatchSchedule) other).matchSchedulePerson)); // state check
    }

    @Override
    public int hashCode() {
        return matchSchedulePerson.hashCode();
    }

}
