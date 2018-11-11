package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TestUtil.waitForRunLater;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseBook;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.embed.swing.JFXPanel;

import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.EventBookBuilder;
import seedu.address.testutil.ExpenseBookBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TaskBuilder;


public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Before
    public void setup() {
        new JFXPanel();
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    @Test
    public void getFilteredExpenseList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredExpenseList().remove(0);
    }

    //@@author QzSG
    @Test
    public void restoreAddressBook_withValidAddressBook_restoreSuccess() throws InterruptedException {
        AddressBook addressBook = getTypicalAddressBook();
        modelManager.addPerson(new PersonBuilder().build());
        modelManager.restoreAddressBook(addressBook);
        waitForRunLater();
        assertEquals(addressBook, modelManager.getAddressBook());
    }

    @Test
    public void restoreEventBook_withValidEventBook_restoreSuccess() throws InterruptedException {
        ReadOnlyEventBook eventBook = SampleDataUtil.getSampleEventBook();
        modelManager.restoreEventBook(eventBook);
        waitForRunLater();
        //Equals method not implemented correctly by implementer
        assertEquals(eventBook.toString(), modelManager.getEventBook().toString());
    }

    @Test
    public void restoreExpenseBook_withValidExpenseBook_restoreSuccess() throws InterruptedException {
        ExpenseBook expenseBook = getTypicalExpenseBook();
        modelManager.restoreExpenseBook(expenseBook);
        waitForRunLater();
        assertEquals(expenseBook, modelManager.getExpenseBook());
    }

    @Test
    public void restoreTaskBook_withValidTaskBook_restoreSuccess() throws InterruptedException {
        TaskBook taskBook = getTypicalTaskBook();
        modelManager.addTask(new TaskBuilder().build());
        modelManager.restoreTaskBook(taskBook);
        waitForRunLater();
        assertEquals(taskBook, modelManager.getTaskBook());
    }
    //@@author
    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        EventBook eventBook = new EventBookBuilder().build();
        ExpenseBook expenseBook = new ExpenseBookBuilder().build();
        AddressBook differentAddressBook = new AddressBook();
        EventBook differentEventBook = new EventBook();
        ExpenseBook differentExpenseBook = new ExpenseBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, expenseBook, eventBook, new TaskBook(), userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, expenseBook, eventBook,
                new TaskBook(), userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, differentExpenseBook,
                differentEventBook, new TaskBook(), userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, expenseBook, eventBook,
                new TaskBook(), userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        modelManager.updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, expenseBook, eventBook,
                new TaskBook(), differentUserPrefs)));
    }
}
