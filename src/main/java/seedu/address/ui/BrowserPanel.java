package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
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

    private Person person;

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
    @FXML
    private VBox welcomePane;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        registerAsAnEventHandler(this);
    }

    /**
     * updates panel based on selected person.
     */
    private void loadPersonPage(Person person) {
        this.person = person;
        welcomePane.setOpacity(0);
        tagPanel.getChildren().clear();
        String nameText = person.getName().toString();
        if (person.positionDoesExist()) {
            nameText += "\n" + person.getPosition().toString();
        }
        nameLabel.setText(nameText);
        addressLabel.setText(person.getAddress().toString());
        phoneLabel.setText(person.getPhone().toString());
        emailLabel.setText(person.getEmail().toString());
        if (person.kpiDoesExist()) {
            kpiLabel.setText("KPI: " + person.getKpi().toString());
        } else {
            kpiLabel.setText("KPI: ");
        }
        if (person.noteDoesExist()) {
            noteLabel.setText(person.getNote().toString());
        } else {
            noteLabel.setText("");
        }
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
