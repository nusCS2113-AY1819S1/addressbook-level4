package seedu.address.ui;

import javafx.scene.shape.Rectangle;
import seedu.address.model.person.TimeSlot;


/**
 * A graphical representation of a {@code TimeSlot} to be displayed on the UI
 */
public class TimeTablePanelTimeSlot {
    public final TimeSlot timeSlot;
    private Rectangle box;

    public TimeTablePanelTimeSlot(TimeSlot timeSlot, double currRowDimensions, double currColDimensions) {
        this.timeSlot = timeSlot;

        box = new Rectangle(currColDimensions * timeSlot.getDuration().toMinutes() / 60.0, currRowDimensions);
        box.setTranslateX(timeSlot.getStartTime().getMinute() / 60.0 * currColDimensions);
        box.setFill(timeSlot.getColor());
        box.setOpacity(0.5);
        box.setArcHeight(10);
        box.setArcWidth(10);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TimeTablePanelTimeSlot)) {
            return false;
        }

        // state check
        TimeTablePanelTimeSlot card = (TimeTablePanelTimeSlot) other;

        return timeSlot.equals(card.timeSlot);
    }

    public Rectangle getBox() {
        return box;
    }
}
