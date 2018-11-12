package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.workout.Parameter;

/**
 * An UI component that displays information of a {@code Workout}.
 */
public class TrackedDataListCard extends UiPart<Region> {

    private static final String FXML = "TrackedDataListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Parameter parameter;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label prefix;
    @FXML
    private Label value;

    public TrackedDataListCard(Parameter parameter, int displayedIndex) {
        super(FXML);
        this.parameter = parameter;
        id.setText(displayedIndex + ". ");
        prefix.setText(parameter.getPrefix().getPrefix());
        value.setText(parameter.getValue());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TrackedDataListCard)) {
            return false;
        }

        // state check
        TrackedDataListCard card = (TrackedDataListCard) other;
        return id.getText().equals(card.id.getText())
                && parameter.equals(card.parameter);
    }
}
