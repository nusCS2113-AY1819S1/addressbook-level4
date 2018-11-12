package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.divelog.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.divelog.ui.StatusBarFooter.SYNC_STATUS_UPDATED;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.DiveListPanelHandle;
import guitests.guihandles.DiveSessionCardHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import javafx.collections.ObservableList;
import seedu.divelog.TestApp;
import seedu.divelog.commons.core.EventsCenter;
import seedu.divelog.commons.core.index.Index;
import seedu.divelog.logic.commands.ClearCommand;
import seedu.divelog.logic.commands.FindCommand;
import seedu.divelog.logic.commands.ListCommand;
import seedu.divelog.logic.commands.SelectCommand;
import seedu.divelog.model.DiveLog;
import seedu.divelog.model.Model;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.testutil.TypicalDiveSessions;
import seedu.divelog.ui.CommandBox;
import seedu.divelog.ui.DiveSessionCard;

/**
 * A system test class for DiveLog, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class DiveLogSystemTest {
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
    protected DiveLog getInitialData() {
        return TypicalDiveSessions.getTypicalDiveLog();
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

    public DiveListPanelHandle getDiveListPanel() {
        return mainWindowHandle.getDiveListPanel();
    }

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
    }

    /**
     * Displays all dive in the divelog book.
     */
    protected void showAllDives() {
        executeCommand(ListCommand.COMMAND_WORD);
        assertEquals(getModel().getDiveLog().getDiveList().size(), getModel().getFilteredDiveList().size());
    }

    /**
     * Displays all dive with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showDivesWithLocation(String keyword) {
        executeCommand(FindCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredDiveList().size() < getModel().getDiveLog().getDiveList().size());
    }

    /**
     * Selects the dive at {@code index} of the displayed list.
     */
    protected void selectDive(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getDiveListPanel().getSelectedCardIndex());
    }

    /**
     *
     * Deletes all dive sessions in the divelog book.
     */
    protected void deleteAllDiveSessions() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getDiveLog().getDiveList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same dive session objects as {@code expectedModel}
     * and the dive list panel displays the dive sessions in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
            Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new DiveLog(expectedModel.getDiveLog()), testApp.readStorageDiveLog());
        assertListMatching(getDiveListPanel(), expectedModel.getFilteredDiveList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code DiveListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        //getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getDiveListPanel().rememberSelectedDiveCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url remains displaying the details
     * of the previously selected dive session.
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertSelectedCardDeselected() {
        //assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getDiveListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the dive session in the dive list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see DiveListPanelHandle#isSelectedDiveSessionCardChanged()
     * TODO REIMPLEMENT
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getDiveListPanel().navigateToCard(getDiveListPanel().getSelectedCardIndex());
        String selectedCardName = getDiveListPanel().getHandleToSelectedCard().getName();


        assertEquals(expectedSelectedCardIndex.getZeroBased(), getDiveListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url and the selected card in the dive session list panel remain unchanged.
     *
     * @see DiveListPanelHandle#isSelectedDiveSessionCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        //assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getDiveListPanel().isSelectedDiveSessionCardChanged());
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
        assertFalse(handle.isSyncStatusChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, while the save location remains the same.
     */
    protected void assertStatusBarUnchangedExceptSyncStatus() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());
        assertFalse(handle.isSaveLocationChanged());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertListMatching(getDiveListPanel(), getModel().getFilteredDiveList());
        assertEquals(Paths.get(".").resolve(testApp.getStorageSaveLocation()).toString(),
                getStatusBarFooter().getSaveLocation());
        assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    /**
     * Asserts the list
     * @param diveListPanel
     * @param filteredDiveList
     */
    public static void assertListMatching(DiveListPanelHandle diveListPanel,
                                          ObservableList<DiveSession> filteredDiveList) {
        DiveSession []dives = filteredDiveList.toArray(new DiveSession[0]);
        assertListMatching(diveListPanel, dives);
    }

    /**
     * Asserts that the list in {@code DiveListPanelHandle} displays the details of {@code dives} correctly and
     * in the correct order.
     */
    public static void assertListMatching(DiveListPanelHandle diveListPanel, DiveSession[] dives) {

        for (int i = 0; i < dives.length; i++) {
            diveListPanel.navigateToCard(i);
            assertCardDisplays(dives[i], diveListPanel.getDiveCardHandle(i));
        }
    }

    /**
     * Asserts the card and the dive are the same.
     * @param dive The {@Code DiveSession} has the same data as the divecard
     * @param diveCardHandle The {@Code DiveSessionCard} in question
     */
    public static void assertCardDisplays(DiveSession dive, DiveSessionCardHandle diveCardHandle) {
        assertEquals(diveCardHandle.getName(), DiveSessionCard.DIVE_PREFIX + dive.getLocation().getLocationName());
        assertEquals(diveCardHandle.getDepth(), dive.getDepthProfile().getFormattedString());
    }


    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
