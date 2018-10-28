package seedu.planner.ui;

import javax.swing.plaf.synth.Region;

import javafx.scene.control.TextField;

/**
 * The UI component that is responsible for displaying the possible auto complete text inputs
 * under the CommandBox UI component.
 */
public class AutoCompleteBox extends UiPart<Region> {

    private static final String FXML = "AutoCompleteBox.fxml";
    private static final int MAX_ROWS = 5;

    public AutoCompleteBox(TextField commandTextField) {
        super(FXML);
        CustomSuggestionProvider suggestionProvider = new CustomSuggestionProvider();
        new CustomAutoCompletionTextFieldBinding<>(commandTextField,
                suggestionProvider.getSuggestions()).setVisibleRowCount(MAX_ROWS);
    }

}
