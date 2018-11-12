package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.prioritylevel.PriorityLevel;
import seedu.address.model.prioritylevel.PriorityLevelEnum;
import seedu.address.session.SessionManager;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

//@@author jylee-git
/**
 * Contains integration tests and unit tests for SetPriorityLevelCommand.
 */
public class SetPriorityLevelCommandTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    private ModelManager expectedModel;

    @BeforeEach
    void setUp() {
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            new SetPriorityLevelCommand(null, null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new SetPriorityLevelCommand(INDEX_FIRST_PERSON, null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new SetPriorityLevelCommand(null, new PriorityLevel(PriorityLevelEnum.BASIC.getPriorityLevelCode()));
        });
    }

    @Test
    public void execute_notLoggedIn_throwsCommandException() {
        Throwable msg = Assertions.assertThrows(CommandException.class, () -> {
            SetPriorityLevelCommand spl = new SetPriorityLevelCommand(INDEX_FIRST_PERSON,
                    new PriorityLevel(PriorityLevelEnum.BASIC.getPriorityLevelCode()));
            spl.execute(model, commandHistory);
        });
        Assertions.assertEquals(SessionManager.NOT_LOGGED_IN, msg.getMessage());
    }

    /**
     * After this test method, Alice's PriorityLevel remains, whereas Benson's PriorityLevel will become BASIC (lowest).
     */
    @Test
    public void execute_setPriorityLevelOfSecondPerson_success() throws CommandException {
        SessionManager.getInstance(model).destroy();
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

        LoginCommand loginCommand = new LoginCommand(TypicalPersons.ALICE.getNric(),
                TypicalPersons.ALICE.getPassword());
        loginCommand.execute(model, commandHistory);

        Person editedBenson = new PersonBuilder(TypicalPersons.BENSON)
                .withPriorityLevel(PriorityLevelEnum.BASIC.getPriorityLevelCode()).build();

        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(TypicalPersons.BENSON, editedBenson);
        expectedModel.commitAddressBook();

        SetPriorityLevelCommand spl = new SetPriorityLevelCommand(INDEX_SECOND_PERSON,
                new PriorityLevel(PriorityLevelEnum.BASIC.getPriorityLevelCode()));
        String expectedMessage = String.format(SetPriorityLevelCommand.MESSAGE_CHANGE_PLVL_SUCCESS,
                TypicalPersons.BENSON.getName(), PriorityLevelEnum.BASIC);
        assertCommandSuccess(spl, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_insufficientPriorityLevel_throwsCommandException() throws CommandException {
        Person editedBenson = new PersonBuilder(TypicalPersons.BENSON)
                .withPriorityLevel(PriorityLevelEnum.BASIC.getPriorityLevelCode()).build();
        model.updatePerson(TypicalPersons.BENSON, editedBenson);
        LoginCommand loginCommand = new LoginCommand(TypicalPersons.BENSON.getNric(),
                TypicalPersons.BENSON.getPassword());
        loginCommand.execute(model, commandHistory);

        SetPriorityLevelCommand spl = new SetPriorityLevelCommand(INDEX_FIRST_PERSON,
                new PriorityLevel(PriorityLevelEnum.BASIC.getPriorityLevelCode()));

        Throwable msg = Assertions.assertThrows(CommandException.class, () -> spl.execute(model, commandHistory));
        Assertions.assertEquals(String.format(PriorityLevel.INSUFFICIENT_PRIORITY_LEVEL,
                PriorityLevelEnum.ADMINISTRATOR), msg.getMessage());
    }

    @Test
    public void execute_editOwnPriorityLevel_throwsCommandException() throws CommandException {
        LoginCommand loginCommand = new LoginCommand(TypicalPersons.ALICE.getNric(),
                TypicalPersons.ALICE.getPassword());
        loginCommand.execute(model, commandHistory);

        SetPriorityLevelCommand spl = new SetPriorityLevelCommand(INDEX_FIRST_PERSON,
                new PriorityLevel(PriorityLevelEnum.BASIC.getPriorityLevelCode()));

        Throwable msg = Assertions.assertThrows(CommandException.class, () -> spl.execute(model, commandHistory));
        Assertions.assertEquals(SetPriorityLevelCommand.MESSAGE_CANNOT_EDIT_OWN_PLVL, msg.getMessage());
    }

    /**
     * Logs out of the application after each test
     */
    @AfterEach
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
