package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.password.Password;
import seedu.address.model.prioritylevel.PriorityLevelEnum;

import seedu.address.session.SessionManager;
import seedu.address.testutil.PersonBuilder;
import systemtests.SessionHelper;

public class CheckLoginStatusTest {

    private static final Nric CORRECT_NRIC = new Nric("S0000001A");
    private static final Password CORRECT_PASSWORD = new Password("NeUeR2018");

    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();
    private ModelManager expectedModel;
    private Person personA;
    private SessionManager sessionManager;

    /**
     * Sets up an empty addressBook, then add a user inside; for purpose of CheckLoginStatus Command testing.
     */
    @BeforeEach
    void setUp() throws CommandException {
        SessionHelper.forceLoginWithPriorityLevelOf(PriorityLevelEnum.ADMINISTRATOR.getPriorityLevelCode());
        personA = new PersonBuilder().withNric(CORRECT_NRIC.toString())
                .withPassword(CORRECT_PASSWORD.toString()).build();
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(personA);
        expectedModel.commitAddressBook();
        new AddCommand(personA).execute(model, commandHistory);
        SessionHelper.logoutOfSession();
    }

    @Test
    void execute_checkLoginStatusTestPackage_success() throws CommandException {
        // Phase 1: Not logged in initially, should show "You are not logged in".
        CheckLoginStatusCommand checkLoginStatusCommand = new CheckLoginStatusCommand();
        assertCommandSuccess(checkLoginStatusCommand, model, commandHistory,
                CheckLoginStatusCommand.STATUS_NOT_LOGGED_IN, expectedModel);

        // Phase 2: Logged in, should show that you are logged in with the logged in NRIC.
        new LoginCommand(CORRECT_NRIC, CORRECT_PASSWORD).execute(model, commandHistory);
        assertCommandSuccess(checkLoginStatusCommand, model, commandHistory,
                String.format(CheckLoginStatusCommand.STATUS_LOGGED_IN, CORRECT_NRIC), expectedModel);

        // Phase 3: Log out, then check again. Message should now show that you are not logged in.
        new LogoutCommand().execute(model, commandHistory);
        assertCommandSuccess(checkLoginStatusCommand, model, commandHistory,
                CheckLoginStatusCommand.STATUS_NOT_LOGGED_IN, expectedModel);
    }
}
