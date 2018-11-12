package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_CALVIN;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.testutil.EventBuilder;

//@@author jieliangang
public class AttendeeContainsNamePredicateTest {

    private Set<String> attendeesSetOne;
    private Set<String> attendeesSetTwo;

    @Before
    public void setup() {
        attendeesSetOne = new HashSet<>();
        attendeesSetTwo = new HashSet<>();

        attendeesSetOne.add(VALID_EMAIL_AMY);
        attendeesSetTwo.add(VALID_EMAIL_BOB);
        attendeesSetTwo.add(VALID_EMAIL_CALVIN);
    }


    @Test
    public void test_attendeesContainEmail_returnsTrue() {
        //event with attendee size one
        AttendeeContainsEmailPredicate predicate = new AttendeeContainsEmailPredicate(VALID_EMAIL_AMY);
        assertTrue(predicate.test(new EventBuilder().withAttendee(attendeesSetOne).build()));

        //event with attendee size more than one
        predicate = new AttendeeContainsEmailPredicate(VALID_EMAIL_BOB);
        assertTrue(predicate.test(new EventBuilder().withAttendee(attendeesSetTwo).build()));

    }

    @Test
    public void test_attendeesDoesNotContainEmail_returnsFalse() {
        //empty email
        AttendeeContainsEmailPredicate predicate = new AttendeeContainsEmailPredicate("");
        assertFalse(predicate.test(new EventBuilder().withAttendee(attendeesSetOne).build()));

        //person not in attendee
        predicate = new AttendeeContainsEmailPredicate(VALID_EMAIL_AMY);
        assertFalse(predicate.test(new EventBuilder().withAttendee(attendeesSetTwo).build()));

        predicate = new AttendeeContainsEmailPredicate(VALID_EMAIL_CALVIN);
        assertFalse(predicate.test(new EventBuilder().withAttendee(attendeesSetOne).build()));

        //no attendee in event
        predicate = new AttendeeContainsEmailPredicate(VALID_EMAIL_CALVIN);
        assertFalse(predicate.test(new EventBuilder().build()));

    }


    @Test
    public void equals() {

        AttendeeContainsEmailPredicate firstPredicate = new AttendeeContainsEmailPredicate(VALID_EMAIL_AMY);
        AttendeeContainsEmailPredicate secondPredicate = new AttendeeContainsEmailPredicate(VALID_EMAIL_BOB);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        AttendeeContainsEmailPredicate firstPredicateCopy = new AttendeeContainsEmailPredicate(VALID_EMAIL_AMY);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

    }
}
