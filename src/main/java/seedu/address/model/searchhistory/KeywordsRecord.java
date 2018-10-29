package seedu.address.model.searchhistory;

import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;

/**
 * Represents the in-app memory of all keywords relevant to the current Persons search.
 */
public class KeywordsRecord implements ReadOnlyKeywordsRecord {

    private HashMap<KeywordType, KeywordSet> map = new HashMap<>();

    private KeywordsHistoryStack historyStack = new KeywordsHistoryStack();

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
    public void undoKeywordsHistory() {
        KeywordsBundle bundle = historyStack.pop();
        KeywordSet set = map.get(bundle.getType());
        set.removeKeywordsFromSet(bundle.getKeywords());
    }

    /**
     * Records keywords according to their type.
     *
     * @param type KeywordType.
     * @param keywords list of words to be stored in memory.
     */
    public void recordKeywords (KeywordType type, List<String> keywords) {
        KeywordSet set = map.get(type);
        set.addKeywordsToSet(keywords);
        updateHistoryStack(type, keywords);
    }

    private void updateHistoryStack(KeywordType type, List<String> keywords) {
        historyStack.push(type, keywords);
    }

    @Override
    public SortedSet<String> getKeywordSet(KeywordType type) {
        KeywordSet set = map.get(type);
        return set.getUniqueKeywordsSet();
    }

    private void prepareKeywordSets() {
        for (KeywordType type: KeywordType.values()) {
            map.put(type, new KeywordSet());
        }
    }
}
