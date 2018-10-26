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
        loginInfoList = null;
    }

    public LoginInfo getLoginInfo(String userName) {
        for (LoginInfo loginInfo : loginInfoList) {
            if (loginInfo.getUserName ().equals (userName)) {
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
    public boolean isUserNameExist(String userName) {
        LoginInfo user = getLoginInfo (userName);
        if (user == null) {
            return false;
        }
        return true;
    }
    /**
     * Change password in the list with {@code userName} and {@code newHashedPassword}
     */
    public void changePassword(String userName, String newHashedPassword) {
        for (int i = 0; i < loginInfoList.size (); i++) {
            if (loginInfoList.get (i).getUserName ().equals (userName)) {
                loginInfoList.get (i).setPassword (newHashedPassword);
            }
        }
    }
    //    private boolean checkUserName (LoginInfo listItem, String userNameWanted){
    //        if (listItem.getUserName ().equals (userNameWanted)){
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

            sb.append("\nuserName : " + loginInfo.getUserName ());
            sb.append ("\npassword : " + loginInfo.getPassword ());
            sb.append ("\nauthenticationLevel : " + loginInfo.getAuthenticationLevel ());
        }
        return sb.toString();
    }

}
