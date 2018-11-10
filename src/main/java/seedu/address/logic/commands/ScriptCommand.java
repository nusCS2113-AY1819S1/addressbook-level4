package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.script.CommandType;
import seedu.address.model.script.TextFile;
import seedu.address.storage.scripts.ScriptLog;

/**
 * Execute multiple same using a text file to the address book.
 */
public class ScriptCommand extends Command {

    public static final String COMMAND_WORD = "script";
    public static final String COMMAND_WORD_2 = "scr";

    public static final String TEXT_EXTENSION = ".txt";
    public static final String COMMA = ",";
    public static final String SPACE = " ";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Run multiple commands based on the text file selected.\n"
            + "Parameters: TEXTFILE CommandType\n"
            + "Example: " + COMMAND_WORD + " txt/StudentList c/add\n"
            + "Example: " + COMMAND_WORD_2 + " txt/Studentlist c/group";

    public static final String MESSAGE_SUCCESS = "All persons from the text file %s has been added";
    public static final String MESSAGE_EXECUTE_ERROR = "Line %s of %s cannot be executed";
    public static final String MESSAGE_FILE_MISSING = "%s is not present in the folder";
    public static final String MESSAGE_LOG_CANNOT_WRITE = "The log file cannot be written";

    private String projectLocation;
    private String textFileName;
    private CommandType commandType;

    public ScriptCommand(TextFile fileName, CommandType commandType) {
        requireNonNull(fileName);
        requireNonNull(commandType);

        textFileName = fileName.getTextFile() + TEXT_EXTENSION;
        this.projectLocation = FileUtil.getRootLocation();
        this.commandType = commandType;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        String multCommandError;
        String scriptFolderLocation = model.getScriptFolderLocation();
        Path scriptPath = FileUtil.getPath(projectLocation + scriptFolderLocation + textFileName);
        List<String> commandArguments;
        AddressBookParser scriptParser = new AddressBookParser();

        try {
            commandArguments = FileUtil.readEachLineFromFile(scriptPath);
        } catch (IOException ioe) {
            return new CommandResult(String.format(MESSAGE_FILE_MISSING, textFileName));
        }

        try {
            commandArguments.replaceAll(s -> commandType + SPACE + s);
            multCommandError = executeMultipleCommand(scriptParser, commandArguments, model, history);
        } catch (IOException ioe) {
            return new CommandResult(String.format(MESSAGE_LOG_CANNOT_WRITE, textFileName));
        }

        if (!multCommandError.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_EXECUTE_ERROR, multCommandError , textFileName));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, textFileName));
    }

    /**
     * This method will execute multiple commands.
     */
    public String executeMultipleCommand(AddressBookParser scriptParser, List<String> commandArguments,
                                         Model model, CommandHistory history) throws IOException {
        String errorMessage = new String();
        ScriptLog scriptLog = new ScriptLog(commandType, textFileName, model);
        for (String fullCommands : commandArguments) {
            try {
                Command command = scriptParser.parseCommand(fullCommands);
                command.execute(model, history);
            } catch (ParseException | CommandException pe) {
                String lineNumber = Integer.toString(commandArguments.indexOf(fullCommands) + 1);
                errorMessage = errorMessage + lineNumber + COMMA;
                scriptLog.write(lineNumber, fullCommands, pe.getMessage());
            }
        }
        errorMessage = errorMessage.replaceAll(".$", "");
        return errorMessage;
    }
}
