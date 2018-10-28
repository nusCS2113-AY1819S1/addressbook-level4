package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAY_AND_MONTH_0;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAY_FOR_COMMON_YEAR_FEB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAY_FOR_LEAP_YEAR_FEB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAY_FOR_MONTHS_WITH_30_DAYS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAY_FOR_MONTHS_WITH_31_DAYS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MONTH_13;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_10000;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_PASSED_2017;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_FOR_FEB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_FOR_LEAP_YEAR_FEB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_FOR_MONTHS_WITH_30_DAYS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_FOR_MONTHS_WITH_31_DAYS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_APR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_FEB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_JAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_2018;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_2020;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_9999;

import seedu.address.model.task.Deadline;

//@@author emobeany
/**
 * A utility class containing a list of {@code Deadline} objects to be used in tests.
 */
public class TypicalDeadlines {
    // For day validity testing
    public static final Deadline INVALID_0_JAN_2018 = new DeadlineBuilder().withDay(INVALID_DAY_AND_MONTH_0)
            .withMonth(VALID_MONTH_JAN).withYear(VALID_YEAR_2018).build();

    public static final Deadline VALID_1ST_JAN_2018 = new DeadlineBuilder().withDay(VALID_DAY_1)
            .withMonth(VALID_MONTH_JAN).withYear(VALID_YEAR_2018).build();

    // For february
    public static final Deadline VALID_28TH_FEB_2018 = new DeadlineBuilder().withDay(VALID_DAY_FOR_FEB)
            .withMonth(VALID_MONTH_FEB).withYear(VALID_YEAR_2018).build();

    public static final Deadline INVALID_29TH_FEB_2018 = new DeadlineBuilder()
            .withDay(INVALID_DAY_FOR_COMMON_YEAR_FEB).withMonth(VALID_MONTH_JAN).withYear(VALID_YEAR_2018).build();

    // For leap year
    public static final Deadline VALID_29TH_FEB_2020 = new DeadlineBuilder().withDay(VALID_DAY_FOR_LEAP_YEAR_FEB)
            .withMonth(VALID_MONTH_FEB).withYear(VALID_YEAR_2020).build();

    public static final Deadline INVALID_30TH_FEB_2020 = new DeadlineBuilder().withDay(INVALID_DAY_FOR_LEAP_YEAR_FEB)
            .withMonth(VALID_MONTH_FEB).withYear(VALID_YEAR_2020).build();

    // Months with 30 days
    public static final Deadline VALID_30TH_APR_2018 = new DeadlineBuilder().withDay(VALID_DAY_FOR_MONTHS_WITH_30_DAYS)
            .withMonth(VALID_MONTH_APR).withYear(VALID_YEAR_2018).build();

    public static final Deadline INVALID_31ST_APR_2018 = new DeadlineBuilder()
            .withDay(INVALID_DAY_FOR_MONTHS_WITH_30_DAYS).withMonth(VALID_MONTH_APR).withYear(VALID_YEAR_2018).build();

    // Months with 31 days
    public static final Deadline VALID_31ST_JAN_2018 = new DeadlineBuilder().withDay(VALID_DAY_FOR_MONTHS_WITH_31_DAYS)
            .withMonth(VALID_MONTH_JAN).withYear(VALID_YEAR_2018).build();

    public static final Deadline INVALID_32ND_JAN_2018 = new DeadlineBuilder()
            .withDay(INVALID_DAY_FOR_MONTHS_WITH_31_DAYS).withMonth(VALID_MONTH_JAN).withYear(VALID_YEAR_2018).build();

    // For month validity testing
    public static final Deadline VALID_1ST_APR_2018 = new DeadlineBuilder().withDay(VALID_DAY_1)
            .withMonth(VALID_MONTH_APR).withYear(VALID_YEAR_2018).build();

    public static final Deadline INVALID_1ST_0_2018 = new DeadlineBuilder().withDay(VALID_DAY_1)
            .withMonth(INVALID_DAY_AND_MONTH_0).withYear(VALID_YEAR_2018).build();

    public static final Deadline INVALID_1ST_13_2018 = new DeadlineBuilder().withDay(VALID_DAY_1)
            .withMonth(INVALID_MONTH_13).withYear(VALID_YEAR_2018).build();

    // For year validity testing
    public static final Deadline INVALID_1ST_JAN_2017 = new DeadlineBuilder().withDay(VALID_DAY_1)
            .withMonth(VALID_MONTH_JAN).withYear(INVALID_YEAR_PASSED_2017).build();

    public static final Deadline VALID_1ST_JAN_9999 = new DeadlineBuilder().withDay(VALID_DAY_1)
            .withMonth(VALID_MONTH_JAN).withYear(VALID_YEAR_9999).build();

    public static final Deadline INVALID_1ST_JAN_10000 = new DeadlineBuilder().withDay(VALID_DAY_1)
            .withMonth(VALID_MONTH_JAN).withYear(INVALID_YEAR_10000).build();

    private TypicalDeadlines() {} // prevents instantiation
}
