package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.recruit.ui.StatusBarFooter.SYNC_COMPANY_STATUS_INITIAL;
import static seedu.recruit.ui.StatusBarFooter.SYNC_COMPANY_STATUS_UPDATED;
import static seedu.recruit.ui.StatusBarFooter.TOTAL_COMPANIES_STATUS;
import static seedu.recruit.ui.UiPart.FXML_FILE_FOLDER;
import static seedu.recruit.ui.testutil.GuiTestAssert.assertListMatching;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CompanyDetailsPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.recruit.MainApp;
import seedu.recruit.TestApp;
import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.commands.ClearCompanyBookCommand;
import seedu.recruit.logic.commands.FindCompanyCommand;
import seedu.recruit.logic.commands.ListCommand;
import seedu.recruit.logic.commands.SelectCommand;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.Model;
import seedu.recruit.testutil.TypicalCompanies;
import seedu.recruit.ui.BrowserPanel;
import seedu.recruit.ui.CommandBox;


/**
 * A system test class for CompanyBook, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
@Ignore
public abstract class CompanyBookSystemTest {
    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();
        testApp = setupHelper.setupApplication(this::getInitialData, getDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        waitUntilBrowserLoaded(getBrowserPanel());
        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
        EventsCenter.clearSubscribers();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected CompanyBook getInitialData() {
        return TypicalCompanies.getTypicalCompanyBook();
    }

    /**
     * Returns the directory of the data file.
     */
    protected Path getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public CompanyDetailsPanelHandle getCompanyListPanel() { return mainWindowHandle.getCompanyListPanel(); }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public BrowserPanelHandle getBrowserPanel() {
        return mainWindowHandle.getBrowserPanel();
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void executeCommand(String command) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        mainWindowHandle.getCommandBox().run(command);

        waitUntilBrowserLoaded(getBrowserPanel());
    }

    /**
     * Displays all companies in the recruit book.
     */
    protected void showAllPersons() {
        executeCommand(ListCommand.COMMAND_WORD);
        assertEquals(getModel().getCompanyBook().getCompanyList().size(),
                getModel().getFilteredCompanyList().size());
    }

    /**
     * Displays all companies with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showPersonsWithName(String keyword) {
        executeCommand(FindCompanyCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredCompanyList().size()
                < getModel().getCompanyBook().getCompanyList().size());
    }

    /**
     * Selects the company at {@code index} of the displayed list.
     */
    protected void selectPerson(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getCompanyListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all companies in the recruit book.
     */
    protected void deleteAllPersons() {
        executeCommand(ClearCompanyBookCommand.COMMAND_WORD);
        assertEquals(0, getModel().getCompanyBook().getCompanyList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same company objects as {@code expectedModel}
     * and the company list panel displays the companies in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
                                                     Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new CompanyBook(expectedModel.getCompanyBook()), testApp.readStorageCompanyBook());
        assertListMatching(getCompanyListPanel(), expectedModel.getFilteredCompanyList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code CompanyDetailsPanelHandle}
     * and {@code StatusBarFooterHandle} to remember their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberTotalPersonsStatus();
        statusBarFooterHandle.rememberSyncStatus();
        getCompanyListPanel().rememberSelectedCompanyCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url remains displaying the details
     * of the previously selected company.
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertSelectedCardDeselected() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getCompanyListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the company in the company list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see CompanyDetailsPanelHandle#isSelectedPersonCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getCompanyListPanel().navigateToCard(getCompanyListPanel().getSelectedCardIndex());
        String selectedCardName = getCompanyListPanel().getHandleToSelectedCard().getName();
        URL expectedUrl;
        try {
            expectedUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + selectedCardName.replaceAll(" ", "%20"));
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.", mue);
        }
        assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getCompanyListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url and the selected card in the company list panel remain unchanged.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see CompanyDetailsPanelHandle#isSelectedCompanyCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getCompanyListPanel().isSelectedCompanyCardChanged());
    }

    /**
     * Asserts that the command box's shows the default style.
     */
    protected void assertCommandBoxShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's shows the error style.
     */
    protected void assertCommandBoxShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    protected void assertStatusBarUnchanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isTotalPersonsStatusChanged());
        assertFalse(handle.isSyncStatusChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, while the save location and the total company
     * list remains the same.
     */
    protected void assertStatusBarUnchangedExceptSyncStatus() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_COMPANY_STATUS_INITIAL, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isTotalPersonsStatusChanged());
    }

    /**
     * Asserts that the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, and total persons was changed to match the total
     * number of persons in the recruit book, while the save location remains the same.
     */
    protected void assertStatusBarChangedExceptSaveLocation() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_COMPANY_STATUS_INITIAL, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());
        final int totalPersons = testApp.getModel().getCandidateBook().getCandidateList().size();
        assertEquals(String.format(TOTAL_COMPANIES_STATUS, totalPersons), handle.getTotalPersonsStatus());
        assertFalse(handle.isSaveLocationChanged());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertListMatching(getCompanyListPanel(), getModel().getFilteredCompanyList());
        assertEquals(MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE), getBrowserPanel().getLoadedUrl());
        assertEquals(Paths.get(".").resolve(testApp.getCompanyStorageSaveLocation()).toString(),
                getStatusBarFooter().getSaveLocation());
        assertEquals(SYNC_COMPANY_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
        assertEquals(String.format(TOTAL_COMPANIES_STATUS, getModel().getCompanyBook().getCompanyList().size()),
                getStatusBarFooter().getTotalPersonsStatus());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
