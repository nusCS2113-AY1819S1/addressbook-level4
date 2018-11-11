package seedu.address.model.searchhistory;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import seedu.address.model.searchhistory.exceptions.EmptyHistoryException;

/**
 * Represents the in-app memory of all keywords and their types that were previously executed.
 */
public class KeywordsHistoryStack {

    protected Stack<KeywordsBundle> keywordsBundlesStack = new Stack<>();

    /**
     * Creates a new KeywordsBundle object and adds it to the stack
     */
    public void push(KeywordType type, List<String> keywords) {
        if (type == null || keywords == null) {
            throw new NullPointerException();
        }
        KeywordsBundle bundle = new KeywordsBundle(type, keywords);
        keywordsBundlesStack.push(bundle);
    }

    /**
     * Adds a new KeywordsBundle object adds it to the stack
     */
    public void push(KeywordsBundle bundle) {
        if (bundle == null) {
            throw new NullPointerException();
        }
        keywordsBundlesStack.push(bundle);
    }
    /**
     * Returns a KeywordBundle object that was at the top of the stack
     * and removes it from the stack.
     * @throws EmptyHistoryException If search history is empty before revert.
     */
    public KeywordsBundle pop() throws EmptyHistoryException {
        try {
            return keywordsBundlesStack.pop();
        } catch (EmptyStackException e) {
            throw new EmptyHistoryException();
        }
    }

    public void clear() {
        keywordsBundlesStack.clear();
    }

    public boolean isEmpty() {
        return keywordsBundlesStack.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof KeywordsHistoryStack)) {
            return false;
        }

        KeywordsHistoryStack other = (KeywordsHistoryStack) obj;
        return keywordsBundlesStack.equals(other.keywordsBundlesStack);
    }
}
