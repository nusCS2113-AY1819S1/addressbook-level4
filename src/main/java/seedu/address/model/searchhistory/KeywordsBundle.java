package seedu.address.model.searchhistory;

import java.util.List;

/**
 * A utility class to store the list of keywords and its type.
 */
public class KeywordsBundle {
    protected KeywordType type;
    protected List<String> keywords;

    public KeywordsBundle (KeywordType type, List<String> keywords) {
        assert type != null;
        assert keywords != null;
        this.type = type;
        this.keywords = keywords;
    }

    public KeywordType getType() {
        return type;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof KeywordsBundle)) {
            return false;
        }

        KeywordsBundle other = (KeywordsBundle) obj;
        return type == other.type && keywords.equals(other.keywords);
    }
}
