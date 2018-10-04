package seedu.address.model;

import java.util.Stack;
import java.util.function.Predicate;

/**
 * Represents in-memory model for Search Predicates used in finding contacts
 */
public class SearchHistoryManager {

    private static SearchHistoryManager searchManagerInstance;
    private Stack<Predicate> searchHistoryStack = new Stack<>();

    private SearchHistoryManager() {

    }

    public static SearchHistoryManager getInstance() {
        if (searchManagerInstance == null) {
            searchManagerInstance = new SearchHistoryManager();
        }
        return searchManagerInstance;
    }

    /**
     * Retrieves Predicate that executes the most recent search.
     */
    private Predicate retrieveLastSearch() {
        if (searchHistoryStack.empty()) {
            searchHistoryStack.push(newPredicate -> true);
        }
        return searchHistoryStack.peek();
    }

    /**
     * Adds new Predicate Predicate to stack which will execute the next Search
     */
    public void addNewSearch(Predicate newPredicate) {
        Predicate updatedPredicate = retrieveLastSearch().and(newPredicate);
        searchHistoryStack.push(updatedPredicate);
    }

    /**
     * Returns new Predicate which will execute the next Search
     */
    public Predicate updateNewSearch(Predicate newPredicate) {
        addNewSearch(newPredicate);
        return searchHistoryStack.peek();
    }

    /**
     * Resets the most recent search
     */
    public Predicate revertLastSearch() {
        if (!searchHistoryStack.empty()) {
            searchHistoryStack.pop();
        }
        return retrieveLastSearch();
    }

    /**
     * Resets all searches that had been done previously
     */
    public void clearSearchHistory() {
        searchHistoryStack.clear();
    }
}
