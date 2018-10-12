package seedu.recruit.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.recruit.commons.core.LogsCenter;
import seedu.recruit.commons.events.ui.JumpToListRequestEvent;
import seedu.recruit.commons.events.ui.CompanyDetailsPanelSelectionChangedEvent;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * Left side of the Panel containing the list of company.
 * Upon selection, right side of the panel shows the
 * list of job offers offered in that selected company.
 */
public class CompanyJobDetailsPanel extends UiPart<Region> {
    private static final String FXML = "CompanyJobDetailsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CompanyJobDetailsPanel.class);

    @FXML
    private ListView<Company> companyView;

    @FXML
    private ListView<JobOffer> companyJobDetailsView;

    public CompanyJobDetailsPanel(ObservableList<Company> companyList, ObservableList<JobOffer> companyJobList) {
        super(FXML);
        setConnections(companyList, companyJobList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Company> companyList, ObservableList<JobOffer> companyJobList) {
        companyView.setItems(companyList);
        companyView.setCellFactory(listView -> new CompanyViewCell());
        companyJobDetailsView.setItems(companyJobList);
        companyJobDetailsView.setCellFactory(listView -> new CompanyJobDetailsViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        companyView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in company job details panel changed to : '" + newValue + "'");
                        raise(new CompanyDetailsPanelSelectionChangedEvent(newValue));
                        showJobDetailsOfSelectedCompany();
                    }
                });
    }

    /**
     * Right side of {@code CompanyJobDetailsPanel} shows the list of jobs
     * and their details {@code JobCard} of the selected Company
     */
    private void showJobDetailsOfSelectedCompany() {
        Company selectedCompany = companyView.getSelectionModel().getSelectedItem();
        companyJobDetailsView.setItems(selectedCompany.getUniqueJobList().getInternalList());
        companyJobDetailsView.setCellFactory(listView -> new CompanyJobDetailsViewCell());
    }

    /**
     * Scrolls to the {@code CompanyCard} at the {@code index} and selects it.
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
     * Custom {@code ListCell} that displays the graphics of a company in {@code companyView}
     * on the left side of {@code CompanyJobDetailsPanel}
     * using a {@code CompanyCard}.
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
     * Custom {@code ListCell} that displays the graphics of a job in {@code companyJobDetailsView}
     * on the right side of {@code CompanyJobDetailsPanel}
     * using a {@code JobCard}.
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
