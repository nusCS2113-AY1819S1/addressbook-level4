package seedu.divelog.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

//@author Cjunx
/**
 * tests
 */
public class CompareUtilTest {

    @Test
    public void checkTimeDifference_sameDay_calculationTest() throws Exception {
        long normalTime = CompareUtil.checkTimeDifference("1900", "2010", "25102018", "25102018");
        long date2 = 70;
        assertEquals(normalTime, date2);

        normalTime = CompareUtil.checkTimeDifference("0000", "2359", "25102018", "25102018");
        date2 = 1439;
        assertEquals(normalTime, date2);

    }
    @Test
    public void checkTimeDifference_diffDay_calculationTest1() throws Exception {
        long normalTime = CompareUtil.checkTimeDifference("2359", "0030", "25102018", "26102018");
        long date2 = 31;
        assertEquals(normalTime, date2);

        normalTime = CompareUtil.checkTimeDifference("0000", "2359", "25102018", "26102018");
        date2 = 1439 + 1440;
        assertEquals(normalTime, date2);
    }

    @Test
    public void convertTimeToLocal_test() throws Exception {
        long answer = CompareUtil.convertTimeToLocal("0500", "25102018", 5);
        long l = Long.parseLong("251020181000");
        assertEquals(answer, l);
    }

    @Test
    public void convertTimeToLocal_diffDay_test() throws Exception {
        long answer = CompareUtil.convertTimeToLocal("2359", "25102018", 5);
        long l = Long.parseLong("261020180459");
        assertEquals(answer, l);
    }
    @Test
    public void convertTimeToLocal_negative_test() throws Exception {
        long answer = CompareUtil.convertTimeToLocal("2359", "25102018", -10);
        long l = Long.parseLong("251020181359");
        assertEquals(answer, l);
    }

    @Test
    public void convertTimeToUtc_positive_test() throws ParseException {
        Date answer = CompareUtil.convertTimeToUtc("1000", "25102018", 5);
        SimpleDateFormat inputFormat = new SimpleDateFormat("ddMMyyyyHHmm");
        Date expectedDate = inputFormat.parse("251020180500");

        assertEquals(answer, expectedDate);
    }

    @Test
    public void convertTimeToUtc_negative_test() throws ParseException {
        Date answer = CompareUtil.convertTimeToUtc("0400", "25102018", -5);
        SimpleDateFormat inputFormat = new SimpleDateFormat("ddMMyyyyHHmm");
        Date expectedDate = inputFormat.parse("251020180900");

        assertEquals(answer, expectedDate);
    }

    @Test
    public void convertTimeToUtc_differentDay_test() throws ParseException {
        Date answer = CompareUtil.convertTimeToUtc("0100", "25102018", 5);
        SimpleDateFormat inputFormat = new SimpleDateFormat("ddMMyyyyHHmm");
        Date expectedDate = inputFormat.parse("241020182000");
        assertEquals(answer, expectedDate);

        Date answer1 = CompareUtil.convertTimeToUtc("2300", "25102018", -5);
        Date expectedDate1 = inputFormat.parse("261020180400");
        assertEquals(answer1, expectedDate1);

        answer1 = CompareUtil.convertTimeToUtc("0000", "25102018", -12);
        expectedDate1 = inputFormat.parse("251020181200");
        assertEquals(answer1, expectedDate1);
    }

    //    @Test
    //    public void getCurrentDateTime_Test() {
    //        long expected = (long) CompareUtil.getCurrentDateTime();
    //        long answer = Long.parseLong("251020180043");
    //        assertEquals(expected,answer);
    //    }

    //    @Test
    //    public void getCurrentDateTimeLong(){
    //        Instant.now(Clock.fixed(
    //                Instant.parse("2018-08-22T10:00:00Z"),
    //                ZoneOffset.UTC));
    //    }

    @Test
    public void readTimeFromLong_test() {
        long datetime = Long.parseLong("251020180510");
        String time1 = CompareUtil.readTimeFromLong(datetime);
        assertEquals(time1, "0510");

        datetime = Long.parseLong("121120181510");
        time1 = CompareUtil.readTimeFromLong(datetime);
        assertEquals(time1, "1510");

        datetime = Long.parseLong("121120180030");
        time1 = CompareUtil.readTimeFromLong(datetime);
        assertEquals(time1, "0030");

        datetime = Long.parseLong("121120180002");
        time1 = CompareUtil.readTimeFromLong(datetime);
        assertEquals(time1, "0002");

        datetime = Long.parseLong("121120180000");
        time1 = CompareUtil.readTimeFromLong(datetime);
        assertEquals(time1, "0000");
    }


    @Test
    public void readDateFromLong_test() {
        long datetime = Long.parseLong("051020180510");
        String time1 = CompareUtil.readDateFromLong(datetime);
        assertEquals(time1, "05102018");

        datetime = Long.parseLong("251020180510");
        time1 = CompareUtil.readDateFromLong(datetime);
        assertEquals(time1, "25102018");

        datetime = Long.parseLong("010120180510");
        time1 = CompareUtil.readDateFromLong(datetime);
        assertEquals(time1, "01012018");
    }

}
