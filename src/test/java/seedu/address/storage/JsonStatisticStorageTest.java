package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.statistic.Statistic;

public class JsonStatisticStorageTest {
    public static final int VALID_YEAR = 2018;
    public static final int VALID_MONTH = 11;
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonStatisticStorageTest");


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readStatistic_nullFilePath_throwsNullPointerException() throws DataConversionException {
        thrown.expect(NullPointerException.class);
        readStatistic(null);
    }

    private Optional<Statistic> readStatistic(String statisticFileInTestDataFolder) throws DataConversionException {
        Path prefsFilePath = addToTestDataPathIfNotNull(statisticFileInTestDataFolder);
        return new JsonStatisticStorage(prefsFilePath).readStatistic(prefsFilePath);
    }

    @Test
    public void readStatistic_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readStatistic("NonExistentFile.json").isPresent());
    }

    @Test
    public void readStatistic_notJsonFormat_exceptionThrown() throws DataConversionException {
        thrown.expect(DataConversionException.class);
        readStatistic("NotJsonFormatStatistic.json");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    private Path addToTestDataPathIfNotNull(String statisticFileInTestDataFolder) {
        return statisticFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(statisticFileInTestDataFolder)
                : null;
    }

    @Test
    public void readStatistic_fileInOrder_successfullyRead() throws DataConversionException {
        Statistic expected = getTypicalStatistic();
        Statistic actual = readStatistic("TypicalStatistic.json").get();
        assertEquals(expected, actual);
    }

    @Test
    public void readStatistic_extraValuesInFile_extraValuesIgnored() throws DataConversionException {
        Statistic expected = getTypicalStatistic();
        Statistic actual = readStatistic("ExtraValuesStatistic.json").get();

        assertEquals(expected, actual);
    }

    private Statistic getTypicalStatistic() {
        Statistic statistic = new Statistic(VALID_MONTH, VALID_YEAR);
        return statistic;
    }

    @Test
    public void savePrefs_nullPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveStatistic(null, "SomeFile.json");
    }

    @Test
    public void saveStatistic_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveStatistic(new Statistic(), null);
    }

    /**
     * Saves {@code statistic} at the specified {@code prefsFileInTestDataFolder} filepath.
     */
    private void saveStatistic(Statistic statistic, String prefsFileInTestDataFolder) {
        try {
            new JsonStatisticStorage(addToTestDataPathIfNotNull(prefsFileInTestDataFolder))
                    .saveStatistic(statistic);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file", ioe);
        }
    }
}
