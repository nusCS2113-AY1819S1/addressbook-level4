package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class EventContainsKeywordsPredicateTest {
    private Event event;
    private EventContainsKeywordsPredicate predicate;
    private List<String> keywords;

    @BeforeAll
    public void setup() {
        EventName eventName = new EventName("SE team meeting");
        Description description = new Description("Weekly meeting with software project");
        EventDate eventDate = new EventDate("11-11-2018");
        StartTime startTime = new StartTime("10:10");
        EndTime endTime = new EndTime("11:11");
        Location location = new Location("Room 1");
        event = new Event(eventName, description, eventDate, startTime, endTime, location);
    }

    @Before
    public void initialize() {
        keywords = new ArrayList<>();
    }


    @Test
    void test_matchesName_returnsTrue() {
        keywords.add("team");
        predicate = new EventContainsKeywordsPredicate(keywords);
        assertTrue(predicate.test(event));
    }

    @Test
    void test_matchesDescription_returnsTrue() {
        keywords.add("Weekly");
        predicate = new EventContainsKeywordsPredicate(keywords);
        assertTrue(predicate.test(event));
    }

    @Test
    void test_notMatchesNameAndDescription_returnsFalse() {
        keywords.add("scrum");
        predicate = new EventContainsKeywordsPredicate(keywords);
        assertFalse(predicate.test(event));
    }
}
