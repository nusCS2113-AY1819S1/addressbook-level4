package seedu.address.testutil;

import java.util.function.Predicate;

import seedu.address.model.person.Person;
import seedu.address.model.searchhistory.SearchHistoryManager;

/**
 * A utility class for SearchHistoryManager
 */
public class SearchHistoryManagerUtil {

    public static final Predicate<Person> DEFAULT_PREDICATE = (t) -> true;

    public static SearchHistoryManager<Person> getEmptySearchHistoryManager() {
        return new SearchHistoryManager<>();
    }

    public static SearchHistoryManager<Person> getFilledSearchHistoryManager(int size) {
        SearchHistoryManager<Person> searchHistoryManager = new SearchHistoryManager<>();
        for (int x = 0; x < size; x++) {
            searchHistoryManager.executeNewSearch(DEFAULT_PREDICATE);
        }
        return searchHistoryManager;
    }
}
