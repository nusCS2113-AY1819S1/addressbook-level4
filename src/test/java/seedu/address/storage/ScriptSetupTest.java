package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import seedu.address.model.UserPrefs;
import seedu.address.storage.scripts.ScriptSetup;

public class ScriptSetupTest {
    private static final String TEST_FILES_LOCATION = "/src/test/data/ScriptFiles/";
    private static final String SCRIPTS_LOCATION = "/scripts/";

    private File addGroupsFile;
    private File addPersonsFile;

    private File testaddGroupsFile;
    private File testaddPersonsFile;

    private UserPrefs userPrefs;
    private ScriptSetup scriptSetup;

    @Before
    public void setUp() {
        userPrefs = new UserPrefs();
        scriptSetup = new ScriptSetup();

        testaddGroupsFile = new File(scriptSetup.getDefaultLocation() + TEST_FILES_LOCATION
                + scriptSetup.ADD_GROUPS_FILE);

        testaddPersonsFile = new File(scriptSetup.getDefaultLocation() + TEST_FILES_LOCATION
                + scriptSetup.ADD_PERSONS_FILE);

        //Remove the scripts directory
        File dir = new File(scriptSetup.getDefaultLocation() + SCRIPTS_LOCATION);
        for (File file:dir.listFiles()) {
            file.delete();
        }
        dir.delete();
    }

    @Test
    public void execute_success() throws IOException {
        scriptSetup.execute(userPrefs.getScriptFileDirectory());
        addGroupsFile = new File(scriptSetup.getDefaultLocation() + userPrefs.getScriptFileDirectory()
                + scriptSetup.ADD_GROUPS_FILE);

        addPersonsFile = new File(scriptSetup.getDefaultLocation() + userPrefs.getScriptFileDirectory()
                + scriptSetup.ADD_PERSONS_FILE);

        boolean isTwoEqual = FileUtils.contentEquals(addGroupsFile, testaddGroupsFile);

        assertEquals(isTwoEqual, true);
    }

}
