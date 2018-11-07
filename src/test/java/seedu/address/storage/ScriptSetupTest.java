package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.FileUtilTest;
import seedu.address.model.UserPrefs;
import seedu.address.storage.scripts.ScriptSetup;
import seedu.address.storage.scripts.ScriptsGenerator;

public class ScriptSetupTest {
    public static final String SCRIPTS_LOCATION = "/scripts/";
    public static final String INVALID_SCRIPT_PATH = "//scripts/";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private File addGroupsFile;
    private File addPersonsFile;

    private File testAddGroupsFile;
    private File testAddPersonsFile;

    private UserPrefs userPrefs;
    private UserPrefs invalidUserPrefs;
    private ScriptSetup scriptSetup;

    @Before
    public void setUp() throws IOException {
        userPrefs = new UserPrefs();

        invalidUserPrefs = new UserPrefs();
        invalidUserPrefs.setScriptFileDirectory(INVALID_SCRIPT_PATH);

        scriptSetup = new ScriptSetup();

        testAddGroupsFile = new File(FileUtil.getRootLocation() + FileUtilTest.TEST_FILE_LOCATION
                + scriptSetup.ADD_GROUPS_FILE);

        FileUtil.writeToTextFile(testAddGroupsFile, ScriptsGenerator.getGroupCommand());

        testAddPersonsFile = new File(FileUtil.getRootLocation() + FileUtilTest.TEST_FILE_LOCATION
                + scriptSetup.ADD_PERSONS_FILE);

        FileUtil.writeToTextFile(testAddPersonsFile, ScriptsGenerator.getAddCommand());

        /*Remove the scripts directory
        File dir = new File(scriptSetup.getDefaultLocation() + SCRIPTS_LOCATION);
        for (File file:dir.listFiles()) {
            file.delete();
        }
        dir.delete();*/
    }

    @Test
    public void execute_validPath_success() throws IOException {
        scriptSetup.execute(userPrefs);
        addGroupsFile = new File(scriptSetup.getDefaultLocation() + userPrefs.getScriptFileDirectory()
                + scriptSetup.ADD_GROUPS_FILE);

        addPersonsFile = new File(scriptSetup.getDefaultLocation() + userPrefs.getScriptFileDirectory()
                + scriptSetup.ADD_PERSONS_FILE);

        boolean isTwoEqual = FileUtils.contentEquals(addGroupsFile, testAddGroupsFile)
                && FileUtils.contentEquals(addPersonsFile, testAddPersonsFile);

        assertEquals(isTwoEqual, true);
    }

    @Test
    public void execute_invalidPath_success() throws IOException {
        scriptSetup.execute(invalidUserPrefs);
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
