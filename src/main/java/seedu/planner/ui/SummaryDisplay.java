package seedu.planner.ui;

import javafx.scene.layout.Region;

/**
 * This UI component is responsible for displaying the summary requested by the user
 */
public class SummaryDisplay extends UiPart<Region> {

    private static final String FXML = "SummaryDisplay.fxml";

    public SummaryDisplay() {
        super(FXML);
        registerAsAnEventHandler(this);
    }


}
