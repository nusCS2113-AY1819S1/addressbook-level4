package seedu.recruit.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.recruit.commons.core.LogsCenter;
import seedu.recruit.model.candidate.Candidate;

/**
 * Panel containing the master list of candidates when on Candidate Book
 */
public class MasterCandidateListPanel extends UiPart<Region> {
    private static final String FXML = "MasterCandidateListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MasterCandidateListPanel.class);

    @FXML
    private ListView<Candidate> candidateListView;

    public MasterCandidateListPanel(ObservableList<Candidate> candidateList) {
        super(FXML);
        setConnectionsForCandidateList(candidateList);
        registerAsAnEventHandler(this);
    }

    private void setConnectionsForCandidateList(ObservableList<Candidate> candidateList) {
        candidateListView.setItems(candidateList);
        candidateListView.setCellFactory(listView -> new CandidateListViewCell());
        //setEventHandlerForCandidateListSelectionChangeEvent();
    }

    /**
    private void setEventHandlerForCandidateListSelectionChangeEvent() {
        candidateListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in master candidate list changed to : '" + newValue + "'");
                        raise(new CandidateDetailsPanelSelectionChangedEvent(newValue));
                    }
                });
    } */

    /**
     * Scrolls to the {@code CandidateCard} at the {@code index} and selects it.

    private void scrollToCandidateCard(int index) {
        Platform.runLater(() -> {
            candidateListView.scrollTo(index);
            candidateListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToCandidateListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollToCandidateCard(event.targetIndex);
    }

    @Subscribe
    private void handleCandidateDetailsPanelSelectionChangedEvent(CandidateDetailsPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Selection Changed to " + event.getNewSelection().getName().fullName));
    } */

    /**
     * Custom {@code ListCell} that displays the graphics of a Candidate using a {@code CandidateCard}.
     */
    class CandidateListViewCell extends ListCell<Candidate> {
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
