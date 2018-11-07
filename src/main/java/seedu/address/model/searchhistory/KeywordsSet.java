package seedu.address.model.searchhistory;

import java.util.List;
import java.util.SortedSet;

import com.google.common.collect.TreeMultiset;

/**
 * Represents the storage of keywords.
 */
public class KeywordsSet {
    private TreeMultiset<String> treeMultiSet;

    public KeywordsSet(TreeMultiset<String> set) {
        treeMultiSet = set;
    }

    KeywordsSet() {
        treeMultiSet = TreeMultiset.create();
    }

    void addKeywordsToSet(List<String> keywords) {
        treeMultiSet.addAll(keywords);
    }

    void removeKeywordsFromSet(List<String> keywords) {
        for (String keyword : keywords) {
            treeMultiSet.remove(keyword);
        }
    }

    boolean isEmpty() {
        return treeMultiSet.isEmpty();
    }

    SortedSet<String> getUniqueKeywordsSet() {
        return treeMultiSet.elementSet();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof KeywordsSet)) {
            return false;
        }

        KeywordsSet other = (KeywordsSet) obj;
        return treeMultiSet.equals(other.treeMultiSet);
    }
}
