package seedu.address.storage.scripts;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.Model;
import seedu.address.model.script.CommandType;

/**
 * Create the log file and write commands that cannot be executed.
 */
public class ScriptLog {
    public static final String NEXT_LINE = System.lineSeparator();

    private static final String UNDERSCORE = "_";
    private static final String COLON = ":";
    private static final String LINE = "Line ";


    private String logFolder;
    private String logName;
    private File logFile;
    private String scriptFolderLocation;

    public ScriptLog(CommandType commandType, String textFile, Model model) {
        this.logFolder = ScriptSetup.DEFAULT_LOGS_FOLDER;
        this.logName = commandType.value + UNDERSCORE + textFile;
        this.scriptFolderLocation = model.getScriptFolderLocation();

        File file = new File(FileUtil.getRootLocation() + scriptFolderLocation + logFolder + logName);
        if (file.exists()) {
            file.delete();
        }
        this.logFile = new File(FileUtil.getRootLocation() + scriptFolderLocation + logFolder + logName);
    }

    /**
     * Create the log file and write commands that cannot be executed.
     * @ lineNumber is the line number that cannot be executed
     * @ errorMessage is the error message that return for not being able to execute the message
     */
    public void write(String lineNumber, String errorMessage) throws IOException {
        lineNumber = LINE + lineNumber + COLON;
        FileUtil.appendToTextFile(logFile, lineNumber + NEXT_LINE);
        FileUtil.appendToTextFile(logFile, errorMessage + NEXT_LINE);
    }



}
