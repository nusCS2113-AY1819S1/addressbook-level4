package seedu.address.commons.core;

import java.util.Objects;

import seedu.address.model.user.AuthenticationLevel;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;

/**
 * This contains the login information of user
 */
public class LoginInfo {

    protected UserName userName;
    protected Password password;
    protected AuthenticationLevel authenticationLevel;

    public LoginInfo(){}
    public LoginInfo (String userName, String authenticationLevel) {
        this.userName = new UserName (userName);
        this.authenticationLevel = new AuthenticationLevel (authenticationLevel);
    }
    public LoginInfo (UserName userName, Password password, AuthenticationLevel authenticationLevel) {
        this.userName = userName;
        this.password = password;
        this.authenticationLevel = authenticationLevel;
    }

    public void setPassword (String password) {
        this.password = new Password (password);
    }


    @Override
    public int hashCode() {
        return Objects.hash(userName , password, authenticationLevel);
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append("userName : " + userName.toString ());
        sb.append ("\npassword : " + password.toString ());
        sb.append ("\nauthenticationLevel : " + authenticationLevel.toString ());
        return sb.toString();
    }

    public String getUserName () {
        return userName.toString ();
    }

    public String getPassword () {
        return password.toString ();
    }

    public String getAuthenticationLevel () {
        return authenticationLevel.toString ();
    }

}
