package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.Model;
import seedu.address.model.ModelTypes;
import seedu.address.model.addressbook.ReadOnlyAddressBook;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.ReadOnlyExpensesList;
import seedu.address.model.person.Person;
import seedu.address.model.recruitment.ReadOnlyRecruitmentList;
import seedu.address.model.recruitment.Recruitment;
import seedu.address.model.schedule.ReadOnlyScheduleList;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public boolean canRedoModel() {
            throw new AssertionError("This method should not be called."); }

        @Override
        public boolean canUndoModel() {
            throw new AssertionError("This method should not be called."); }

        @Override
        public Set<ModelTypes> getNextCommitType() {
            throw new AssertionError("This method should not be called."); }

        @Override
        public Set<ModelTypes> getLastCommitType() {
            throw new AssertionError("This method should not be called."); }

        @Override
        public void addExpenses(Expenses expenses) {
            throw new AssertionError("This method should not be called."); }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be "
                    + "called.");
        }

        @Override
        public void addRecruitment(Recruitment recruitment) {
            throw new AssertionError("This method should not be "
                    + "called.");
        }

        @Override
        public void addSchedule(Schedule schedule) {
            throw new AssertionError("This method should not be "
                    + "called.");
        }


        //------------------------------------------------
        @Override
        public void resetAddressBookData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetDataExpenses(ReadOnlyExpensesList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetRecruitmentListData(ReadOnlyRecruitmentList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetScheduleListData(ReadOnlyScheduleList newData) {
            throw new AssertionError("This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public ReadOnlyExpensesList getExpensesList() {
            throw new AssertionError("This method should not be called."); }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public ReadOnlyRecruitmentList getRecruitmentList() {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public ReadOnlyScheduleList getScheduleList() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        //------------------------------------------------
        @Override
        public boolean hasExpenses(Expenses expenses) {
            throw new AssertionError("This method should not be called."); }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public boolean hasPerson(Person person, Predicate<Person> predicate) {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public boolean hasRecruitment(Recruitment recruitment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEmployeeId(Person person) {
            throw new AssertionError("This method "
                    + "should not be called.");
        }

        @Override
        public boolean hasSchedule(Schedule schedule) {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        //------------------------------------------------
        @Override
        public void deleteExpenses(Expenses target) {
            throw new AssertionError("This method should not be called."); }
        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public void deleteRecruitmentPost(Recruitment target) {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public void deleteSchedule(Schedule target) {
            throw new AssertionError("This method should "
                    + "not be called.");
        }


        //------------------------------------------------
        @Override
        public void updateExpenses(Expenses target, Expenses editedExpenses) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateRecruitment(Recruitment target, Recruitment editedRecruitment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSchedule(Schedule target, Schedule editedSchedule) {
            throw new AssertionError("This method should not be called.");
        }


        //------------------------------------------------
        @Override
        public ObservableList<Expenses> getFilteredExpensesList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList(FilteredList<Person> dummyFilteredPersons) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Recruitment> getFilteredRecruitmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Schedule> getFilteredScheduleList() {
            throw new AssertionError("This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public void updateFilteredExpensesList(Predicate<Expenses> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate, String sortOrder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRecruitmentList(Predicate<Recruitment> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredScheduleList(Predicate<Schedule> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public boolean canUndoExpensesList() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public boolean canUndoRecruitmentList() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public boolean canUndoScheduleList() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        //------------------------------------------------
        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoExpensesList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoRecruitmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoScheduleList() {
            throw new AssertionError("This method should not be called.");
        }
        //------------------------------------------------
        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoExpensesList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoRecruitmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoScheduleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoModelList() {
            throw new AssertionError("This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoExpensesList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoRecruitmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoScheduleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoModelList() {
            throw new AssertionError("This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitExpensesList() {
            throw new AssertionError("This method should not be called."); }

        @Override
        public void commitRecruitmentPostList() {
            throw new AssertionError("This method should "
                    + "not be called.");
        }

        @Override
        public void commitScheduleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitMultipleLists(Set<ModelTypes> set) {
            throw new AssertionError("This method should not be called.");
        }

    }

}
