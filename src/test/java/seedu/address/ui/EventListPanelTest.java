package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalEvents.getTypicalEvents;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysEvent;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.EventListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToEventListRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.event.Event;
import seedu.address.storage.XmlSerializableEventList;

public class EventListPanelTest extends GuiUnitTest {
    private static final ObservableList<Event> TYPICAL_EVENTS =
            FXCollections.observableList(getTypicalEvents());

    private static final JumpToEventListRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToEventListRequestEvent(INDEX_SECOND_EVENT);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private EventListPanelHandle eventListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_EVENTS);

        for (int i = 0; i < TYPICAL_EVENTS.size(); i++) {
            eventListPanelHandle.navigateToCard(TYPICAL_EVENTS.get(i));
            Event expectedEvent = TYPICAL_EVENTS.get(i);
            EventCardHandle actualCard = eventListPanelHandle.getEventCardHandle(i);

            assertCardDisplaysEvent(expectedEvent, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_EVENTS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        EventCardHandle expectedEvent = eventListPanelHandle.getEventCardHandle(INDEX_SECOND_EVENT.getZeroBased());
        EventCardHandle selectedEvent = eventListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedEvent, selectedEvent);
    }

    /**
     * Verifies that creating and deleting large number of events in {@code EventListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Event> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of event cards exceeded time limit");
    }

    /**
     * Returns a list of events containing {@code eventCount} events that is used to populate the
     * {@code EventListPanel}.
     */
    private ObservableList<Event> createBackingList(int eventCount) throws Exception {
        Path xmlFile = createXmlFileWithEvents(eventCount);
        XmlSerializableEventList xmlEventList =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableEventList.class);
        return FXCollections.observableArrayList(xmlEventList.toModelType().getEventList());
    }

    /**
     * Returns a .xml file containing {@code eventCount} events. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithEvents(int eventCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<eventlist>\n");
        for (int i = 0; i < eventCount; i++) {
            builder.append("<events>\n");
            builder.append("<eventName>a</eventName>\n");
            builder.append("<description>a</description>\n");
            builder.append("<location>").append(i).append("a</location>\n");
            builder.append("<date>2018-11-09</date>\n");
            builder.append("<startTime>10:00</startTime>\n");
            builder.append("<endTime>12:00</endTime>\n");
            builder.append("</events>\n");
        }
        builder.append("</eventlist>\n");

        Path manyEventsFile = Paths.get(TEST_DATA_FOLDER + "manyEvents.xml");
        FileUtil.createFile(manyEventsFile);
        FileUtil.writeToFile(manyEventsFile, builder.toString());
        manyEventsFile.toFile().deleteOnExit();
        return manyEventsFile;
    }

    /**
     * Initializes {@code eventListPanelHandle} with a {@code EventListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code EventListPanel}.
     */
    private void initUi(ObservableList<Event> backingList) {
        EventListPanel eventListPanel = new EventListPanel(backingList);
        uiPartRule.setUiPart(eventListPanel);

        eventListPanelHandle = new EventListPanelHandle(getChildNode(eventListPanel.getRoot(),
                EventListPanelHandle.EVENT_LIST_VIEW_ID));
    }
}
