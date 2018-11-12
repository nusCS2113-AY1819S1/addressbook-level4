package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.ProfileWindowHandle;

public class ProfileWindowTest extends GuiUnitTest {

    private static final String userProfileUrl = System.getProperty("user.dir") + "/ProfileWindow.html";
    private ProfileWindow profileWindow;
    private ProfileWindowHandle profileWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> profileWindow = new ProfileWindow());
        FxToolkit.registerStage(profileWindow::getRoot);
        profileWindowHandle = new ProfileWindowHandle(profileWindow.getRoot());
    }

    @Test
    public void display() throws Exception {
        FxToolkit.showStage();
        assertEquals(new File(userProfileUrl).toURI().toURL(), profileWindowHandle.getLoadedUrl());
    }

    @Test
    public void isShowing_profileWindowIsShowing_returnsTrue() {
        guiRobot.interact(profileWindow::show);
        assertTrue(profileWindow.isShowing());
    }

    @Test
    public void isShowing_profileWindowIsHiding_returnsFalse() {
        assertFalse(profileWindow.isShowing());
    }

}
