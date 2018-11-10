package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEvents.ALICE;
import static seedu.address.testutil.TypicalEvents.BENSON;

import org.junit.Test;

import seedu.address.model.user.Username;

public class AttendanceContainsUserPredicateTest {
    @Test
    public void equals() {
        AttendanceContainsUserPredicate predicate = new AttendanceContainsUserPredicate(new Username("admin"));
        AttendanceContainsUserPredicate predicateEqual = new AttendanceContainsUserPredicate(new Username("admin"));
        AttendanceContainsUserPredicate predicateNotEqual = new AttendanceContainsUserPredicate(new Username("ad"));

        assertTrue(predicate.equals(predicate));

        assertTrue(predicate.equals(predicateEqual));

        assertFalse(predicate.equals(predicateNotEqual));
    }

    @Test
    public void test_eventContainsAttendee() {
        AttendanceContainsUserPredicate predicate = new AttendanceContainsUserPredicate(new Username("admin"));
        assertTrue(predicate.test(ALICE));
    }

    @Test
    public void test_eventNotContainsAttendee() {
        AttendanceContainsUserPredicate predicate = new AttendanceContainsUserPredicate(new Username("admin"));
        assertFalse(predicate.test(BENSON));
    }

}
