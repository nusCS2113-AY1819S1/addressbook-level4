package seedu.recruit.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.recruit.commons.core.LogsCenter;
import seedu.recruit.commons.events.ui.CandidateDetailsPanelSelectionChangedEvent;
import seedu.recruit.commons.events.ui.JumpToListRequestEvent;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * Left side of the Panel containing the list of company.
 * Upon selection, right side of the panel shows the
 * list of job offers offered in that selected company.
 */
public class ShortlistPanel extends UiPart<Region> {
    private static final String FXML = "ShortlistPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ShortlistPanel.class);

    private CompanyJobDetailsPanel companyJobDetailsPanel;

    @FXML
    private ListView<Candidate> leftPlaceholder;

    @FXML
    private  StackPane rightPlaceholder;

    public ShortlistPanel(ObservableList<Candidate> candidateList, ObservableList<Company> companyList,
                          ObservableList<JobOffer> companyJobList) {
        super(FXML);
        companyJobDetailsPanel = new CompanyJobDetailsPanel(companyList, companyJobList);
        rightPlaceholder.getChildren().add(companyJobDetailsPanel.getRoot());
        leftPlaceholder.setItems(candidateList);
        leftPlaceholder.setCellFactory(listView -> new CandidateDetailsViewCell());
        registerAsAnEventHandler(this);
        setEventHandlerForCandidateSelectionChangeEvent();
    }

    private void setEventHandlerForCandidateSelectionChangeEvent() {
        leftPlaceholder.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in candidate details panel changed to : '" + newValue + "'");
                        raise(new CandidateDetailsPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code CandidateCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            leftPlaceholder.scrollTo(index);
            leftPlaceholder.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToCandidateListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    @Subscribe
    private void handleCandidateDetailsPanelSelectionChangedEvent(CandidateDetailsPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Selection Changed to " + event.getNewSelection().getName().fullName));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a Candidate using a {@code CandidateCard}.
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
