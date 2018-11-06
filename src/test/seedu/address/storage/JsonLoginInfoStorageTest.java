package storage;

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
import seedu.address.model.LoginInfoManager;
import seedu.address.storage.logininfo.JsonLoginInfoStorage;

public class JsonLoginInfoStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test",
                                                    "seedu", "address", "data", "JsonLoginInfoStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readLoginInfo_nullFilePath_throwsNullPointerException() throws DataConversionException {
        thrown.expect(NullPointerException.class);
        readLoginInfo (null);
    }

    private Optional<LoginInfoManager> readLoginInfo (String userLoginFileInTestDataFolder)
                                        throws DataConversionException {
        Path prefsFilePath = addToTestDataPathIfNotNull(userLoginFileInTestDataFolder);
        return new JsonLoginInfoStorage (prefsFilePath).readLoginInfo(prefsFilePath);
    }

    @Test
    public void readUserPrefs_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readLoginInfo ("NonExistentFile.json").isPresent());
    }

    @Test
    public void readUserPrefs_notJsonFormat_exceptionThrown() throws DataConversionException {
        thrown.expect(DataConversionException.class);
        readLoginInfo ("NotJsonFormatLoginInfo.json");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    private Path addToTestDataPathIfNotNull(String userLoginFileInTestDataFolder) {
        return userLoginFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(userLoginFileInTestDataFolder)
                : null;
    }

    @Test
    public void readUserPrefs_fileInOrder_successfullyRead() throws DataConversionException {
        LoginInfoManager expected = getTypicalLoginInfo ();
        LoginInfoManager actual = readLoginInfo ("TypicalLoginInfo.json").get();
        assertEquals(expected.toString (), actual.toString ());
    }

    @Test
    public void readUserPrefs_valuesMissingFromFile_defaultValuesUsed() throws DataConversionException {
        LoginInfoManager actual = readLoginInfo ("EmptyLoginInfo.json").get();
        assertEquals(new LoginInfoManager().toString (), actual.toString ());
    }

    @Test
    public void readUserPrefs_extraValuesInFile_extraValuesIgnored() throws DataConversionException {
        LoginInfoManager expected = getTypicalLoginInfo ();
        LoginInfoManager actual = readLoginInfo ("ExtraValuesLoginInfo.json").get();

        assertEquals(expected.toString (), actual.toString ());
    }

    private LoginInfoManager getTypicalLoginInfo () {
        LoginInfoManager typicalLoginManager = new LoginInfoManager ();
        return typicalLoginManager;
    }

    @Test
    public void savePrefs_nullPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveUserPrefs(null, "SomeFile.json");
    }

    @Test
    public void saveUserPrefs_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveUserPrefs(new LoginInfoManager(), null);
    }

    /**
     * Saves {@code userPrefs} at the specified {@code loginFileInTestDataFolder} filepath.
     */
    private void saveUserPrefs(LoginInfoManager loginInfoManager, String loginFileInTestDataFolder) {
        try {
            new JsonLoginInfoStorage(addToTestDataPathIfNotNull(loginFileInTestDataFolder))
                    .saveLoginInfo(loginInfoManager);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file", ioe);
        }
    }

    @Test
    public void saveLoginINfo_allInOrder_success () throws DataConversionException, IOException {

        LoginInfoManager original = new LoginInfoManager ();


        Path pefsFilePath = testFolder.getRoot().toPath().resolve("TempLoginInfo.json");
        JsonLoginInfoStorage jsonLoginInfoStorage = new JsonLoginInfoStorage (pefsFilePath);

        //Try writing when the file doesn't exist
        jsonLoginInfoStorage.saveLoginInfo (original);
        LoginInfoManager readBack = jsonLoginInfoStorage.readLoginInfo ().get ();
        assertEquals(original.toString (), readBack.toString ());

        //Try saving when the file exists
        jsonLoginInfoStorage.saveLoginInfo (original);
        readBack = jsonLoginInfoStorage.readLoginInfo ().get ();
        assertEquals(original.toString (), readBack.toString ());
    }

}
