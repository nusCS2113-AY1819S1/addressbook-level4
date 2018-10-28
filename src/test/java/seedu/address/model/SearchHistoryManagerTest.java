package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.SearchHistoryManagerUtil.DEFAULT_PREDICATE;
import static seedu.address.testutil.SearchHistoryManagerUtil.getEmptySearchHistoryManager;
import static seedu.address.testutil.SearchHistoryManagerUtil.getFilledSearchHistoryManager;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.Person;
import seedu.address.model.searchhistory.SearchHistoryManager;
import seedu.address.model.searchhistory.exceptions.EmptyHistoryException;

public class SearchHistoryManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void revertLastSearch_emptySearchHistory_throwsEmptyHistoryException() {
        thrown.expect(EmptyHistoryException.class);
        SearchHistoryManager<Person> searchHistoryManager = getEmptySearchHistoryManager();
        searchHistoryManager.revertLastSearch();
    }

    @Test
    public void clearSearchHistory_nonEmptySearchHistory_searchHistoryIsEmpty() {
        SearchHistoryManager<Person> searchHistoryManager = getFilledSearchHistoryManager(2);
        searchHistoryManager.clearSearchHistory();
        assertTrue(searchHistoryManager.isEmpty());
    }

    @Test
    public void revertLastSearch_nonEmptySearchHistory_searchHistoryIsNotEmpty() {
        SearchHistoryManager<Person> searchHistoryManager = getFilledSearchHistoryManager(2);
        searchHistoryManager.revertLastSearch();
        assertFalse(searchHistoryManager.isEmpty());
    }

    @Test
    public void executeNewSearch_emptySearchHistory_searchHistoryIsNotEmpty() {
        SearchHistoryManager<Person> searchHistoryManager = getEmptySearchHistoryManager();
        searchHistoryManager.executeNewSearch(DEFAULT_PREDICATE);
        assertFalse(searchHistoryManager.isEmpty());
    }
}
