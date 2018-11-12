package guitests.guihandles;

import static seedu.divelog.ui.DiveSessionCard.FORMAT_END_TIME;
import static seedu.divelog.ui.DiveSessionCard.FORMAT_START_TIME;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.ui.DiveSessionCard;
//@@author arjo129
/**
 * Provides a handle to a dive card in the dive list panel.
 */
public class DiveSessionCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String DEPTH_FIELD_ID = "#depth";
    private static final String DATE_START_ID = "#dateStart";
    private static final String DATE_END_ID = "#dateEnd";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label depth;
    private final Label dateStart;
    private final Label dateEnd;

    public DiveSessionCardHandle(Node cardNode) {
        super(cardNode);
        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        depth = getChildNode(DEPTH_FIELD_ID);
        dateStart = getChildNode(DATE_START_ID);
        dateEnd = getChildNode(DATE_END_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getDepth() {
        return depth.getText();
    }

    public String getDateStart() {
        return dateStart.getText();
    }

    public String getDateEndId() {
        return dateEnd.getText();
    }

    //@@author shuanang
    /**
     * Returns true if this handle contains {@code dive}.
     */
    public boolean equals(DiveSession dive) {
        return (getName().equals(DiveSessionCard.DIVE_PREFIX + dive.getLocation().getLocationName()))
                && getDepth().equals(dive.getDepthProfile().getFormattedString())
                && getDateStart().equals(String.format(FORMAT_START_TIME, dive.getDateStart().getOurDateString()))
                && getDateEndId().equals(String.format(FORMAT_END_TIME, dive.getDateEnd().getOurDateString()));
    }
}
