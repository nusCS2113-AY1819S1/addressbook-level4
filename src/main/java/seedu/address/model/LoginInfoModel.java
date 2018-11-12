package seedu.address.model;

import java.util.ArrayList;

import seedu.address.commons.core.LoginInfo;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;

/**
 *  The API of the Login component.
 */
public interface LoginInfoModel {
    /**
     *  Returns {@code LoginInfo} according to username
     *  Returns null if no username matched
     */
    LoginInfo getLoginInfo(UserName userName);

    /**
     * Returns true if {@code String userName} is equal to Username in storage
     */
    boolean isUserNameExist(UserName userName);

    /**
     * Change password in the list with {@code userName} and {@code newHashedPassword}
     */
    void changePassword(UserName userName, Password newHashedPassword);

    /**
     * Delete according in the list contains{@code userName}
     */
    void deleteAccount(UserName userName);

    /**
     * Add in a {@code newAccount} to the list
     */
    void createNewAccount(LoginInfo newAccount);

    /**
     * Return loginInfoList
     * @return
     */
    ArrayList< LoginInfo > getLoginInfoList();
}
