//@@author lekoook
package seedu.address.model.autocomplete;

/**
 * Pair object used in parsing of commands for auto complete functionality
 */
public class AutoCompleteParserPair {
    public final String parseType;
    public final String parseValue;

    public AutoCompleteParserPair(String parseType, String parseValue) {
        this.parseType = parseType;
        this.parseValue = parseValue;
    }
}
