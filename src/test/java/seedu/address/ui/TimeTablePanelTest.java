package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.TimeTablePanel.DEFAULT_PAGE;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.TimetablePanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;

public class TimeTablePanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private TimeTablePanel timetablePanel;
    private TimetablePanelHandle timetablePanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(ALICE);

        guiRobot.interact(() -> timetablePanel = new TimeTablePanel());
        uiPartRule.setUiPart(timetablePanel);

        timetablePanelHandle = new TimetablePanelHandle(timetablePanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, timetablePanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(TimeTablePanel.SEARCH_PAGE_URL + ALICE.getName().fullName.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(timetablePanelHandle);
        assertEquals(expectedPersonUrl, timetablePanelHandle.getLoadedUrl());
    }
}
