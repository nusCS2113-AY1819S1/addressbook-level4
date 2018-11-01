package seedu.address.model.searchhistory;

import java.util.SortedSet;

/**
 * An interface for KeywordsRecord that only allows the retrieval of data.
 */
public interface ReadOnlyKeywordsRecord {
    SortedSet<String> getKeywordSet(KeywordType type);
}
