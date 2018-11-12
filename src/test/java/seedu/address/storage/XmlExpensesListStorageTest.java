package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.expenses.TypicalExpenses.ALICE_CLAIM;
import static seedu.address.testutil.expenses.TypicalExpenses.AMY_CLAIM;
import static seedu.address.testutil.expenses.TypicalExpenses.BOB_CLAIM;
import static seedu.address.testutil.expenses.TypicalExpenses.getTypicalExpensesList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.expenses.ExpensesList;
import seedu.address.model.expenses.ReadOnlyExpensesList;
import seedu.address.storage.expenses.XmlExpensesListStorage;

public class XmlExpensesListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlExpensesListStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readExpensesList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readExpensesList(null);
    }

    private java.util.Optional<ReadOnlyExpensesList> readExpensesList(String filePath) throws Exception {
        return new XmlExpensesListStorage(Paths.get(filePath)).readExpensesList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readExpensesList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readExpensesList("NotXmlFormatExpensesList.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readExpensesList_invalidExpensesExpensesList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readExpensesList("invalidExpensesExpensesList.xml");
    }

    @Test
    public void readExpensesList_invalidAndValidExpensesExpensesList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readExpensesList("invalidAndValidExpensesExpensesList.xml");
    }

    @Test
    public void readAndSaveExpensesList_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempExpensesList.xml");
        ExpensesList original = getTypicalExpensesList();
        XmlExpensesListStorage xmlExpensesListStorage = new XmlExpensesListStorage(filePath);

        //Save in new file and read back
        xmlExpensesListStorage.saveExpensesList(original, filePath);
        ReadOnlyExpensesList readBack = xmlExpensesListStorage.readExpensesList(filePath).get();
        assertEquals(original, new ExpensesList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addExpenses(BOB_CLAIM);
        original.removeExpenses(ALICE_CLAIM);
        xmlExpensesListStorage.saveExpensesList(original, filePath);
        readBack = xmlExpensesListStorage.readExpensesList(filePath).get();
        assertEquals(original, new ExpensesList(readBack));

        //Save and read without specifying file path
        original.addExpenses(AMY_CLAIM);
        xmlExpensesListStorage.saveExpensesList(original); //file path not specified
        readBack = xmlExpensesListStorage.readExpensesList().get(); //file path not specified
        assertEquals(original, new ExpensesList(readBack));

    }

    @Test
    public void saveExpensesList_nullExpensesList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveExpensesList(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveExpensesList(ReadOnlyExpensesList expensesList, String filePath) {
        try {
            new XmlExpensesListStorage(Paths.get(filePath))
                    .saveExpensesList(expensesList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveExpensesList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveExpensesList(new ExpensesList(), null);
    }


}
