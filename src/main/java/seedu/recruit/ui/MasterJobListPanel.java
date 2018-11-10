package seedu.recruit.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.recruit.commons.core.LogsCenter;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * Panel containing the master list of job offers when on Company Book
 */
public class MasterJobListPanel extends UiPart<Region> {
    private static final String FXML = "MasterJobListPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(MasterJobListPanel.class);

    @FXML
    private ListView<JobOffer> jobListView;

    public MasterJobListPanel(ObservableList<JobOffer> jobList) {
        super(FXML);
        setConnectionsForJobList(jobList);
        //registerAsAnEventHandler(this);
    }

    private void setConnectionsForJobList(ObservableList<JobOffer> jobList) {
        jobListView.setItems(jobList);
        jobListView.setCellFactory(listView -> new JobListViewCell());
        //setEventHandlerForJobListSelectionChangeEvent();
    }

    /**
    private void setEventHandlerForJobListSelectionChangeEvent() {
        jobListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in master job list changed to : '" + newValue + "'");
                        raise(new CompanyJobListDetailsPanelSelectionChangedEvent(newValue));
                    }
                });
    } */

    /**
     * Scrolls to the {@code JobCard} at the {@code index} and selects it.

    private void scrollToJobCard(int index) {
        Platform.runLater(() -> {
            jobListView.scrollTo(index);
            jobListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToJobListRequestEvent(JumpToCompanyJobListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollToJobCard(event.targetIndex);
    }

    @Subscribe
    private void handleCompanyJobListDetailsPanelSelectionChangedEvent(CompanyJobListDetailsPanelSelectionChangedEvent
                                                                               event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Selection Changed to " + event.getNewSelection().getJob().value));
    } */

    /**
     * Custom {@code ListCell} that displays the graphics of a job using a {@code JobCard}.
     */
    class JobListViewCell extends ListCell<JobOffer> {
        @Override
        protected void updateItem(JobOffer job, boolean empty) {
            super.updateItem(job, empty);

            if (empty || job == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new JobCard(job, getIndex() + 1).getRoot());
            }
        }
    }
}
