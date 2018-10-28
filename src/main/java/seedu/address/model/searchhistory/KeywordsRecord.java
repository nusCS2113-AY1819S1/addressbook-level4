package seedu.address.model.searchhistory;

import java.util.List;
import java.util.SortedSet;

import com.google.common.collect.TreeMultiset;

/**
 * Represents the in-app memory of all keywords relevant to the current Persons search.
 */
public class KeywordsRecord implements ReadOnlyKeywordsRecord {

    private TreeMultiset<String> includedNamesSet = TreeMultiset.create();
    private TreeMultiset<String> includedTagsSet = TreeMultiset.create();
    private TreeMultiset<String> excludedNamesSet = TreeMultiset.create();
    private TreeMultiset<String> excludedTagsSet = TreeMultiset.create();
    private KeywordsHistoryStack historyStack = new KeywordsHistoryStack();

    /**
     * Clears all keywords in memory.
     */
    public void clearKeywordsHistory() {
        includedNamesSet.clear();
        includedTagsSet.clear();
        excludedNamesSet.clear();
        excludedTagsSet.clear();
        historyStack.clear();
    }

    /**
     * Resets in-app memory of keywords back to its previous state.
     */
    public void undoKeywordsHistory() {
        KeywordsBundle bundle = historyStack.pop();
        switch (bundle.getType()) {
        case IncludeNames:
            removeKeywordsFromSet(includedNamesSet, bundle.getKeywords());
            break;
        case ExcludeNames:
            removeKeywordsFromSet(excludedNamesSet, bundle.getKeywords());
            break;
        case IncludeTags:
            removeKeywordsFromSet(includedTagsSet, bundle.getKeywords());
            break;
        case ExcludeTags:
            removeKeywordsFromSet(excludedTagsSet, bundle.getKeywords());
            break;
        default:
            break;
        }
    }

    /**
     * Records keywords according to their type.
     *
     * @param type KeywordType.
     * @param keywords list of words to be stored in memory.
     */
    public void recordKeywords (KeywordType type, List<String> keywords) {
        switch (type) {
        case IncludeNames:
            addKeywordsToSet(includedNamesSet, keywords);
            break;
        case ExcludeNames:
            addKeywordsToSet(excludedNamesSet, keywords);
            break;
        case IncludeTags:
            addKeywordsToSet(includedTagsSet, keywords);
            break;
        case ExcludeTags:
            addKeywordsToSet(excludedTagsSet, keywords);
            break;
        default:
            break;
        }
        updateHistoryStack(type, keywords);
    }

    private void removeKeywordsFromSet(TreeMultiset<String> set, List<String> keywords) {
        for (String keyword : keywords) {
            set.remove(keyword);
        }
    }

    private void addKeywordsToSet(TreeMultiset<String> set, List<String> keywords) {
        set.addAll(keywords);
    }

    private void updateHistoryStack(KeywordType type, List<String> keywords) {
        historyStack.push(type, keywords);
    }

    public SortedSet<String> getIncludedTags() {
        return includedTagsSet.elementSet();
    }

    public SortedSet<String> getExcludedTags() {
        return excludedTagsSet.elementSet();
    }

    public SortedSet<String> getIncludedNames() {
        return includedNamesSet.elementSet();
    }

    public SortedSet<String> getExcludedNames() {
        return excludedNamesSet.elementSet();
    }
}
