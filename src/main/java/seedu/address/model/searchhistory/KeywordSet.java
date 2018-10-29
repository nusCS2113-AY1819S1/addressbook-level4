package seedu.address.model.searchhistory;

import java.util.List;
import java.util.SortedSet;

import com.google.common.collect.TreeMultiset;

/**
 * Represents the storage of keywords.
 */
class KeywordSet {
    private TreeMultiset<String> keywordsSet = TreeMultiset.create();

    void addKeywordsToSet(List<String> keywords) {
        keywordsSet.addAll(keywords);
    }

    void removeKeywordsFromSet(List<String> keywords) {
        for (String keyword : keywords) {
            keywordsSet.remove(keyword);
        }
    }

    SortedSet<String> getUniqueKeywordsSet() {
        return keywordsSet.elementSet();
    }
}
