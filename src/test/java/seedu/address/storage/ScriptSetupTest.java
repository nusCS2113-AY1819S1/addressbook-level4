package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.UserPrefs;
import seedu.address.storage.scripts.ScriptSetup;

public class ScriptSetupTest {
    public static final String TEST_FILES_LOCATION = "ScriptFiles/";
    public static final String SCRIPTS_LOCATION = "/scripts/";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private File addGroupsFile;
    private File addPersonsFile;

    private File testAddGroupsFile;
    private File testAddPersonsFile;

    private UserPrefs userPrefs;
    private ScriptSetup scriptSetup;

    @Before
    public void setUp() {
        userPrefs = new UserPrefs();
        scriptSetup = new ScriptSetup();

        ClassLoader classLoader = getClass().getClassLoader();

        testAddGroupsFile = new File(classLoader.getResource(TEST_FILES_LOCATION
                + scriptSetup.ADD_GROUPS_FILE).getFile());

        testAddPersonsFile = new File(classLoader.getResource(TEST_FILES_LOCATION
                + scriptSetup.ADD_PERSONS_FILE).getFile());

        /*Remove the scripts directory
        File dir = new File(scriptSetup.getDefaultLocation() + SCRIPTS_LOCATION);
        for (File file:dir.listFiles()) {
            file.delete();
        }
        dir.delete();*/
    }

    @Test
    public void execute_success() throws IOException {
        scriptSetup.execute(userPrefs.getScriptFileDirectory());
        addGroupsFile = new File(scriptSetup.getDefaultLocation() + userPrefs.getScriptFileDirectory()
                + scriptSetup.ADD_GROUPS_FILE);

        addPersonsFile = new File(scriptSetup.getDefaultLocation() + userPrefs.getScriptFileDirectory()
                + scriptSetup.ADD_PERSONS_FILE);

        boolean isTwoEqual = FileUtils.contentEquals(addGroupsFile, testAddGroupsFile)
                && FileUtils.contentEquals(addPersonsFile, testAddPersonsFile);

        assertEquals(isTwoEqual, true);
    }

    @Test
    public void addSampleTextFile_success() throws IOException {
        scriptSetup.addSampleTextFiles(userPrefs.getScriptFileDirectory(), scriptSetup.getDefaultLocation());
        addGroupsFile = new File(scriptSetup.getDefaultLocation() + userPrefs.getScriptFileDirectory()
                + scriptSetup.ADD_GROUPS_FILE);

        addPersonsFile = new File(scriptSetup.getDefaultLocation() + userPrefs.getScriptFileDirectory()
                + scriptSetup.ADD_PERSONS_FILE);

        boolean isTwoEqual = FileUtils.contentEquals(addGroupsFile, testAddGroupsFile)
                && FileUtils.contentEquals(addPersonsFile, testAddPersonsFile);

        assertEquals(isTwoEqual, true);
    }
}
