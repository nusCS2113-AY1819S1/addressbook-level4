//@@author lekoook
package seedu.address.model.autocomplete;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments of a command text input for auto completing the command
 */
public class AutoCompleteArgumentsParser {
    /**
     * Regex used for Pattern instance
     */
    private static final String argumentRegex = "["
            + PREFIX_NAME.getPrefix()
            + PREFIX_PHONE.getPrefix()
            + PREFIX_EMAIL.getPrefix()
            + PREFIX_ADDRESS.getPrefix()
            + PREFIX_TAG.getPrefix()
            + PREFIX_NOTE.getPrefix()
            + "]";
    /**
     * Pattern instance used to separate the arguments
     */
    private static final Pattern ARGS_INPUT_FORMAT = Pattern.compile(argumentRegex);

    /**
     * Default constructor
     */
    public AutoCompleteArgumentsParser() {}

    /**
     * Parses the arguments to be used for auto completing of commands
     * @param argsInput the full string of arguments to parse
     * @return the prefix that will be used for the prediction
     * @throws ParseException if the user input does not conform the expected format
     */
    public String parseArguments(String argsInput) throws ParseException {
        final Matcher matcher = ARGS_INPUT_FORMAT.matcher(argsInput.trim());


        return "argument parser test output";
    }
}
