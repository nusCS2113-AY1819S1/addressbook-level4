package seedu.recruit.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.recruit.testutil.EventsUtil.postNow;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.recruit.testutil.TypicalPersons.getTypicalPersons;
import static seedu.recruit.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.recruit.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Ignore;
import org.junit.Test;

import guitests.guihandles.CandidateCardHandle;
import guitests.guihandles.CandidateDetailsPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.recruit.commons.events.ui.JumpToListRequestEvent;
import seedu.recruit.commons.util.FileUtil;
import seedu.recruit.commons.util.XmlUtil;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.storage.XmlSerializableCandidateBook;

@Ignore
public class CandidateDetailsPanelTest extends GuiUnitTest {
    private static final ObservableList<Candidate> TYPICAL_CANDIDATES =
            FXCollections.observableList(getTypicalPersons());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private CandidateDetailsPanelHandle candidateDetailsPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_CANDIDATES);

        for (int i = 0; i < TYPICAL_CANDIDATES.size(); i++) {
            candidateDetailsPanelHandle.navigateToCard(TYPICAL_CANDIDATES.get(i));
            Candidate expectedCandidate = TYPICAL_CANDIDATES.get(i);
            CandidateCardHandle actualCard = candidateDetailsPanelHandle.getCandidateCardHandle(i);

            assertCardDisplaysPerson(expectedCandidate, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_CANDIDATES);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        CandidateCardHandle expectedPerson = candidateDetailsPanelHandle
                .getCandidateCardHandle(INDEX_SECOND.getZeroBased());
        CandidateCardHandle selectedPerson = candidateDetailsPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedPerson, selectedPerson);
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code PersonListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Candidate> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of candidate cards exceeded time limit");
    }

    /**
     * Returns a list of persons containing {@code personCount} persons that is used to populate the
     * {@code PersonListPanel}.
     */
    private ObservableList<Candidate> createBackingList(int personCount) throws Exception {
        Path xmlFile = createXmlFileWithPersons(personCount);
        XmlSerializableCandidateBook xmlAddressBook =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableCandidateBook.class);
        return FXCollections.observableArrayList(xmlAddressBook.toModelType().getCandidateList());
    }

    /**
     * Returns a .xml file containing {@code personCount} persons. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithPersons(int personCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<candidatebook>\n");
        for (int i = 0; i < personCount; i++) {
            builder.append("<persons>\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("<phone>000</phone>\n");
            builder.append("<email>a@aa</email>\n");
            builder.append("<recruit>a</recruit>\n");
            builder.append("</persons>\n");
        }
        builder.append("</candidatebook>\n");

        Path manyPersonsFile = Paths.get(TEST_DATA_FOLDER + "manyPersons.xml");
        FileUtil.createFile(manyPersonsFile);
        FileUtil.writeToFile(manyPersonsFile, builder.toString());
        manyPersonsFile.toFile().deleteOnExit();
        return manyPersonsFile;
    }

    /**
     * Initializes {@code CandidateDetailsPanelHandle} with a
     * {@code CandidateDetailsPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code CandidateDetailsPanel}.
     */
    private void initUi(ObservableList<Candidate> backingList) {
        CandidateDetailsPanel candidateDetailsPanel = new CandidateDetailsPanel(backingList);
        uiPartRule.setUiPart(candidateDetailsPanel);

        candidateDetailsPanelHandle = new CandidateDetailsPanelHandle(getChildNode(candidateDetailsPanel.getRoot(),
                CandidateDetailsPanelHandle.CANDIDATE_DETAILS_VIEW_ID));
    }
}
