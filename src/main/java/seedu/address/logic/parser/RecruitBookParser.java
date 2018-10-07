package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.LogicManager;
import seedu.address.logic.LogicState;
import seedu.address.logic.commands.*;

import seedu.address.logic.commands.EmailCommand.EmailInitialiseCommand;
import seedu.address.logic.commands.EmailCommand.EmailSelectContentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class RecruitBookParser {

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
    public Command parseCommand(String userInput, LogicState state) throws ParseException {
        if (state.nextCommand != "primary") {
            switch(state.nextCommand)   {

            case AddJobDetailsCommand.COMMAND_WORD:
                return new AddJobDetailsCommandParser().parse(userInput);
            default:
                LogicManager.setLogicState("primary");
            }
        }

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCandidateCommand.COMMAND_WORD:
            return new AddCandidateCommandParser().parse(arguments);

        case AddJobCommand.COMMAND_WORD:
            return new AddJobCommand();

        case EditCandidateCommand.COMMAND_WORD:
            return new EditCandidateCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCandidateBookCommand.COMMAND_WORD:
            return new ClearCandidateBookCommand();

        case ClearJobBookCommand.COMMAND_WORD:
            return new ClearJobBookCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case EmailInitialiseCommand.COMMAND_WORD:
            return new EmailInitialiseCommand();

        case EmailSelectContentsCommand.COMMAND_WORD:
            return new EmailSelectContentsCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }


}
