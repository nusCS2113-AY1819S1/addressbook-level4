package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code ProfileWindow} of the application.
 */
public class ProfileWindowHandle extends StageHandle {

    public static final String PROFILE_WINDOW_TITLE = "Profile";

    private static final String PROFILE_WINDOW_BROWSER_ID = "#browser";

    public ProfileWindowHandle(Stage profileWindowStage) {
        super(profileWindowStage);
    }

    /**
     * Returns true if a profile window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(PROFILE_WINDOW_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(PROFILE_WINDOW_BROWSER_ID));
    }
}
