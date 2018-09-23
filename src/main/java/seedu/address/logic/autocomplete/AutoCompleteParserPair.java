package seedu.address.logic.autocomplete;

public class AutoCompleteParserPair {
    public final String parseType;
    public final String parseValue;

    public AutoCompleteParserPair(String parseType, String parseValue) {
        this.parseType = parseType;
        this.parseValue = parseValue;
    }
}
