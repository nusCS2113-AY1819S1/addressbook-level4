package seedu.address.commons.loginAuthentication;

import seedu.address.logic.commands.*;
import seedu.address.logic.commands.user.ChangePasswordCommand;
import seedu.address.logic.commands.user.CreateUserCommand;

/**
 * this stores the authentication level and command available for each level
 */
public enum AuthenticationLevel {
    ADMIN (new String[] {AddCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD, EditCommand.COMMAND_WORD,
                    ExitCommand.COMMAND_WORD, FindCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD,
                    ListCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD,
                    ChangePasswordCommand.COMMAND_WORD, CreateUserCommand.COMMAND_WORD}),
    MANAGER (new String[] {"add", "clear", "other"}),
    ACCOUNTANT (new String[] {"add", "clear"}),
    STOCK_TAKER (new String[] {"add", "clear"});

    private String[] authenticationLevel;

    AuthenticationLevel(String[] authenticationLevel){
        this.authenticationLevel = authenticationLevel;
    }

    /**
     * Return an array command available for each level
     * @param level
     * @return command that is allow for
     */
    public String[] commandAvailable(String level) {
        if (level.equals ( "ADMIN")) {
            return AuthenticationLevel.ADMIN.authenticationLevel;
        } else if (level.equals ("STOCK_TAKER")){
            return AuthenticationLevel.STOCK_TAKER.authenticationLevel;
        } else if (level.equals ("ACCOUNTANT")){
            return AuthenticationLevel.ACCOUNTANT.authenticationLevel;
        } else if (level.equals ("MANAGER")){
            return AuthenticationLevel.MANAGER.authenticationLevel;
        }
        return new String[0];
    }

}
