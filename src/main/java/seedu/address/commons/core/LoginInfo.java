package seedu.address.commons.core;
//@@author tianhang
import java.util.Objects;
public class LoginInfo {

    protected String userName;
    protected String password;
    protected String authenticationLevel;

    public LoginInfo(){

    }

    public void setPassword (String password) {
        this.password = password;
    }

    public LoginInfo (String userName, String authenticationLevel){
        this.userName = userName;
        this.authenticationLevel = authenticationLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName , password, authenticationLevel);
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append("userName : " + userName);
        sb.append ("\npassword : " + password);
        sb.append ("\nauthenticationLevel : " + authenticationLevel);
        return sb.toString();
    }

    public String getUserName () {
        return userName;
    }

    public String getPassword () {
        return password;
    }

    public String getAuthenticationLevel () {
        return authenticationLevel;
    }

}
