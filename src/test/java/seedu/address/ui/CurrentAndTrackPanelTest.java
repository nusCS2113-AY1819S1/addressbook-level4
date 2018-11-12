package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.getTypicalWorkouts;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysWorkout;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.CurrentAndTrackPanelHandle;
import guitests.guihandles.WorkoutCardHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.workout.Workout;
import seedu.address.storage.XmlSerializableTrackedData;

public class CurrentAndTrackPanelTest extends GuiUnitTest {
    private static final ObservableList<Workout> TYPICAL_WORKOUTS =
            FXCollections.observableList(getTypicalWorkouts());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_WORKOUT);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 10000;

    private CurrentAndTrackPanelHandle currentAndTrackPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_WORKOUTS);

        for (int i = 0; i < TYPICAL_WORKOUTS.size(); i++) {
            currentAndTrackPanelHandle.navigateToCard(TYPICAL_WORKOUTS.get(i));
            Workout expectedWorkout = TYPICAL_WORKOUTS.get(i);
            WorkoutCardHandle actualCard = currentAndTrackPanelHandle.getWorkoutCardHandle(i);

            assertCardDisplaysWorkout(expectedWorkout, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    /**
     * Verifies that creating and deleting large number of workouts in {@code CurrentAndTrackPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Workout> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of workout cards exceeded time limit");
    }

    /**
     * Returns a list of workouts containing {@code workoutCount} workouts that is used to populate the
     * {@code CurrentAndTrackPanel}.
     */
    private ObservableList<Workout> createBackingList(int workoutCount) throws Exception {
        Path xmlFile = createXmlFileWithWorkouts(workoutCount);
        XmlSerializableTrackedData xmlTrackedData =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableTrackedData.class);
        return FXCollections.observableArrayList(xmlTrackedData.toModelType().getTrackedData());
    }

    /**
     * Returns a .xml file containing {@code workoutCount} workouts. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithWorkouts(int workoutCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<trackeddata>\n");
        for (int i = 0; i < workoutCount; i++) {
            builder.append("<workouts>\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("<type>a</type>\n");
            builder.append("<duration>1m</duration>\n");
            builder.append("<difficulty>beginner</difficulty>\n");
            builder.append("<equipment>a</equipment>\n");
            builder.append("<muscle>a</muscle>\n");
            builder.append("<calories>1</calories>\n");
            builder.append("<instruction>").append(i).append("a</instruction>\n");
            builder.append("</workouts>\n");
        }
        builder.append("</trackeddata>\n");

        Path manyWorkoutsFile = Paths.get(TEST_DATA_FOLDER + "manyWorkouts.xml");
        FileUtil.createFile(manyWorkoutsFile);
        FileUtil.writeToFile(manyWorkoutsFile, builder.toString());
        manyWorkoutsFile.toFile().deleteOnExit();
        return manyWorkoutsFile;
    }

    /**
     * Initializes {@code currentAndTrackPanelHandle} with a {@code CurrentAndTrackPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code CurrentAndTrackPanel}.
     */
    private void initUi(ObservableList<Workout> backingList) {
        CurrentAndTrackPanel currentAndTrackPanel = new CurrentAndTrackPanel(backingList);
        uiPartRule.setUiPart(currentAndTrackPanel);

        currentAndTrackPanelHandle = new CurrentAndTrackPanelHandle(getChildNode(currentAndTrackPanel.getRoot(),
                CurrentAndTrackPanelHandle.WORKOUT_LIST_VIEW_ID));
    }
}
