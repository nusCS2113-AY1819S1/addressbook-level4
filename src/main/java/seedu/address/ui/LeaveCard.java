package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.leave.Leave;

/**
 * An UI component that displays information of a {@code Leave}.
 */
public class LeaveCard extends UiPart<Region> {
    private static final String FXML = "LeaveListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Leave leave;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label nric;
    @FXML
    private Label date;
    @FXML
    private Label status;

    public LeaveCard(Leave leave, int displayedIndex) {
        super(FXML);
        this.leave = leave;
        id.setText(displayedIndex + ". ");
        nric.setText(leave.getEmployeeId().nric);
        date.setText(leave.getDate().date);
        status.setText(leave.getApproval().status);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LeaveCard)) {
            return false;
        }

        // state check
        LeaveCard card = (LeaveCard) other;
        return id.getText().equals(card.id.getText())
                && leave.equals(card.leave);
    }

}
