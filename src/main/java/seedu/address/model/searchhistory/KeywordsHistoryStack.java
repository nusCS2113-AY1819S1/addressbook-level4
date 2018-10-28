package seedu.address.model.searchhistory;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import seedu.address.model.searchhistory.exceptions.EmptyHistoryException;

/**
 * Represents the in-app memory of all keywords and their types that were previously executed.
 */
class KeywordsHistoryStack {

    private Stack<KeywordsBundle> keywordsBundlesStack = new Stack<>();

    void push(KeywordType type, List<String> keywords) {
        KeywordsBundle bundle = new KeywordsBundle(type, keywords);
        keywordsBundlesStack.push(bundle);
    }

    /**
     * Returns a KeywordBundle object that was at the top of the stack
     * and removes it from the stack.
     * @throws EmptyHistoryException If search history is empty before revert.
     */
    KeywordsBundle pop() throws EmptyHistoryException {
        try {
            return keywordsBundlesStack.pop();
        } catch (EmptyStackException e) {
            throw new EmptyHistoryException();
        }
    }

    void clear() {
        keywordsBundlesStack.clear();
    }
}
