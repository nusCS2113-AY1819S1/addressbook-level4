package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Department;
import seedu.address.model.person.Person;
import seedu.address.model.prioritylevel.PriorityLevel;
import seedu.address.model.prioritylevel.PriorityLevelEnum;
import seedu.address.session.SessionManager;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

//@@author Woonhian
/**
 * Contains integration tests and unit tests for SetDepartmentCommand.
 */
public class SetDepartmentCommandTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            new SetDepartmentCommand(null, null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new SetDepartmentCommand(INDEX_FIRST_PERSON, null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new SetDepartmentCommand(null, new Department("Junior Management"));
        });
    }

    @Test
    public void execute_notLoggedIn_throwsCommandException() {
        Assertions.assertThrows(CommandException.class, () -> {
            SetDepartmentCommand sd = new SetDepartmentCommand(INDEX_FIRST_PERSON,
                    new Department("Junior Management"));
            sd.execute(model, commandHistory);
        }, SessionManager.NOT_LOGGED_IN);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws CommandException {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        LoginCommand loginCommand = new LoginCommand(TypicalPersons.ALICE.getNric(),
                TypicalPersons.ALICE.getPassword());
        loginCommand.execute(model, commandHistory);

        SetDepartmentCommand sd = new SetDepartmentCommand(outOfBoundIndex,
                new Department("Junior Management"));

        assertCommandFailure(sd, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * After this test method, Alice's Department remains, whereas Daniel's Department will become Junior Management.
     */
    @Test
    public void execute_setDepartmentOfFourthPerson_success() throws CommandException {
        SessionManager.getInstance(model).destroy();
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

        LoginCommand loginCommand = new LoginCommand(TypicalPersons.ALICE.getNric(),
                TypicalPersons.ALICE.getPassword());
        loginCommand.execute(model, commandHistory);

        Person editedDaniel = new PersonBuilder(TypicalPersons.DANIEL)
                .withDepartment("Junior Management").build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(TypicalPersons.DANIEL, editedDaniel);
        expectedModel.commitAddressBook();

        SetDepartmentCommand sd = new SetDepartmentCommand(INDEX_FOURTH_PERSON,
                new Department("Junior Management"));
        String expectedMessage = String.format(SetDepartmentCommand.MESSAGE_CHANGE_DEPARTMENT_SUCCESS,
                TypicalPersons.DANIEL.getName(), "Junior Management");
        assertCommandSuccess(sd, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_insufficientPriorityLevel_throwsCommandException() throws CommandException {
        Person editedDaniel = new PersonBuilder(TypicalPersons.DANIEL)
                .withPriorityLevel(PriorityLevelEnum.BASIC.getPriorityLevelCode()).build();
        model.updatePerson(TypicalPersons.DANIEL, editedDaniel);
        LoginCommand loginCommand = new LoginCommand(TypicalPersons.DANIEL.getNric(),
                TypicalPersons.DANIEL.getPassword());
        loginCommand.execute(model, commandHistory);

        SetDepartmentCommand sd = new SetDepartmentCommand(INDEX_FIRST_PERSON,
                new Department("Junior Management"));

        Assertions.assertThrows(CommandException.class, () -> sd.execute(model, commandHistory),
                String.format(PriorityLevel.INSUFFICIENT_PRIORITY_LEVEL, PriorityLevelEnum.ADMINISTRATOR));

    }

    @Test
    public void execute_editOwnDepartment_throwsCommandException() throws CommandException {
        LoginCommand loginCommand = new LoginCommand(TypicalPersons.ALICE.getNric(),
                TypicalPersons.ALICE.getPassword());
        loginCommand.execute(model, commandHistory);

        SetDepartmentCommand sd = new SetDepartmentCommand(INDEX_FIRST_PERSON,
                new Department("Senior Management"));

        Assertions.assertThrows(CommandException.class, () -> sd.execute(model, commandHistory),
                SetDepartmentCommand.MESSAGE_CANNOT_EDIT_OWN_DEPARTMENT);
    }

    /**
     * Logs out of the application after each test
     */
    @After
    public void tearDown() throws CommandException {
        try {
            new LogoutCommand().execute(model, commandHistory);
        } catch (CommandException ce) {
            //Ignores the CommandException if user is not logged in in the first place.
            if (!ce.getMessage().equals(LogoutCommand.NOT_LOGGED_IN)) {
                throw new CommandException(ce.getMessage());
            }
        } finally {
            SessionManager.getInstance(model).destroy();
        }
    }
}
