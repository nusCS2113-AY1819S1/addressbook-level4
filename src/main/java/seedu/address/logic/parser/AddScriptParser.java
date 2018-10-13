package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_WRONG_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AddScriptParser extends AddressBookParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses the String from the file for execution
     *
     * @param fullCommand which is the entire command in String
     * @return the command based on the user input
     * @throws ParseException if the fullCommand does not conform the expected format
     */
    public Command parseCommand(String fullCommand) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(fullCommand.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (commandWord.equals(AddCommand.COMMAND_WORD) || commandWord.equals(AddCommand.COMMAND_WORD_2)) {
            return new AddCommandParser().parse(arguments);
        }
        else {
            throw new ParseException(MESSAGE_WRONG_COMMAND);
        }
    }

}
