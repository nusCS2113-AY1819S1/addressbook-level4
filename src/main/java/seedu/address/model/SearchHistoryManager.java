package seedu.address.model;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.function.Predicate;

/**
 * Represents in-memory model for Predicates used in Searching
 */
public class SearchHistoryManager {

    private Stack<Predicate> searchHistoryStack = new Stack<>();

    private Predicate retrievePredicateAtTopOfStack() {
        return searchHistoryStack.peek();
    }

    /**
     * Resets the most recent search
     */
    public Predicate revertLastSearch() throws EmptyStackException {
        removeLastPredicateFromStack();
        return retrievePredicateAtTopOfStack();
    }

    public Predicate executeNewSearch(Predicate predicate) {
        addNewPredicateToStack(predicate);
        return retrievePredicateAtTopOfStack();
    }

    private void addNewPredicateToStack(Predicate newPredicate) {
        try {
            Predicate updatedPredicate = retrievePredicateAtTopOfStack().and(newPredicate);
            searchHistoryStack.push(updatedPredicate);
        } catch (EmptyStackException e) {
            searchHistoryStack.push(newPredicate);
        }
    }

    private void removeLastPredicateFromStack() {
        if (!searchHistoryStack.empty()) {
            searchHistoryStack.pop();
        }
    }

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
