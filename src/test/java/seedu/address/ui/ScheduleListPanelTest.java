package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.schedule.TypicalSchedules.getTypicalSchedules;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysSchedule;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEqualsSchedule;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.ScheduleCardHandle;
import guitests.guihandles.ScheduleListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListScheduleRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.schedule.Schedule;
import seedu.address.storage.schedule.XmlSerializableScheduleList;


public class ScheduleListPanelTest extends GuiUnitTest {

    private static final ObservableList<Schedule> TYPICAL_SCHEDULES =
            FXCollections.observableList(getTypicalSchedules());

    private static final JumpToListScheduleRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToListScheduleRequestEvent(INDEX_SECOND_PERSON);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private ScheduleListPanelHandle scheduleListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_SCHEDULES);

        for (int i = 0; i < TYPICAL_SCHEDULES.size(); i++) {
            scheduleListPanelHandle.navigateToCard(TYPICAL_SCHEDULES.get(i));
            Schedule expectedSchedule = TYPICAL_SCHEDULES.get(i);
            ScheduleCardHandle actualCard = scheduleListPanelHandle.getScheduleCardHandle(i);

            assertCardDisplaysSchedule(expectedSchedule, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListScheduleRequestEvent() {
        initUi(TYPICAL_SCHEDULES);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        ScheduleCardHandle expectedSchedule = scheduleListPanelHandle.getScheduleCardHandle(
                INDEX_SECOND_PERSON.getZeroBased());
        ScheduleCardHandle selectedScheduke = scheduleListPanelHandle.getHandleToSelectedCard();
        assertCardEqualsSchedule(expectedSchedule, selectedScheduke);
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code ScheduleListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Schedule> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of person cards exceeded time limit");
    }

    /**
     * Returns a list of schedules containing {@code scheduleCount} schedules that is used to populate the
     * {@code ScheduleListPanel}.
     */
    private ObservableList<Schedule> createBackingList(int scheduleCount) throws Exception {
        Path xmlFile = createXmlFileWithSchedules(scheduleCount);
        XmlSerializableScheduleList xmlScheduleList =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableScheduleList.class);
        return FXCollections.observableArrayList(xmlScheduleList.toModelType().getScheduleList());
    }

    /**
     * Returns a .xml file containing {@code scheduleCount} schedules.
     * This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithSchedules(int scheduleCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<schedulelist>\n");
        for (int i = 0; i < scheduleCount; i++) {
            builder.append("<schedules>\n");
            String employeeIdFormatted = String.format("%06d", i);
            builder.append("<employeeId>" + employeeIdFormatted + "</employeeId>\n");
            builder.append("<type>LEAVE</type>\n");
            builder.append("<date>1/2/2099</date>\n");
            builder.append("</schedules>\n");
        }
        builder.append("</schedulelist>\n");

        Path manySchedulesFile = Paths.get(TEST_DATA_FOLDER + "manySchedules.xml");
        FileUtil.createFile(manySchedulesFile);
        FileUtil.writeToFile(manySchedulesFile, builder.toString());
        manySchedulesFile.toFile().deleteOnExit();
        return manySchedulesFile;
    }

    /**
     * Initializes {@code scheduleListPanelHandle} with a {@code ScheduleListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code ScheduleListPanel}.
     */
    private void initUi(ObservableList<Schedule> backingList) {
        ScheduleListPanel scheduleListPanel = new ScheduleListPanel(backingList);
        uiPartRule.setUiPart(scheduleListPanel);

        scheduleListPanelHandle = new ScheduleListPanelHandle(getChildNode(scheduleListPanel.getRoot(),
                ScheduleListPanelHandle.SCHEDULE_LIST_VIEW_ID));
    }
}
