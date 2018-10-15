package seedu.address.ui;

import javafx.scene.shape.Rectangle;
import seedu.address.model.person.TimeSlot;


/**
 * An UI component that displays information of a {@code TimeSlot}.
 * Lives in the TimeTablePanelMainGrid.
 *
 * Will be used to implement normal TimeSlots, and free-TimeSlots.
 */
public class TimeTablePanelTimeSlot {
    public final TimeSlot timeSlot;
    private Rectangle box;

    public TimeTablePanelTimeSlot(TimeSlot timeSlot, double currRowDimensions, double currColDimensions) {
        this.timeSlot = timeSlot;

        box = new Rectangle(currColDimensions * timeSlot.getDuration().toHours(), currRowDimensions);
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
