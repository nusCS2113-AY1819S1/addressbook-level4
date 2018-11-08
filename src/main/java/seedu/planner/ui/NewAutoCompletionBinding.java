package seedu.planner.ui;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;

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
 * and updates the list of suggestions for the commandbox
 */
public class NewAutoCompletionBinding<T> {

    private final Node completionTarget;
    private final AutoCompletePopup<T> autoCompletionPopup;
    private final Object suggestionsTaskLock = new Object();

    private FetchSuggestionsTask suggestionsTask = null;
    private CustomSuggestionProvider suggestionProvider = new CustomSuggestionProvider();
    private boolean ignoreInputChanges = false;

    private String prefixText = "";
    private String suffixText = "";
    private final StringConverter<T> converter = defaultStringConverter();

    /**
     * This listener observes the position of the caret in the command box and passed different String
     * arguments to allow for accurate text suggestions.
     */
    private final ChangeListener<Number> caretChangedListener = (obs, oldPosition, newPosition) -> {
        String newText = getCompletionTarget().getText();
        String inputText;
        String prefix = "";
        if (newPosition.intValue() == newText.length()) {
            inputText = caretAtEnd(newPosition, newText);
        } else {
            if (newPosition.intValue() == 0) {
                inputText = resetTexts();
            } else {
                if (Character.isWhitespace(newText.charAt(newPosition.intValue()))
                        && Character.isWhitespace(newText.charAt(newPosition.intValue() - 1))) {
                    inputText = resetTexts();
                } else {
                    if (Character.isWhitespace(newText.charAt(newPosition.intValue()))) {
                        inputText = caretInBetween(newPosition, newText);
                    } else {
                        inputText = resetTexts();
                    }
                }
            }
        }

        if (inputText.startsWith(PREFIX_TAG.getPrefix())) {
            prefixText += PREFIX_TAG.getPrefix();
            prefix = PREFIX_TAG.getPrefix();
            inputText = inputText.substring(2);
        }

        if (getCompletionTarget().isFocused()) {
            setUserInput(newText, prefix, inputText);
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
     * @param completionTarget The target node to which auto-completion shall be added
     */

    public NewAutoCompletionBinding(Node completionTarget) {

        this.completionTarget = completionTarget;
        this.autoCompletionPopup = new AutoCompletePopup<>();
        this.autoCompletionPopup.setConverter(converter);

        autoCompletionPopup.setStyle("-fx-control-inner-background:black;"
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
        init();
    }

    /**
     * Splits the input text accordingly when the caret is found to be at the end of the command box
     * @param newPosition is the current position of the caret
     * @param newText is the entire input in the command box
     * @return
     */
    private String caretAtEnd(Number newPosition, String newText) {
        int startIndex = newPosition.intValue();
        String inputText;
        suffixText = "";
        if (newPosition.intValue() > 0) {
            startIndex--;
            for (; startIndex > 0 && !Character.isWhitespace(newText.charAt(startIndex)); startIndex--);
        }
        inputText = newText.substring((startIndex == 0) ? startIndex : startIndex + 1);
        prefixText = newText.substring(0, (startIndex == 0) ? startIndex : startIndex + 1);
        return inputText;
    }

    /**
     * Splits the input text when the caret is found in the middle of the string in the command box
     * and is found at the end of a word.
     * @param newText
     * @param newPosition
     * @return
     */
    private String caretInBetween(Number newPosition, String newText) {
        int startIndex = newPosition.intValue();
        int endIndex = newPosition.intValue();
        String inputText;
        startIndex--;
        for (; startIndex > 0 && !Character.isWhitespace(newText.charAt(startIndex)); startIndex--);
        prefixText = (startIndex == 0) ? "" : newText.substring(0, startIndex + 1);

        for (; endIndex < newText.length()
                && !Character.isWhitespace(newText.charAt(endIndex)); endIndex++);
        suffixText = (endIndex == newText.length()) ? "" : newText.substring(endIndex);

        inputText = newText.substring((startIndex == 0) ? startIndex : startIndex + 1, (
                endIndex == newText.length()) ? endIndex : endIndex);
        return inputText;
    }

    /**
     * Reset the text Strings to an empty String when there is no word to autocomplete
     * @return the Word to be autocompleted
     */
    private String resetTexts() {
        String inputText;
        prefixText = "";
        suffixText = "";
        inputText = "";
        return inputText;
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
     * Updates the current text input by the user.
     * @param userInput is the entire string input
     * @param wordInput is the last word in the input to be autocompleted
     */
    public final void setUserInput(String userInput, String prefix, String wordInput) {
        if (!isIgnoreInputChanges()) {
            suggestionProvider.updateSuggestions(userInput, prefix, wordInput);
            onUserInputChanged(wordInput);
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
     * Initialise the binding for listeners.
     */
    private void init() {
        getCompletionTarget().caretPositionProperty().addListener(caretChangedListener);
        getCompletionTarget().focusedProperty().addListener(focusChangedListener);
    }

    /**
     * Disposes the binding for listeners.
     */
    public void dispose() {
        getCompletionTarget().caretPositionProperty().removeListener(caretChangedListener);
        getCompletionTarget().focusedProperty().removeListener(focusChangedListener);
    }

    /**
     * Complete the current user-input with the provided completion.
     * @param completion
     */
    private void completeUserInput(T completion) {
        dispose();
        String newText = prefixText + converter.toString(completion) + suffixText;
        getCompletionTarget().setText(newText);
        getCompletionTarget().positionCaret(newText.length());
        init();
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
