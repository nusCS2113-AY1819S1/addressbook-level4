package seedu.address.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label nameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label kpiLabel;
    @FXML
    private Label noteLabel;
    @FXML
    private FlowPane tagPanel;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    private void loadPersonPage(Person person) {
        tagPanel.getChildren().clear();
        nameLabel.setText(person.getName().toString() + "\n" + person.getPosition().toString());
        addressLabel.setText(person.getAddress().toString());
        phoneLabel.setText(person.getPhone().toString());
        emailLabel.setText(person.getEmail().toString());
        kpiLabel.setText("KPI: " + person.getKpi().toString());
        noteLabel.setText(person.getNote().toString());
        List<Label> highPriorityTags = new ArrayList<>();
        List<Label> mediumPriorityTags = new ArrayList<>();
        List<Label> lowPriorityTags = new ArrayList<>();
        person.getTags().forEach(tag -> {
                    Label newLabel = new Label(tag.tagName);
                    if (tag.priority.getZeroBased() == Tag.PRIORITY_HIGH) {
                        newLabel.setStyle("-fx-text-fill:Black; -fx-border-color:red; -fx-background-color: red; "
                                + "-fx-border-radius: 5 5 5 5;"
                                + "-fx-background-radius: 5 5 5 5; ");
                        highPriorityTags.add(newLabel);
                    }
                    if (tag.priority.getZeroBased() == Tag.PRIORITY_MEDIUM) {
                        newLabel.setStyle("-fx-text-fill:Black; -fx-border-color:yellow; -fx-background-color: yellow; "
                                + "-fx-border-radius: 5 5 5 5;"
                                + "-fx-background-radius: 5 5 5 5;");
                        mediumPriorityTags.add(newLabel);
                    }
                    if (tag.priority.getZeroBased() == Tag.PRIORITY_LOW) {
                        newLabel.setStyle("-fx-text-fill:Black; -fx-border-color:green; -fx-background-color: green;"
                                + "-fx-border-radius: 5 5 5 5;"
                                + "-fx-background-radius: 5 5 5 5;");
                        lowPriorityTags.add(newLabel);
                    }
                }
        );
        for (Label label : highPriorityTags) {
            tagPanel.getChildren().add(label);
        }
        for (Label label : mediumPriorityTags) {
            tagPanel.getChildren().add(label);
        }
        for (Label label : lowPriorityTags) {
            tagPanel.getChildren().add(label);
        }
    }


    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection());
    }
}
