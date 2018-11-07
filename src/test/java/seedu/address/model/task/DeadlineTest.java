package seedu.address.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalDeadlines.INVALID_0_JAN_2018;
import static seedu.address.testutil.TypicalDeadlines.INVALID_1ST_0_2018;
import static seedu.address.testutil.TypicalDeadlines.INVALID_1ST_13_2018;
import static seedu.address.testutil.TypicalDeadlines.INVALID_1ST_JAN_10000;
import static seedu.address.testutil.TypicalDeadlines.INVALID_1ST_JAN_2017;
import static seedu.address.testutil.TypicalDeadlines.INVALID_29TH_FEB_2018;
import static seedu.address.testutil.TypicalDeadlines.INVALID_30TH_FEB_2020;
import static seedu.address.testutil.TypicalDeadlines.INVALID_31ST_APR_2018;
import static seedu.address.testutil.TypicalDeadlines.INVALID_32ND_JAN_2018;
import static seedu.address.testutil.TypicalDeadlines.INVALID_32ND_JAN_WITHOUT_YEAR;
import static seedu.address.testutil.TypicalDeadlines.INVALID_DEADLINE_ILLEGAL_CHAR_DAY;
import static seedu.address.testutil.TypicalDeadlines.INVALID_DEADLINE_ILLEGAL_CHAR_MONTH;
import static seedu.address.testutil.TypicalDeadlines.INVALID_DEADLINE_ILLEGAL_CHAR_YEAR;
import static seedu.address.testutil.TypicalDeadlines.INVALID_YEAR_WITH_ALPHABETS;
import static seedu.address.testutil.TypicalDeadlines.INVALID_YEAR_WITH_SPACE;
import static seedu.address.testutil.TypicalDeadlines.INVALID_YEAR_WITH_SYMBOLS;
import static seedu.address.testutil.TypicalDeadlines.LEAP_YEAR_2020;
import static seedu.address.testutil.TypicalDeadlines.LEAP_YEAR_2400;
import static seedu.address.testutil.TypicalDeadlines.NON_LEAP_YEAR_2100;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_APR_2018;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_JAN_2018;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_JAN_9999;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_JAN_WITHOUT_YEAR;
import static seedu.address.testutil.TypicalDeadlines.VALID_28TH_FEB_2018;
import static seedu.address.testutil.TypicalDeadlines.VALID_29TH_FEB_2020;
import static seedu.address.testutil.TypicalDeadlines.VALID_30TH_APR_2018;
import static seedu.address.testutil.TypicalDeadlines.VALID_31ST_JAN_2018;
import static seedu.address.testutil.TypicalDeadlines.VALID_DAY_1;
import static seedu.address.testutil.TypicalDeadlines.VALID_YEAR_2018;

import java.util.logging.Logger;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.LogsCenter;
import seedu.address.testutil.DeadlineBuilder;

