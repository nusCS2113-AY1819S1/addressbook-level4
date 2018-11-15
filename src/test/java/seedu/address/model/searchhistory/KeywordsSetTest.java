package seedu.address.model.searchhistory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.TreeMultiset;

public class KeywordsSetTest {

    @Test
    public void equals() {
        KeywordsSet emptyKeywordsSet = prepareKeywordSet(new ArrayList<>());

        KeywordsSet firstFilledKeywordsSet = prepareKeywordSet(Collections.singletonList("firstKeyword"));

        KeywordsSet secondFilledKeywordsSet = prepareKeywordSet(Collections.singletonList("secondKeyword"));

        KeywordsSet thirdFilledKeywordsSet = prepareKeywordSet(Collections.singletonList("firstKeyword"));

        // same object -> returns true
        assertTrue(emptyKeywordsSet.equals(emptyKeywordsSet));
        assertTrue(firstFilledKeywordsSet.equals(firstFilledKeywordsSet));

        // same content -> returns true
        assertTrue(firstFilledKeywordsSet.equals(thirdFilledKeywordsSet));

        // different content -> returns false
        assertFalse(firstFilledKeywordsSet.equals(secondFilledKeywordsSet));

        // different types -> returns false
        assertFalse(emptyKeywordsSet.equals(1));

        // null -> returns false
        assertFalse(emptyKeywordsSet.equals(null));
        assertFalse(firstFilledKeywordsSet.equals(null));
    }


    /**
     * Creates a new KeywordsSet object storing the list of keywords in keywordsList
     */
    private KeywordsSet prepareKeywordSet(List<String> keywordsList) {
        assert keywordsList != null;
        TreeMultiset<String> treeMultiset = TreeMultiset.create();
        treeMultiset.addAll(keywordsList);
        return new KeywordsSet(treeMultiset);
    }
}
