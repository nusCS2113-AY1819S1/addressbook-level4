package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.testutil.EventBuilder;

public class EventContainsAttendeeAndDatePredicateTest {

    private Set<String> attendeesSetOne;
    private Set<String> attendeesSetTwo;
    private Event eventOne;
    private Event eventTwo;

    @Before
    public void setup() {
        attendeesSetOne = new HashSet<>();
        attendeesSetTwo = new HashSet<>();

        attendeesSetOne.add(VALID_NAME_AMY);
        attendeesSetTwo.add(VALID_NAME_BOB);

        eventOne = new EventBuilder().withAttendee(attendeesSetOne).build();
        eventTwo = new EventBuilder().build();
    }

    @Test
    public void equals() {
        String personNameAlice = "ALICE";
        String personNameBob = "BOB";
        String eventDateHalloween = "2018-10-31";
        String eventDateChristmas = "2018-12-25";

        EventContainsAttendeeAndDatePredicate firstPredicate =
                new EventContainsAttendeeAndDatePredicate(personNameAlice, eventDateHalloween, TimeType.DAY);
        EventContainsAttendeeAndDatePredicate secondPredicate =
                new EventContainsAttendeeAndDatePredicate(personNameBob, eventDateChristmas, TimeType.DAY);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // copy of object -> returns true
        EventContainsAttendeeAndDatePredicate firstPredicateCopy =
                new EventContainsAttendeeAndDatePredicate(personNameAlice, eventDateHalloween, TimeType.DAY);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        EventContainsAttendeeAndDatePredicate firstPredicateMonth =
                new EventContainsAttendeeAndDatePredicate(personNameAlice, eventDateHalloween, TimeType.MONTH);
        assertFalse(firstPredicate.equals(firstPredicateMonth));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person and eventdate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

    }

    @Test
    public void test_attendeesContainNameAndEventMatchesDate_returnsTrue() {
        EventContainsAttendeeAndDatePredicate predicate =
                new EventContainsAttendeeAndDatePredicate("Amy Bee", "2018-09-18", TimeType.DAY);
        //event with attendee size one
        assertTrue(predicate.test(eventOne));
    }

    @Test
    public void test_attendeesContainNameAndEventMatchesMonth_returnsTrue() {
        EventContainsAttendeeAndDatePredicate predicate =
                new EventContainsAttendeeAndDatePredicate("Amy Bee", "09", TimeType.MONTH);
        //event with attendee size one
        assertTrue(predicate.test(eventOne));
    }
    @Test
    public void test_attendeesContainNameAndEventMatchesYear_returnsTrue() {
        EventContainsAttendeeAndDatePredicate predicate =
                new EventContainsAttendeeAndDatePredicate("Amy Bee", "2018", TimeType.YEAR);
        //event with attendee size one
        assertTrue(predicate.test(eventOne));
    }

    @Test
    public void test_attendeesContainNameAndEventMatchesMonthAndYear_returnsTrue() {
        EventContainsAttendeeAndDatePredicate predicate =
                new EventContainsAttendeeAndDatePredicate(
                        "Amy Bee", "2018-09", TimeType.MONTH_AND_YEAR);
        //event with attendee size one
        assertTrue(predicate.test(eventOne));
    }

    @Test
    public void test_eventDoesNotMatchesDate_returnsFalse() {
        EventContainsAttendeeAndDatePredicate predicate =
                new EventContainsAttendeeAndDatePredicate("Amy Bee", "2018-10-25", TimeType.DAY);
        //different date
        assertFalse(predicate.test(eventOne));
    }

    @Test
    public void test_eventDoesNotMatchesMonth_returnsFalse() {
        EventContainsAttendeeAndDatePredicate predicate =
                new EventContainsAttendeeAndDatePredicate("Amy Bee", "11", TimeType.MONTH);
        //different date
        assertFalse(predicate.test(eventOne));
    }

    @Test
    public void test_eventDoesNotMatchesYear_returnsFalse() {
        EventContainsAttendeeAndDatePredicate predicate =
                new EventContainsAttendeeAndDatePredicate("Amy Bee", "2010", TimeType.YEAR);
        //different date
        assertFalse(predicate.test(eventOne));
    }

    @Test
    public void test_eventDoesNotMatchesYearAndMonth_returnsFalse() {
        EventContainsAttendeeAndDatePredicate predicate =
                new EventContainsAttendeeAndDatePredicate("Amy Bee", "2017-09", TimeType.YEAR);
        //different date
        assertFalse(predicate.test(eventOne));
    }

    @Test
    public void test_attendeeDoesNotContainName_returnsFalse() {
        EventContainsAttendeeAndDatePredicate predicate =
                new EventContainsAttendeeAndDatePredicate("Bob Choo", "2018-09-18", TimeType.DAY);
        //name not in attendee
        assertFalse(predicate.test(eventOne));
        //empty attendee
        assertFalse(predicate.test(eventTwo));
    }

    @Test
    public void test_bothPersonNameAndDateDoesNotMatch_returnsFalse() {
        EventContainsAttendeeAndDatePredicate predicate =
                new EventContainsAttendeeAndDatePredicate("Bob Choo", "2018-10-25", TimeType.DAY);
        //event with attendee size one
        assertFalse(predicate.test(eventOne));
        //event with attendee size zero
        assertFalse(predicate.test(eventTwo));
    }


}
