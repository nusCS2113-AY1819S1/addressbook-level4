package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Mode;
import seedu.address.model.person.Person;
import seedu.address.session.SessionManager;
import seedu.address.testutil.PersonBuilder;
import systemtests.SessionHelper;

/**
 * Contains integration tests (interaction with the Model) and unit tests for CheckCommand.
 */
public class CheckCommandTest {
    private static final Person GEORGEFOREDIT = new PersonBuilder().withName("George Best").withPhone("9482442")
        .withNric("T2457775E").withPassword("ASd654").withPriorityLevel(3)
        .withEmail("anna@example.com").withDepartment("Junior Management")
        .withAddress("4th street").withMode("in")
        .withWorkingRate("7.5").withCheckedInTime("9:00:00").build();
    private static final Person FIONAFOREDIT = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
        .withNric("T2457005E").withPassword("ASd654").withPriorityLevel(0)
        .withEmail("lydia@example.com").withDepartment("Junior Management")
        .withAddress("little tokyo").withMode("out")
        .withWorkingRate("7.5").withCheckedInTime("18:00:00").build();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private SessionManager sessionManager;

    @Before
    public void setUp() {
        sessionManager = SessionManager.getInstance(model);
    }

    @Test
    public void execute_checkIn_success() throws CommandException {
        //check in success
        final Mode inMode = new Mode("in");
        Person editedPerson = GEORGEFOREDIT;
        Person personToEdit = GEORGE;

        CheckCommand checkCommand = new CheckCommand(inMode);
        String expectedMessage = String.format(CheckCommand.MESSAGE_CHECKED_IN, CheckCommand.currentDate(),
            CheckCommand.currentTime());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(personToEdit, editedPerson);
        expectedModel.commitAddressBook();

        sessionManager.destroy();
        sessionManager = SessionManager.getInstance(model);
        sessionManager.loginToSession(GEORGE.getNric(), GEORGE.getPassword());

        assertCommandSuccess(checkCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_checkOut_success() throws CommandException {
        //check in success
        final Mode outMode = new Mode("out");
        Person editedPerson = FIONAFOREDIT;
        Person personToEdit = FIONA;
        String[] timeArray = CheckCommand.splitTime(FIONA.getCheckedInTime().toString());
        double checkedInHours = Double.parseDouble(timeArray[0]) + (Double.parseDouble(timeArray[1]) / 60)
            + (Double.parseDouble(timeArray[2]) / 3600);
        timeArray = CheckCommand.splitTime(CheckCommand.currentTime());
        double checkedOutHours = Double.parseDouble(timeArray[0]) + (Double.parseDouble(timeArray[1]) / 60)
            + (Double.parseDouble(timeArray[2]) / 3600);
        double hoursWorked = checkedOutHours - checkedInHours;
        double salaryPerDay = Double.parseDouble(FIONA.getWorkingRate().toString()) * hoursWorked;

        CheckCommand checkCommand = new CheckCommand(outMode);
        String expectedMessage = String.format(CheckCommand.MESSAGE_CHECKED_OUT, CheckCommand.currentDate(),
            CheckCommand.currentTime(), hoursWorked, salaryPerDay);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(personToEdit, editedPerson);
        expectedModel.commitAddressBook();

        sessionManager.destroy();
        sessionManager = SessionManager.getInstance(model);
        sessionManager.loginToSession(FIONA.getNric(), FIONA.getPassword());

        assertCommandSuccess(checkCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_failure() throws CommandException {
        final Mode outMode = new Mode("out");

        //user not login
        assertCommandFailure(new CheckCommand(outMode),
            model, new CommandHistory(),
            CheckCommand.MESSAGE_NOT_LOGIN);

        sessionManager.destroy();
        sessionManager = SessionManager.getInstance(model);
        sessionManager.loginToSession(GEORGE.getNric(), GEORGE.getPassword());

        //duplicate mode
        assertCommandFailure(new CheckCommand(outMode),
            model, new CommandHistory(),
            String.format(CheckCommand.MESSAGE_DUPLICATE, outMode));
    }

    @Test
    public void equals() {
        final CheckCommand standardCommand = new CheckCommand(new Mode(VALID_MODE_AMY));

        // same values -> returns true
        CheckCommand commandWithSameValues = new CheckCommand(new Mode(VALID_MODE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different mode -> returns false
        assertFalse(standardCommand.equals(new CheckCommand(new Mode(VALID_MODE_BOB))));
    }

    @After
    public void tearDown() {
        SessionHelper.logoutOfSession();
    }
}
