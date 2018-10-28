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

    public LoginInfo (UserName userName, AuthenticationLevel authenticationLevel) {
        this.userName = userName;
        this.authenticationLevel = authenticationLevel;
    }
    public LoginInfo (UserName userName, Password password, AuthenticationLevel authenticationLevel) {
        this.userName = userName;
        this.password = password;
        this.authenticationLevel = authenticationLevel;
    }

    public void setPassword (Password password) {
        this.password = password;
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

    public String getUserNameString () {
        return userName.toString ();
    }
    public String getPasswordString () {
        return password.toString ();
    }

    public String getAuthenticationLevelString () {
        return authenticationLevel.toString ();
    }

    public AuthenticationLevel getAuthenticationLevel () {
        return authenticationLevel;
    }

    public UserName getUserName(){
        return userName;
    }
    /**
     * Returns true is {@code test} is equal to {@code userName}
     */
    public boolean isUserNameMatched(UserName test){
        if (userName.equals (test)){
            return true;
        }
        return false;
    }

}
