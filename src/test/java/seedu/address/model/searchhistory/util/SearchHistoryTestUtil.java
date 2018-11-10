package seedu.address.model.searchhistory.util;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import com.google.common.collect.TreeMultiset;

import seedu.address.model.searchhistory.KeywordType;
import seedu.address.model.searchhistory.KeywordsBundle;
import seedu.address.model.searchhistory.KeywordsHistoryStack;
import seedu.address.model.searchhistory.KeywordsRecord;
import seedu.address.model.searchhistory.KeywordsSet;
import seedu.address.model.searchhistory.SearchHistoryManager;

/**
 * A utility class for SearchHistoryManager
 */
public class SearchHistoryTestUtil {

    public static final Predicate DEFAULT_PREDICATE = (t) -> true;

    public static SearchHistoryManager getEmptySearchHistoryManager() {
        return new SearchHistoryManager<>();
    }

    public static SearchHistoryManager getFilledSearchHistoryManager(int size) {
        return new SearchHistoryManagerMock<>(size);
    }

    public static KeywordsHistoryStack getEmptyKeywordsHistoryStack() {
        return new KeywordsHistoryStack();
    }

    public static KeywordsHistoryStack getFilledKeywordsHistoryStack(int size) {
        return new KeywordsHistoryStackMock(size);
    }

    public static KeywordsRecord getEmptyKeywordsRecord() {
        return new KeywordsRecord();
    }

    public static KeywordsRecord getFilledKeywordsRecord(int size) {
        return new KeywordsRecordMock(size);
    }

    public static KeywordsBundle getKeywordsBundleStub() {
        return new KeywordsBundleStub();
    }
    /**
     * A Mock of SearchHistoryManager.
     */
    private static class SearchHistoryManagerMock<T> extends SearchHistoryManager<T> {

        final Predicate<T> defaultPredicate = (t) -> true;

        private SearchHistoryManagerMock(int size) {
            for (int x = 0; x < size; x++) {
                searchHistoryStack.push(defaultPredicate);
            }
        }
    }


    /**
     * A Mock of KeywordsHistoryStack.
     */
    private static class KeywordsHistoryStackMock extends KeywordsHistoryStack {

        private KeywordsHistoryStackMock(int size) {
            for (int x = 0; x < size; x++) {
                keywordsBundlesStack.push(new KeywordsBundleStub());
            }
        }
    }

    /**
     * A KeywordsBundle stub that always returns the IncludeNames KeywordsType.
     */
    private static class KeywordsBundleStub extends KeywordsBundle {

        KeywordsBundleStub() {
            super(KeywordType.IncludeNames, Collections.singletonList("keyword"));
        }

        @Override
        public KeywordType getType() {
            return KeywordType.IncludeNames;
        }

        @Override
        public List<String> getKeywords() {
            return Collections.singletonList("keyword");
        }
    }

    /**
     * A Mock of KeywordsRecord.
     */
    private static class KeywordsRecordMock extends KeywordsRecord {

        private KeywordsRecordMock(int size) {
            super();
            TreeMultiset<String> treeMultiset = TreeMultiset.create();
            for (int x = 0; x < size; x++) {
                historyStack.push(new KeywordsBundleStub());
                treeMultiset.add("keyword");
            }
            map.replace(KeywordType.IncludeNames, new KeywordsSet(treeMultiset));
        }
    }
}
