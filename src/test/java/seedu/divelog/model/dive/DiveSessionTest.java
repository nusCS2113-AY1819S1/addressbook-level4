package seedu.divelog.model.dive;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import seedu.divelog.testutil.DiveSessionBuilder;

public class DiveSessionTest {
    @Test
    public void compareTo_test() {
        //Create a set of DiveSessions using different timezone
        DiveSession diveSession1 = new DiveSessionBuilder()
                .withStart("1000")
                .withStartDate("01011996")
                .withEnd("1030")
                .withEndDate("01011996")
                .withTimeZone("+8")
                .build();
        DiveSession diveSession2 = new DiveSessionBuilder()
                .withStart("1000")
                .withStartDate("01011996")
                .withEnd("1030")
                .withEndDate("01011996")
                .withTimeZone("+9")
                .build();
        DiveSession diveSession3 = new DiveSessionBuilder()
                .withStart("1300")
                .withStartDate("01011996")
                .withEnd("1230")
                .withEndDate("01011996")
                .withTimeZone("+8")
                .build();

        DiveSession[] arr = new DiveSession[3];
        arr[0] = diveSession1;
        arr[1] = diveSession2;
        arr[2] = diveSession3;

        Arrays.sort(arr);

        assertEquals(diveSession2, arr[0]);
        assertEquals(diveSession1, arr[1]);
        assertEquals(diveSession3, arr[2]);
    }
}
