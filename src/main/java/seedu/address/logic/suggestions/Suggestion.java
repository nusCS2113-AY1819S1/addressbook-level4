package seedu.address.logic.suggestions;

import java.util.List;

/**
 * API of the suggestion component
 */
public interface Suggestion {
    /**
     * Executes getSuggestion
     * @param input any user input that requires suggestions
     * @return a list of possible suggestions
     */
    List<String> getSuggestions(String input);
}
