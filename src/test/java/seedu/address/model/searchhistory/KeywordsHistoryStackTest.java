package seedu.address.model.searchhistory;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import seedu.address.model.searchhistory.util.SearchHistoryTestUtil;
import static seedu.address.model.searchhistory.util.SearchHistoryTestUtil.getEmptyKeywordsHistoryStack;
import static seedu.address.model.searchhistory.util.SearchHistoryTestUtil.getFilledKeywordsHistoryStack;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.searchhistory.exceptions.EmptyHistoryException;
import static seedu.address.model.searchhistory.util.SearchHistoryTestUtil.getKeywordsBundleStub;

public class KeywordsHistoryStackTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void equals() {
        KeywordsHistoryStack emptyKeywordsHistoryStack = getEmptyKeywordsHistoryStack();

        KeywordsHistoryStack firstFilledKeywordsHistoryStack = getFilledKeywordsHistoryStack(1);

        KeywordsHistoryStack secondFilledKeywordsHistoryStack = getEmptyKeywordsHistoryStack();

        KeywordsHistoryStack thirdFilledSearchHistoryStack = getFilledKeywordsHistoryStack(1);

        // same object -> returns true
        assertTrue(emptyKeywordsHistoryStack.equals(emptyKeywordsHistoryStack));
        assertTrue(firstFilledKeywordsHistoryStack.equals(firstFilledKeywordsHistoryStack));

        // same content -> returns true
        assertTrue(firstFilledKeywordsHistoryStack.equals(thirdFilledSearchHistoryStack));

        // different content -> returns false
        assertFalse(firstFilledKeywordsHistoryStack.equals(secondFilledKeywordsHistoryStack));

        // different types -> returns false
        assertFalse(emptyKeywordsHistoryStack.equals(1));

        // null -> returns false
        assertFalse(emptyKeywordsHistoryStack.equals(null));
        assertFalse(firstFilledKeywordsHistoryStack.equals(null));
    }

    @Test
    public void clear_nonEmptyHistoryStack_isEmpty() {
        KeywordsHistoryStack historyStack = getFilledKeywordsHistoryStack(2);
        historyStack.clear();
        assertTrue(historyStack.isEmpty());
    }

    @Test
    public void pop_emptyHistoryStack_throwsEmptyHistoryException() {
        thrown.expect(EmptyHistoryException.class);;
        KeywordsHistoryStack historyStack = getEmptyKeywordsHistoryStack();
        historyStack.pop();
    }

    @Test
    public void pop_sizedOneHistoryStack_isEmpty() {
        KeywordsHistoryStack historyStack = getFilledKeywordsHistoryStack(1);
        historyStack.pop();
        assertTrue(historyStack.isEmpty());
    }

    @Test
    public void pop_sizedTwoHistoryStack_isNotEmpty() {
        KeywordsHistoryStack historyStack = getFilledKeywordsHistoryStack(2);
        historyStack.pop();
        assertFalse(historyStack.isEmpty());
    }

    @Test
    public void push_validInput_success() {
        KeywordsHistoryStack historyStack = getEmptyKeywordsHistoryStack();
        historyStack.push(KeywordType.IncludeNames, new ArrayList<>());
    }

    @Test
    public void push_nullBundle_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        KeywordsHistoryStack historyStack = getEmptyKeywordsHistoryStack();
        historyStack.push(null);
    }


    @Test
    public void push_nonNullBundleAndEmptyHistoryStack_isNotEmpty() {
        KeywordsHistoryStack historyStack = getEmptyKeywordsHistoryStack();
        historyStack.push(getKeywordsBundleStub());
        assertFalse(historyStack.isEmpty());
    }

    @Test
    public void push_nullKeywordType_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        KeywordsHistoryStack historyStack = getEmptyKeywordsHistoryStack();
        historyStack.push(null, new ArrayList<>());
    }

    @Test
    public void push_nullKeywordList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        KeywordsHistoryStack historyStack = getEmptyKeywordsHistoryStack();
        historyStack.push(KeywordType.IncludeNames, null);
    }

    @Test
    public void push_includeNamesTypeAndEmptyHistoryStack_isNotEmpty() {
        KeywordsHistoryStack historyStack = getEmptyKeywordsHistoryStack();
        historyStack.push(KeywordType.IncludeNames, new ArrayList<>());
        assertFalse(historyStack.isEmpty());
    }

    @Test
    public void push_excludeNamesTypeAndEmptyHistoryStack_isNotEmpty() {
        KeywordsHistoryStack historyStack = getEmptyKeywordsHistoryStack();
        historyStack.push(KeywordType.ExcludeNames, new ArrayList<>());
        assertFalse(historyStack.isEmpty());
    }

    @Test
    public void push_includeTagsTypeAndEmptyHistoryStack_isNotEmpty() {
        KeywordsHistoryStack historyStack = getEmptyKeywordsHistoryStack();
        historyStack.push(KeywordType.IncludeTags, new ArrayList<>());
        assertFalse(historyStack.isEmpty());
    }

    @Test
    public void push_excludeTagsTypeAndEmptyHistoryStack_isNotEmpty() {
        KeywordsHistoryStack historyStack = getEmptyKeywordsHistoryStack();
        historyStack.push(KeywordType.ExcludeTags, new ArrayList<>());
        assertFalse(historyStack.isEmpty());
    }
}
