package seedu.address.logic.parser;

import static unrefactored.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandParser;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class TaskBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private List<CommandParser> commands;

    /**
     * Takes in
     * @param commandsUsed and keeps them in an
     * array {@code commands} (to enforce OCP)
     */
    public TaskBookParser(CommandParser... commandsUsed) {
        commands = new ArrayList<>();
        for (CommandParser command: commandsUsed) {
            commands.add(command);
        }
    }
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        //@@author emobeany
        /*case SelectDeadlineCommand.COMMAND_WORD:
            return new SelectDeadlineCommandParser().parse(arguments);
         */
        Command commandToReturn = null;
        for (CommandParser command : commands) {
            if (command.getCommandWord().equals(commandWord)) {
                 commandToReturn = command.parse(arguments);
                 break;
            }
        }
        // JUNIT: test commandToReturn != null
        return commandToReturn;
    }

}
