package seedu.address.storage.scripts;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FileUtil;

/**
 * Load the and add default scripts into in the default directory of the Application.
 */
public class ScriptSetup {
    private static final Logger logger = LogsCenter.getLogger(ScriptSetup.class);

    private static final String DIR_SAMPLE_TEXT = "/src/main/resources/defaultScripts";

    private String defaultLocation;

    public ScriptSetup() {
        this.defaultLocation = FileUtil.getRootLocation();
    }

    /**
     * Creates a script folder and add some sample text files if folder is missing.
     * @param scriptFolder is the directory of the script folder
     */
    public void execute(String scriptFolder) {
        Path scriptPath = FileUtil.getPath(defaultLocation + scriptFolder);
        try {
            boolean isScriptFolderPresent = FileUtil.createFolder(scriptPath);
            if (!isScriptFolderPresent) {
                addSampleTextFiles(scriptFolder);
            }
        } catch (IOException ioe) {
            logger.info(scriptPath + " is not a valid directory "
                    + "and a default script folder will be automatically generated");
        }

    }

    /**
     * Add some sample text files
     */
    public void addSampleTextFiles(String newFileDir) {
        File source = new File(defaultLocation + DIR_SAMPLE_TEXT);
        File destination = new File(defaultLocation + newFileDir);
        try {
            FileUtil.copyFile(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
