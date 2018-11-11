package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.model.DateTimeUtil.PAGE_DATE_FORMAT;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalEvents.ALICE;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.EventSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private EventSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new EventSelectionChangedEvent(ALICE);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a event
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(BrowserPanel.getEventPageUrl()
                + "?name="
                + browserPanel.encodeString(ALICE.getName().fullName)
                + "&contact="
                + browserPanel.encodeString(ALICE.getContact().fullContactName)
                + "&phone="
                + browserPanel.encodeString(ALICE.getPhone().toString())
                + "&email="
                + browserPanel.encodeString(ALICE.getEmail().toString())
                + "&venue="
                + browserPanel.encodeString(ALICE.getVenue().value)
                + "&dateTime="
                + browserPanel.encodeString(PAGE_DATE_FORMAT.format(ALICE.getDateTime().dateTime))
                + "&status="
                + browserPanel.encodeString(ALICE.getStatus().toString())
                + "&tags="
                + browserPanel.encodeString(ALICE.getTagsString())
                + "&attendance="
                + browserPanel.encodeString(ALICE.getAttendanceString())
                + "&comment="
                + browserPanel.encodeString(ALICE.getComment().value.replace("{", "<").replace("}", ">")));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }
}
