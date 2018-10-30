package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
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
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_APR_2018;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_JAN_2018;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_JAN_9999;
import static seedu.address.testutil.TypicalDeadlines.VALID_28TH_FEB_2018;
import static seedu.address.testutil.TypicalDeadlines.VALID_29TH_FEB_2020;
import static seedu.address.testutil.TypicalDeadlines.VALID_30TH_APR_2018;
import static seedu.address.testutil.TypicalDeadlines.VALID_31ST_JAN_2018;

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
    }

    @Test
    public void equals() {
        Deadline copy1stJan2018 = new DeadlineBuilder(VALID_1ST_JAN_2018).build();
        logger.info("original: " + VALID_1ST_JAN_2018);
        logger.info("copy: " + copy1stJan2018);

        assertTrue(VALID_1ST_JAN_2018.equals(copy1stJan2018));

        // Same object -> returns true
        assertTrue(VALID_1ST_JAN_2018.equals(VALID_1ST_JAN_2018));

        // Null -> returns false
        assertFalse(VALID_1ST_JAN_2018.equals(null));

        // Different types -> return false
        assertFalse(VALID_1ST_JAN_2018.equals(1));

        // Different day -> returns false
        assertFalse(VALID_1ST_JAN_2018.equals(VALID_31ST_JAN_2018));

        // Different month -> returns false
        assertFalse(VALID_1ST_JAN_2018.equals(VALID_1ST_APR_2018));

        // Different year -> returns false
        assertFalse(VALID_1ST_JAN_2018.equals(VALID_1ST_JAN_9999));
    }
}
