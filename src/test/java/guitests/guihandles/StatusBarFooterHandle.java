package guitests.guihandles;

import org.controlsfx.control.StatusBar;

import javafx.scene.Node;

/**
 * A handle for the {@code StatusBarFooter} at the footer of the application.
 */
public class StatusBarFooterHandle extends NodeHandle<Node> {
    public static final String STATUS_BAR_PLACEHOLDER = "#statusbarPlaceholder";

    private static final String SYNC_CANDIDATES_STATUS_ID = "#syncCandidatesStatus";
    private static final String TOTAL_CANDIDATES_STATUS_ID = "#totalCandidatesStatus";
    private static final String SAVE_CANDIDATE_BOOK_LOCATION_STATUS_ID = "#saveCandidateBookLocationStatus";
    private static final String SYNC_COMPANIES_STATUS_ID = "#syncCompaniesStatus";
    private static final String TOTAL_COMPANIES_STATUS_ID = "#totalCompaniesStatus";
    private static final String SAVE_COMPANY_BOOK_LOCATION_STATUS_ID = "#saveCompanyBookLocationStatus";

    private final StatusBar syncCandidatesStatusNode;
    private final StatusBar totalCandidatesStatusNode;
    private final StatusBar saveCandidateBookLocationNode;
    private final StatusBar syncCompaniesStatusNode;
    private final StatusBar totalCompaniesStatusNode;
    private final StatusBar saveCompanyBookLocationNode;

    private String lastRememberedCandidatesSyncStatus;
    private String lastRememberedTotalCandidatesStatus;
    private String lastRememberedSaveCandidateBookLocation;
    private String lastRememberedCompaniesSyncStatus;
    private String lastRememberedTotalCompaniesStatus;
    private String lastRememberedSaveCompanyBookLocation;

    public StatusBarFooterHandle(Node statusBarFooterNode) {
        super(statusBarFooterNode);

        syncCandidatesStatusNode = getChildNode(SYNC_CANDIDATES_STATUS_ID);
        totalCandidatesStatusNode = getChildNode(TOTAL_CANDIDATES_STATUS_ID);
        saveCandidateBookLocationNode = getChildNode(SAVE_CANDIDATE_BOOK_LOCATION_STATUS_ID);
        syncCompaniesStatusNode = getChildNode(SYNC_COMPANIES_STATUS_ID);
        totalCompaniesStatusNode = getChildNode(TOTAL_COMPANIES_STATUS_ID);
        saveCompanyBookLocationNode = getChildNode(SAVE_COMPANY_BOOK_LOCATION_STATUS_ID);
    }

    // ====================================== CANDIDATE BOOK =======================================//
    /**
     * Returns the text of the sync status portion of the status bar.
     */
    public String getSyncStatus() {
        return syncCandidatesStatusNode.getText();
    }

    /**
     * Returns the text of the 'total candidates' portion of the status bar.
     */
    public String getTotalPersonsStatus() {
        return totalCandidatesStatusNode.getText();
    }

    /**
     * Returns the text of the 'save location' portion of the status bar.
     */
    public String getSaveLocation() {
        return saveCandidateBookLocationNode.getText();
    }

    /**
     * Remembers the content of the sync status portion of the status bar.
     */
    public void rememberSyncStatus() {
        lastRememberedCandidatesSyncStatus = getSyncStatus();
    }

    /**
     * Returns true if the current content of the sync status is different from the value remembered by the most recent
     * {@code rememberSyncStatus()} call.
     */
    public boolean isSyncStatusChanged() {
        return !lastRememberedCandidatesSyncStatus.equals(getSyncStatus());
    }

    /**
     * Remembers the content of the 'total candidates' portion of the status bar.
     */
    public void rememberTotalPersonsStatus() {
        lastRememberedTotalCandidatesStatus = getTotalPersonsStatus();
    }

    /**
     * Returns true if the current content of the 'total persons' is different from the value remembered by the most
     * recent {@code rememberTotalPersonsStatus()} call.
     */
    public boolean isTotalPersonsStatusChanged() {
        return !lastRememberedTotalCandidatesStatus.equals(getTotalPersonsStatus());
    }

    /**
     * Remembers the content of the 'save location' portion of the status bar.
     */
    public void rememberSaveLocation() {
        lastRememberedSaveCandidateBookLocation = getSaveLocation();
    }

    /**
     * Returns true if the current content of the 'save location' is different from the value remembered by the most
     * recent {@code rememberSaveLocation()} call.
     */
    public boolean isSaveLocationChanged() {
        return !lastRememberedSaveCandidateBookLocation.equals(getSaveLocation());
    }

    // ====================================== COMPANY BOOK =======================================//

    /**
     * Returns the text of the sync status portion of the status bar.
     */
    public String getCompaniesSyncStatus() {
        return syncCompaniesStatusNode.getText();
    }

    /**
     * Returns the text of the 'total companies' portion of the status bar.
     */
    public String getTotalCompaniesStatus() {
        return totalCompaniesStatusNode.getText();
    }

    /**
     * Returns the text of the 'save location' portion of the status bar.
     */
    public String getSaveCompanyBookLocation() {
        return saveCompanyBookLocationNode.getText();
    }

    /**
     * Remembers the content of the sync status portion of the status bar.
     */
    public void rememberCompaniesSyncStatus() {
        lastRememberedCompaniesSyncStatus = getSyncStatus();
    }

    /**
     * Returns true if the current content of the sync status is different from the value remembered by the most recent
     * {@code rememberCompaniesSyncStatus()} call.
     */
    public boolean isCompaniesSyncStatusChanged() {
        return !lastRememberedCompaniesSyncStatus.equals(getSyncStatus());
    }

    /**
     * Remembers the content of the 'total companies' portion of the status bar.
     */
    public void rememberTotalCompaniesStatus() {
        lastRememberedTotalCompaniesStatus = getTotalPersonsStatus();
    }

    /**
     * Returns true if the current content of the 'total persons' is different from the value remembered by the most
     * recent {@code rememberTotalCompaniesStatus()} call.
     */
    public boolean isTotalCompaniesStatusChanged() {
        return !lastRememberedTotalCompaniesStatus.equals(getTotalPersonsStatus());
    }

    /**
     * Remembers the content of the 'save location' portion of the status bar.
     */
    public void rememberSaveCompanyBookLocation() {
        lastRememberedSaveCompanyBookLocation = getSaveLocation();
    }

    /**
     * Returns true if the current content of the 'save location' is different from the value remembered by the most
     * recent {@code rememberSaveCompanyBookLocation()} call.
     */
    public boolean isSaveCompanyBookLocationChanged() {
        return !lastRememberedSaveCompanyBookLocation.equals(getSaveLocation());
    }

}
