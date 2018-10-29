package seedu.address.model.searchhistory;

import java.util.List;

/**
 * A utility class to store the list of keywords and its type.
 */
public class KeywordsBundle {
    private KeywordType type;
    private List<String> keywords;

    KeywordsBundle (KeywordType type, List<String> keywords) {
        this.type = type;
        this.keywords = keywords;
    }

    public KeywordType getType() {
        return type;
    }

    public List<String> getKeywords() {
        return keywords;
    }
}
