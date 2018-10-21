package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.searchhistory.SearchHistoryManager;


/**
 * Queries the login book to see if there is a user ID and password that matches input
 * user ID and password. Used for the login process.
 * Keyword matching is case insensitive for user ID and case sensitive for user Password.
 */
public abstract class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":Login into NUSSU-Connect with input "
            + "user ID, password and role."
            + "Parameters: USERID PASSWORD ROLE\n"
            + "Example: " + COMMAND_WORD + " A3583758X 1qaxcdwd2w member";

    private SearchHistoryManager searchHistoryManager = new SearchHistoryManager();

    /**
     * Clears the current searchHistoryManager object of previous login input details in preparation for another
     * login attempt so that the filtered login details list becomes unfiltered again. Returns a new predicate generated
     * from user input login id to be used in the filtering of the login details list.
     * @param idPredicate the predicate generated from user input login id
     * @return a new predicate generated from user input login id
     */
    Predicate getMostUpdatedIdPredicate(Predicate idPredicate) {
        searchHistoryManager.clearSearchHistory();
        return searchHistoryManager.executeNewSearch(idPredicate);
    }

    /**
     * Returns a new predicate generated from user input login password to be used in the filtering of the
     * login details list.
     * @param passwordPredicate the predicate generated from user input login password
     * @return a new predicate generated from user input login password
     */
    Predicate getMostUpdatedPasswordPredicate(Predicate passwordPredicate) {
        return searchHistoryManager.executeNewSearch(passwordPredicate);
    }

    /**
     * Returns a new predicate generated from user input login role to be used in the filtering of the
     * login details list.
     * @param rolePredicate the predicate generated from user input login role
     * @return a new predicate generated from user input login role
     */
    Predicate getMostUpdatedRolePredicate(Predicate rolePredicate) {
        return searchHistoryManager.executeNewSearch(rolePredicate);
    }
}
