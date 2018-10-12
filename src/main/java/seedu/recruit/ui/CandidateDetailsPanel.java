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
import seedu.recruit.commons.events.ui.CandidateDetailsPanelSelectionChangedEvent;
import seedu.recruit.model.candidate.Candidate;

/**
 * Panel containing the list of candidates,
 * and their details upon selection
 */
public class CandidateDetailsPanel extends UiPart<Region> {
    private static final String FXML = "CandidateDetailsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CandidateDetailsPanel.class);

    @FXML
    private ListView<Candidate> candidateDetailsView;
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

    public CandidateDetailsPanel(ObservableList<Candidate> candidateList) {
        super(FXML);
        setConnections(candidateList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Candidate> candidateList) {
        candidateDetailsView.setItems(candidateList);
        candidateDetailsView.setCellFactory(listView -> new CandidateDetailsViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        candidateDetailsView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in candidate details panel changed to : '" + newValue + "'");
                        raise(new CandidateDetailsPanelSelectionChangedEvent(newValue));
                        showDetailsOfSelectedCandidate();
                    }
                });
    }

    /**
     * Expands {@code CandidateCard} by listing all the details of the selected Candidate
     */
    private void showDetailsOfSelectedCandidate() {
        Candidate selectedCandidate = candidateDetailsView.getSelectionModel().getSelectedItem();
        name.setText(selectedCandidate.getName().fullName);
        gender.setText(selectedCandidate.getGender().value);
        age.setText(selectedCandidate.getAge().value);
        phone.setText(selectedCandidate.getPhone().value);
        email.setText(selectedCandidate.getEmail().value);
        address.setText(selectedCandidate.getAddress().value);
        desired_job.setText(selectedCandidate.getJob().value);
        education.setText(selectedCandidate.getEducation().value);
        salary.setText(selectedCandidate.getSalary().value);
    }

        /**
         * Scrolls to the {@code CandidateCard} at the {@code index} and selects it.
         */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            candidateDetailsView.scrollTo(index);
            candidateDetailsView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Candidate} using a {@code CandidateCard}.
     */
    class CandidateDetailsViewCell extends ListCell<Candidate> {
        @Override
        protected void updateItem(Candidate candidate, boolean empty) {
            super.updateItem(candidate, empty);

            if (empty || candidate == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CandidateCard(candidate, getIndex() + 1).getRoot());
            }
        }
    }
}
