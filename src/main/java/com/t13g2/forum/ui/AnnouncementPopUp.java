package com.t13g2.forum.ui;

import java.util.logging.Logger;

import com.t13g2.forum.commons.core.LogsCenter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Controller for a help page
 */
public class AnnouncementPopUp extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(AnnouncementPopUp.class);
    private static final String FXML = "AnnouncementPopUp.fxml";

    /**
     * Creates a new AnnouncementPopUp.
     *
     * @param root Stage to use as the root of the AnnouncementPopUp.
     */
    public AnnouncementPopUp(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new AnnouncementPopUp.
     */
    public AnnouncementPopUp() {
        this(new Stage());
    }

    /**
     * Shows the AnnouncementPopUp.
     * @throws IllegalStateException
     */
    public void show(String aTitle, String aContent) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 200);

        Label lblContent = new Label(aContent);
        lblContent.setWrapText(true);
        lblContent.setFont(new Font("Arial", 20));
        grid.add(lblContent, 0, 0, 2, 1);

        Button btnClose = new Button();
        btnClose.setText("Close");

        btnClose.setOnAction(event -> {
            getRoot().close(); // return to main window
        });
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(btnClose);
        grid.add(hbBtn, 1, 1, 2, 1);

        getRoot().setTitle(aTitle);
        getRoot().setScene(scene);
        logger.fine("Showing announcement pop up.");
        getRoot().show();
    }

    /**
     * Returns true if the announcement pop up is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the announcement pop up.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
