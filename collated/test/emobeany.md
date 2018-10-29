# emobeany
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
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
    public static final String VALID_YEAR_9999 = "9999";
    public static final String INVALID_YEAR_PASSED_2017 = "2017";
    public static final String INVALID_YEAR_10000 = "10000";

    public static final String DAY_DESC_1 = " " + PREFIX_DAY + "1";
    public static final String DAY_DESC_2 = " " + PREFIX_DAY + "2";
    public static final String MONTH_DESC_1 = " " + PREFIX_MONTH + "1";
    public static final String MONTH_DESC_2 = " " + PREFIX_MONTH + "2";
    public static final String YEAR_DESC_2018 = " " + PREFIX_YEAR + "2018";
    public static final String YEAR_DESC_2019 = " " + PREFIX_YEAR + "2019";

    public static final String DEADLINE_DESC_1ST_JAN = " " + PREFIX_DEADLINE + VALID_DEADLINE_1ST_JAN;
    public static final String DEADLINE_DESC_31ST_MARCH = " " + PREFIX_DEADLINE + VALID_DEADLINE_31ST_MARCH;
    public static final String DEADLINE_DESC_12TH_MAY = " " + PREFIX_DEADLINE + VALID_DEADLINE_12TH_MAY;
    public static final String MODULE_CODE_CS2113_DESC = " " + PREFIX_MODULE_CODE + VALID_MODULE_CODE_CS2113;
    public static final String MODULE_CODE_CG2271_DESC = " " + PREFIX_MODULE_CODE + VALID_MODULE_CODE_CG2271;
    public static final String TITLE_DESC_1 = " " + PREFIX_TITLE + VALID_TITLE_1;
    public static final String TITLE_DESC_2 = " " + PREFIX_TITLE + VALID_TITLE_2;
    public static final String TITLE_DESC_3 = " " + PREFIX_TITLE + VALID_TITLE_3;
    public static final String DESCRIPTION_DESC_1 = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_1;
    public static final String DESCRIPTION_DESC_2 = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_2;
    public static final String DESCRIPTION_DESC_3 = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_3;
    public static final String PRIORITY_LEVEL_DESC_LOW = " " + PREFIX_PRIORITY + VALID_PRIORITY_LEVEL_LOW;
    public static final String PRIORITY_LEVEL_DESC_HIGH = " " + PREFIX_PRIORITY + VALID_PRIORITY_LEVEL_HIGH;
    public static final String PRIORITY_LEVEL_DESC_MEDIUM = " " + PREFIX_PRIORITY + VALID_PRIORITY_LEVEL_MEDIUM;
    public static final String HOURS_DESC_1 = " " + PREFIX_HOURS + VALID_1_HOUR;
    public static final String HOURS_DESC_2 = " " + PREFIX_HOURS + VALID_2_HOURS;

    public static final String INVALID_DEADLINE_DESC = " " + PREFIX_DEADLINE + "31/2"; // No 31st February in calendar
    public static final String INVALID_PRIORITY_LEVEL_DESC = " " + PREFIX_PRIORITY + "mid"; // not a priority level
    public static final String INVALID_HOURS_DESC = " " + PREFIX_HOURS + "one"; // not an integer
    public static final int OVERFLOW_INT = Integer.MAX_VALUE;
    public static final String INVALID_HOURS_OVERFLOW = " " + PREFIX_HOURS
            + Long.toString((long) OVERFLOW_INT + 1); // integer overflow
    public static final int MAX_HOURS = 24; // integer overflow
    public static final String INVALID_MAX_HOURS = " " + PREFIX_HOURS + Integer.toString(24); // integer overflow

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    //Mainly for EditCommandTests --> can remove
    //public static final EditCommand.EditPersonDescriptor DESC_AMY;
    //public static final EditCommand.EditPersonDescriptor DESC_BOB;
    /*
    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }
    */
```
###### \java\seedu\address\logic\parser\SelectDeadlineCommandParserTest.java
``` java
public class SelectDeadlineCommandParserTest {
    private static final Logger logger = LogsCenter.getLogger(SelectDeadlineCommandParserTest.class);
    private SelectDeadlineCommandParser parser = new SelectDeadlineCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Parser parser = new SelectDeadlineCommandParser();
        Deadline expectedDeadline = VALID_1ST_JAN_2018;

        // preamble only contains whitespace
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DAY_DESC_1 + MONTH_DESC_1 + YEAR_DESC_2018,
                new SelectDeadlineCommand(expectedDeadline));

        // multiple days - last day accepted
        assertParseSuccess(parser, DAY_DESC_2 + DAY_DESC_1 + MONTH_DESC_1 + YEAR_DESC_2018,
                new SelectDeadlineCommand(expectedDeadline));

        // multiple months - last month accepted
        assertParseSuccess(parser, DAY_DESC_1 + MONTH_DESC_2 + MONTH_DESC_1 + YEAR_DESC_2018,
                new SelectDeadlineCommand(expectedDeadline));

        // multiple years - last year accepted
        assertParseSuccess(parser, DAY_DESC_1 + MONTH_DESC_1 + YEAR_DESC_2019 + YEAR_DESC_2018,
                new SelectDeadlineCommand(expectedDeadline));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDeadlineCommand.MESSAGE_USAGE);

        // missing day prefix
        assertParseFailure(parser, VALID_DAY_1 + MONTH_DESC_1 + YEAR_DESC_2018, expectedMessage);

        // missing month prefix
        assertParseFailure(parser, DAY_DESC_1 + VALID_MONTH_JAN + YEAR_DESC_2018, expectedMessage);

        // missing year prefix
        assertParseFailure(parser, DAY_DESC_1 + MONTH_DESC_1 + VALID_YEAR_2018, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + DAY_DESC_1 + MONTH_DESC_1 + YEAR_DESC_2018,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDeadlineCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\task\DeadlineTest.java
``` java
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

        /*// Valid deadline for february -> returns true
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
*/
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
```
###### \java\seedu\address\testutil\DeadlineBuilder.java
``` java
/**
 * A utility class to build Deadline objects.
 */

public class DeadlineBuilder {

    public static final String DEFAULT_DAY = "1";
    public static final String DEFAULT_MONTH = "1";
    public static final String DEFAULT_YEAR = "2018";

    private String day;
    private String month;
    private String year;

    public DeadlineBuilder() {
        this.day = DEFAULT_DAY;
        this.month = DEFAULT_MONTH;
        this.year = DEFAULT_YEAR;
    }

    /**
     * Initialises the DeadBuilder with the data of {@code deadlineToCopu}.
     */
    public DeadlineBuilder(Deadline deadlineToCopy) {
        this.day = deadlineToCopy.getDay();
        this.month = deadlineToCopy.getMonth();
        this.year = deadlineToCopy.getYear();
    }

    /**
     * Sets the {@code Day} of the {@code Deadline} that we are building.
     */
    public DeadlineBuilder withDay(String day) {
        this.day = day;
        return this;
    }

    /**
     * Sets the {@code Month} of the {@code Deadline} that we are building.
     */
    public DeadlineBuilder withMonth(String month) {
        this.month = month;
        return this;
    }

    /**
     * Sets the {@code Year} of the {@code Deadline} that we are building.
     */
    public DeadlineBuilder withYear(String year) {
        this.year = year;
        return this;
    }

    public Deadline build() {
        return new Deadline(day, month, year);
    }
}
```
###### \java\seedu\address\testutil\TypicalDeadlines.java
``` java
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
```
