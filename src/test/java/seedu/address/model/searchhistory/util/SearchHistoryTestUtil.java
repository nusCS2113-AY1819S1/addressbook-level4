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
        return new SearchHistoryManagerSpy<>(size);
    }

    public static KeywordsHistoryStack getEmptyKeywordsHistoryStack() {
        return new KeywordsHistoryStack();
    }

    public static KeywordsHistoryStack getFilledKeywordsHistoryStack(int size) {
        return new KeywordsHistoryStackSpy(size);
    }

    public static KeywordsRecord getEmptyKeywordsRecord() {
        return new KeywordsRecord();
    }

    public static KeywordsRecord getFilledKeywordsRecord(int size) {
        return new KeywordsRecordSpy(size);
    }

    /**
     * A SearchHistoryManagerSpy object.
     */
    private static class SearchHistoryManagerSpy<T> extends SearchHistoryManager<T> {

        final Predicate<T> defaultPredicate = (t) -> true;

        private SearchHistoryManagerSpy(int size) {
            for (int x = 0; x < size; x++) {
                searchHistoryStack.push(defaultPredicate);
            }
        }
    }


    /**
     * KeywordsHistoryStackSpy object.
     */
    private static class KeywordsHistoryStackSpy extends KeywordsHistoryStack {

        private KeywordsHistoryStackSpy(int size) {
            for (int x = 0; x < size; x++) {
                keywordsBundlesStack.push(new KeywordsBundleDummy());
            }
        }
    }

    /**
     * A dummy class for KeywordsBundle
     */
    private static class KeywordsBundleDummy extends KeywordsBundle {

        KeywordsBundleDummy() {
            super(KeywordType.IncludeNames, Collections.singletonList("keyword"));
        }

        @Override
        public KeywordType getType() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<String> getKeywords() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A KeywordsBundle stub that always returns the Keyword Type
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
     * KeywordsRecordSpy object.
     */
    private static class KeywordsRecordSpy extends KeywordsRecord {

        private KeywordsRecordSpy(int size) {
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
