package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static com.t13g2.forum.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.t13g2.forum.logic.commands.AddCommand;
import com.t13g2.forum.logic.commands.AddUserCommand;
import com.t13g2.forum.logic.commands.AnnounceCommand;
import com.t13g2.forum.logic.commands.BlockUserFromCreatingCommand;
import com.t13g2.forum.logic.commands.CheckAnnouncmentCommand;
import com.t13g2.forum.logic.commands.ClearCommand;
import com.t13g2.forum.logic.commands.Command;
import com.t13g2.forum.logic.commands.CreateModuleCommand;
import com.t13g2.forum.logic.commands.CreateThreadCommand;
import com.t13g2.forum.logic.commands.DeleteCommand;
import com.t13g2.forum.logic.commands.DeleteThreadCommand;
import com.t13g2.forum.logic.commands.EditCommand;
import com.t13g2.forum.logic.commands.ExitCommand;
import com.t13g2.forum.logic.commands.FindCommand;
import com.t13g2.forum.logic.commands.HelpCommand;
import com.t13g2.forum.logic.commands.HistoryCommand;
import com.t13g2.forum.logic.commands.ListCommand;
import com.t13g2.forum.logic.commands.ListModuleCommand;
import com.t13g2.forum.logic.commands.LoginCommand;
import com.t13g2.forum.logic.commands.RedoCommand;
import com.t13g2.forum.logic.commands.SelectCommand;
import com.t13g2.forum.logic.commands.SelectModuleCommand;
import com.t13g2.forum.logic.commands.SelectThreadCommand;
import com.t13g2.forum.logic.commands.SetAdminCommand;
import com.t13g2.forum.logic.commands.UndoCommand;
import com.t13g2.forum.logic.commands.UpdateThreadCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

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
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

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

        case AnnounceCommand.COMMAND_WORD:
            return new AnnounceCommandParser().parse(arguments);

        case CheckAnnouncmentCommand.COMMAND_WORD:
            return new CheckAnnouncmentCommand();

        case BlockUserFromCreatingCommand.COMMAND_WORD:
            return new BlockUserFromPostingCommandParser().parse(arguments);

        case SetAdminCommand.COMMAND_WORD:
            return new SetAdminCommandParser().parse(arguments);

        case CreateModuleCommand.COMMAND_WORD:
            return new CreateModuleCommandParser().parse(arguments);

        case CreateThreadCommand.COMMAND_WORD:
            return new CreateThreadCommandParser().parse(arguments);

        case UpdateThreadCommand.COMMAND_WORD:
            return new UpdateThreadCommandParser().parse(arguments);

        case DeleteThreadCommand.COMMAND_WORD:
            return new DeleteThreadCommandParser().parse(arguments);

        case ListModuleCommand.COMMAND_WORD:
            return new ListModuleCommand();

        case SelectModuleCommand.COMMAND_WORD:
            return new SelectModuleCommandParser().parse(arguments);

        case SelectThreadCommand.COMMAND_WORD:
            return new SelectThreadCommandParser().parse(arguments);

        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case AddUserCommand.COMMAND_WORD:
            return new AddUserCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
