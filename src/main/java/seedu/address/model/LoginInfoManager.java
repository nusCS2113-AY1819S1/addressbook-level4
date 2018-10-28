package seedu.address.model;

import java.util.ArrayList;
import java.util.Objects;

import seedu.address.commons.core.LoginInfo;
import seedu.address.model.user.AuthenticationLevel;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;


/**
 * Represents the facade of loginInfo
 */
public class LoginInfoManager {
    private ArrayList< LoginInfo > loginInfoList;

    public LoginInfoManager () {
        UserName userName = new UserName ("tester");
        Password password = new Password ("Gcf70h4aWQ1T9NMxE03XM3nq3nCmFGihnO4xMzHMgP0=");
        AuthenticationLevel authenticationLevel = new AuthenticationLevel ("ADMIN");
        LoginInfo loginInfo = new LoginInfo (userName, password, authenticationLevel);
        this.loginInfoList = new ArrayList<>();
        loginInfoList.add (loginInfo);
    }

    public LoginInfo getLoginInfo(UserName userName) {
        for (LoginInfo loginInfo : loginInfoList) {
            if (loginInfo.isUserNameMatched (userName)) {
                return loginInfo;
            }
        }
        return null;
    }

    /**
     * Returns true if {@code String userName} is equal to Username in storage
     * @param userName
     * @return
     */
    public boolean isUserNameExist(UserName userName) {
        LoginInfo user = getLoginInfo (userName);
        if (user == null) {
            return false;
        }
        return true;
    }
    /**
     * Change password in the list with {@code userName} and {@code newHashedPassword}
     */
    public void changePassword(UserName userName, Password newHashedPassword) {
        for (int i = 0; i < loginInfoList.size (); i++) {
            if (loginInfoList.get (i).isUserNameMatched(userName)) {
                loginInfoList.get (i).setPassword (newHashedPassword);
            }
        }
    }
    /**
     * Delete according in the list contains{@code userName}
     */
    public void deleteAccount(UserName userName){
        for (int i = 0; i < loginInfoList.size (); i++) {
            if (loginInfoList.get (i).isUserNameMatched(userName)) {
                loginInfoList.remove (i);
            }
        }
    }
    //    private boolean checkUserName (LoginInfo listItem, String userNameWanted){
    //        if (listItem.getUserNameString ().equals (userNameWanted)){
    //            return true;
    //        }
    //        return false;
    //    }

    /**
     * Add in a new account to the list
     * @param userName The username of the account
     * @param password The Password of the account
     * @param authenticationLevel  the authentication level of the account
     */
    public void createNewAccount(UserName userName, Password password, AuthenticationLevel authenticationLevel) {
        LoginInfo newAccount = new LoginInfo (userName, password, authenticationLevel);
        loginInfoList.add (newAccount);
    }

    public ArrayList< LoginInfo > getLoginInfoList() {
        return loginInfoList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(loginInfoList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (LoginInfo loginInfo: loginInfoList) {

            sb.append("\nuserName : " + loginInfo.getUserNameString ());
            sb.append ("\npassword : " + loginInfo.getPasswordString ());
            sb.append ("\nauthenticationLevel : " + loginInfo.getAuthenticationLevelString ());
        }
        return sb.toString();
    }

}
