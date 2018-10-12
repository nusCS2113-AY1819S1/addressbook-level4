package seedu.recruit.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.recruit.commons.core.LogsCenter;
import seedu.recruit.commons.events.ui.JumpToListRequestEvent;
import seedu.recruit.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.recruit.model.candidate.Candidate;

/**
 * Panel containing the list of persons,
 * and their details upon selection
 */
public class PersonDetailsPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonDetailsPanel.class);

    @FXML
    private ListView<Candidate> personDetailsView;
    @FXML
    private Label name;
    @FXML
    private Label gender;
    @FXML
    private Label age;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label desired_job;
    @FXML
    private Label education;
    @FXML
    private Label salary;

    public PersonDetailsPanel(ObservableList<Candidate> candidateList) {
        super(FXML);
        setConnections(candidateList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Candidate> candidateList) {
        personDetailsView.setItems(candidateList);
        personDetailsView.setCellFactory(listView -> new PersonDetailsViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        personDetailsView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in candidate details panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue));
                        showDetailsOfSelectedCandidate();
                    }
                });
    }

    /**
     * Expands {@code PersonCard} by listing all the details of the selected Candidate
     */
    private void showDetailsOfSelectedCandidate() {
        Candidate selectedPerson = personDetailsView.getSelectionModel().getSelectedItem();
        name.setText(selectedPerson.getName().fullName);
        gender.setText(selectedPerson.getGender().value);
        age.setText(selectedPerson.getAge().value);
        phone.setText(selectedPerson.getPhone().value);
        email.setText(selectedPerson.getEmail().value);
        address.setText(selectedPerson.getAddress().value);
        desired_job.setText(selectedPerson.getJob().value);
        education.setText(selectedPerson.getEducation().value);
        salary.setText(selectedPerson.getSalary().value);
    }

        /**
         * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
         */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            personDetailsView.scrollTo(index);
            personDetailsView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Candidate} using a {@code PersonCard}.
     */
    class PersonDetailsViewCell extends ListCell<Candidate> {
        @Override
        protected void updateItem(Candidate candidate, boolean empty) {
            super.updateItem(candidate, empty);

            if (empty || candidate == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(candidate, getIndex() + 1).getRoot());
            }
        }
    }
}
