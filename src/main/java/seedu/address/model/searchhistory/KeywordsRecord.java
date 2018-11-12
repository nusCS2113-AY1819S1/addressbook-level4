package seedu.address.model.searchhistory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.searchhistory.exceptions.EmptyHistoryException;

/**
 * Represents the in-app memory of all keywords relevant to the current Persons search.
 */
public class KeywordsRecord implements ReadOnlyKeywordsRecord {

    private static final Logger logger = LogsCenter.getLogger(KeywordsRecord.class);

    protected HashMap<KeywordType, KeywordsSet> map = new HashMap<>();

    protected KeywordsHistoryStack historyStack = new KeywordsHistoryStack();

    public KeywordsRecord() {
        prepareKeywordSets();
    }

    /**
     * Clears all keywords in memory.
     */
    public void clearKeywordsHistory() {
        map.clear();
        historyStack.clear();
        prepareKeywordSets();
    }

    /**
     * Resets in-app memory of keywords back to its previous state.
     */
    public void undoKeywordsHistory() throws EmptyHistoryException {
        KeywordsBundle bundle = historyStack.pop();
        logger.info("Removing from keywords history: "
                + bundle.getType().name() + ":" + bundle.getKeywords().toString());
        KeywordsSet set = map.get(bundle.getType());
        set.removeKeywordsFromSet(bundle.getKeywords());
    }

    /**
     * Records keywords according to their type.
     * @param type KeywordType.
     * @param keywords list of words to be stored in memory.
     */
    public void recordKeywords (KeywordType type, List<String> keywords) {
        if (type == null || keywords == null) {
            throw new NullPointerException();
        }
        logger.info("Adding to keywords history: " + type.name() + "," + keywords.toString());
        KeywordsSet set = map.get(type);
        set.addKeywordsToSet(keywords);
        updateHistoryStack(type, keywords);
    }

    private void updateHistoryStack(KeywordType type, List<String> keywords) {
        assert type != null;
        assert keywords != null;
        historyStack.push(type, keywords);
    }

    @Override
    public SortedSet<String> getReadOnlyKeywordsSet(KeywordType type) {
        KeywordsSet set = map.get(type);
        SortedSet<String> uniqueKeywordsSet = set.getUniqueKeywordsSet();
        return Collections.unmodifiableSortedSet(uniqueKeywordsSet);
    }

    private void prepareKeywordSets() {
        for (KeywordType type: KeywordType.values()) {
            map.put(type, new KeywordsSet());
        }
    }

    /**
     * Returns true if no keywords are stored.
     */
    public boolean isEmpty() {
        for (KeywordsSet set: map.values()) {
            if (!set.isEmpty()) {
                return false;
            }
        }
        return historyStack.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof KeywordsRecord)) {
            return false;
        }

        KeywordsRecord other = (KeywordsRecord) obj;
        return map.equals(other.map) && historyStack.equals(other.historyStack);
    }
}
