package seedu.address.model.searchhistory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.searchhistory.util.SearchHistoryTestUtil.DEFAULT_PREDICATE;
import static seedu.address.model.searchhistory.util.SearchHistoryTestUtil.getEmptySearchHistoryManager;
import static seedu.address.model.searchhistory.util.SearchHistoryTestUtil.getFilledSearchHistoryManager;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.searchhistory.exceptions.EmptyHistoryException;

public class SearchHistoryManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() {
        SearchHistoryManager emptySearchHistoryManager = getEmptySearchHistoryManager();

        SearchHistoryManager firstFilledSearchHistoryManager = getFilledSearchHistoryManager(3);

        SearchHistoryManager secondFilledSearchHistoryManager = getFilledSearchHistoryManager(4);

        SearchHistoryManager thirdFilledSearchHistoryManager = getFilledSearchHistoryManager(3);

        // same object -> returns true
        assertTrue(emptySearchHistoryManager.equals(emptySearchHistoryManager));
        assertTrue(firstFilledSearchHistoryManager.equals(firstFilledSearchHistoryManager));

        // same size -> returns true
        assertTrue(firstFilledSearchHistoryManager.equals(thirdFilledSearchHistoryManager));

        // different size -> returns false
        assertFalse(firstFilledSearchHistoryManager.equals(secondFilledSearchHistoryManager));

        // different types -> returns false
        assertFalse(emptySearchHistoryManager.equals(1));

        // null -> returns false
        assertFalse(emptySearchHistoryManager.equals(null));
        assertFalse(firstFilledSearchHistoryManager.equals(null));
    }

    @Test
    public void revertLastSearch_emptySearchHistory_throwsEmptyHistoryException() {
        thrown.expect(EmptyHistoryException.class);
        SearchHistoryManager searchHistoryManager = getEmptySearchHistoryManager();
        searchHistoryManager.revertLastSearch();
    }

    @Test
    public void clearSearchHistory_nonEmptySearchHistory_searchHistoryIsEmpty() {
        SearchHistoryManager searchHistoryManager = getFilledSearchHistoryManager(2);
        searchHistoryManager.clearSearchHistory();
        assertTrue(searchHistoryManager.isEmpty());
    }

    @Test
    public void revertLastSearch_nonEmptySearchHistory_searchHistoryIsNotEmpty() {
        SearchHistoryManager searchHistoryManager = getFilledSearchHistoryManager(2);
        searchHistoryManager.revertLastSearch();
        assertFalse(searchHistoryManager.isEmpty());
    }

    @Test
    public void executeNewSearch_validPredicate_searchHistoryIsNotEmpty() {
        SearchHistoryManager searchHistoryManager = getEmptySearchHistoryManager();
        searchHistoryManager.executeNewSearch(DEFAULT_PREDICATE);
        assertFalse(searchHistoryManager.isEmpty());
    }

    @Test
    public void executeNewSearch_nullPredicate_searchHistoryIsEmpty() {
        SearchHistoryManager searchHistoryManager = getEmptySearchHistoryManager();
        searchHistoryManager.executeNewSearch(null);
        assertTrue(searchHistoryManager.isEmpty());
    }
}
