package seedu.address.request.requestparser;

import static seedu.address.commons.core.Messages.MESSAGE_ACCESS_DENIED;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_SIMILARITY_FOUND;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.Role;

import seedu.address.logic.commands.CommandList;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.SimilarityParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.request.requestcommands.CommandSecondary;
import seedu.address.request.requestcommands.DeleteRequestCommand;
import seedu.address.request.requestcommands.DeleteRequestCommandParser;
import seedu.address.request.requestcommands.RedoRequestCommand;
import seedu.address.request.requestcommands.RequestCommand;
import seedu.address.request.requestcommands.RequestCommandParser;
import seedu.address.request.requestcommands.ToggleRequestCommand;
import seedu.address.request.requestcommands.UndoRequestCommand;




/**
 * Parses user input.
 */
public class RequestListParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public CommandSecondary parseCommandRequest(String userInput) throws ParseException {
        SimilarityParser similarityParser = new SimilarityParser();
        CommandList commandList = new CommandList();
        CommandSecondary finalCommand;
        final String finalCommandWord;
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }


        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        case RequestCommand.COMMAND_WORD:
            finalCommandWord = RequestCommand.COMMAND_WORD;
            finalCommand = new RequestCommandParser().parse(arguments);
            break;
        case ToggleRequestCommand.COMMAND_WORD:
            finalCommandWord = ToggleRequestCommand.COMMAND_WORD;
            finalCommand = new ToggleRequestCommand();
            break;
        case DeleteRequestCommand.COMMAND_WORD:
            finalCommandWord = DeleteRequestCommand.COMMAND_WORD;
            finalCommand = new DeleteRequestCommandParser().parse(arguments);
            break;
        case UndoRequestCommand.COMMAND_WORD:
            finalCommandWord = UndoRequestCommand.COMMAND_WORD;
            finalCommand = new UndoRequestCommand();
            break;
        case RedoRequestCommand.COMMAND_WORD:
            finalCommandWord = RedoRequestCommand.COMMAND_WORD;
            finalCommand = new RedoRequestCommand();
            break;
        default: {
            {
                String similarCommandFound = similarityParser
                        .performSimilarityCheck(commandWord, commandList.getCommandList());
                if (!similarCommandFound.isEmpty()) {
                    throw new ParseException(MESSAGE_SIMILARITY_FOUND + similarCommandFound + "?");
                }
            }
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
        }
        if (finalCommand != null) {
            try {
                Role.checkAccess(finalCommandWord);
            } catch (IllegalAccessException e) {
                throw new ParseException(MESSAGE_ACCESS_DENIED + finalCommandWord + ".");
            }
            return finalCommand;
        }
        return null;
    }
}
