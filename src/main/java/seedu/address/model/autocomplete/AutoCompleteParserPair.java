//@@author lekoook
package seedu.address.model.autocomplete;

import seedu.address.logic.parser.Prefix;

/**
 * Pair object used in parsing of commands for auto complete functionality
 */
public class AutoCompleteParserPair {
    public final Prefix prefixType;
    public final String prefixValue;

    public AutoCompleteParserPair(Prefix prefixType, String prefixValue) {
        this.prefixType = prefixType;
        this.prefixValue = prefixValue.trim();
    }
}
