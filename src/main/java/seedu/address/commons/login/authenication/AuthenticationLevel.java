package seedu.address.commons.login.authenication;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddItemCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SellCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.user.ChangePasswordCommand;
import seedu.address.logic.commands.user.CreateUserCommand;

/**
 * this stores the authentication level and command available for each level
 */
public enum AuthenticationLevel {
    ACCOUNTANT (new String[] {"add" , "clear"}),
    ADMIN (new String[] {AddCommand.COMMAND_WORD , ClearCommand.COMMAND_WORD , DeleteCommand.COMMAND_WORD ,
                            EditCommand.COMMAND_WORD , ExitCommand.COMMAND_WORD , FindCommand.COMMAND_WORD ,
                            HelpCommand.COMMAND_WORD , HistoryCommand.COMMAND_WORD , ListCommand.COMMAND_WORD ,
                            RedoCommand.COMMAND_WORD , SelectCommand.COMMAND_WORD , UndoCommand.COMMAND_WORD ,
                            ChangePasswordCommand.COMMAND_WORD , CreateUserCommand.COMMAND_WORD,
                            AddItemCommand.COMMAND_WORD, SellCommand.COMMAND_WORD}),
    MANAGER (new String[] {AddItemCommand.COMMAND_WORD, CreateUserCommand.COMMAND_WORD,
                           ChangePasswordCommand.COMMAND_WORD }),
    STOCK_TAKER (new String[] {SellCommand.COMMAND_WORD});

    private String[] authenticationLevel;

    AuthenticationLevel (String[] authenticationLevel) {
        this.authenticationLevel = authenticationLevel;
    }

    /**
     * Return an array command available for each level
     * @param level
     * @return command that is allow for
     */
    public String[] commandAvailable (String level) {
        if (level.equals ("ADMIN")) {
            return AuthenticationLevel.ADMIN.authenticationLevel;
        } else if (level.equals ("STOCK_TAKER")) {
            return AuthenticationLevel.STOCK_TAKER.authenticationLevel;
        } else if (level.equals ("ACCOUNTANT")) {
            return AuthenticationLevel.ACCOUNTANT.authenticationLevel;
        } else if (level.equals ("MANAGER")) {
            return AuthenticationLevel.MANAGER.authenticationLevel;
        }
        return new String[0];
    }

}
