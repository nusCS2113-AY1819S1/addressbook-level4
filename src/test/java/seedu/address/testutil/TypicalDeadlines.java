package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.model.task.Deadline;

//@@author emobeany
/**
 * A utility class containing a list of {@code Deadline} objects to be used in tests.
 */
public class TypicalDeadlines {

    public static final String INVALID_DAY_AND_MONTH_0 = "0";
    public static final String VALID_DAY_1 = "1";
    public static final String VALID_DAY_FOR_FEB = "28";
    public static final String VALID_DAY_FOR_LEAP_YEAR_FEB = "29";
    public static final String INVALID_DAY_FOR_COMMON_YEAR_FEB = "29";
    public static final String INVALID_DAY_FOR_LEAP_YEAR_FEB = "30";
    public static final String VALID_DAY_FOR_MONTHS_WITH_30_DAYS = "30";
    public static final String VALID_DAY_FOR_MONTHS_WITH_31_DAYS = "31";
    public static final String INVALID_DAY_FOR_MONTHS_WITH_30_DAYS = "31";
    public static final String INVALID_DAY_FOR_MONTHS_WITH_31_DAYS = "32";
    public static final String VALID_MONTH_JAN = "1";
    public static final String VALID_MONTH_FEB = "2";
    public static final String VALID_MONTH_APR = "4";
    public static final String INVALID_MONTH_13 = "13";
    public static final String VALID_YEAR_2018 = "2018";
    public static final String VALID_YEAR_2020 = "2020";
    public static final String LEAP_YEAR_2020 = "2020";
    public static final String NON_LEAP_YEAR_2100 = "2100";
    public static final String LEAP_YEAR_2400 = "2400";
    public static final String VALID_YEAR_9999 = "9999";
    public static final String INVALID_YEAR_PASSED_2017 = "2017";
    public static final String INVALID_YEAR_10000 = "10000";
    public static final String VALID_1ST_JAN_2018_WITHOUT_PREFIX = "1/1/2018";
    public static final String VALID_1ST_JAN_WITHOUT_PREFIX = "1/1";
    public static final String INVALID_DAY_WITH_SYMBOLS = "2!";
    public static final String INVALID_MONTH_WITH_SYMBOLS = "2?";
    public static final String INVALID_YEAR_WITH_SYMBOLS = "2@18";
    public static final String INVALID_YEAR_WITH_ALPHABETS = "2o18";
    public static final String INVALID_YEAR_WITH_SPACE = "2 18";

    public static final String DAY_DESC_1 = " " + PREFIX_DAY + "1";
    public static final String DAY_DESC_01 = " " + PREFIX_DAY + "01";
    public static final String DAY_DESC_2 = " " + PREFIX_DAY + "2";
    public static final String MONTH_DESC_1 = " " + PREFIX_MONTH + "1";
    public static final String MONTH_DESC_01 = " " + PREFIX_MONTH + "01";
    public static final String MONTH_DESC_2 = " " + PREFIX_MONTH + "2";
    public static final String YEAR_DESC_2018 = " " + PREFIX_YEAR + "2018";
    public static final String YEAR_DESC_02018 = " " + PREFIX_YEAR + "02018";
    public static final String YEAR_DESC_2019 = " " + PREFIX_YEAR + "2019";

    // For day validity testing
    public static final Deadline INVALID_0_JAN_2018 = new DeadlineBuilder().withDay(INVALID_DAY_AND_MONTH_0)
            .withMonth(VALID_MONTH_JAN).withYear(VALID_YEAR_2018).build();

    public static final Deadline VALID_1ST_JAN_2018 = new DeadlineBuilder().withDay(VALID_DAY_1)
            .withMonth(VALID_MONTH_JAN).withYear(VALID_YEAR_2018).build();

    // For february
    public static final Deadline VALID_28TH_FEB_2018 = new DeadlineBuilder().withDay(VALID_DAY_FOR_FEB)
            .withMonth(VALID_MONTH_FEB).withYear(VALID_YEAR_2018).build();

    public static final Deadline INVALID_29TH_FEB_2018 = new DeadlineBuilder()
            .withDay(INVALID_DAY_FOR_COMMON_YEAR_FEB).withMonth(VALID_MONTH_FEB).withYear(VALID_YEAR_2018).build();

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

    // Deadlines without year
    public static final Deadline VALID_1ST_JAN_WITHOUT_YEAR = new DeadlineBuilder().withDay(VALID_DAY_1)
            .withMonth(VALID_MONTH_JAN).build();

    public static final Deadline INVALID_32ND_JAN_WITHOUT_YEAR = new DeadlineBuilder()
            .withDay(INVALID_DAY_FOR_MONTHS_WITH_31_DAYS).withMonth(VALID_MONTH_JAN).build();

    public static final Deadline VALID_1ST_APR_WITHOUT_YEAR = new DeadlineBuilder()
            .withDay(VALID_DAY_1).withMonth(VALID_MONTH_APR).build();

    // For illegal character testing
    public static final Deadline INVALID_DEADLINE_ILLEGAL_CHAR_DAY = new DeadlineBuilder()
            .withDay(INVALID_DAY_WITH_SYMBOLS).withMonth(VALID_MONTH_JAN).withYear(VALID_YEAR_2018).build();

    public static final Deadline INVALID_DEADLINE_ILLEGAL_CHAR_MONTH = new DeadlineBuilder().withDay(VALID_DAY_1)
            .withMonth(INVALID_MONTH_WITH_SYMBOLS).withYear(VALID_YEAR_2018).build();

    public static final Deadline INVALID_DEADLINE_ILLEGAL_CHAR_YEAR = new DeadlineBuilder().withDay(VALID_DAY_1)
            .withMonth(VALID_MONTH_JAN).withYear(INVALID_YEAR_WITH_SYMBOLS).build();

    private TypicalDeadlines() {} // prevents instantiation
}
