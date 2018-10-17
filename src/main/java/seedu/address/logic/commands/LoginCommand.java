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

    protected Predicate getMostUpdatedIdPredicate(Predicate predicate) {
        searchHistoryManager.clearSearchHistory();
        return searchHistoryManager.executeNewSearch(predicate);
    }

    protected Predicate getMostUpdatedPasswordPredicate(Predicate predicate) {
        return searchHistoryManager.executeNewSearch(predicate);
    }

    protected Predicate getMostUpdatedRolePredicate(Predicate predicate) {
        return searchHistoryManager.executeNewSearch(predicate);
    }
}
