package seedu.planner.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.planner.testutil.EventsUtil.postNow;
import static seedu.planner.testutil.TypicalRecords.INDO;
import static seedu.planner.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.planner.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.planner.MainApp;
import seedu.planner.commons.events.ui.RecordPanelSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private RecordPanelSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new RecordPanelSelectionChangedEvent(INDO);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a record
        postNow(selectionChangedEventStub);
        URL expectedRecordUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + INDO.getName().fullName.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedRecordUrl, browserPanelHandle.getLoadedUrl());
    }
}
