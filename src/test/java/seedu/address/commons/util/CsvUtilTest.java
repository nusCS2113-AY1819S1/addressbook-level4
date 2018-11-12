package seedu.address.commons.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
/**
 * This is a test class for CsvUtilTest
 */
public class CsvUtilTest {

    private static String fileName = "test_file";

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
                fileName,
                null,
                new ArrayList<>(Arrays.asList("unused,unused", "unused,unused"))
        );
    }

    @Test
    public void writeToCsv_nullDataParam_throwsNullPointerException() throws Exception {

        thrown.expect(NullPointerException.class);
        CsvUtil.writeToCsv(
                fileName,
                new ArrayList<>(Arrays.asList("unused", "unused")),
                null
        );
    }

    @Test
    public void writeToCsv_validParams_returnsTrue() throws Exception {
        assertTrue(CsvUtil.writeToCsv(
                fileName,
                new ArrayList<>(Arrays.asList("unused", "unused")),
                new ArrayList<>(Arrays.asList("unused,unused", "unused,unused"))
        ));
    }

    @AfterClass
    public static void tearDown() {
        File testFile = new File(CsvUtil.BASE_DIRECTORY, fileName + ".csv");
        testFile.delete();
    }
}
