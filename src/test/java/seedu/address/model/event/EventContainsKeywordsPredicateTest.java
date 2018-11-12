package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class EventContainsKeywordsPredicateTest {

    private Event event;
    private List<String> keywords;
    private List<String> keywords2;

    @Before
    public void setup() {
        EventName eventName = new EventName("SE team meeting");
        Description description = new Description("Weekly meeting with software project");
        EventDate eventDate = new EventDate("2018-11-11");
        StartTime startTime = new StartTime("10:10");
        EndTime endTime = new EndTime("11:11");
        Location location = new Location("Room 1");
        event = new Event(eventName, description, eventDate, startTime, endTime, location);
        keywords = new ArrayList<>();
        keywords2 = new ArrayList<>();
    }


    @Test
    public void test_matchesName_returnsTrue() {
        keywords.add("team");
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(keywords);
        assertTrue(predicate.test(event));
    }

    @Test
    public void test_matchesDescription_returnsTrue() {
        keywords.add("Weekly");
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(keywords);
        assertTrue(predicate.test(event));
    }

    @Test
    public void test_notMatchesNameAndDescription_returnsFalse() {
        keywords.add("scrum");
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(keywords);
        assertFalse(predicate.test(event));
    }

    @Test
    public void equals() {
        keywords.add("team");
        keywords2.add("scrum");

        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(keywords);
        EventContainsKeywordsPredicate predicate2 = new EventContainsKeywordsPredicate(keywords2);

        //same object
        assertTrue(predicate.equals(predicate));

        //different object
        assertFalse(predicate.equals(predicate2));
    }
}
