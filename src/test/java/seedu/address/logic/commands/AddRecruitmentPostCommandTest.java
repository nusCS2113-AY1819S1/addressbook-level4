package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRecruitments.RECRUITMENT_EXAMPLE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelTypes;
import seedu.address.model.addressbook.ReadOnlyAddressBook;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.ReadOnlyExpensesList;
import seedu.address.model.person.Person;
import seedu.address.model.recruitment.ReadOnlyRecruitmentList;
import seedu.address.model.recruitment.Recruitment;
import seedu.address.model.recruitment.RecruitmentList;
import seedu.address.model.schedule.ReadOnlyScheduleList;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.recruitment.RecruitmentBuilder;

public class AddRecruitmentPostCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void constructor_nullSchedule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddRecruitmentPostCommand(null);
    }

    @Test
    public void execute_recruitmentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRecruitmentAdded modelStub = new ModelStubAcceptingRecruitmentAdded();
        Recruitment validRecruitment = new RecruitmentBuilder().build();

        CommandResult commandResult = new AddRecruitmentPostCommand(validRecruitment).execute(
                modelStub, commandHistory);

        assertEquals(String.format(AddRecruitmentPostCommand.MESSAGE_SUCCESS, validRecruitment),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validRecruitment), modelStub.recruitmentsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateRecruitment_throwsCommandException() throws Exception {
        Recruitment validRecruitment = new RecruitmentBuilder().build();
        AddRecruitmentPostCommand addRecruitmentPostCommand = new AddRecruitmentPostCommand(validRecruitment);
        ModelStub modelStub = new ModelStubWithRecruitment(validRecruitment);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddRecruitmentPostCommand.MESSAGE_DUPLICATE_POST);
        addRecruitmentPostCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_samePostHasRecruitment_throwsCommandException() throws CommandException {
        ModelStubAcceptingRecruitmentAdded modelStub = new ModelStubAcceptingRecruitmentAdded();

        //Add recruitment post -> success
        Recruitment reRecruitment = new RecruitmentBuilder(RECRUITMENT_EXAMPLE).build();
        CommandResult commandResult = new AddRecruitmentPostCommand(reRecruitment).execute(modelStub, commandHistory);
        assertEquals(String.format(AddRecruitmentPostCommand.MESSAGE_SUCCESS, reRecruitment),
                commandResult.feedbackToUser);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Recruitment re = new RecruitmentBuilder().withPost("Network Engineer").withJobDescription(
                "Ensure all networking devices are in working condition").build();
        AddRecruitmentPostCommand addReCommand = new AddRecruitmentPostCommand(re);

        // same object -> returns true
        assertTrue(addReCommand.equals(addReCommand));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Recruitment re = new RecruitmentBuilder().withPost("Network Engineer").withJobDescription(
                "Ensure all networking devices are in working condition").build();
        AddRecruitmentPostCommand addReCommand = new AddRecruitmentPostCommand(re);

        // same values -> returns true
        AddRecruitmentPostCommand addReCommandCopy = new AddRecruitmentPostCommand(re);
        assertTrue(addReCommand.equals(addReCommandCopy));
    }

    @Test
    public void equals_null_returnsFalse() {
        Recruitment re = new RecruitmentBuilder().withPost("Network Engineer").withJobDescription(
                "Ensure all networking devices are in working condition").build();
        AddRecruitmentPostCommand addReCommand = new AddRecruitmentPostCommand(re);

        // null -> returns false
        assertFalse(addReCommand == null);
    }

    @Test
    public void equals_differentRecruitment_returnsFalse() {
        Recruitment re = new RecruitmentBuilder().withPost("Network Engineer").withJobDescription(
                "Ensure all networking devices are in working condition").build();
        AddRecruitmentPostCommand addReCommand = new AddRecruitmentPostCommand(re);
        Recruitment reOne = new RecruitmentBuilder().withPost("Accountants").withJobDescription(
                "To perform audits and financial statement analysis").build();
        AddRecruitmentPostCommand addReOneCommand = new AddRecruitmentPostCommand(reOne);

        // different recruitment posts -> returns false
        assertFalse(addReCommand.equals(addReOneCommand));
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
            throw new AssertionError("This method should not be called."); }

        @Override
        public void addRecruitment(Recruitment recruitment) {
            throw new AssertionError("This method should not be called."); }

        @Override
        public void addSchedule(Schedule schedule) {
            throw new AssertionError("This method should not be " + "called."); }


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
            throw new AssertionError("This method should " + "not be called."); }

        @Override
        public ReadOnlyRecruitmentList getRecruitmentList() {
            throw new AssertionError("This method "
                    + "should not be called."); }

        @Override
        public ReadOnlyScheduleList getScheduleList() {
            throw new AssertionError("This method should not be called."); }

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
        public boolean hasEmployeeId(Person person) {
            return true;
        }

        @Override
        public boolean hasRecruitment(Recruitment recruitment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSchedule(Schedule schedule) {
            throw new AssertionError(
                    "This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public void deleteExpenses(Expenses target) {
            throw new AssertionError("This method should not be called."); }
        @Override
        public void deletePerson(Person target) {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public void deleteRecruitmentPost(Recruitment recruitment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSchedule(Schedule target) {
            throw new AssertionError(
                    "This method should not be called.");
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

        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {

        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate, String sortOrder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRecruitmentList(Predicate<Recruitment> predicate) {

        }

        @Override
        public void updateFilteredScheduleList(Predicate<Schedule> predicate) {

        }

        //------------------------------------------------
        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public boolean canUndoExpensesList() {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public boolean canUndoRecruitmentList() {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public boolean canUndoScheduleList() {
            throw new AssertionError(
                    "This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public boolean canRedoExpensesList() {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public boolean canRedoRecruitmentList() {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public boolean canRedoScheduleList() {
            throw new AssertionError(
                    "This method should not be called.");
        }

        //------------------------------------------------
        @Override
        public void undoAddressBook() {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public void undoExpensesList() {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public void undoRecruitmentList() {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public void undoScheduleList() {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public void undoModelList() {
            throw new AssertionError(
                    "This method should not be called.");
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
            throw new AssertionError(
                    "This method should not be called.");
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

    /**
     * A Model stub that contains a single recruitment.
     */
    private class ModelStubWithRecruitment extends ModelStub {
        private final Recruitment recruitment;

        ModelStubWithRecruitment(Recruitment recruitment) {
            requireNonNull(recruitment);
            this.recruitment = recruitment;
        }

        @Override
        public boolean hasRecruitment(Recruitment recruitment) {
            requireNonNull(recruitment);
            return this.recruitment.isSameRecruitment(recruitment);
        }
    }

    /**
     * A Model stub that always accept the post being added.
     */
    private class ModelStubAcceptingRecruitmentAdded extends ModelStub {
        private final ArrayList<Recruitment> recruitmentsAdded = new ArrayList<>();

        @Override
        public boolean hasRecruitment(Recruitment recruitment) {
            requireNonNull(recruitment);
            return recruitmentsAdded.stream().anyMatch(recruitment::isSameRecruitment);
        }

        @Override
        public void addRecruitment(Recruitment recruitment) {
            requireNonNull(recruitment);
            recruitmentsAdded.add(recruitment);
        }

        @Override
        public void commitRecruitmentPostList() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyRecruitmentList getRecruitmentList() {
            return new RecruitmentList();
        }
    }
}
