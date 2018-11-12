package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.expenses.TypicalExpenses.getTypicalExpenses;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysExpenses;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEqualsExpenses;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.ExpensesCardHandle;
import guitests.guihandles.ExpensesListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListExpensesRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.expenses.Expenses;
import seedu.address.storage.expenses.XmlSerializableExpensesList;


public class ExpensesListPanelTest extends GuiUnitTest {

    private static final ObservableList<Expenses> TYPICAL_EXPENSES =
            FXCollections.observableList(getTypicalExpenses());

    private static final JumpToListExpensesRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToListExpensesRequestEvent(INDEX_SECOND_PERSON);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private ExpensesListPanelHandle expensesListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_EXPENSES);

        for (int i = 0; i < TYPICAL_EXPENSES.size(); i++) {
            expensesListPanelHandle.navigateToCard(TYPICAL_EXPENSES.get(i));
            Expenses expectedExpenses = TYPICAL_EXPENSES.get(i);
            ExpensesCardHandle actualCard = expensesListPanelHandle.getExpensesCardHandle(i);

            assertCardDisplaysExpenses(expectedExpenses, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListExpensesRequestEvent() {
        initUi(TYPICAL_EXPENSES);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        ExpensesCardHandle expectedExpenses = expensesListPanelHandle.getExpensesCardHandle(
                INDEX_SECOND_PERSON.getZeroBased());
        ExpensesCardHandle selectedScheduke = expensesListPanelHandle.getHandleToSelectedCard();
        assertCardEqualsExpenses(expectedExpenses, selectedScheduke);
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code ExpensesListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Expenses> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of person cards exceeded time limit");
    }

    /**
     * Returns a list of expenses containing {@code expensesCount} expenses that is used to populate the
     * {@code ExpensesListPanel}.
     */
    private ObservableList<Expenses> createBackingList(int expensesCount) throws Exception {
        Path xmlFile = createXmlFileWithExpenses(expensesCount);
        XmlSerializableExpensesList xmlExpensesList =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableExpensesList.class);
        return FXCollections.observableArrayList(xmlExpensesList.toModelType().getExpensesRequestList());
    }

    /**
     * Returns a .xml file containing {@code expensesCount} expenses.
     * This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithExpenses(int expensesCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<expenseslist>\n");
        for (int i = 0; i < expensesCount; i++) {
            builder.append("<multiExpenses>\n");
            String employeeIdFormatted = String.format("%06d", i);
            builder.append("<id>" + employeeIdFormatted + "</id>\n");
            builder.append("<expensesAmount>369.00</expensesAmount>\n");
            builder.append("<travelExpenses>123.00</travelExpenses>\n");
            builder.append("<medicalExpenses>123.00</medicalExpenses>\n");
            builder.append("<miscellaneousExpenses>123.00</miscellaneousExpenses>\n");
            builder.append("</multiExpenses>\n");
        }
        builder.append("</expenseslist>\n");

        Path manyExpensesFile = Paths.get(TEST_DATA_FOLDER + "manyExpenses.xml");
        FileUtil.createFile(manyExpensesFile);
        FileUtil.writeToFile(manyExpensesFile, builder.toString());
        manyExpensesFile.toFile().deleteOnExit();
        return manyExpensesFile;
    }

    /**
     * Initializes {@code expensesListPanelHandle} with a {@code ExpensesListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code ExpensesListPanel}.
     */
    private void initUi(ObservableList<Expenses> backingList) {
        ExpensesListPanel expensesListPanel = new ExpensesListPanel(backingList);
        uiPartRule.setUiPart(expensesListPanel);

        expensesListPanelHandle = new ExpensesListPanelHandle(getChildNode(expensesListPanel.getRoot(),
                ExpensesListPanelHandle.EXPENSES_LIST_VIEW_ID));
    }
}
