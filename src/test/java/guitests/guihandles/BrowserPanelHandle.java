package guitests.guihandles;

import static seedu.divelog.ui.BrowserPanel.FORMAT_DIVE_DEPTH;
import static seedu.divelog.ui.BrowserPanel.FORMAT_DIVE_LOCATION;
import static seedu.divelog.ui.BrowserPanel.FORMAT_END_TIME;
import static seedu.divelog.ui.BrowserPanel.FORMAT_SAFETY_STOP;
import static seedu.divelog.ui.BrowserPanel.FORMAT_START_TIME;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.divelog.model.dive.DiveSession;
//@@author arjo129
/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class BrowserPanelHandle extends NodeHandle<Node> {
    public static final String BROWSER_ID = "#";
    private static final String LOCATION_ID = "#diveLocation";
    private static final String DEPTH_FIELD_ID = "#diveDepth";
    private static final String PG_START_ID = "#pgStart";
    private static final String PG_END_ID = "#pgEnd";
    private static final String START_ID = "#startTime";
    private static final String END_ID = "#endTime";
    private static final String SAFETY_STOP_ID = "#safetyStop";
    private Label diveLocation;
    private Label diveDepth;
    private Label pgStart;
    private Label pgEnd;
    private Label startTime;
    private Label endTime;
    private Label safetyStop;

    public BrowserPanelHandle(Node node) {
        super(node);
        diveLocation = getChildNode(LOCATION_ID);
        diveDepth = getChildNode(DEPTH_FIELD_ID);
        pgStart = getChildNode(PG_START_ID);
        pgEnd = getChildNode(PG_END_ID);
        startTime = getChildNode(START_ID);
        endTime = getChildNode(END_ID);
        safetyStop = getChildNode(SAFETY_STOP_ID);
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof DiveSession)) {
            return false;
        }


        DiveSession dive = (DiveSession) obj;

        String location = String.format(FORMAT_DIVE_LOCATION, dive.getLocation().getLocationName());
        if (!location.equals(diveLocation.getText())) {
            return false;
        }


        String depth = String.format(FORMAT_DIVE_DEPTH, dive.getDepthProfile().getDepth());
        if (!depth.equals(diveDepth.getText())) {
            return false;
        }


        String startingPg = dive.getPressureGroupAtBeginning().getPressureGroup();
        if (!startingPg.equals(pgStart.getText())) {
            return false;
        }


        String endingPg = dive.getPressureGroupAtEnd().getPressureGroup();
        if (!endingPg.equals(pgEnd.getText())) {
            return false;
        }


        String timeStart = String.format(FORMAT_START_TIME, dive.getStart().getTimeString());
        if (!timeStart.equals(startTime.getText())) {
            return false;
        }


        String timeEnd = String.format(FORMAT_END_TIME, dive.getEnd().getTimeString());
        if (!timeEnd.equals(endTime.getText())) {
            return false;
        }


        String timeSafetyStop = String.format(FORMAT_SAFETY_STOP, dive.getSafetyStop().getTimeString());
        if (!timeSafetyStop.equals(safetyStop.getText())) {
            return false;
        }

        return true;
    }
}
