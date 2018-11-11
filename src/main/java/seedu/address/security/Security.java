package seedu.address.security;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.User;

/***
 * API of Security Component
 */
public interface Security {

    /**
     * Login a user with associated password
     * @param username Username
     * @param password Password
     */
    void login(String username, String password);

    /**
     * Logs out
     */
    void logout();

    /**
     * Registers the user
     * Returns 1 if successful, 2 if user exists, 3 if the fields are incomplete
     */
    RegisterFlag register(String username, String password);

    /**
     * @return is authenticated for tests
     */
    boolean getAuthentication();

    /**
     * @return the current authenticated User
     */
    User getUser();

    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException, SecurityAuthenticationException;
}
