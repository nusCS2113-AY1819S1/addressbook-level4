package seedu.recruit.ui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.recruit.commons.core.LogsCenter;
import seedu.recruit.commons.events.model.CandidateBookChangedEvent;
import seedu.recruit.commons.events.model.CompanyBookChangedEvent;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    public static final String SYNC_CANDIDATE_STATUS_INITIAL = "Candidate Book - not updated yet in this session";
    public static final String SYNC_CANDIDATE_STATUS_UPDATED = "Candidate Book - Last Updated: %s";
    public static final String SYNC_COMPANY_STATUS_INITIAL = "Company Book - Not updated yet in this session";
    public static final String SYNC_COMPANY_STATUS_UPDATED = "Company Book - Last Updated: %s";
    public static final String TOTAL_CANDIDATES_STATUS = "%d candidate(s) total";
    public static final String TOTAL_COMPANIES_STATUS = "%d company(s) total";

    /**
     * Used to generate time stamps.
     *
     * TODO: change clock to an instance variable.
     * We leave it as a static variable because manual dependency injection
     * will require passing down the clock reference all the way from MainApp,
     * but it should be easier once we have factories/DI frameworks.
     */
    private static Clock clock = Clock.systemDefaultZone();

    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private StatusBar syncCandidatesStatus;
    @FXML
    private StatusBar saveCandidateBookLocationStatus;
    @FXML
    private StatusBar totalCandidatesStatus;
    @FXML
    private StatusBar syncCompaniesStatus;
    @FXML
    private StatusBar totalCompaniesStatus;
    @FXML
    private StatusBar saveCompanyBookLocationStatus;


    public StatusBarFooter(Path saveCandidateBookLocation, Path saveCompanyBookLocation,
                           int totalCandidates, int totalCompanies) {
        super(FXML);
        setCandidateSyncStatus(SYNC_CANDIDATE_STATUS_INITIAL);
        setCompanySyncStatus(SYNC_COMPANY_STATUS_INITIAL);
        setSaveCandidateBookLocation(Paths.get(".").resolve(saveCandidateBookLocation).toString());
        setSaveCompanyBookLocation(Paths.get(".").resolve(saveCompanyBookLocation).toString());
        setTotalCandidates(totalCandidates);
        setTotalCompanies(totalCompanies);
        registerAsAnEventHandler(this);
    }

    /**
     * Sets the clock used to determine the current time.
     */
    public static void setClock(Clock clock) {
        StatusBarFooter.clock = clock;
    }

    /**
     * Returns the clock currently in use.
     */
    public static Clock getClock() {
        return clock;
    }

    private void setSaveCandidateBookLocation(String location) {
        Platform.runLater(() -> saveCandidateBookLocationStatus.setText(location));
    }

    private void setSaveCompanyBookLocation(String location) {
        Platform.runLater(() -> saveCompanyBookLocationStatus.setText(location));
    }

    private void setCandidateSyncStatus(String status) {
        Platform.runLater(() -> syncCandidatesStatus.setText(status));
    }

    private void setCompanySyncStatus(String status) {
        Platform.runLater(() -> syncCompaniesStatus.setText(status));
    }

    private void setTotalCandidates(int totalCandidates) {
        Platform.runLater(() -> totalCandidatesStatus.setText(String.format(TOTAL_CANDIDATES_STATUS, totalCandidates)));
    }

    private void setTotalCompanies(int totalCompanies) {
        Platform.runLater(() -> totalCompaniesStatus.setText(String.format(TOTAL_COMPANIES_STATUS, totalCompanies)));
    }

    @Subscribe
    public void handleCandidateBookChangedEvent(CandidateBookChangedEvent abce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce,
                "Candidate Book - Setting last updated status to " + lastUpdated));
        setCandidateSyncStatus(String.format(SYNC_CANDIDATE_STATUS_UPDATED, lastUpdated));
        setTotalCandidates(abce.data.getCandidateList().size());
    }

    @Subscribe
    public void handleCompanyBookChangedEvent(CompanyBookChangedEvent abce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce,
                "Company Book - Setting last updated status to " + lastUpdated));
        setCompanySyncStatus(String.format(SYNC_COMPANY_STATUS_UPDATED, lastUpdated));
        setTotalCompanies(abce.data.getCompanyList().size());
    }
}
