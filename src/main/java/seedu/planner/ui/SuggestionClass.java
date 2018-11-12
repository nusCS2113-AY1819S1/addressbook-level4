package seedu.planner.ui;

import java.util.Collection;
import java.util.Comparator;

import org.controlsfx.control.textfield.AutoCompletionBinding;

import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.util.Callback;

/**
 * Class that is reponsible for holding the methods to create a customised autocomplete suggestions
 * provider that follows a similar implementation as the library SuggestionProvider class but with a different
 * match comparison method.
 */
public abstract class SuggestionClass {

    public static <T> SuggestionProvider<T> newCreate(Collection<T> possibleSuggestions) {
        return newCreate(null, possibleSuggestions);
    }

    /**
     * Method to create a new SuggestProvider
     * @param stringConverter used to convert T objects to String
     * @param possibleSuggestions the collection of objects to compare the input String to check for match
     * @param <T> a generic type of suggestions to be held, usually Strings
     * @return a Suggestion Provider
     */
    public static <T> SuggestionProvider<T> newCreate(Callback<T, String> stringConverter,
                                                   Collection<T> possibleSuggestions) {
        SuggestionClassString<T> suggestionProvider = new SuggestionClassString<>(stringConverter);
        suggestionProvider.addPossibleSuggestions(possibleSuggestions);
        return suggestionProvider;
    }

    /**
     * This is a string based suggestion provider.
     * All generic suggestions T are turned into strings for processing.
     */
    private static class SuggestionClassString<T> extends SuggestionProvider<T> {
        private Callback<T, String> stringConverter;

        private final Comparator<T> stringComparator = new Comparator<>() {
            @Override
            public int compare(T o1, T o2) {
                String o1str = stringConverter.call(o1);
                String o2str = stringConverter.call(o2);
                return o1str.compareTo(o2str);
            }
        };

        /**
         * Create a new SuggestionClassString
         * @param stringConverter
         */
        public SuggestionClassString(Callback<T, String> stringConverter) {
            this.stringConverter = stringConverter;

            // In case no stringConverter was provided, use a default strategy
            if (this.stringConverter == null) {
                this.stringConverter = obj -> {
                    return obj != null ? obj.toString() : ""; //$NON-NLS-1$
                };
            }
        }

        @Override
        protected Comparator<T> getComparator() {
            return stringComparator;
        }

        @Override
        protected boolean isMatch(T suggestion, AutoCompletionBinding.ISuggestionRequest request) {
            String userTextLower = request.getUserText().toLowerCase();
            String suggestionStr = suggestion.toString().toLowerCase();
            return suggestionStr.startsWith(userTextLower)
                    && !suggestionStr.equals(userTextLower);
        }
    }

}

