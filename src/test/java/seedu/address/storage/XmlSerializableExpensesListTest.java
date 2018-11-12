package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.expenses.ExpensesList;
import seedu.address.storage.expenses.XmlSerializableExpensesList;
import seedu.address.testutil.expenses.TypicalExpenses;

public class XmlSerializableExpensesListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "XmlSerializableExpensesListTest");
    private static final Path TYPICAL_EXPENSES_FILE = TEST_DATA_FOLDER.resolve("typicalExpensesExpensesList.xml");
    private static final Path TYPICAL_EXPENSES_FILE_COPY = TEST_DATA_FOLDER.resolve(
            "typicalExpensesExpensesListCopy.xml");
    private static final Path INVALID_EXPENSES_FILE = TEST_DATA_FOLDER.resolve("invalidExpensesExpensesList.xml");
    private static final Path DUPLICATE_EXPENSES_FILE = TEST_DATA_FOLDER.resolve("duplicateExpensesExpensesList.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalExpensesFile_success() throws Exception {
        XmlSerializableExpensesList dataFromFile = XmlUtil.getDataFromFile(TYPICAL_EXPENSES_FILE,
                XmlSerializableExpensesList.class);
        ExpensesList expensesListFromFile = dataFromFile.toModelType();
        ExpensesList typicalExpensesExpensesList = TypicalExpenses.getTypicalExpensesList();
        assertEquals(expensesListFromFile, typicalExpensesExpensesList);
    }

    @Test
    public void toModelType_invalidExpensesFile_throwsIllegalValueException() throws Exception {
        XmlSerializableExpensesList dataFromFile = XmlUtil.getDataFromFile(INVALID_EXPENSES_FILE,
                XmlSerializableExpensesList.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateExpenses_throwsIllegalValueException() throws Exception {
        XmlSerializableExpensesList dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_EXPENSES_FILE,
                XmlSerializableExpensesList.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableExpensesList.MESSAGE_DUPLICATE_EXPENSES);
        dataFromFile.toModelType();
    }

    @Test
    public void equals_sameExpenses_true() throws Exception {
        XmlSerializableExpensesList dataFromFile = XmlUtil.getDataFromFile(TYPICAL_EXPENSES_FILE,
                XmlSerializableExpensesList.class);
        // same object -> returns true
        assertTrue(dataFromFile.equals(dataFromFile));
    }
    @Test
    public void equals_null_false() throws Exception {
        XmlSerializableExpensesList dataFromFile = XmlUtil.getDataFromFile(TYPICAL_EXPENSES_FILE,
                XmlSerializableExpensesList.class);
        // null object -> returns false
        assertFalse(dataFromFile.equals(null));
    }

    @Test
    public void equals_sameExpensesInDifferentFile_true() throws Exception {
        // same expenses in different file -> returns true
        XmlSerializableExpensesList dataFromFile = XmlUtil.getDataFromFile(TYPICAL_EXPENSES_FILE,
                XmlSerializableExpensesList.class);
        XmlSerializableExpensesList dataFromFileCopy = XmlUtil.getDataFromFile(TYPICAL_EXPENSES_FILE_COPY,
                XmlSerializableExpensesList.class);
        assertTrue(dataFromFile.equals(dataFromFileCopy));
    }

}
