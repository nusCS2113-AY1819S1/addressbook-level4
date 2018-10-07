package seedu.address.model;

import java.util.ArrayList;
import java.util.Objects;

import seedu.address.commons.core.LoginInfo;


/**
 * Represents Login information such as userName,password and authentication right
 */
public class LoginInfoManager {
    private ArrayList< LoginInfo > loginInfoList;

    public LoginInfoManager (){
        loginInfoList = null;
    }
    public LoginInfo getLoginInfo(String userName){
        for (LoginInfo loginInfo : loginInfoList){
            if (loginInfo.getUserName ().equals (userName)){
               return loginInfo;
            }
        }
        return new LoginInfo ();
    }
    public void changePassword(String userName, String newHashedPassword){
        for (int i= 0; i< loginInfoList.size (); i++){
            loginInfoList.get (i).setPassword (newHashedPassword);
        }
    }

    public void createNewAccount(String userName, String password, String authenticationLevel){
        LoginInfo newAccount = new LoginInfo (userName, password, authenticationLevel);
        loginInfoList.add (newAccount);
    }

    public ArrayList< LoginInfo > getLoginInfoList(){
        return loginInfoList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(loginInfoList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (LoginInfo loginInfo: loginInfoList){

            sb.append("\nuserName : " + loginInfo.getUserName ());
            sb.append ("\npassword : " + loginInfo.getPassword ());
            sb.append ("\nauthenticationLevel" + loginInfo.getAuthenticationLevel ());
        }
        return sb.toString();
    }

}
