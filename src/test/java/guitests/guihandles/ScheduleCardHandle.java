package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.schedule.Schedule;

/**
 * Provides a handle to a schedule card in the schedule list panel.
 */
public class ScheduleCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String EMPLOYEEID_FIELD_ID = "#employeeId";
    private static final String DATE_ID = "#dateOfSchedule";
    private static final String TYPE_ID = "#type";

    private final Label idLabel;
    private final Label employeeIdLabel;
    private final Label dateLabel;
    private final Label typeLabel;

    public ScheduleCardHandle(Node cardNode) {
        super(cardNode);
        idLabel = getChildNode(ID_FIELD_ID);
        employeeIdLabel = getChildNode(EMPLOYEEID_FIELD_ID);
        dateLabel = getChildNode(DATE_ID);
        typeLabel = getChildNode(TYPE_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getEmployeeId() {
        return employeeIdLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public String getType() {
        return typeLabel.getText();
    }


    /**
     * Returns true if this handle contains {@code schedule}.
     */
    public boolean equals(Schedule schedule) {
        return getEmployeeId().equals(schedule.getEmployeeId().value)
                && getDate().equals(schedule.getScheduleDate().value)
                && getType().equals(schedule.getType().value);
    }

}
