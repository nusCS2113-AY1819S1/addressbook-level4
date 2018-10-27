package seedu.address.storage.scripts;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.ValidCommandList;

/**
 * Load the and add default scripts into in the default directory of the Application.
 */
public class ScriptSetup {
    public static final String ADD_GROUPS_FILE = "AddGroups.txt";
    public static final String ADD_PERSONS_FILE = "AddPersons.txt";

    private static final Logger logger = LogsCenter.getLogger(ScriptSetup.class);

    private static final String DIR_SAMPLE_TEXT = "/src/main/resources/defaultScripts";

    private String defaultLocation;

    public ScriptSetup() {
        this.defaultLocation = FileUtil.getRootLocation();
    }

    /**
     * Creates a script folder and add some sample text files if folder is missing.
     *
     * @param scriptFolder is the directory of the script folder
     */
    public void execute(String scriptFolder) {
        Path scriptPath = FileUtil.getPath(defaultLocation + scriptFolder);
        boolean isScriptFolderPresent = false;
        try {
            isScriptFolderPresent = FileUtil.createFolder(scriptPath);
        } catch (IOException ioe) {
            logger.info(scriptPath + " is not a valid directory "
                    + "and a default script folder will be automatically generated");
        }
        if (!isScriptFolderPresent) {
            addSampleTextFiles(scriptFolder, defaultLocation);
        }
    }

    /**
     * Add some sample text files
     */
    public void addSampleTextFiles(String scriptFolder, String defaultLocation) {
        File file = new File(defaultLocation + scriptFolder + ADD_PERSONS_FILE);
        File file2 = new File(defaultLocation + scriptFolder + ADD_GROUPS_FILE);
        try {
            FileUtil.writetoTextFile(file, ValidCommandList.getAddCommand());
            FileUtil.writetoTextFile(file2, ValidCommandList.getGroupCommand());
        } catch (IOException ioe) {
            logger.info("Sample Text cannot be created");
        }
    }

    public String getDefaultLocation() {
        return this.defaultLocation;
    }
}
