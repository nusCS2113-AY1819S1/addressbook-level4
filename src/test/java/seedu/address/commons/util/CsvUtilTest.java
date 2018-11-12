package seedu.address.commons.util;

import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void writeToCsv_nullFileNameParam_throwsNullPointerException() throws Exception {

        thrown.expect(NullPointerException.class);
        CsvUtil.writeToCsv(
                null,
                new ArrayList<>(Arrays.asList("unused", "unused")),
                new ArrayList<>(Arrays.asList("unused,unused", "unused,unused"))
        );
    }

    @Test
    public void writeToCsv_nullHeadersParam_throwsNullPointerException() throws Exception {

        thrown.expect(NullPointerException.class);
        CsvUtil.writeToCsv(
                "filename",
                null,
                new ArrayList<>(Arrays.asList("unused,unused", "unused,unused"))
        );
    }

    @Test
    public void writeToCsv_nullDataParam_throwsNullPointerException() throws Exception {

        thrown.expect(NullPointerException.class);
        CsvUtil.writeToCsv(
                "filename",
                new ArrayList<>(Arrays.asList("unused", "unused")),
                null
        );
    }

    @Test
    public void writeToCsv_validParams_returnsTrue() throws Exception {
        assertTrue(CsvUtil.writeToCsv(
                "test_file",
                new ArrayList<>(Arrays.asList("unused", "unused")),
                new ArrayList<>(Arrays.asList("unused,unused", "unused,unused"))
        ));
    }

    @AfterClass
    public static void tearDown() {
        // TODO
    }
}
