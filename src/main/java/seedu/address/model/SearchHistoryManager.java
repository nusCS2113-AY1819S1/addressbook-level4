package seedu.address.model;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.function.Predicate;

/**
 * Represents in-memory model for Predicates containing system search logic
 */
public class SearchHistoryManager {

    private Stack<Predicate> searchHistoryStack = new Stack<>();

    private Predicate retrievePredicateAtTopOfStack() {
        return searchHistoryStack.peek();
    }

    /** Returns system search logic after reverting to its previous state
     * @return a Predicate containing the system search logic after reverting
     * @throws EmptyStackException If search history is empty and there is nothing to revert.
     */
    public Predicate revertLastSearch() throws EmptyStackException {
        removeLastPredicateFromStack();
        return retrievePredicateAtTopOfStack();
    }
    /** Returns system search logic given a user-defined search logic
     * @param predicate a Predicate containing the user-defined search logic
     * @return a Predicate containing the system search logic
     **/
    public Predicate executeNewSearch(Predicate predicate) {
        addNewPredicateToStack(predicate);
        assert !searchHistoryStack.empty();
        return retrievePredicateAtTopOfStack();
    }

    /** Updates system search logic to its next state given a user-defined search logic
     * @param newPredicate a Predicate containing the user-defined search logic
     **/
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