//@@author emobeany
public class DeadlineTest {
    private static final Logger logger = LogsCenter.getLogger(DeadlineTest.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValidFormat() {
        // Deadline with only day dd -> false
        assertFalse(Deadline.isValidFormat(VALID_DAY_1));

        // Deadline with day and month dd/mm -> true
        assertTrue(Deadline.isValidFormat(VALID_1ST_JAN_WITHOUT_YEAR.toString()));

        // Deadline with all 3 fields dd/mm/yyyy -> true
        assertTrue(Deadline.isValidFormat(VALID_1ST_JAN_2018.toString()));
    }

    @Test
    public void containsIllegalCharacters() {
        // Deadline contains illegal character in Day -> true
        assertTrue(Deadline.containsIllegalCharacters(INVALID_DEADLINE_ILLEGAL_CHAR_DAY.toString()));

        // Deadline contains illegal character in Month -> true
        assertTrue(Deadline.containsIllegalCharacters(INVALID_DEADLINE_ILLEGAL_CHAR_MONTH.toString()));

        // Deadline contains illegal character in Year -> true
        assertTrue(Deadline.containsIllegalCharacters(INVALID_DEADLINE_ILLEGAL_CHAR_YEAR.toString()));

        // No illegal character -> false
        assertFalse(Deadline.containsIllegalCharacters(VALID_1ST_JAN_2018.toString()));
        assertFalse(Deadline.containsIllegalCharacters(VALID_1ST_JAN_WITHOUT_YEAR.toString()));
    }

    @Test
    public void isValidDeadline() {
        // Invalid deadline with 0 day or month -> Returns false
        assertFalse(Deadline.isValidDeadline(INVALID_0_JAN_2018.toString()));
        assertFalse(Deadline.isValidDeadline(INVALID_1ST_0_2018.toString()));

        // Valid deadline -> returns true
        assertTrue(Deadline.isValidDeadline(VALID_1ST_JAN_2018.toString()));

        // Valid deadline for february -> returns true
        assertTrue(Deadline.isValidDeadline(VALID_28TH_FEB_2018.toString()));

        // Invalid deadline for february in common year -> returns false
        assertFalse(Deadline.isValidDeadline(INVALID_29TH_FEB_2018.toString()));

        // Valid deadline for february during leap year -> returns true
        assertTrue(Deadline.isValidDeadline(VALID_29TH_FEB_2020.toString()));

        // Invalid deadline for february during leap year -> returns false
        assertFalse(Deadline.isValidDeadline(INVALID_30TH_FEB_2020.toString()));

        // Valid deadline for months with 30 days -> returns true
        assertTrue(Deadline.isValidDeadline(VALID_30TH_APR_2018.toString()));

        // Invalid deadline for months with 30 days -> returns false
        assertFalse(Deadline.isValidDeadline(INVALID_31ST_APR_2018.toString()));

        // Valid deadline for months with 31 days -> returns true
        assertTrue(Deadline.isValidDeadline(VALID_31ST_JAN_2018.toString()));

        // Invalid deadline for months with 31 days -> returns false
        assertFalse(Deadline.isValidDeadline(INVALID_32ND_JAN_2018.toString()));

        // Invalid month -> returns false
        assertFalse(Deadline.isValidDeadline(INVALID_1ST_0_2018.toString()));
        assertFalse(Deadline.isValidDeadline(INVALID_1ST_13_2018.toString()));

        // Valid month -> returns true
        assertTrue(Deadline.isValidDeadline(VALID_1ST_APR_2018.toString()));

        // Valid year -> returns true
        assertTrue(Deadline.isValidDeadline(VALID_1ST_JAN_9999.toString()));

        // Invalid year -> returns false
        assertFalse(Deadline.isValidDeadline(INVALID_1ST_JAN_2017.toString()));
        assertFalse(Deadline.isValidDeadline(INVALID_1ST_JAN_10000.toString()));

        // Valid date without year -> returns true
        assertTrue(Deadline.isValidDeadline(VALID_1ST_JAN_WITHOUT_YEAR.toString()));

        // Invalid date without year -> returns false
        assertFalse(Deadline.isValidDeadline(INVALID_32ND_JAN_WITHOUT_YEAR.toString()));
    }


    @Test
    public void isNumeric() {
        //contains numbers only -> true
        assertTrue(Deadline.isNumeric(VALID_YEAR_2018));

        //contains other symbols -> false
        assertFalse(Deadline.isNumeric(INVALID_YEAR_WITH_SYMBOLS));

        //contains alphabets -> false
        assertFalse(Deadline.isNumeric(INVALID_YEAR_WITH_ALPHABETS));

        //contains space -> false
        assertFalse(Deadline.isNumeric(INVALID_YEAR_WITH_SPACE));
    }

    @Test
    public void isLeapYear() {
        // Valid common year -> returns false
        assertFalse(Deadline.isLeapYear(Integer.parseInt(VALID_YEAR_2018)));

        // Valid leap year -> returns true
        assertTrue(Deadline.isLeapYear(Integer.parseInt(LEAP_YEAR_2020)));

        // Valid year divisible by 4 but is common year -> returns false
        assertFalse(Deadline.isLeapYear(Integer.parseInt(NON_LEAP_YEAR_2100)));

        // Valid year divisible by 100 but is leap year -> returns true
        assertTrue(Deadline.isLeapYear(Integer.parseInt(LEAP_YEAR_2400)));
    }

    @Test
    public void equals() {
        Deadline copy1stJan2018 = new DeadlineBuilder(VALID_1ST_JAN_2018).build();
        logger.info("original: " + VALID_1ST_JAN_2018);
        logger.info("copy: " + copy1stJan2018);

        assertEquals(VALID_1ST_JAN_2018, copy1stJan2018);

        // Same object -> returns true
        assertEquals(VALID_1ST_JAN_2018, VALID_1ST_JAN_2018);

        // Null -> returns false
        assertNotEquals(VALID_1ST_JAN_2018, null);

        // Different types -> return false
        assertNotEquals(VALID_1ST_JAN_2018, 1);

        // Different day -> returns false
        assertNotEquals(VALID_1ST_JAN_2018, VALID_31ST_JAN_2018);

        // Different month -> returns false
        assertNotEquals(VALID_1ST_JAN_2018, VALID_1ST_APR_2018);

        // Different year -> returns false
        assertNotEquals(VALID_1ST_JAN_2018, VALID_1ST_JAN_9999);
    }
}
