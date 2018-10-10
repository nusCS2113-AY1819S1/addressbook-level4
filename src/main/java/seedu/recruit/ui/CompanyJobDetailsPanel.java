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
import seedu.recruit.commons.events.ui.CompanyPanelSelectionChangedEvent;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * Panel containing the list of persons,
 * and their details upon selection
 */
public class CompanyJobDetailsPanel extends UiPart<Region> {
    private static final String FXML = "CompanyJobDetailsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CompanyJobDetailsPanel.class);

    @FXML
    private ListView<Company> companyView;
    @FXML
    private ListView<JobOffer> companyJobDetailsView;

    public CompanyJobDetailsPanel(ObservableList<Company> companyList, ObservableList<JobOffer> jobList) {
        super(FXML);
        setConnections(companyList, jobList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Company> companyList, ObservableList<JobOffer> jobList) {
        companyView.setItems(companyList);
        companyView.setCellFactory(listView -> new CompanyViewCell());
        companyJobDetailsView.setItems(jobList);
        companyJobDetailsView.setCellFactory(listView -> new CompanyJobDetailsViewCell());
        setEventHandlerForSelectionChangeEvent(jobList);
    }

    private void setEventHandlerForSelectionChangeEvent(ObservableList<JobOffer> jobList) {
        companyView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in company job details panel changed to : '" + newValue + "'");
                        raise(new CompanyPanelSelectionChangedEvent(newValue));
                        showJobDetailsOfSelectedCompany(jobList);
                    }
                });
    }

    /**
     * Expands {@code PersonCard} by listing all the details of the selected Candidate
     */
    private void showJobDetailsOfSelectedCompany(ObservableList<JobOffer> jobList) {
        Company selectedCompany = companyView.getSelectionModel().getSelectedItem();
        //selectedCompany.setItems(jobList);
        //selectedCompany.setCellFactory(listView -> new CompanyJobDetailsViewCell());
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            companyView.scrollTo(index);
            companyView.getSelectionModel().clearAndSelect(index);
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
    class CompanyViewCell extends ListCell<Company> {
        @Override
        protected void updateItem(Company company, boolean empty) {
            super.updateItem(company, empty);

            if (empty || company == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CompanyCard(company, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Candidate} using a {@code PersonCard}.
     */
    class CompanyJobDetailsViewCell extends ListCell<JobOffer> {
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
