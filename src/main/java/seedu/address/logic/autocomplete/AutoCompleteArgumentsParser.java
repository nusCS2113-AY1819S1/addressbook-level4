package seedu.address.logic.autocomplete;

import seedu.address.logic.parser.exceptions.ParseException;

import java.util.regex.Pattern;

/**
 * Parses the arguments of a command text input for auto completing the command
 */
public class AutoCompleteArgumentsParser {
    /**
     * Pattern instance used to separate the arguments
     */
    private static final Pattern COMMAND_INPUT_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Default constructor
     */
    public AutoCompleteArgumentsParser() {}

    public String parseArguments(String argsInput) throws ParseException {
        return "argument parser test output";
    }
}
