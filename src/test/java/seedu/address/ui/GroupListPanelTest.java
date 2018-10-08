package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalGroups.getTypicalGroups;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GROUP;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysGroup;
import static seedu.address.ui.testutil.GuiTestAssert.assertGroupCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.GroupCardHandle;
import guitests.guihandles.GroupListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.group.Group;
import seedu.address.storage.XmlSerializableAddressBook;

public class GroupListPanelTest extends GuiUnitTest {
    private static final ObservableList<Group> TYPICAL_GROUPS =
            FXCollections.observableList(getTypicalGroups());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_GROUP);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private GroupListPanelHandle groupListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_GROUPS);

        for (int i = 0; i < TYPICAL_GROUPS.size(); i++) {
            groupListPanelHandle.navigateToCard(TYPICAL_GROUPS.get(i));
            Group expectedGroup = TYPICAL_GROUPS.get(i);
            GroupCardHandle actualCard = groupListPanelHandle.getGroupCardHandle(i);

            assertCardDisplaysGroup(expectedGroup, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_GROUPS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        GroupCardHandle expectedGroup = groupListPanelHandle.getGroupCardHandle(INDEX_SECOND_GROUP.getZeroBased());
        GroupCardHandle selectedGroup = groupListPanelHandle.getHandleToSelectedCard();
        assertGroupCardEquals(expectedGroup, selectedGroup);
    }

    /**
     * Verifies that creating and deleting large number of groups in {@code GroupListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Group> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of group cards exceeded time limit");
    }

    /**
     * Returns a list of groups containing {@code groupCount} groups that is used to populate the
     * {@code GroupListPanel}.
     */
    private ObservableList<Group> createBackingList(int groupCount) throws Exception {
        Path xmlFile = createXmlFileWithGroups(groupCount);
        XmlSerializableAddressBook xmlAddressBook =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableAddressBook.class);
        return FXCollections.observableArrayList(xmlAddressBook.toModelType().getGroupList());
    }

    /**
     * Returns a .xml file containing {@code groupCount} groups. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithGroups(int groupCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<addressbook>\n");
        for (int i = 0; i < groupCount; i++) {
            builder.append("<groups>\n");
            builder.append("<groupName>").append(i).append("a</groupName>\n");
            builder.append("<groupLocation>a</groupLocation>\n");
            builder.append("</groups>\n");
        }
        builder.append("</addressbook>\n");

        Path manyGroupsFile = Paths.get(TEST_DATA_FOLDER + "manyGroups.xml");
        FileUtil.createFile(manyGroupsFile);
        FileUtil.writeToFile(manyGroupsFile, builder.toString());
        manyGroupsFile.toFile().deleteOnExit();
        return manyGroupsFile;
    }

    /**
     * Initializes {@code groupListPanelHandle} with a {@code GroupListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code GroupListPanel}.
     */
    private void initUi(ObservableList<Group> backingList) {
        GroupListPanel groupListPanel = new GroupListPanel(backingList);
        uiPartRule.setUiPart(groupListPanel);

        groupListPanelHandle = new GroupListPanelHandle(getChildNode(groupListPanel.getRoot(),
                GroupListPanelHandle.GROUP_LIST_VIEW_ID));
    }

}
