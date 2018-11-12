package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecruitments.getTypicalRecruitmentList;
import static seedu.address.testutil.expenses.TypicalExpenses.getTypicalExpensesList;
import static seedu.address.testutil.schedule.TypicalSchedules.getTypicalScheduleList;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.addressbook.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(), getTypicalScheduleList(),
                getTypicalRecruitmentList(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder(ALICE).withEmployeeId("111111")
                .withDateOfBirth("21/03/1970").withPhone("85355255")
                .withEmail("alice@gmail.com").build();

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getExpensesList(), model.getScheduleList(),
                 model.getRecruitmentList(), new UserPrefs());
        expectedModel.addPerson(validPerson);
        expectedModel.commitAddressBook();

        try {
            assertCommandSuccess(new AddCommand(validPerson), model, commandHistory,
                    String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    @Test
    public void execute_duplicateEmployeeId_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_EMPLOYEEID);
    }

    @Test
    public void execute_duplicateEmail_throwsCommandException() {
        Person personInList = new PersonBuilder(ALICE).withEmployeeId("111111").build();
        assertCommandFailure(new AddCommand(personInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_EMAIL);
    }

    @Test
    public void execute_duplicatePhone_throwsCommandException() {
        Person personInList = new PersonBuilder(ALICE).withEmployeeId("111111").withEmail("hello@example.com").build();
        assertCommandFailure(new AddCommand(personInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_PHONE);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = new PersonBuilder(ALICE).withEmployeeId("111111").withEmail("hello@example.com")
                .withPhone("88888888").build();
        assertCommandFailure(new AddCommand(personInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_PERSON);
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
