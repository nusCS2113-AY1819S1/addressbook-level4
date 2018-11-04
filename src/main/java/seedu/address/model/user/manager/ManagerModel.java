package seedu.address.model.user.manager;

import seedu.address.model.Model;
import seedu.address.model.user.AuthenticationLevel;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;

/**
 * The interface for ManagerModel which control the api for Manager role
 */
public interface ManagerModel extends Model {
    //=========== Login feature command========================================

    /**
     * Create account based on {@code userName} {@code password} {@code authenticationLevel}
     */
    void createNewAccount(UserName userName, Password password, AuthenticationLevel authenticationLevel);

    /**
     * Delete a account based on the name
     */
    void deleteAccount(UserName userName);
}
