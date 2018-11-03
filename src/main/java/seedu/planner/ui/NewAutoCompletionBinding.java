package seedu.planner.ui;

import java.util.Collection;

import org.controlsfx.control.textfield.AutoCompletionBinding;

import impl.org.controlsfx.autocompletion.SuggestionProvider;
import impl.org.controlsfx.skin.AutoCompletePopup;
import impl.org.controlsfx.skin.AutoCompletePopupSkin;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

/**
 * This object creates the binding for the auto complete popup to the commandBox
 * and handles whenever there is a change in the user input by updating the suggestions
 * and inserts the new suggested text when selected by the user.
 */
public class NewAutoCompletionBinding<T> {

    private final Node completionTarget;
    private final AutoCompletePopup<T> autoCompletionPopup;
    private final Object suggestionsTaskLock = new Object();

    private FetchSuggestionsTask suggestionsTask = null;
    private CustomSuggestionProvider suggestionProvider = new CustomSuggestionProvider();
    private boolean ignoreInputChanges = false;

    private String prevText = "";
    private final StringConverter<T> converter;

    /**
     * This listener observes if there is a change in the text in the command box.
     */

    private final ChangeListener<String> textChangedListener = (obs, oldText, newText) -> {
        int index;
        for (index = newText.length() - 1; index >= 0 && !Character.isWhitespace(newText.charAt(index)); index--);
        if (index > 0) {
            prevText = newText.substring(0, index) + " ";
        } else {
            prevText = "";
        }
        String inputText = newText.substring(index + 1);
        if (getCompletionTarget().isFocused()) {
            setUserInput(newText, inputText);
        }
    };

    /**
     * This listener checks if the commandBox is currently in focus and hides the autocomplete
     * popup if not.
     */
    private final ChangeListener<Boolean> focusChangedListener = (obs, oldFocused, newFocused) -> {
        if (!newFocused) {
            hidePopup();
        }
    };

    /**
     * Creates a new NewAutoCompletionBinding
     *
     * @param completionTarget The target node to which auto-completion shall be added
     * @param converter The converter to be used to convert suggestions to strings
     */

    protected NewAutoCompletionBinding(Node completionTarget,
                                       StringConverter<T> converter) {

        this.completionTarget = completionTarget;
        this.autoCompletionPopup = new AutoCompletePopup<>();
        this.converter = converter;
        this.autoCompletionPopup.setConverter(converter);

        autoCompletionPopup.setStyle("-fx-control-inner-background:grey;"
                + "-fx-accent: #f7f7c3;"
                + "-fx-selection-bar-non-focused:white;"
                + "-fx-font:18px 'Georgia';");

        autoCompletionPopup.setOnSuggestion(sce -> {
            try {
                setIgnoreInputChanges(true);
                completeUserInput(sce.getSuggestion());
                hidePopup();
            } finally {
                setIgnoreInputChanges(false);
            }
        });

        getCompletionTarget().textProperty().addListener(textChangedListener);
        getCompletionTarget().focusedProperty().addListener(focusChangedListener);
    }

    public NewAutoCompletionBinding(TextField textField) {
        this(textField, defaultStringConverter());
    }

    /**
     * String converter to be used to convert suggestions to strings.
     */
    private static <T> StringConverter<T> defaultStringConverter() {
        return new StringConverter<T>() {
            @Override
            public String toString(T t) {
                return t == null ? null : t.toString();
            }

            @SuppressWarnings("unchecked")
            @Override
            public T fromString(String string) {
                return (T) string;
            }
        };
    }

    /**
     * Specifies the number of suggested strings that should appear under the Textfield
     * @param value is the maximum number of rows to be displayed.
     */
    public void setVisibleRowCount(int value) {
        autoCompletionPopup.setVisibleRowCount(value);
    }

    /**
     * Specifies whether the PopupWindow should be hidden when an unhandled
     * escape key is pressed while the popup has focus.
     * @param value
     */
    public void setHideOnEscape(boolean value) {
        autoCompletionPopup.setHideOnEscape(value);
    }

