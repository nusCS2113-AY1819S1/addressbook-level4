//package seedu.address.logic.commands;
//
//import static java.util.Objects.requireNonNull;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static seedu.address.testutil.PersonBuilder.DEFAULT_NAME;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.function.Predicate;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//
//import javafx.collections.ObservableList;
//import seedu.address.logic.CommandHistory;
//import seedu.address.logic.commands.exceptions.CommandException;
//import seedu.address.model.AddressBook;
//import seedu.address.model.Model;
//import seedu.address.model.ReadOnlyAddressBook;
//import seedu.address.model.grade.Marks;
//import seedu.address.model.grade.TestName;
//import seedu.address.model.group.AddGroup;
//import seedu.address.model.group.Group;
//import seedu.address.model.person.Name;
//import seedu.address.model.person.NameContainsKeywordsPredicate;
//import seedu.address.model.person.Person;
//import seedu.address.testutil.PersonBuilder;
//
//public class AddTestMarksCommandTest {
//
//    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
//
//    @Rule
//    public ExpectedException thrown = ExpectedException.none();
//
//    private CommandHistory commandHistory = new CommandHistory();
//
//    @Test
//    public void constructor_nullTest_throwsNullPointerException() {
//        thrown.expect(NullPointerException.class);
//        new AddTestMarksCommand(null,null,null);
//    }
//
//    @Test
//    public void execute_testAcceptedByModel_addSuccessful() throws Exception {
//        ModelStubAcceptingTest modelStub = new ModelStubAcceptingTest();
//        Name validPerson = new NameContainsKeywordsPredicate();
//        TestName validTest = new PersonBuilder().build().getTests().iterator().next().getTestName();
//        Marks validMarks = new PersonBuilder().build().getTests().iterator().next().getMarks();
//        CommandResult commandResult = new AddTestMarksCommand(validPerson, validTest.toString(), validMarks.toString()).execute(modelStub, commandHistory);
//
//        assertEquals(String.format(AddTestMarksCommand.MESSAGE_SUCCESS, validPerson, validTest), commandResult.feedbackToUser);
//        assertEquals(Arrays.asList(validPerson, validTest), modelStub.testsAdded);
//        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
//    }
//
//    @Test
//    public void execute_duplicateTest_throwsCommandException() throws Exception {
//        Person validPerson = new PersonBuilder().build();
//        List<String> nameList = new ArrayList<>();
//        nameList.add(DEFAULT_NAME);
//        AddTestMarksCommand addTestMarksCommand = new AddTestMarksCommand(new NameContainsKeywordsPredicate(nameList),"Test1","22");
//        ModelStub modelStub = new ModelStubWithPerson(validPerson);
//
//        thrown.expect(CommandException.class);
//        thrown.expectMessage(AddTestMarksCommand.MESSAGE_DUPLICATE_TEST);
//        addTestMarksCommand.execute(modelStub, commandHistory);
//
//    }
//
//    /**
//     * A default model stub that have all of the methods failing.
//     */
//    private class ModelStub implements Model {
//        @Override
//        public void addPerson(Person person) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void resetData(ReadOnlyAddressBook newData) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public ReadOnlyAddressBook getAddressBook() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public boolean hasPerson(Person person) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void deletePerson(Person target) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void updatePerson(Person target, Person editedPerson) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public ObservableList<Person> getFilteredPersonList() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public ObservableList<Group> getFilteredGroupList() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void updateFilteredPersonList(Predicate<Person> predicate) {
//            throw new AssertionError("This test already exists in the system");
//        }
//
//        @Override
//        public void updateFilteredGroupList(Predicate<Group> predicate) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public boolean canUndoAddressBook() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public boolean canRedoAddressBook() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void undoAddressBook() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void redoAddressBook() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void commitAddressBook() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void createGroup(Group createGroup) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public boolean hasGroup(Group checkGroup) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void addGroup(AddGroup addGroup) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public boolean hasPersonInGroup(AddGroup addGroup) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//    }
//
//    /**
//     * A Model stub that contains a single person.
//     */
//    private class ModelStubWithPerson extends ModelStub {
//        private final Person person;
//
//        ModelStubWithPerson(Person person) {
//            requireNonNull(person);
//            this.person = person;
//        }
//
//        @Override
//        public boolean hasPerson(Person person) {
//            requireNonNull(person);
//            return this.person.isSamePerson(person);
//        }
//    }
//
//
//    /**
//     * A Model stub that always accept the person being added.
//     */
//    private class ModelStubAcceptingTest extends ModelStub {
//        final ArrayList<Test> testsAdded = new ArrayList<>();
//
//        @Override
//        public boolean hasPerson(Person person) {
//            requireNonNull(person);
//            return personsAdded.stream().anyMatch(person::isSamePerson);
//        }
//
//        @Override
//        public void addPerson(Person person) {
//            requireNonNull(person);
//            personsAdded.add(person);
//        }
//
//        @Override
//        public void commitAddressBook() {
//            // called by {@code AddCommand#execute()}
//        }
//
//        @Override
//        public ReadOnlyAddressBook getAddressBook() {
//            return new AddressBook();
//        }
//    }
//
//}
