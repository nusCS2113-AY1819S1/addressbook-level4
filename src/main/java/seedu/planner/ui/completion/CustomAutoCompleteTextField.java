package seedu.planner.ui.completion;

import java.util.Collection;

import org.controlsfx.control.textfield.AutoCompletionBinding;

import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.scene.control.TextField;

/**
 * An intermediate class that creates the auto-completion
 */
public class CustomAutoCompleteTextField {

    /**
     * Create a new auto-completion binding between the given {@link TextField}
     * using the given auto-complete suggestions
     *
     * @param textField The {@link TextField} to which auto-completion shall be added
     * @param possibleSuggestions Possible auto-complete suggestions
     * @return The AutoCompletionBinding
     */
    public static <T> AutoCompletionBinding<T> bindAutoCompletion(
            TextField textField, Collection<T> possibleSuggestions) {
        return new CustomAutoCompletionTextFieldBinding<>(textField,
                SuggestionProvider.create(possibleSuggestions));
    }
}
