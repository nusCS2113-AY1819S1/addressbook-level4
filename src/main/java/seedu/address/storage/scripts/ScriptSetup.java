package seedu.address.storage.scripts;

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

    private String defaultLocation;

    public ScriptSetup() {
        this.defaultLocation = FileUtil.getRootLocation();
    }
    /**
     * Creates a script folder if folder is missing.
     */
    public void execute(String scriptFolder) {
        Path scriptPath = FileUtil.getPath(defaultLocation + scriptFolder);
        try {
            boolean isScriptFolderPresent = FileUtil.createFolder(scriptPath);
            System.out.println(isScriptFolderPresent);
        } catch (IOException ioe) {
            logger.info(scriptFolder + " is not a valid directory "
                    + "and a default script folder will be automatically generated");
        }
    }
}
