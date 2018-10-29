package seedu.address.testutil;

import java.util.List;

import seedu.address.logic.commands.formatter.KeywordsOutputFormatter;
import seedu.address.model.searchhistory.KeywordType;
import seedu.address.model.searchhistory.KeywordsRecord;

/**
 * A utility class to generate output string of keywords
 */
public class KeywordsOutputUtil {

    public static String getOutputString (List<String> includedTags, List<String> includedNames,
                                              List<String> excludedTags, List<String> excludedNames) {
        KeywordsRecord record = prepareKeywordsRecord(includedNames, excludedNames, includedTags, excludedTags);
        KeywordsOutputFormatter formatter = new KeywordsOutputFormatter();
        return formatter.getOutputString(record);
    }

    /**
     * Prepares keywordRecords according to the provided parameters
     */
    private static KeywordsRecord prepareKeywordsRecord(List<String> includedNames, List<String> excludedNames,
                                                        List<String> includedTags, List<String> excludedTags) {
        KeywordsRecord record = new KeywordsRecord();
        if (includedNames != null) {
            record.recordKeywords(KeywordType.IncludeNames, includedNames);
        }
        if (excludedNames != null) {
            record.recordKeywords(KeywordType.ExcludeNames, excludedNames);
        }
        if (includedTags != null) {
            record.recordKeywords(KeywordType.IncludeTags, includedTags);
        }
        if (excludedTags != null) {
            record.recordKeywords(KeywordType.ExcludeTags, excludedTags);
        }
        return record;
    }
}
