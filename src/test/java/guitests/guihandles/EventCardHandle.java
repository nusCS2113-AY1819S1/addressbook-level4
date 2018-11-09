package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

import seedu.address.model.event.Event;


/**
 * Provides a handle to a event card in the event list panel.
 */
public class EventCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#eventId";
    private static final String EVENT_NAME_FIELD_ID = "#eventName";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String LOCATION_FIELD_ID = "#eventLocation";
    private static final String DATE_FIELD_ID = "#eventDate";
    private static final String START_TIME_FIELD_ID = "#startTime";
    private static final String END_TIME_FIELD_ID = "#endTime";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label descriptionLabel;
    private final Label locationLabel;
    private final Label dateLabel;
    private final Label startTimeLabel;
    private final Label endTimeLabels;

    public EventCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(EVENT_NAME_FIELD_ID);
        descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
        locationLabel = getChildNode(LOCATION_FIELD_ID);
        dateLabel = getChildNode(DATE_FIELD_ID);
        startTimeLabel = getChildNode(START_TIME_FIELD_ID);
        endTimeLabels = getChildNode(END_TIME_FIELD_ID);

    }

    public String getId() {
        return idLabel.getText();
    }

    public String getEventName() {
        return nameLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public String getLocation() {
        return locationLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public String getStartTime() {
        return startTimeLabel.getText();
    }

    public String getEndTime() {
        return endTimeLabels.getText();
    }


    /**
     * Returns true if this handle contains {@code event}.
     */
    public boolean equals(Event event) {
        return getEventName().equals(event.getEventName().fullName)
                && getDescription().equals(event.getDescription().value)
                && getLocation().equals(event.getLocation().value)
                && getDate().equals(event.getDate().eventDate)
                && getStartTime().equals(event.getStartTime().time)
                && getEndTime().equals(event.getEndTime().time);
    }
}
