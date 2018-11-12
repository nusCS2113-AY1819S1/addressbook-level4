package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.schedule.Schedule;

/**
 * An UI component that displays information of a {@code schedule}.
 */
public class ScheduleCard extends UiPart<Region> {

    private static final String FXML = "ScheduleListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Schedule schedule;

    @FXML
    private Label id;
    @FXML
    private Label employeeId;
    @FXML
    private Label employeeIdLabel;
    @FXML
    private Label type;
    @FXML
    private Label typeLabel;
    @FXML
    private Label dateOfSchedule;
    @FXML
    private Label dateOfScheduleLabel;

    public ScheduleCard(Schedule schedule, int displayedIndex) {
        super(FXML);
        this.schedule = schedule;
        id.setText(displayedIndex + ". ");
        employeeId.setText(schedule.getEmployeeId().value);
        employeeIdLabel.setText("Employee ID: ");
        typeLabel.setText("Type :");
        type.setText(schedule.getType().value);
        dateOfScheduleLabel.setText("Date: ");
        dateOfSchedule.setText(schedule.getScheduleDate().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduleCard)) {
            return false;
        }

        // state check
        ScheduleCard card = (ScheduleCard) other;
        return id.getText().equals(card.id.getText())
                && employeeId.getText().equals(card.employeeId.getText())
                && type.getText().equals(card.type.getText())
                && dateOfSchedule.getText().equals(card.dateOfSchedule.getText())
                && schedule.equals(card.schedule);
    }
}
