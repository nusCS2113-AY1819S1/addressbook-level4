package seedu.address.logic.parser.validation.check;

import seedu.address.commons.core.CurrentUser;
import seedu.address.commons.core.LoginInfo;
import seedu.address.commons.login.authenication.AuthenticationLevel;
import seedu.address.logic.commands.UnAuthorisedCommand;

/**
 * Class for checking use authentication level for right command
 */
public class UserAuthenticationCheckUtils implements UserAuthenticationCheck {
    private String commandWord;

    public UserAuthenticationCheckUtils(String commandWord) {
        this.commandWord = commandWord;
    }

    /**
     * Check For Authentication
     * @return same command if passed
     * else return UnAuthorisedCommand.COMMAND_WORD
     */
    public String checkAuthentication() {
        LoginInfo currentUser = CurrentUser.getCurrentUser ();
        if (isAuthenticationValid (currentUser)) {
            return commandWord;
        }
        //pass in relevant unAuthorised command
        UnAuthorisedCommand unAuthorisedCommand = new UnAuthorisedCommand ();
        unAuthorisedCommand.setCommandWord (commandWord);
        return UnAuthorisedCommand.COMMAND_WORD;
    }
    @Override
    public boolean isAuthenticationValid (LoginInfo loginInfo) {
        for (String commandAllow : AuthenticationLevel.ADMIN.commandAvailable ("ADMIN")) {
            if (commandAllow.equals (commandWord)) {
                return true;
            }
        }
        return false;
    }
}
