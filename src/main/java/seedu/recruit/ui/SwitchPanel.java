package seedu.recruit.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import seedu.recruit.commons.core.LogsCenter;

/**
 * Panel containing the list of persons,
 * and their details upon selection
 */
public class SwitchPanel extends UiPart<Region> {
    private static final String FXML = "SwitchPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private BorderPane rootStage;

    public SwitchPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }


}
