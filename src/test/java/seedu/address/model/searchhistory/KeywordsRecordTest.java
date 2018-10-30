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
    public void clearKeywordsHistory_emptyKeywordsRecord_isEmpty() {
        KeywordsRecord keywordsRecord = getFilledKeywordsRecord(1);
        keywordsRecord.clearKeywordsHistory();
        assertTrue(keywordsRecord.isEmpty());
    }

    @Test
    public void recordKeywords_emptyKeywordsRecord_isNotEmpty() {
        KeywordsRecord keywordsRecord = getEmptyKeywordsRecord();
        keywordsRecord.recordKeywords(KeywordType.IncludeNames, Collections.singletonList("keyword"));
        assertFalse(keywordsRecord.isEmpty());
    }

    @Test
    public void undoKeywordsHistory_nonEmptyKeywordsRecord_isEmpty() {
        KeywordsRecord keywordsRecord = getFilledKeywordsRecord(1);
        keywordsRecord.undoKeywordsHistory();
        assertTrue(keywordsRecord.isEmpty());
    }

    @Test
    public void recordKeywords_nullType_isEmpty() {
        KeywordsRecord keywordsRecord = getEmptyKeywordsRecord();
        keywordsRecord.recordKeywords(null, Collections.singletonList("keyword"));
        assertTrue(keywordsRecord.isEmpty());
    }

    @Test
    public void recordKeywords_nullKeywords_isEmpty() {
        KeywordsRecord keywordsRecord = getEmptyKeywordsRecord();
        keywordsRecord.recordKeywords(KeywordType.IncludeNames, null);
        assertTrue(keywordsRecord.isEmpty());
    }
}
