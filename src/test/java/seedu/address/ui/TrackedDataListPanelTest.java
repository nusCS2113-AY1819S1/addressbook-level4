package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_WORKOUT;
import static seedu.address.testutil.TypicalParameters.getTypicalParameters;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysParameter;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.TrackedDataListCardHandle;
import guitests.guihandles.TrackedDataListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.workout.Parameter;
import seedu.address.storage.XmlSerializableTrackedDataList;

public class TrackedDataListPanelTest extends GuiUnitTest {
    private static final ObservableList<Parameter> TYPICAL_PARAMETERS =
            FXCollections.observableList(getTypicalParameters());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_WORKOUT);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private TrackedDataListPanelHandle trackedDataListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_PARAMETERS);

        for (int i = 0; i < TYPICAL_PARAMETERS.size(); i++) {
            trackedDataListPanelHandle.navigateToCard(TYPICAL_PARAMETERS.get(i));
            Parameter expectedWorkout = TYPICAL_PARAMETERS.get(i);
            TrackedDataListCardHandle actualCard = trackedDataListPanelHandle.getTrackedDataListCardHandle(i);

            assertCardDisplaysParameter(expectedWorkout, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_PARAMETERS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        TrackedDataListCardHandle expectedParameter = trackedDataListPanelHandle
                .getTrackedDataListCardHandle(INDEX_SECOND_WORKOUT.getZeroBased());
        TrackedDataListCardHandle selectedParameter = trackedDataListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedParameter, selectedParameter);
    }

    /**
     * Verifies that creating and deleting large number of parameters in {@code TrackedDataListPanel} requires
     * lesser than {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Parameter> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of parameter cards exceeded time limit");
    }

    /**
     * Returns a list of parameters containing {@code parameterCount} parameters that is used to populate the
     * {@code TrackedDataListPanel}.
     */
    private ObservableList<Parameter> createBackingList(int parameterCount) throws Exception {
        Path xmlFile = createXmlFileWithParameters(parameterCount);
        XmlSerializableTrackedDataList xmlTrackedDataList =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableTrackedDataList.class);
        return FXCollections.observableArrayList(xmlTrackedDataList.toModelType().getTrackedDataList());
    }

    /**
     * Returns a .xml file containing {@code parameterCount} parameters.
     * This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithParameters(int parameterCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<trackeddatalist>\n");
        for (int i = 0; i < parameterCount; i++) {
            builder.append("<parameters>\n");
            builder.append("<prefix>name/</prefix>\n");
            builder.append("<value>").append(i).append("a</value>\n");
            builder.append("</parameters>\n");
        }
        builder.append("</trackeddatalist>\n");

        Path manyParametersFile = Paths.get(TEST_DATA_FOLDER + "manyParameters.xml");
        FileUtil.createFile(manyParametersFile);
        FileUtil.writeToFile(manyParametersFile, builder.toString());
        manyParametersFile.toFile().deleteOnExit();
        return manyParametersFile;
    }

    /**
     * Initializes {@code trackedDataListPanelHandle} with a {@code TrackedDataListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code TrackedDataListPanel}.
     */
    private void initUi(ObservableList<Parameter> backingList) {
        TrackedDataListPanel trackedDataListPanel = new TrackedDataListPanel(backingList);
        uiPartRule.setUiPart(trackedDataListPanel);

        trackedDataListPanelHandle = new TrackedDataListPanelHandle(getChildNode(trackedDataListPanel.getRoot(),
                TrackedDataListPanelHandle.PARAMETER_LIST_VIEW_ID));
    }
}
