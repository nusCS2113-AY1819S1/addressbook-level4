package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RECRUITMENT;
import static seedu.address.testutil.TypicalRecruitments.getTypicalRecruitments;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysRecruitment;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEqualsRecruitment;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

import guitests.guihandles.RecruitmentCardHandle;
import guitests.guihandles.RecruitmentListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRecruitmentPostRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.recruitment.Recruitment;
import seedu.address.storage.recruitment.XmlSerializableRecruitmentList;

public class RecruitmentListPanelTest extends GuiUnitTest {

    private static final ObservableList<Recruitment> TYPICAL_RECRUITMENTS =
            FXCollections.observableList(getTypicalRecruitments());

    private static final JumpToListRecruitmentPostRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToListRecruitmentPostRequestEvent(INDEX_SECOND_RECRUITMENT);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private RecruitmentListPanelHandle recruitmentListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_RECRUITMENTS);

        for (int i = 0; i < TYPICAL_RECRUITMENTS.size(); i++) {
            recruitmentListPanelHandle.navigateToCard(TYPICAL_RECRUITMENTS.get(i));
            Recruitment expectedRecruitment = TYPICAL_RECRUITMENTS.get(i);
            RecruitmentCardHandle actualCard = recruitmentListPanelHandle.getRecruitmentCardHandle(i);

            assertCardDisplaysRecruitment(expectedRecruitment, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRecruitmentPostRequestEvent() {
        initUi(TYPICAL_RECRUITMENTS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        RecruitmentCardHandle expectedRecruitment = recruitmentListPanelHandle.getRecruitmentCardHandle(
                INDEX_SECOND_RECRUITMENT.getZeroBased());
        RecruitmentCardHandle selectedRecruitment = recruitmentListPanelHandle.getHandleToSelectedCard();
        assertCardEqualsRecruitment(expectedRecruitment, selectedRecruitment);
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code RecruitmentListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Recruitment> backingList = createBackingList(10000);
        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of person cards exceeded time limit");
    }

    /**
     * Returns a list of recruitments containing {@code recruitmentCount} schedules that is used to populate the
     * {@code RecruitmentListPanel}.
     */
    private ObservableList<Recruitment> createBackingList(int recruitmentCount) throws Exception {
        Path xmlFile = createXmlFileWithRecruitmentPosts(recruitmentCount);
        XmlSerializableRecruitmentList xmlRecruitmentList =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableRecruitmentList.class);
        return FXCollections.observableArrayList(xmlRecruitmentList.toModelType().getRecruitmentList());
    }

    /**
     * Returns a .xml file containing {@code recruitmentCount} schedules.
     * This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithRecruitmentPosts(int recruitmentCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        // Solution for alphabets array below adapted from
        // https://stackoverflow.com/questions/17575840/better-way-to-generate-array-of-all-letters-in-the-alphabet
        char[] alphabets = IntStream.rangeClosed('a', 'z')
                .mapToObj(c -> "" + (char) c).collect(Collectors.joining()).toCharArray();
        String recruitmentStr = "a";
        int workExp = 0;
        int j = 0;
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<recruitmentlist>\n");
        for (int i = 0; i < recruitmentCount; i++) {
            builder.append("<recruitments>\n");
            builder.append("<post>" + recruitmentStr + "</post>\n");
            if ((i + 1) % 30 == 0) {
                j++;
                recruitmentStr = recruitmentStr.substring(0, (recruitmentStr.length() - 1));
                recruitmentStr += alphabets[j];
            }
            if ((i + 1) % 750 == 0) {
                j = 0;
                recruitmentStr += alphabets[j];
            }
            builder.append("<workExp>" + workExp + "</workExp>\n");
            if (workExp == 30) {
                workExp = 0;
            }
            workExp++;
            builder.append("<jobDescription>aaa</jobDescription>\n");
            builder.append("</recruitments>\n");
        }
        builder.append("</recruitmentlist>\n");

        Path manyRecruitmentPostsFile = Paths.get(TEST_DATA_FOLDER + "manyRecruitmentPosts.xml");
        FileUtil.createFile(manyRecruitmentPostsFile);
        FileUtil.writeToFile(manyRecruitmentPostsFile, builder.toString());
        manyRecruitmentPostsFile.toFile().deleteOnExit();
        return manyRecruitmentPostsFile;
    }

    /**
     * Initializes {@code recruitmentListPanelHandle} with a {@code RecruitmentListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code RecruitmentListPanel}.
     */
    private void initUi(ObservableList<Recruitment> backingList) {
        RecruitmentListPanel recruitmentListPanel = new RecruitmentListPanel(backingList);
        uiPartRule.setUiPart(recruitmentListPanel);

        recruitmentListPanelHandle = new RecruitmentListPanelHandle(getChildNode(recruitmentListPanel.getRoot(),
                RecruitmentListPanelHandle.RECRUITMENT_LIST_VIEW_ID));
    }
}
