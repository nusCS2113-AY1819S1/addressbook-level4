package seedu.address.model;

import java.util.ArrayList;
import java.util.Objects;

import seedu.address.commons.core.LoginInfo;


/**
 * Represents Login information such as userName,password and authentication right
 */
public class LoginInfoList {
    private ArrayList< LoginInfo > loginInfoList;

    public LoginInfoList (){
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
