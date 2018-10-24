package seedu.recruit.ui;

import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.recruit.commons.core.LogsCenter;

public class EmailPreview extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(EmailPreview.class);
    private static final String FXML = "EmailPreview.fxml";

    private Text preview = new Text();
    private ScrollPane scrollPane = new ScrollPane(preview);
    private Scene scene = new Scene(scrollPane, 400, 400);

    public EmailPreview(Stage root) {
        super(FXML, root);
        root.setScene(scene);
    }

    public EmailPreview() {
        this(new Stage());
    }

    public void setEmailPreview(String emailPreview) {
        preview.setText(emailPreview);
    }

    public void show() {
        logger.fine("Showing email preview");
        getRoot().show();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}
