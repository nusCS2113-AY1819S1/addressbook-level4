package seedu.planner.ui.completion;

import java.util.Collection;

import org.controlsfx.control.textfield.AutoCompletionBinding;

import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.scene.control.TextField;

public class CustomAutoCompleteTextField {

    public static <T> AutoCompletionBinding<T> bindAutoCompletion(
            TextField textField, Collection<T> possibleSuggestions) {
        return new CustomAutoCompletionTextFieldBinding<>(textField,
                SuggestionProvider.create(possibleSuggestions));
    }
}
