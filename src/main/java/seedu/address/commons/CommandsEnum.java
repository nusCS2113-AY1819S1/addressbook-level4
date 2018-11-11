package seedu.address.commons;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.AddTimeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteTimeCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FreeCommand;
import seedu.address.logic.commands.FriendCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RegisterCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.UiCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnfriendCommand;

/**
 * Containing Commands return type with their string representation, and method to check whether its allowed
 */
public enum CommandsEnum {
    HELP(HelpCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD_ALIAS),
    EXIT(ExitCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD_ALIAS),

    REGISTER(RegisterCommand.COMMAND_WORD, RegisterCommand.COMMAND_WORD_ALIAS),
    LOGIN(LoginCommand.COMMAND_WORD, LoginCommand.COMMAND_WORD_ALIAS),
    UI(UiCommand.COMMAND_WORD),

    LOGOUT(LogoutCommand.COMMAND_WORD, LogoutCommand.COMMAND_WORD_ALIAS),
    EDIT(EditCommand.COMMAND_WORD, EditCommand.COMMAND_WORD_ALIAS),
    FIND(FindCommand.COMMAND_WORD, FindCommand.COMMAND_WORD_ALIAS),
    LISTALL(ListCommand.COMMAND_WORD, ListCommand.COMMAND_WORD_ALIAS),
    HISTORY(HistoryCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD_ALIAS),
    UNDO(UndoCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD_ALIAS),
    REDO(RedoCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD_ALIAS),
    SELECT(SelectCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD_ALIAS),
    ADD(AddTimeCommand.COMMAND_WORD, AddTimeCommand.COMMAND_WORD_ALIAS),
    DELETE(DeleteTimeCommand.COMMAND_WORD, DeleteTimeCommand.COMMAND_WORD_ALIAS),
    FREE(FreeCommand.COMMAND_WORD, FreeCommand.COMMAND_WORD_ALIAS),
    CLEAR(ClearCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD_ALIAS),
    FRIEND(FriendCommand.COMMAND_WORD, FriendCommand.COMMAND_WORD_ALIAS),
    UNFRIEND(UnfriendCommand.COMMAND_WORD, UnfriendCommand.COMMAND_WORD_ALIAS),
    GROUP(TagCommand.COMMAND_WORD, TagCommand.COMMAND_WORD_ALIAS),
    IMPORT(ImportCommand.COMMAND_WORD, ImportCommand.COMMAND_WORD_ALIAS),
    EXPORT(ExportCommand.COMMAND_WORD, ExportCommand.COMMAND_WORD_ALIAS);


    private final List<String> values;

    CommandsEnum(String ...values) {
        this.values = Arrays.asList(values);
    }

    /**
     * Checks whether command is allowed in the current status
     * @param command Type of command
     * @param isLoggedIn Status of application
     * @return Authreturn type to determine pass or error messages
     */
    public AuthReturn isCommandAllowed(CommandsEnum command, boolean isLoggedIn) {

        switch(command) {
        case HELP:
        case EXIT:
            return AuthReturn.COMMAND_ALLOWED;

        case LOGIN:
        case REGISTER:
        case UI:
            if (isLoggedIn) {
                return AuthReturn.COMMAND_LOGOUTFIRST;
            } else {
                return AuthReturn.COMMAND_ALLOWED;
            }

        case LOGOUT:
        case EDIT:
        case FIND:
        case LISTALL:
        case HISTORY:
        case UNDO:
        case REDO:
        case SELECT:
        case ADD:
        case DELETE:
        case FREE:
        case CLEAR:
        case FRIEND:
        case UNFRIEND:
        case GROUP:
        case IMPORT:
        case EXPORT:
            if (isLoggedIn) {
                return AuthReturn.COMMAND_ALLOWED;
            } else {
                return AuthReturn.COMMAND_LOGINFIRST;
            }

        default:
            return AuthReturn.COMMAND_ERROR;
        }
    }

    public List<String> getValues() {
        return values;
    }

    /**
     * Finds the correct CommandsEnum type from a string
     * @param name String of the command whether it be full or alias
     * @return the CommandsEnum Type
     */
    public static CommandsEnum find(String name) {
        for (CommandsEnum commandsEnum : CommandsEnum.values()) {
            if (commandsEnum.getValues().contains(name)) {
                return commandsEnum;
            }
        }
        return null;
    }
}
