package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WORKOUT;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.ProfileWindowHandle;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ProfileCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.ui.StatusBarFooter;

/**
 * A system test class for the help window, which contains interaction with other UI components.
 */
public class ProfileCommandSystemTest extends WorkoutBookSystemTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void openProfileWindow() {
        //use accelerator
        getCommandBox().click();
        getMainMenu().openProfileWindowUsingAccelerator();
        assertProfileWindowOpen();

        getResultDisplay().click();
        getMainMenu().openProfileWindowUsingAccelerator();
        assertProfileWindowOpen();

        getWorkoutListPanel().click();
        getMainMenu().openProfileWindowUsingAccelerator();
        assertProfileWindowOpen();

        //use menu button
        getMainMenu().openProfileWindowUsingMenu();
        assertProfileWindowOpen();

        //use command box
        executeCommand(ProfileCommand.COMMAND_WORD);
        assertProfileWindowOpen();

        // open help window and give it focus
        executeCommand(ProfileCommand.COMMAND_WORD);
        getMainWindowHandle().focus();

        // assert that while the help window is open the UI updates correctly for a command execution
        executeCommand(SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_WORKOUT.getOneBased());
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        assertNotEquals(ProfileCommand.SHOWING_PROFILE_MESSAGE, getResultDisplay().getText());
        assertListMatching(getWorkoutListPanel(), getModel().getFilteredWorkoutList());

        // assert that the status bar too is updated correctly while the help window is open
        // note: the select command tested above does not update the status bar
        executeCommand(DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_WORKOUT.getOneBased());
        assertNotEquals(StatusBarFooter.SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    @Test
    public void help_multipleCommands_onlyOneProfileWindowOpen() {
        getMainMenu().openProfileWindowUsingMenu();

        getMainWindowHandle().focus();
        getMainMenu().openProfileWindowUsingAccelerator();

        getMainWindowHandle().focus();
        executeCommand(ProfileCommand.COMMAND_WORD);

        assertEquals(1, guiRobot.getNumberOfWindowsShown(ProfileWindowHandle.PROFILE_WINDOW_TITLE));
    }

    /**
     * Asserts that the help window is open, and closes it after checking.
     */
    private void assertProfileWindowOpen() {
        assertTrue(ERROR_MESSAGE, ProfileWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new ProfileWindowHandle(guiRobot.getStage(ProfileWindowHandle.PROFILE_WINDOW_TITLE)).close();
        getMainWindowHandle().focus();
    }

    /**
     * Asserts that the help window isn't open.
     */
    private void assertProfileWindowNotOpen() {
        assertFalse(ERROR_MESSAGE, ProfileWindowHandle.isWindowPresent());
    }

}
