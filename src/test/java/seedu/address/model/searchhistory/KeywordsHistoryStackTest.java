package seedu.address.model.searchhistory;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.model.searchhistory.util.SearchHistoryTestUtil.getEmptyKeywordsHistoryStack;
import static seedu.address.model.searchhistory.util.SearchHistoryTestUtil.getFilledKeywordsHistoryStack;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.searchhistory.exceptions.EmptyHistoryException;

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
        KeywordsHistoryStack historyStack = getFilledKeywordsHistoryStack(1);
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
    public void pop_nonEmptyHistoryStack_isEmpty() {
        KeywordsHistoryStack historyStack = getFilledKeywordsHistoryStack(1);
        historyStack.pop();
        assertTrue(historyStack.isEmpty());
    }


    @Test
    public void push_validInput_isNotEmpty() {
        KeywordsHistoryStack historyStack = getEmptyKeywordsHistoryStack();
        historyStack.push(KeywordType.IncludeNames, new ArrayList<>());
        assertFalse(historyStack.isEmpty());
    }

    @Test
    public void push_nullKeywordType_isEmpty() {
        KeywordsHistoryStack historyStack = getEmptyKeywordsHistoryStack();
        historyStack.push(null, new ArrayList<>());
        assertTrue(historyStack.isEmpty());
    }

    @Test
    public void push_nullKeywordList_isEmpty() {
        KeywordsHistoryStack historyStack = getEmptyKeywordsHistoryStack();
        historyStack.push(null, new ArrayList<>());
        assertTrue(historyStack.isEmpty());
    }
}
