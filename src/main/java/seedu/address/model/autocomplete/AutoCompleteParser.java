//@@author lekoook
package seedu.address.model.autocomplete;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the text input for auto completing the command
 */
public class AutoCompleteParser {
    /**
     * Pattern instance used to separate command and it's arguments
     */
    private static final Pattern COMMAND_INPUT_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private AutoCompleteArgumentsParser argumentsParser;

    /**
     * Default constructor
     */
    public AutoCompleteParser() {
        argumentsParser = new AutoCompleteArgumentsParser();
    }

    /**
     * TODO: Extend to other attributes
     * Parses the command to be used for auto completing of commands
     * @param textInput text to be parsed
     * @return a pair of values to be used to determine the Trie to use for auto complete
     * @throws ParseException if the user input does not conform the expected format
     */
    public AutoCompleteParserPair parseCommand(String textInput) {
        final Matcher matcher = COMMAND_INPUT_FORMAT.matcher(textInput);
        if (!matcher.matches()) {
            return new AutoCompleteParserPair(CommandCompleter.COMPLETE_INVALID, CommandCompleter.COMPLETE_INVALID);
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (arguments.isEmpty()) {
            return new AutoCompleteParserPair(CommandCompleter.COMPLETE_COMMAND, commandWord);
        }

        switch(commandWord) {
        case CliSyntax.COMMAND_FIND:
        case CliSyntax.COMMAND_SELECT:
            return new AutoCompleteParserPair(CommandCompleter.COMPLETE_NAME, arguments.trim());
        default:
            return new AutoCompleteParserPair(CommandCompleter.COMPLETE_INVALID, arguments.trim());
        }
    }
}
