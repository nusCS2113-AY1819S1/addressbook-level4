package seedu.address.logic.commands;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class AddScriptCommand extends Command {
    public static final String COMMAND_WORD = "addscript";
    public static final String COMMAND_WORD_2 = "as";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Run multiple add commands based on the text file selected.\n"
            + "Parameters: TEXTFILE\n"
            + "Example: " + COMMAND_WORD + " StudentList.txt\n"
            + "Example: " + COMMAND_WORD_2  + " Studentlist.txt";

    public static final String MESSAGE_SUCCESS = "All persons from the text file %s has been added";
    public static final String MESSAGE_UNABLE_TO_READ_FILE = "%s is not able to be read";
    public static final String MESSAGE_ADD_COMMAND_FAIL = "Add Command is not valid";

    private String textFileName;
    private Path path;

    public AddScriptCommand(String fileName) {
        requireNonNull(fileName);
        textFileName = fileName.replaceAll("^\\s+","");
        this.path = FileUtil.getPath(textFileName);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        AddressBookParser scriptParser = new AddressBookParser();
        List<String> addCommands = new ArrayList<>();
        try (Stream<String> stream = Files.lines(path,Charset.defaultCharset())) {
            stream.forEachOrdered(addCommands::add);
            for(String s : addCommands) {
                Command command = scriptParser.parseCommand(s);
                CommandResult commandResult = command.execute(model,history);
            }
        } catch (IOException ioe) {
            return new CommandResult(String.format(MESSAGE_UNABLE_TO_READ_FILE, textFileName));
        } catch (ParseException pe) {
            return new CommandResult(MESSAGE_ADD_COMMAND_FAIL);
        }  catch (CommandException ce) {
            return new CommandResult(MESSAGE_ADD_COMMAND_FAIL);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, textFileName));
    }
}
