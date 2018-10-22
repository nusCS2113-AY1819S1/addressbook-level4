package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

public class MePanel extends UiPart<Region> {

    private static final String FXML = "MePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MePanel.class);

    public MePanel(URL fxmlFileUrl) {
        super(fxmlFileUrl);
    }
}
