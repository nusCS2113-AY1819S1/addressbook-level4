package seedu.address.model;

import java.util.Stack;
import java.util.function.Predicate;

/**
 * Represents in-memory model for Predicates used in Searching
 */
public class SearchHistoryManager {

    private Stack<Predicate> searchHistoryStack = new Stack<>();

    /**
     * Retrieves Predicate that executes the most recent search.
     */
    public Predicate retrieveLastSearch() {
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof SearchHistoryManager)) {
            return false;
        }
        SearchHistoryManager other = (SearchHistoryManager) obj;
        return searchHistoryStack.size() == other.searchHistoryStack.size();
    }
}