    /**
     * Set the current text the user has entered
     * @param userText
     */
    public final void setUserInput(String newText, String userText) {
        if (!isIgnoreInputChanges()) {
            suggestionProvider.updateSuggestions(newText);
            onUserInputChanged(userText);
        }
    }

    /**
     * Gets the target node for auto completion
     * @return the target node for auto completion
     */
    public TextField getCompletionTarget() {
        return (TextField) completionTarget;
    }

    /**
     * Disposes the binding.
     */
    public void dispose() {
        getCompletionTarget().textProperty().removeListener(textChangedListener);
        getCompletionTarget().focusedProperty().removeListener(focusChangedListener);
    }

    /**
     * Complete the current user-input with the provided completion.
     * @param completion
     */
    private void completeUserInput(T completion) {
        String newText = prevText + converter.toString(completion);
        getCompletionTarget().setText(newText);
        getCompletionTarget().positionCaret(newText.length());
    }

    /**
     * Show the auto completion popup
     */
    private void showPopup() {
        autoCompletionPopup.show(completionTarget);
        selectFirstSuggestion(autoCompletionPopup);
    }

    /**
     * Hide the auto completion targets
     */
    private void hidePopup() {
        autoCompletionPopup.hide();
    }

    /**
     * Selects the first suggestion (if any), so the user can choose it
     * by pressing enter immediately.
     */
    private void selectFirstSuggestion(AutoCompletePopup<?> autoCompletionPopup) {
        Skin<?> skin = autoCompletionPopup.getSkin();
        if (skin instanceof AutoCompletePopupSkin) {
            AutoCompletePopupSkin<?> autoCompleteSkin = (AutoCompletePopupSkin<?>) skin;
            ListView<?> list = (ListView<?>) autoCompleteSkin.getNode();
            if (list.getItems() != null && !list.getItems().isEmpty()) {
                list.getSelectionModel().select(0);
            }
        }
    }

    /**
     * Occurs when the user text has changed and the suggestions require an update
     * @param userText
     */
    private void onUserInputChanged(final String userText) {
        synchronized (suggestionsTaskLock) {
            if (suggestionsTask != null && suggestionsTask.isRunning()) {
                // cancel the current running task
                suggestionsTask.cancel();
            }
            // create a new fetcher task
            suggestionsTask = new FetchSuggestionsTask(userText);
            new Thread(suggestionsTask).start();
        }
    }

    /**
     * Boolean that specifies if the user's changes to the input can be ignored.
     * @return
     */
    private boolean isIgnoreInputChanges() {
        return ignoreInputChanges;
    }

    /**
     * If IgnoreInputChanges is set to true, all changes to the user input are
     * ignored. This is used to avoid self triggering while auto completing.
     * @param state
     */
    private void setIgnoreInputChanges(boolean state) {
        ignoreInputChanges = state;
    }

    /**
     * This task is responsible to fetch suggestions asynchronous
     * by using the current defined suggestionProvider
     */
    private class FetchSuggestionsTask extends Task<Void> implements AutoCompletionBinding.ISuggestionRequest {
        private final String userText;

        public FetchSuggestionsTask(String userText) {
            this.userText = userText;
        }

        @Override
        protected Void call() {
            SuggestionProvider provider = suggestionProvider.getSuggestions();
            if (provider != null) {
                final Collection<T> fetchedSuggestions = provider.call(this);
                if (!isCancelled()) {
                    Platform.runLater(() -> {
                        if (fetchedSuggestions != null && !fetchedSuggestions.isEmpty()) {
                            autoCompletionPopup.getSuggestions().setAll(fetchedSuggestions);
                            showPopup();
                        } else {
                            // No suggestions found, so hide the popup
                            hidePopup();
                        }
                    });
                }
            } else {
                hidePopup();
            }
            return null;
        }

        @Override
        public String getUserText() {
            return userText;
        }
    }

}
