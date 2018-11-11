package seedu.address.model.searchhistory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.searchhistory.util.SearchHistoryTestUtil.getEmptyKeywordsRecord;
import static seedu.address.model.searchhistory.util.SearchHistoryTestUtil.getFilledKeywordsRecord;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.searchhistory.exceptions.EmptyHistoryException;

public class KeywordsRecordTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void undoKeywordsHistory_emptyKeywordsRecord_throwsEmptyHistoryException() {
        thrown.expect(EmptyHistoryException.class);
        KeywordsRecord keywordsRecord = getEmptyKeywordsRecord();
        keywordsRecord.undoKeywordsHistory();
    }

    @Test
    public void undoKeywordsHistory_sizedOneKeywordsRecord_isEmpty() {
        KeywordsRecord keywordsRecord = getFilledKeywordsRecord(1);
        keywordsRecord.undoKeywordsHistory();
        assertTrue(keywordsRecord.isEmpty());
    }

    @Test
    public void undoKeywordsHistory_sizedTwoKeywordsRecord_isNotEmpty() {
        KeywordsRecord keywordsRecord = getFilledKeywordsRecord(2);
        keywordsRecord.undoKeywordsHistory();
        assertFalse(keywordsRecord.isEmpty());
    }

    @Test
    public void clearKeywordsHistory_nonEmptyKeywordsRecord_isEmpty() {
        KeywordsRecord keywordsRecord = getFilledKeywordsRecord(2);
        keywordsRecord.clearKeywordsHistory();
        assertTrue(keywordsRecord.isEmpty());
    }

    @Test
    public void recordKeywords_includeNamesTypeAndEmptyKeywordsRecord_isNotEmpty() {
        KeywordsRecord keywordsRecord = getEmptyKeywordsRecord();
        keywordsRecord.recordKeywords(KeywordType.IncludeNames, Collections.singletonList("keyword"));
        assertFalse(keywordsRecord.isEmpty());
    }

    @Test
    public void recordKeywords_excludeNamesTypeAndEmptyKeywordsRecord_isNotEmpty() {
        KeywordsRecord keywordsRecord = getEmptyKeywordsRecord();
        keywordsRecord.recordKeywords(KeywordType.ExcludeNames, Collections.singletonList("keyword"));
        assertFalse(keywordsRecord.isEmpty());
    }

    @Test
    public void recordKeywords_includeTagsTypeAndEmptyKeywordsRecord_isNotEmpty() {
        KeywordsRecord keywordsRecord = getEmptyKeywordsRecord();
        keywordsRecord.recordKeywords(KeywordType.IncludeTags, Collections.singletonList("keyword"));
        assertFalse(keywordsRecord.isEmpty());
    }

    @Test
    public void recordKeywords_excludeTagsTypeAndEmptyKeywordsRecord_isNotEmpty() {
        KeywordsRecord keywordsRecord = getEmptyKeywordsRecord();
        keywordsRecord.recordKeywords(KeywordType.ExcludeTags, Collections.singletonList("keyword"));
        assertFalse(keywordsRecord.isEmpty());
    }

    @Test
    public void recordKeywords_nullType_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        KeywordsRecord keywordsRecord = getEmptyKeywordsRecord();
        keywordsRecord.recordKeywords(null, Collections.singletonList("keyword"));
    }

    @Test
    public void recordKeywords_nullKeywords_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        KeywordsRecord keywordsRecord = getEmptyKeywordsRecord();
        keywordsRecord.recordKeywords(KeywordType.IncludeNames, null);
    }
}
