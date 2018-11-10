package seedu.address.logic.commands;

import seedu.address.model.request.DeleteRequestCommand;
import seedu.address.model.request.RequestCommand;
import seedu.address.model.request.ViewRequestCommand;

/**
 * A static class to return syntax and message usage of command words
 */
public class CommandSyntax {

    public static String getSyntax(String commandWord) {
        switch(commandWord) {
        case AddCommand.COMMAND_WORD:
            return AddCommand.COMMAND_SYNTAX;
        case CheckCommand.COMMAND_WORD:
            return CheckCommand.COMMAND_WORD;
        case ClearCommand.COMMAND_WORD:
            return ClearCommand.COMMAND_WORD;
        case DeleteCommand.COMMAND_WORD:
            return DeleteCommand.COMMAND_SYNTAX;
        case DeleteRequestCommand.COMMAND_WORD:
            return DeleteRequestCommand.COMMAND_WORD;
        case EditCommand.COMMAND_WORD:
            return EditCommand.COMMAND_SYNTAX;
        case ExitCommand.COMMAND_WORD:
            return ExitCommand.COMMAND_WORD;
        case FindCommand.COMMAND_WORD:
            return FindCommand.COMMAND_WORD;
        case HelpCommand.COMMAND_WORD:
            return HelpCommand.COMMAND_WORD;
        case HistoryCommand.COMMAND_WORD:
            return HistoryCommand.COMMAND_WORD;
        case ListCommand.COMMAND_WORD:
            return ListCommand.COMMAND_WORD;
        case RedoCommand.COMMAND_WORD:
            return RedoCommand.COMMAND_WORD;
        case RequestCommand.COMMAND_WORD:
            return RequestCommand.COMMAND_SYNTAX;
        case SelectCommand.COMMAND_WORD:
            return SelectCommand.COMMAND_WORD;
        case SellCommand.COMMAND_WORD:
            return SellCommand.COMMAND_SYNTAX;
        case StockCommand.COMMAND_WORD:
            return StockCommand.COMMAND_SYNTAX;
        case UndoCommand.COMMAND_WORD:
            return UndoCommand.COMMAND_WORD;
        case ViewRequestCommand.COMMAND_WORD:
            return ViewRequestCommand.COMMAND_WORD;
        case ViewStatisticCommand.COMMAND_WORD:
            return ViewStatisticCommand.COMMAND_WORD;
        default:
            return "";
        }
    }

    public static String getMessage(String commandWord) {
        switch(commandWord) {
        case AddCommand.COMMAND_WORD:
            return AddCommand.MESSAGE_USAGE;
        case CheckCommand.COMMAND_WORD:
            return CheckCommand.MESSAGE_USAGE;
        case ClearCommand.COMMAND_WORD:
            return "";
        case DeleteCommand.COMMAND_WORD:
            return DeleteCommand.MESSAGE_USAGE;
        case DeleteRequestCommand.COMMAND_WORD:
            return DeleteCommand.MESSAGE_USAGE;
        case EditCommand.COMMAND_WORD:
            return EditCommand.MESSAGE_USAGE;
        case ExitCommand.COMMAND_WORD:
            return "";
        case FindCommand.COMMAND_WORD:
            return FindCommand.MESSAGE_USAGE;
        case HelpCommand.COMMAND_WORD:
            return "";
        case HistoryCommand.COMMAND_WORD:
            return "";
        case ListCommand.COMMAND_WORD:
            return "";
        case RedoCommand.COMMAND_WORD:
            return "";
        case RequestCommand.COMMAND_WORD:
            return RequestCommand.MESSAGE_USAGE;
        case SelectCommand.COMMAND_WORD:
            return SelectCommand.MESSAGE_USAGE;
        case SellCommand.COMMAND_WORD:
            return SellCommand.MESSAGE_USAGE;
        case StockCommand.COMMAND_WORD:
            return StockCommand.MESSAGE_USAGE;
        case UndoCommand.COMMAND_WORD:
            return "";
        case ViewRequestCommand.COMMAND_WORD:
            return "";
        case ViewStatisticCommand.COMMAND_WORD:
            return "";
        default:
            return "";
        }
    }
}
