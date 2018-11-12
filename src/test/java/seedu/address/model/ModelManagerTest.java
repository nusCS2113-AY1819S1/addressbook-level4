package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SCHEDULES;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.expenses.TypicalExpenses.ALICE_CLAIM;
import static seedu.address.testutil.schedule.TypicalSchedules.ALICE_WORK;
import static seedu.address.testutil.schedule.TypicalSchedules.BENSON_WORK;
import static seedu.address.testutil.schedule.TypicalSchedules.CARL_WORK;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.addressbook.AddressBook;
import seedu.address.model.expenses.ExpensesList;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.recruitment.RecruitmentList;
import seedu.address.model.schedule.ScheduleList;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.schedule.ScheduleListBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPerson(null);
    }

    @Test
    public void hasSchedule_nullSchedule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasSchedule(null);
    }

    @Test
    public void hasExpenses_nullExpenses_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasExpenses(null);
    }

    @Test
    public void hasRecruitment_nullRecruitment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasRecruitment(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasSchedule_personNotInScheduleList_returnsFalse() {
        assertFalse(modelManager.hasSchedule(ALICE_WORK));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasSchedule_personInScheduleList_returnsTrue() {
        modelManager.addSchedule(ALICE_WORK);
        assertTrue(modelManager.hasSchedule(ALICE_WORK));
    }

    @Test
    public void hasExpenses_personInExpensesList_returnsTrue() {
        modelManager.addExpenses(ALICE_CLAIM);
        assertTrue(modelManager.hasExpenses(ALICE_CLAIM));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    @Test
    public void getFilteredScheduleList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredScheduleList().remove(0);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        ScheduleList scheduleList = new ScheduleListBuilder().withSchedule(BENSON_WORK).withSchedule(CARL_WORK).build();
        ExpensesList expensesList = new ExpensesList();
        expensesList.addExpenses(ALICE_CLAIM);
        RecruitmentList recruitmentList = new RecruitmentList();
        AddressBook differentAddressBook = new AddressBook();
        ScheduleList differentScheduleList = new ScheduleList();
        ExpensesList differentExpensesList = new ExpensesList();
        RecruitmentList differentRecruitmentList = new RecruitmentList();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, expensesList, scheduleList, recruitmentList, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, expensesList, scheduleList, recruitmentList,
                userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, expensesList, scheduleList,
                recruitmentList, userPrefs)));

        // different expensesList -> returns false
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentExpensesList, scheduleList,
                recruitmentList, userPrefs)));

        // different scheduleList -> returns false
        assertFalse(modelManager.equals(new ModelManager(addressBook, expensesList, differentScheduleList,
                recruitmentList, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, expensesList, scheduleList, recruitmentList,
                userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        differentUserPrefs.setExpensesListFilePath(Paths.get("differentFilePath"));
        differentUserPrefs.setRecruitmentListFilePath(Paths.get("differentFilePath"));
        differentUserPrefs.setScheduleListFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, expensesList, scheduleList,
                recruitmentList, differentUserPrefs)));
    }
}
