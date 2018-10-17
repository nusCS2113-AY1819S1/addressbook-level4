package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddScriptParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Adds multiple persons to the address book.
 */
public class AddScriptCommand extends Command {
    public static final String COMMAND_WORD = "addscript";
    public static final String COMMAND_WORD_2 = "as";

    public static final String TEXT_EXTENSION = ".txt";
    public static final String COMMA = ",";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Run multiple add commands based on the text file selected.\n"
            + "Parameters: TEXTFILE\n"
            + "Example: " + COMMAND_WORD + " StudentList\n"
            + "Example: " + COMMAND_WORD_2 + " Studentlist";

    public static final String MESSAGE_SUCCESS = "All persons from the text file %s has been added";
    public static final String MESSAGE_ADD_ERROR = "Line %s of %s cannot be executed";
    public static final String MESSAGE_UNABLE_TO_READ_FILE = "%s is not able to be read";
    public static final String MESSAGE_FILE_MISSING = "%s is not present in the folder";

    private String textFileName;
    private Path path;
    private AddScriptParser addScriptParser;


    public AddScriptCommand(String fileName) {
        requireNonNull(fileName);
        textFileName = fileName.replaceAll("^\\s+", "") + TEXT_EXTENSION;
        this.path = FileUtil.getPath(textFileName);
        this.addScriptParser = new AddScriptParser();
    }

    public AddScriptCommand(String folderName, String fileName) {
        requireNonNull(fileName);
        requireNonNull(folderName);
        textFileName = fileName.replaceAll("^\\s+", "") + TEXT_EXTENSION;
        this.path = FileUtil.getPath(folderName + textFileName);
        this.addScriptParser = new AddScriptParser();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        String multCommandError;

        if (!FileUtil.isFileExists(path)) {
            return new CommandResult(String.format(MESSAGE_FILE_MISSING, textFileName));
        }

        try {
            List<String> addCommands = FileUtil.readEachLineFromFile(path);
            multCommandError = executeMultipleCommand(addScriptParser, addCommands, model, history);
        } catch (IOException ioe) {
            return new CommandResult(String.format(MESSAGE_UNABLE_TO_READ_FILE, textFileName));
        }

        if (!multCommandError.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_ADD_ERROR, multCommandError , textFileName));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, textFileName));
    }

    /**
     * This Method will execute multiple add commands.
     */
    public String executeMultipleCommand(AddScriptParser addScriptParser, List<String> commands,
                                       Model model, CommandHistory history) {
        String lineNumbers = new String();
        for (String fullCommands : commands) {
            try {
                Command command = addScriptParser.parseCommand(fullCommands);
                command.execute(model, history);
            } catch (ParseException | CommandException pe) {
                lineNumbers = lineNumbers + (commands.indexOf(fullCommands) + 1) + COMMA;
            }
        }
        lineNumbers = lineNumbers.replaceAll(".$", "");
        return lineNumbers;
    }
}
