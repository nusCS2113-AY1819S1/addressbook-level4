package seedu.divelog.logic.parser;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;

import static seedu.divelog.logic.parser.ParserUtil.MESSAGE_INVALID_DEPTH;
import static seedu.divelog.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.divelog.testutil.TypicalIndexes.INDEX_FIRST_DIVE;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.divelog.commons.core.Messages;
import seedu.divelog.logic.commands.AddCommand;
import seedu.divelog.logic.parser.exceptions.ParseException;
import seedu.divelog.model.dive.DepthProfile;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.PressureGroup;
import seedu.divelog.testutil.DiveSessionBuilder;

public class ParserUtilTest {
    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_DIVE, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_DIVE, ParserUtil.parseIndex("  1  "));
    }

    //@@author arjo129
    @Test
    public void parseDepth_validInput_success() throws Exception {
        //No white spaces
        assertEquals(new DepthProfile(1.0f), ParserUtil.parseDepth("1.0"));
        //Trailing whitespaces
        assertEquals(new DepthProfile(1.0f), ParserUtil.parseDepth(" 1.0  "));
    }

    //@@author arjo129
    @Test
    public void parseDepth_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_DEPTH);
        ParserUtil.parseDepth("otototo MAMAMAMA");
    }

    //@@author arjo129
    @Test
    public void parseDepth_negativeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_DEPTH);
        ParserUtil.parseDepth("-1");
    }

    //@@author arjo129
    @Test
    public void parseDepth_zeroInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_DEPTH);
        ParserUtil.parseDepth("0");
    }
    //@@author

    //    @Test
    //    public void checkTimeformat_Test() {
    //
    //    }

    //@@author Cjunx
    @Test
    public void parsePressureGroup_test() throws ParseException {
        PressureGroup expected = new PressureGroup("A");
        PressureGroup answer = ParserUtil.parsePressureGroup("A");
        assertEquals(answer, expected);


        thrown.expect(ParseException.class);
        thrown.expectMessage(ParserUtil.MESSAGE_INVALID_PRESSURE_GROUP);
        answer = ParserUtil.parsePressureGroup("3");
    }

    @Test
    public void checkTimeFormat_invalidLengthTest() throws ParseException {
        String startTime = "0800";
        String endTime = "0900";
        String safetyTime = "0930";
        ParserUtil.checkTimeformat(startTime, endTime, safetyTime);
        ParserUtil.checkTimeformat("2359", "0001", "0000");

        startTime = "0860";
        endTime = "0900";
        safetyTime = "0930";

        thrown.expect(ParseException.class);
        thrown.expectMessage(startsWith("Minutes component of the time is more than 59!"));
        ParserUtil.checkTimeformat(startTime, endTime, safetyTime);

        startTime = "0800";
        endTime = "09";
        safetyTime = "0930";
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(Messages.MESSAGE_INVALID_TIME_FORMAT, AddCommand.MESSAGE_USAGE));
        ParserUtil.checkTimeformat(startTime, endTime, safetyTime);

        startTime = "0800";
        endTime = "0900";
        safetyTime = "09333";
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(Messages.MESSAGE_INVALID_TIME_FORMAT, AddCommand.MESSAGE_USAGE));
        ParserUtil.checkTimeformat(startTime, endTime, safetyTime);

        startTime = "0800";
        endTime = "0900";
        safetyTime = "23000";
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(Messages.MESSAGE_INVALID_TIME_FORMAT, AddCommand.MESSAGE_USAGE));
        ParserUtil.checkTimeformat(startTime, endTime, safetyTime);
    }


    @Test
    public void checkTimeFormat_invalidHourMinsTest() throws ParseException {
        String startTime = "0800";
        String endTime = "0900";
        String safetyTime = "0930";
        ParserUtil.checkTimeformat(startTime, endTime, safetyTime);

        startTime = "0800";
        endTime = "0960";
        safetyTime = "0930";
        thrown.expect(ParseException.class);
        thrown.expectMessage(startsWith("Minutes component of the time is more than 59!"));
        ParserUtil.checkTimeformat(startTime, endTime, safetyTime);


        startTime = "0800";
        endTime = "0900";
        safetyTime = "2539";
        thrown.expect(ParseException.class);
        thrown.expectMessage(startsWith("Hour component of the time is more than 23!"));
        ParserUtil.checkTimeformat(startTime, endTime, safetyTime);
    }

    @Test
    public void checkTimeZoneFormat_test() throws seedu.divelog.logic.parser.exceptions.ParseException {
        ParserUtil.checkTimeZoneformat("+5");
        ParserUtil.checkTimeZoneformat("0");
        ParserUtil.checkTimeZoneformat("-5");
        ParserUtil.checkTimeZoneformat("+12");
        ParserUtil.checkTimeZoneformat("-12");

        thrown.expect(seedu.divelog.logic.parser.exceptions.ParseException.class);
        thrown.expectMessage(String.format(Messages.MESSAGE_INVALID_TIMEZONE_FORMAT, AddCommand.MESSAGE_USAGE));
        ParserUtil.checkTimeZoneformat("+13");

        thrown.expect(seedu.divelog.logic.parser.exceptions.ParseException.class);
        thrown.expectMessage(String.format(Messages.MESSAGE_INVALID_TIMEZONE_FORMAT, AddCommand.MESSAGE_USAGE));
        ParserUtil.checkTimeZoneformat("-13");
    }

    @Test
    public void checkSafetyTime_test() throws java.text.ParseException {
        String startTimeString = "0800";
        String endTimeString = "0830";
        String safetyTimeString = "0820";
        String startDateString = "12112018";
        String endDateString = "12112018";
        SimpleDateFormat inputFormat = new SimpleDateFormat("ddMMyyyyHHmm");

        Date answer = ParserUtil.checkSafetyTime(startTimeString, endTimeString, safetyTimeString,
                 startDateString, endDateString);
        Date expected = inputFormat.parse("121120180820");
        assertEquals(answer, expected);

        startTimeString = "2350";
        endTimeString = "0030";
        safetyTimeString = "2359";
        startDateString = "12112018";
        endDateString = "13112018";
        answer = ParserUtil.checkSafetyTime(startTimeString, endTimeString, safetyTimeString,
                startDateString, endDateString);
        expected = inputFormat.parse("121120182359");
        assertEquals(answer, expected);


        startTimeString = "2350";
        endTimeString = "0030";
        safetyTimeString = "0010";
        startDateString = "12112018";
        endDateString = "13112018";
        answer = ParserUtil.checkSafetyTime(startTimeString, endTimeString, safetyTimeString,
                startDateString, endDateString);
        expected = inputFormat.parse("131120180010");
        assertEquals(answer, expected);
    }

    @Test
    public void checkEditTimeDateLimit_test() throws ParseException {
        DiveSession diveSession = new DiveSessionBuilder()
                .withStart("1000")
                .withStartDate("11112018")
                .withEnd("1030")
                .withEndDate("11112018")
                .withDepth(5)
                .withSafetyStop("0900")
                .withTimeZone("+8")
                .build();

        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(Messages.MESSAGE_INVALID_DATE_LIMITS));
        ParserUtil.checkEditTimeDateLimit(diveSession);

        diveSession = new DiveSessionBuilder()
                .withStart("1000")
                .withStartDate("12112018")
                .withEnd("1030")
                .withEndDate("11112018")
                .withDepth(5)
                .withSafetyStop("1010")
                .withTimeZone("+8")
                .build();

        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(Messages.MESSAGE_INVALID_DATE_LIMITS));
        ParserUtil.checkEditTimeDateLimit(diveSession);
    }
    //@@author
}
