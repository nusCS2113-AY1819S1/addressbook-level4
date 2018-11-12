package seedu.divelog.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.divelog.logic.pressuregroup.exceptions.LimitExceededException;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.DiveSessionList;
import seedu.divelog.model.dive.exceptions.InvalidTimeException;
import seedu.divelog.testutil.DiveSessionBuilder;

public class DiveSessionListTest {

    @Test
    public void hasOverlap_test() {

        //Checks if the
        DiveSession diveSession1 = new DiveSessionBuilder()
                .withStart("1000")
                .withStartDate("01011996")
                .withEnd("1030")
                .withEndDate("01011996")
                .withTimeZone("+8")
                .build();
        DiveSession diveSession2 = new DiveSessionBuilder()
                .withStart("1100")
                .withStartDate("01011996")
                .withEnd("1200")
                .withTimeZone("+8")
                .withEndDate("01011996").build();
        DiveSession diveSession3 = new DiveSessionBuilder()
                .withStart("1100")
                .withStartDate("01021996")
                .withEnd("1200")
                .withTimeZone("+8")
                .withEndDate("01021996").build();



        //Populate
        DiveSessionList diveSessions = new DiveSessionList();
        diveSessions.add(diveSession1);
        diveSessions.add(diveSession2);
        diveSessions.add(diveSession3);

        //Test a valid case dive. The dive will take place after diveSession2 and before diveSession 3
        DiveSession toBeInserted = new DiveSessionBuilder()
                .withStart("1330")
                .withStartDate("01011996")
                .withEnd("1400")
                .withTimeZone("+8")
                .withEndDate("01011996").build();

        DiveSessionList temporaryList = new DiveSessionList();
        temporaryList.setDives(diveSessions);
        temporaryList.add(toBeInserted);
        assertFalse(temporaryList.hasOverlap());

        //Test an invalid test case where the dive to be inserted will be overlapping with the dive before it
        toBeInserted = new DiveSessionBuilder()
                .withStart("1045")
                .withStartDate("01011996")
                .withEnd("1115")
                .withEndDate("01011996")
                .withTimeZone("+8")
                .build();
        temporaryList.setDives(diveSessions);
        temporaryList.add(toBeInserted);
        assertTrue(temporaryList.hasOverlap());

        //Falls within a dive
        toBeInserted = new DiveSessionBuilder()
                .withStart("1115")
                .withStartDate("01011996")
                .withEnd("1145")
                .withTimeZone("+8")
                .withEndDate("01011996").build();
        temporaryList.setDives(diveSessions);
        temporaryList.add(toBeInserted);
        assertTrue(temporaryList.hasOverlap());

        //Overlaps identical dive
        toBeInserted = diveSession1;
        temporaryList.setDives(diveSessions);
        temporaryList.add(toBeInserted);
        assertTrue(temporaryList.hasOverlap());

        //Overlaps multiple dives
        toBeInserted = new DiveSessionBuilder()
                .withStart("0900")
                .withStartDate("01011996")
                .withEnd("1300")
                .withTimeZone("+8")
                .withEndDate("01011996").build();
        temporaryList.setDives(diveSessions);
        temporaryList.add(toBeInserted);
        assertTrue(temporaryList.hasOverlap());


        //Overlaps at start
        toBeInserted = new DiveSessionBuilder()
                .withStart("09045")
                .withStartDate("01011996")
                .withEnd("1015")
                .withTimeZone("+8")
                .withEndDate("01021996").build();
        temporaryList.setDives(diveSessions);
        temporaryList.add(toBeInserted);
        assertTrue(temporaryList.hasOverlap());

        //Test an overlapping from different timezone.
        toBeInserted = new DiveSessionBuilder()
                .withStart("1145")
                .withStartDate("01011996")
                .withEnd("1215")
                .withEndDate("01011996")
                .withTimeZone("+9")
                .build();
        temporaryList.setDives(diveSessions);
        temporaryList.add(toBeInserted);
        assertTrue(temporaryList.hasOverlap());
    }

    @Test
    public void recalculatePressureGroup_test() throws LimitExceededException, InvalidTimeException {
        //Test dives with incorrect pressure groups
        DiveSession diveSession1 = new DiveSessionBuilder()
                .withStart("1000")
                .withStartDate("01011996")
                .withEnd("1030")
                .withEndDate("01011996").build();
        DiveSession diveSession2 = new DiveSessionBuilder()
                .withStart("1100")
                .withStartDate("01011996")
                .withEnd("1200")
                .withEndDate("01011996").build();
        DiveSession diveSession3 = new DiveSessionBuilder()
                .withStart("1100")
                .withStartDate("01021996")
                .withEnd("1200")
                .withEndDate("01021996").build();

        //Populate
        DiveSessionList diveSessions = new DiveSessionList();
        diveSessions.add(diveSession1);
        diveSessions.add(diveSession2);
        diveSessions.add(diveSession3);
        diveSessions.recalculatePressureGroup();

        //Corrected dives
        DiveSession idealDive1 = new DiveSessionBuilder()
                .withStart("1000")
                .withStartDate("01011996")
                .withEnd("1030")
                .withPressureGroupAtEnd("D")
                .withEndDate("01011996").build();
        DiveSession idealDive2 = new DiveSessionBuilder()
                .withStart("1100")
                .withStartDate("01011996")
                .withPressureGroupAtBeginning("C")
                .withPressureGroupAtEnd("P")
                .withEnd("1200")
                .withEndDate("01011996").build();
        DiveSession idealDive3 = new DiveSessionBuilder()
                .withStart("1100")
                .withStartDate("01021996")
                .withEnd("1200")
                .withEndDate("01021996").build();

        DiveSessionList expected = new DiveSessionList();
        expected.add(idealDive1);
        expected.add(idealDive2);
        expected.add(idealDive3);
        assertEquals(expected, diveSessions);

        DiveSessionList emptyDiveList = new DiveSessionList();
        emptyDiveList.recalculatePressureGroup();
    }
}
