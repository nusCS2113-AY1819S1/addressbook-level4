package seedu.divelog.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.divelog.model.dive.DiveSession;

//@@author arjo129
/**
 * An UI component that displays information of a {@code DiveSession}.
 */
public class DiveSessionCard extends UiPart<Region> {

    public static final String DIVE_PREFIX = "Dive at ";
    public static final String FORMAT_START_TIME = "Date Started: %s";
    public static final String FORMAT_END_TIME = "Date Ended: %s";
    private static final String FXML = "DiveListCards.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on DiveLog level 4</a>
     */
    public final DiveSession dive;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label depth;
    @FXML
    private Label dateStart;
    @FXML
    private Label dateEnd;

    public DiveSessionCard(DiveSession dive, int displayedIndex) {
        super(FXML);
        this.dive = dive;
        id.setText(displayedIndex + ". ");
        name.setText(DIVE_PREFIX + dive.getLocation().getLocationName());
        depth.setText(dive.getDepthProfile().getFormattedString());
        dateStart.setText(String.format(FORMAT_START_TIME, dive.getDateStart().getOurDateString()));
        dateEnd.setText(String.format(FORMAT_END_TIME, dive.getDateEnd().getOurDateString()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DiveSessionCard)) {
            return false;
        }

        // state check
        DiveSessionCard card = (DiveSessionCard) other;
        return id.getText().equals(card.id.getText())
                && dive.equals(card.dive);
    }

}
