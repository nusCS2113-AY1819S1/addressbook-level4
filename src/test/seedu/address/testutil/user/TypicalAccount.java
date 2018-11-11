//@@author liu-tianhang
package seedu.address.testutil.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.LoginInfo;
import seedu.address.model.LoginInfoManager;
import seedu.address.model.user.AuthenticationLevel;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;

/**
 * A utility class containing {@code LoginInfo} objects to be used in tests.
 */
public class TypicalAccount {
    public static final LoginInfo ADMIN_ACCOUNT = new AccountBuilder ().withUserName (new UserName ("tester"))
                                                    .withPassword (new Password (
                                                            "Gcf70h4aWQ1T9NMxE03XM3nq3nCmFGihnO4xMzHMgP0="))
                                                    .withAuthenticationLevel (
                                                            new AuthenticationLevel ("ADMIN")).build ();
    public static final LoginInfo MANAGER_ACCOUNT = new AccountBuilder ()
                                                    .withUserName (new UserName ("manager"))
                                                    .withPassword (new Password (
                                                            "Gcf70h4aWQ1T9NMxE03XM3nq3nCmFGihnO4xMzHMgP0="))
                                                    .withAuthenticationLevel (
                                                            new AuthenticationLevel ("MANAGER")).build ();
    public static final LoginInfo STOCK_TAKER_ACCOUNT = new AccountBuilder ().withUserName (new UserName ("stocktaker"))
                                                    .withPassword (new Password (
                                                            "Gcf70h4aWQ1T9NMxE03XM3nq3nCmFGihnO4xMzHMgP0="))
                                                    .withAuthenticationLevel (
                                                            new AuthenticationLevel ("STOCKTAKER")).build ();
    public static final LoginInfo ACCOUNTANT_ACCOUNT = new AccountBuilder ().withUserName (new UserName ("accountant"))
                                                    .withPassword (new Password (
                                                            "Gcf70h4aWQ1T9NMxE03XM3nq3nCmFGihnO4xMzHMgP0="))
                                                    .withAuthenticationLevel (
                                                            new AuthenticationLevel ("ACCOUNTANT")).build ();
    private TypicalAccount(){}


    public static LoginInfoManager getTypicalSingleAccount() {
        ArrayList< LoginInfo > loginInfoList = new ArrayList <> ();
        loginInfoList.add (ADMIN_ACCOUNT);
        return new LoginInfoManager (loginInfoList);
    }
    public static List<LoginInfo> getTypicalAccount() {
        return new ArrayList<> (Arrays.asList(ADMIN_ACCOUNT , MANAGER_ACCOUNT ,
                                                STOCK_TAKER_ACCOUNT , ACCOUNTANT_ACCOUNT));
    }
}
