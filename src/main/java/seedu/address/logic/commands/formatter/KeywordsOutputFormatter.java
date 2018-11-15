package seedu.address.logic.commands.formatter;

import java.util.SortedSet;

import seedu.address.model.searchhistory.KeywordType;
import seedu.address.model.searchhistory.ReadOnlyKeywordsRecord;

/**
 * A class that creates output strings of name and tag keywords according to KeywordsRecord
 */
public class KeywordsOutputFormatter {

    private static final int MAX_STRING_WIDTH = 100;
    private static final int EXTRA_PADDING_LENGTH = 2;
    private static final char INCLUDE_PREFIX = '+';
    private static final char EXCLUDE_PREFIX = '-';
    private static final String TAG_KEYWORDS_HEADING = "\n=============== Tag Keywords History ================\n";
    private static final String NAME_KEYWORDS_HEADING = "\n=============== Name Keywords History ===============\n";
    private static final String PREFIXED_KEYWORD_FORMAT = "%c%s ";
    private StringBuffer outputString = new StringBuffer();
    private ReadOnlyKeywordsRecord record;
    private int currentStringWidth = 0;

    public String getOutputString(ReadOnlyKeywordsRecord record) {
        assert record != null;
        this.record = record;
        appendNameKeywordsToOutputString();
        appendTagKeywordsToOutputString();
        return outputString.toString();
    }

    /**
     * Appends both included and excluded names to output string
     */
    private void appendNameKeywordsToOutputString() {
        if (hasNameKeywords()) {
            currentStringWidth = 0;
            outputString.append(NAME_KEYWORDS_HEADING);
            appendIncludedKeywordsFromSet(record.getReadOnlyKeywordsSet(KeywordType.IncludeNames));
            appendExcludedKeywordsFromSet(record.getReadOnlyKeywordsSet(KeywordType.ExcludeNames));
        }
    }

    /**
     * Appends both included and excluded tags to output string
     */
    private void appendTagKeywordsToOutputString() {
        if (hasTagKeywords()) {
            currentStringWidth = 0;
            outputString.append(TAG_KEYWORDS_HEADING);
            appendIncludedKeywordsFromSet(record.getReadOnlyKeywordsSet(KeywordType.IncludeTags));
            appendExcludedKeywordsFromSet(record.getReadOnlyKeywordsSet(KeywordType.ExcludeTags));
        }
    }

    /**
     * Appends excluded keywords to output string
     */
    private void appendExcludedKeywordsFromSet(SortedSet<String> set) {
        for (String keyword : set) {
            ensureStringWidthWithinLimit();
            appendExcludedKeyword(keyword);
            recalculateCurrentStringWidth(keyword);
        }
    }

    /**
     * Appends included keywords to output string
     */
    private void appendIncludedKeywordsFromSet(SortedSet<String> set) {
        for (String keyword : set) {
            ensureStringWidthWithinLimit();
            appendIncludedKeyword(keyword);
            recalculateCurrentStringWidth(keyword);
        }
    }

    /**
     * Starts outputString on a newline if its width is too long.
     */
    private void ensureStringWidthWithinLimit() {
        if (currentStringWidth >= MAX_STRING_WIDTH) {
            outputString.append("\n");
            currentStringWidth = 0;
        }
    }

    private boolean hasTagKeywords() {
        return !record.getReadOnlyKeywordsSet(KeywordType.IncludeTags).isEmpty()
                || !record.getReadOnlyKeywordsSet(KeywordType.ExcludeTags).isEmpty();
    }

    private boolean hasNameKeywords() {
        return !record.getReadOnlyKeywordsSet(KeywordType.IncludeNames).isEmpty()
                || !record.getReadOnlyKeywordsSet(KeywordType.ExcludeNames).isEmpty();
    }

    private void appendIncludedKeyword(String keyword) {
        String prefixedKeyword = String.format(PREFIXED_KEYWORD_FORMAT, INCLUDE_PREFIX, keyword);
        outputString.append(prefixedKeyword);
    }

    private void appendExcludedKeyword(String keyword) {
        String prefixedKeyword = String.format(PREFIXED_KEYWORD_FORMAT, EXCLUDE_PREFIX, keyword);
        outputString.append(prefixedKeyword);
    }

    private void recalculateCurrentStringWidth(String keyword) {
        if (keyword != null) {
            currentStringWidth += keyword.length() + EXTRA_PADDING_LENGTH;
        }
    }
}
