package seedu.address.logic.commands.formatter;

import seedu.address.model.searchhistory.ReadOnlyKeywordsRecord;

/**
 * A class that creates output strings of keywords according to KeywordsRecord
 */
public class KeywordsOutputFormatter {

    private static final int MAX_STRING_WIDTH = 100;
    private static final int EXTRA_PADDING_LENGTH = 2;
    private static final char INCLUDE_PREFIX = '+';
    private static final char EXCLUDE_PREFIX = '-';
    private static final String TAG_KEYWORDS_HEADING = "\n=================== Tag Keywords History ====================\n";
    private static final String NAME_KEYWORDS_HEADING = "\n=================== Name Keywords History ===================\n";
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
            for (String keyword: record.getIncludedNames()) {
                ensureStringWidthWithinLimit();
                appendIncludedKeyword(keyword);
                recalculateCurrentStringWidth(keyword);
            }
            for (String keyword: record.getExcludedNames()) {
                ensureStringWidthWithinLimit();
                appendExcludedKeyword(keyword);
                recalculateCurrentStringWidth(keyword);
            }
        }
    }

    /**
     * Appends both included and excluded tags to output string
     */
    private void appendTagKeywordsToOutputString() {
        if (hasTagKeywords()) {
            currentStringWidth = 0;
            outputString.append(TAG_KEYWORDS_HEADING);
            for (String keyword: record.getIncludedTags()) {
                ensureStringWidthWithinLimit();
                appendIncludedKeyword(keyword);
                recalculateCurrentStringWidth(keyword);
            }
            for (String keyword: record.getExcludedTags()) {
                ensureStringWidthWithinLimit();
                appendExcludedKeyword(keyword);
                recalculateCurrentStringWidth(keyword);
            }
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
        return !record.getIncludedTags().isEmpty()
                || !record.getExcludedTags().isEmpty();
    }

    private boolean hasNameKeywords() {
        return !record.getIncludedNames().isEmpty()
                || !record.getExcludedNames().isEmpty();
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
