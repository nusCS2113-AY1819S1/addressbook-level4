package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.testutil.EventBuilder;

public class EventContainsAttendeeAndDatePredicateTest {


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Event eventOne;
    private Event eventTwo;

    @Before
    public void setup() {
        eventOne = new EventBuilder().withDate("2018-09-18").withAttendee("alice@example.com").build();
        eventTwo = new EventBuilder().build();
    }

    @Test
    public void test_attendeesContainNameAndEventMatchesDate_returnsTrue() {
        EventContainsAttendeeAndDatePredicate predicate = new EventContainsAttendeeAndDatePredicate(
                "alice@example.com", "2018-09-18", TimeType.DAY);
        //event with attendee size one
        assertTrue(predicate.test(eventOne));
    }

    @Test
    public void test_attendeesContainNameAndEventMatchesMonth_returnsTrue() {
        EventContainsAttendeeAndDatePredicate predicate = new EventContainsAttendeeAndDatePredicate(
                "alice@example.com", "09", TimeType.MONTH);
        //event with attendee size one
        assertTrue(predicate.test(eventOne));
    }
    @Test
    public void test_attendeesContainNameAndEventMatchesYear_returnsTrue() {
        EventContainsAttendeeAndDatePredicate predicate = new EventContainsAttendeeAndDatePredicate(
                "alice@example.com", "2018", TimeType.YEAR);
        //event with attendee size one
        assertTrue(predicate.test(eventOne));
    }

    @Test
    public void test_attendeesContainNameAndEventMatchesMonthAndYear_returnsTrue() {
        EventContainsAttendeeAndDatePredicate predicate = new EventContainsAttendeeAndDatePredicate(
                "alice@example.com", "2018-09", TimeType.MONTH_AND_YEAR);
        //event with attendee size one
        assertTrue(predicate.test(eventOne));
    }

    @Test
    public void test_eventDoesNotMatchesDate_returnsFalse() {
        EventContainsAttendeeAndDatePredicate predicate = new EventContainsAttendeeAndDatePredicate(
                "alice@example.com", "2018-10-25", TimeType.DAY);
        //different date
        assertFalse(predicate.test(eventOne));
    }

    @Test
    public void test_eventDoesNotMatchesMonth_returnsFalse() {
        EventContainsAttendeeAndDatePredicate predicate = new EventContainsAttendeeAndDatePredicate(
                "alice@example.com", "11", TimeType.MONTH);
        //different date
        assertFalse(predicate.test(eventOne));
    }

    @Test
    public void test_eventDoesNotMatchesYear_returnsFalse() {
        EventContainsAttendeeAndDatePredicate predicate = new EventContainsAttendeeAndDatePredicate(
                "alice@example.com", "2010", TimeType.YEAR);
        //different date
        assertFalse(predicate.test(eventOne));
    }

    @Test
    public void test_eventDoesNotMatchesYearAndMonth_returnsFalse() {
        EventContainsAttendeeAndDatePredicate predicate = new EventContainsAttendeeAndDatePredicate(
                "alice@example.com", "2017-09", TimeType.YEAR);
        //different date
        assertFalse(predicate.test(eventOne));
    }

    @Test
    public void test_attendeeDoesNotContainEmail_returnsFalse() {
        EventContainsAttendeeAndDatePredicate predicate = new EventContainsAttendeeAndDatePredicate(
                "bob@example.com", "2018-09-18", TimeType.DAY);
        //email not in attendee
        assertFalse(predicate.test(eventOne));
        //empty attendee
        assertFalse(predicate.test(eventTwo));
    }

    @Test
    public void equals() {
        String personEmailAlice = "alice@example.com";
        String personEmailBob = "bob@example.com";
        String eventDateHalloween = "2018-10-31";
        String eventDateChristmas = "2018-12-25";

        EventContainsAttendeeAndDatePredicate firstPredicate =
                new EventContainsAttendeeAndDatePredicate(personEmailAlice, eventDateHalloween, TimeType.DAY);


        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // copy of object -> returns true
        EventContainsAttendeeAndDatePredicate firstPredicateCopy =
                new EventContainsAttendeeAndDatePredicate(personEmailAlice, eventDateHalloween, TimeType.DAY);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different types -> returns false
        EventContainsAttendeeAndDatePredicate firstPredicateMonth =
                new EventContainsAttendeeAndDatePredicate(personEmailAlice, eventDateHalloween, TimeType.MONTH);
        assertFalse(firstPredicate.equals(firstPredicateMonth));
        EventContainsAttendeeAndDatePredicate firstPredicateYear =
                new EventContainsAttendeeAndDatePredicate(personEmailAlice, eventDateHalloween, TimeType.YEAR);
        assertFalse(firstPredicate.equals(firstPredicateYear));
        EventContainsAttendeeAndDatePredicate firstPredicateMonthAndYear = new EventContainsAttendeeAndDatePredicate(
                personEmailAlice, eventDateHalloween, TimeType.MONTH_AND_YEAR);
        assertFalse(firstPredicate.equals(firstPredicateMonthAndYear));


        // different person -> returns false
        EventContainsAttendeeAndDatePredicate secondPredicate =
                new EventContainsAttendeeAndDatePredicate(personEmailBob, eventDateHalloween, TimeType.DAY);
        assertFalse(firstPredicate.equals(secondPredicate));

        // different date -> returns false
        EventContainsAttendeeAndDatePredicate thirdPredicate =
                new EventContainsAttendeeAndDatePredicate(personEmailAlice, eventDateChristmas, TimeType.DAY);
        assertFalse(firstPredicate.equals(thirdPredicate));

    }



}
