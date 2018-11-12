package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.PAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BONUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALARY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecruitments.getTypicalRecruitmentList;
import static seedu.address.testutil.expenses.TypicalExpenses.getTypicalExpensesList;
import static seedu.address.testutil.schedule.TypicalSchedules.getTypicalScheduleList;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.ModifyPayCommand.ModSalaryDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.addressbook.AddressBook;
import seedu.address.model.person.Bonus;
import seedu.address.model.person.Person;
import seedu.address.model.person.Salary;
import seedu.address.testutil.ModSalaryDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for ModifyPayCommand.
 */
public class ModifyPayCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(), getTypicalScheduleList(),
             getTypicalRecruitmentList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_someFieldsSpecified_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person modifiedPerson = personInList.withSalary(VALID_SALARY_BOB).withBonus(VALID_BONUS_BOB).build();

        ModSalaryDescriptor descriptor = new ModSalaryDescriptorBuilder().withSalary(VALID_SALARY_BOB)
                .withBonus(VALID_BONUS_BOB).build();
        ModifyPayCommand modifyPayCommand = new ModifyPayCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(ModifyPayCommand.MESSAGE_MODIFIED_SUCCESS, modifiedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getExpensesList(),
                model.getScheduleList(), model.getRecruitmentList(), new UserPrefs());
        expectedModel.updatePerson(lastPerson, modifiedPerson);
        expectedModel.commitAddressBook();

        try {
            assertCommandSuccess(modifyPayCommand, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        } catch (AssertionError ae) {
            ae.printStackTrace();
        }
    }

    @Test
    public void execute_noFieldSpecified_success() {
        ModifyPayCommand modifyPayCommand = new ModifyPayCommand(INDEX_FIRST_PERSON, new ModSalaryDescriptor());
        Person modifiedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(ModifyPayCommand.MESSAGE_MODIFIED_SUCCESS, modifiedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getExpensesList(),
                model.getScheduleList(), model.getRecruitmentList(), new UserPrefs());
        expectedModel.commitAddressBook();

        try {
            assertCommandSuccess(modifyPayCommand, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personInFilteredList).withSalary(VALID_SALARY_BOB).build();
        ModifyPayCommand modifyPayCommand = new ModifyPayCommand(INDEX_FIRST_PERSON,
                new ModSalaryDescriptorBuilder().withSalary(VALID_SALARY_BOB).build());

        String expectedMessage = String.format(ModifyPayCommand.MESSAGE_MODIFIED_SUCCESS, modifiedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getExpensesList(),
                model.getScheduleList(), model.getRecruitmentList(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), modifiedPerson);
        expectedModel.commitAddressBook();

        try {
            assertCommandSuccess(modifyPayCommand, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        } catch (AssertionError ae) {
            ae.printStackTrace();
        }
    }

    @Test
    public void execute_lowerBoundValueSalary_failure() throws ParseException {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ModSalaryDescriptor descriptor = new ModSalaryDescriptorBuilder(firstPerson)
                .withSalary("8000.00").build();
        descriptor.setSalary(ParserUtil.parseSalary("-8000"));
        ModifyPayCommand modifyPayCommand = new ModifyPayCommand(INDEX_FIRST_PERSON, descriptor);

        try {
            assertCommandFailure(modifyPayCommand, model, commandHistory, ModifyPayCommand.MESSAGE_NEGATIVE_PAY);
        } catch (AssertionError ae) {
            ae.printStackTrace();
        }
    }

    @Test
    public void execute_upperBoundValueSalary_failure() throws ParseException {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ModSalaryDescriptor descriptor = new ModSalaryDescriptorBuilder(firstPerson)
                .withSalary("8000.00").build();
        descriptor.setSalary(ParserUtil.parseSalary("992000"));
        ModifyPayCommand modifyPayCommand = new ModifyPayCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(modifyPayCommand, model, commandHistory, Salary.MESSAGE_SALARY_CONSTRAINTS);
    }

    @Test
    public void execute_lowerBoundPercentSalary_failure() throws ParseException {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ModSalaryDescriptor descriptor = new ModSalaryDescriptorBuilder(firstPerson)
                .withSalary("8000.00").build();
        descriptor.setSalary(ParserUtil.parseSalary("%-100"));
        ModifyPayCommand modifyPayCommand = new ModifyPayCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(modifyPayCommand, model, commandHistory, ModifyPayCommand.MESSAGE_NEGATIVE_PAY);
    }

    @Test
    public void execute_upperBoundPercentSalary_failure() throws ParseException {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ModSalaryDescriptor descriptor = new ModSalaryDescriptorBuilder(firstPerson)
                .withSalary("8000.00").build();
        descriptor.setSalary(ParserUtil.parseSalary("%12500"));
        ModifyPayCommand modifyPayCommand = new ModifyPayCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(modifyPayCommand, model, commandHistory, Salary.MESSAGE_SALARY_CONSTRAINTS);
    }

    @Test
    public void execute_lowerBoundValueSalary_success() throws ParseException {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ModSalaryDescriptor descriptor = new ModSalaryDescriptorBuilder(firstPerson)
                .withSalary("8000.00").build();
        descriptor.setSalary(ParserUtil.parseSalary("-7999.99"));
        ModifyPayCommand modifyPayCommand = new ModifyPayCommand(INDEX_FIRST_PERSON, descriptor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getExpensesList(),
                model.getScheduleList(), model.getRecruitmentList(), new UserPrefs());
        expectedModel.commitAddressBook();

        try {
            assertCommandSuccess(modifyPayCommand, model, commandHistory,
                    ModifyPayCommand.MESSAGE_MODIFIED_SUCCESS, expectedModel);
        } catch (AssertionError ae) {
            ae.printStackTrace();
        }
    }

    @Test
    public void execute_upperBoundValueSalary_success() throws ParseException {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(firstPerson).withSalary("999999.99").build();
        ModSalaryDescriptor descriptor = new ModSalaryDescriptorBuilder(firstPerson)
                .withSalary("8000.00").build();
        descriptor.setSalary(ParserUtil.parseSalary("991999.99"));
        ModifyPayCommand modifyPayCommand = new ModifyPayCommand(INDEX_FIRST_PERSON, descriptor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getExpensesList(),
                model.getScheduleList(), model.getRecruitmentList(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), modifiedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(modifyPayCommand, model, commandHistory,
                ModifyPayCommand.MESSAGE_MODIFIED_SUCCESS, expectedModel);
    }

    @Test
    public void execute_lowerBoundPercentSalary_success() throws ParseException {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(firstPerson).withSalary("0.08").build();
        ModSalaryDescriptor descriptor = new ModSalaryDescriptorBuilder(firstPerson)
                .withSalary("8000.00").build();
        descriptor.setSalary(ParserUtil.parseSalary("%-99.99"));
        ModifyPayCommand modifyPayCommand = new ModifyPayCommand(INDEX_FIRST_PERSON, descriptor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getExpensesList(),
                model.getScheduleList(), model.getRecruitmentList(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), modifiedPerson);
        expectedModel.commitAddressBook();

        try {
            assertCommandSuccess(modifyPayCommand, model, commandHistory,
                    ModifyPayCommand.MESSAGE_MODIFIED_SUCCESS, expectedModel);
        } catch (AssertionError ae) {
            ae.printStackTrace();
        }
    }

    @Test
    public void execute_upperBoundPercentSalary_success() throws ParseException {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ModSalaryDescriptor descriptor = new ModSalaryDescriptorBuilder(firstPerson)
                .withSalary("8000.00").build();
        descriptor.setSalary(ParserUtil.parseSalary("%124.99"));
        ModifyPayCommand modifyPayCommand = new ModifyPayCommand(INDEX_FIRST_PERSON, descriptor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getExpensesList(),
                model.getScheduleList(), model.getRecruitmentList(), new UserPrefs());
        expectedModel.commitAddressBook();

        try {
            assertCommandSuccess(modifyPayCommand, model, commandHistory,
                    ModifyPayCommand.MESSAGE_MODIFIED_SUCCESS, expectedModel);
        } catch (AssertionError ae) {
            ae.printStackTrace();
        }
    }

    @Test
    public void execute_upperBoundBonus_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ModSalaryDescriptor descriptor = new ModSalaryDescriptorBuilder(firstPerson)
                .withSalary("999999.99").withBonus("25").build();
        ModifyPayCommand modifyPayCommand = new ModifyPayCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(modifyPayCommand, model, commandHistory, Bonus.MESSAGE_BONUS_CONSTRAINTS);
    }

    @Test
    public void execute_lowerBoundBonus_success() throws ParseException {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ModSalaryDescriptor descriptor = new ModSalaryDescriptorBuilder(firstPerson)
                .withSalary("8000.00").withBonus("0").build();
        descriptor.setBonus(ParserUtil.parseBonus("1"));
        ModifyPayCommand modifyPayCommand = new ModifyPayCommand(INDEX_FIRST_PERSON, descriptor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getExpensesList(),
                model.getScheduleList(), model.getRecruitmentList(), new UserPrefs());
        expectedModel.commitAddressBook();

        try {
            assertCommandSuccess(modifyPayCommand, model, commandHistory,
                    ModifyPayCommand.MESSAGE_MODIFIED_SUCCESS, expectedModel);
        } catch (AssertionError ae) {
            ae.printStackTrace();
        }
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ModSalaryDescriptor descriptor = new ModSalaryDescriptorBuilder().withSalary(VALID_SALARY_BOB).build();
        ModifyPayCommand modifyPayCommand = new ModifyPayCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(modifyPayCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * 1. Modify a {@code Person} from a filtered list.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modify. This ensures {@code RedoCommand} modify the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonModified() throws Exception {
        Person modifiedPerson = new PersonBuilder(BENSON).build();
        ModSalaryDescriptor descriptor = new ModSalaryDescriptorBuilder(modifiedPerson).build();
        ModifyPayCommand modifyPayCommand = new ModifyPayCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getExpensesList(),
                model.getScheduleList(), model.getRecruitmentList(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.updatePerson(personToModify, modifiedPerson);
        expectedModel.commitAddressBook();

        // modify -> modify second person in unfiltered person list / first person in filtered person list
        modifyPayCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        try {
            assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
        } catch (AssertionError ae) {
            ae.printStackTrace();
        }

        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personToModify);
        // redo -> modify same second person in unfiltered person list
        expectedModel.redoAddressBook();
        try {
            assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
        } catch (AssertionError ae) {
            ae.printStackTrace();
        }
    }

    @Test
    public void equals() {
        final ModifyPayCommand standardCommand = new ModifyPayCommand(INDEX_FIRST_PERSON, PAY_AMY);

        // same values -> returns true
        ModSalaryDescriptor copyDescriptor = new ModSalaryDescriptor(PAY_AMY);
        ModifyPayCommand commandWithSameValues = new ModifyPayCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        assertTrue(copyDescriptor.equals(copyDescriptor));

        // null -> returns false
        assertFalse(standardCommand.equals(null));
        assertFalse(copyDescriptor.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ModifyPayCommand(INDEX_SECOND_PERSON, PAY_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new ModifyPayCommand(INDEX_FIRST_PERSON, PAY_BOB)));
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     * - filteredPersonList is changed therefore a different implementation of assertCommandFailure is created
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

}
