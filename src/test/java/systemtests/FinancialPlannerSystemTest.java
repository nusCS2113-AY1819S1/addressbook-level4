package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.planner.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.planner.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.planner.ui.testutil.GuiTestAssert.assertListMatching;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.DetailedRecordCardHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.RecordCardHandle;
import guitests.guihandles.RecordListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.planner.TestApp;
import seedu.planner.commons.core.EventsCenter;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.ClearCommand;
import seedu.planner.logic.commands.FindCommand;
import seedu.planner.logic.commands.ListCommand;
import seedu.planner.logic.commands.SelectCommand;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.Model;
import seedu.planner.testutil.TypicalRecords;
import seedu.planner.ui.CommandBox;

/**
 * A system test class for FinancialPlanner, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class FinancialPlannerSystemTest {
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
        testApp = setupHelper.setupApplication(this::getInitialData, getRecordListDataFileLocation(),
                getLimitListDataFileLocation(), getSummaryMapDataFileLocation(), getDirectoryPathDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
        EventsCenter.clearSubscribers();
    }

    /**
     * Returns the data to be loaded into the file in
     * {@link #getRecordListDataFileLocation()},
     * {@link #getLimitListDataFileLocation()},
     * {@link #getSummaryMapDataFileLocation()},
     */
    protected FinancialPlanner getInitialData() {
        return TypicalRecords.getTypicalFinancialPlanner();
    }

    /**
     * Returns the directory of the record list data file.
     */
    protected Path getRecordListDataFileLocation() {
        return TestApp.RECORD_LIST_LOCATION_FOR_TESTING;
    }

    /**
     * Returns the directory of the summary list data file.
     */
    protected Path getSummaryMapDataFileLocation() {
        return TestApp.SUMMARY_MAP_LOCATION_FOR_TESTING;
    }

    /**
     * Returns the directory of the directory path data file.
     */
    protected Path getDirectoryPathDataFileLocation() {
        return TestApp.DIRECTORY_PATH_LOCATION_FOR_TESTING;
    }

    protected Path getLimitListDataFileLocation() {
        return TestApp.LIMIT_LIST_LOCATION_FOR_TESTING;
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public RecordListPanelHandle getRecordListPanel() {
        return mainWindowHandle.getRecordListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public DetailedRecordCardHandle getDetailedRecordCardPanel() {
        return mainWindowHandle.getDetailedRecordCard();
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
     * Displays all records in the planner book.
     */
    protected void showAllRecords() {
        executeCommand(ListCommand.COMMAND_WORD);
        assertEquals(getModel().getFinancialPlanner().getRecordList().size(),
                getModel().getFilteredRecordList().size());
    }

    /**
     * Displays all records with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showRecordsWithName(String keyword) {
        executeCommand(FindCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredRecordList().size() < getModel()
                .getFinancialPlanner().getRecordList().size());
    }

    /**
     * Selects the record at {@code index} of the displayed list.
     */
    protected void selectRecord(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getRecordListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all records in the planner book.
     */
    protected void deleteAllRecords() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getFinancialPlanner().getRecordList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same record objects as {@code expectedModel}
     * and the record list panel displays the records in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
            Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new FinancialPlanner(expectedModel.getFinancialPlanner()), testApp.readStorageFinancialPlanner());
        assertListMatching(getRecordListPanel(), expectedModel.getFilteredRecordList());
    }

    /**
     * Calls {@code DetailedRecordCardHandle}, {@code RecordListPanelHandle} and {@code StatusBarFooterHandle}
     * to remember their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getDetailedRecordCardPanel().rememberDetailedRecordCard();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getRecordListPanel().rememberSelectedRecordCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the detailed card panel is now invisible
     * @see DetailedRecordCardHandle#isVisible()
     */
    protected void assertSelectedCardDeselected() {
        assertFalse(getDetailedRecordCardPanel().isVisible());
        assertFalse(getRecordListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the detailed record card panel is changed to display the details of the record in the record list
     * panel at {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see DetailedRecordCardHandle#isDetailedRecordCardChanged()
     * @see RecordListPanelHandle#isSelectedRecordCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getRecordListPanel().navigateToCard(getRecordListPanel().getSelectedCardIndex());
        String selectedCardName = getRecordListPanel().getHandleToSelectedCard().getName();
        RecordCardHandle expectedRecordCard;
        try {
            expectedRecordCard = getRecordListPanel().getRecordCardHandle(getRecordListPanel().getSelectedCardIndex());
        } catch (IllegalStateException e) {
            throw new AssertionError("Record card cannot be found", e);
        }
        assertTrue(getDetailedRecordCardPanel().equals(expectedRecordCard));
        assertEquals(expectedSelectedCardIndex.getZeroBased(), getRecordListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url and the selected card in the record list panel remain unchanged.
     * @see DetailedRecordCardHandle#isDetailedRecordCardChanged()
     * @see RecordListPanelHandle#isSelectedRecordCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getDetailedRecordCardPanel().isDetailedRecordCardChanged());
        assertFalse(getRecordListPanel().isSelectedRecordCardChanged());
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
        assertListMatching(getRecordListPanel(), getModel().getFilteredRecordList());
        assertFalse(getDetailedRecordCardPanel().isVisible());
        assertEquals(Paths.get(".").resolve(testApp.getRecordStorageSaveLocation()).toString(),
                getStatusBarFooter().getSaveLocation());
        assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
