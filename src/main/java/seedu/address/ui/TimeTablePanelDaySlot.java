package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.TimeSlot;

/** TODO ALEXIS: morph this into a daySlot (visually a square on left column in the TimetableGrid)
 * An UI component that displays information of a {@code Person}.
 */
public class TimeTablePanelDaySlot extends UiPart<Region> {

    private static final String FXML = "TimeTablePanelDaySlot.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox cardPane;
    @FXML
    private Label day; //day of daySlot

    public TimeTablePanelDaySlot(String day) {
        super(FXML);
        this.day = new Label(day);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TimeTablePanelDaySlot)) {
            return false;
        }

        // state check
        // TODO ALEXIS: pls verify this whole function makes sense.
        TimeTablePanelDaySlot card = (TimeTablePanelDaySlot) other;
        return day.equals(card.day);
    }
}
