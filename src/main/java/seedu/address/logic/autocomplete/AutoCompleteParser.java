package seedu.address.logic.autocomplete;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses the text input for auto completing the command
 */
public class AutoCompleteParser {
    /**
     * Pattern instance used to separate command and it's arguments
     */
    private static final Pattern COMMAND_INPUT_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    public String parseCommand(String textInput) throws ParseException{
        final Matcher matcher = COMMAND_INPUT_FORMAT.matcher(textInput);
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (arguments.isEmpty()) {
            return CommandCompleter.COMPLETE_COMMAND;
        } else {

        }

        return arguments;
    }
}
