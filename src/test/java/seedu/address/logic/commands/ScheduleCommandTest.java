package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

import seedu.address.model.prioritylevel.PriorityLevel;
import seedu.address.model.prioritylevel.PriorityLevelEnum;
import seedu.address.model.schedule.Schedule;
import seedu.address.session.SessionManager;
import systemtests.SessionHelper;

public class ScheduleCommandTest {
    private static final String DEFAULT_NRIC = "S1230000E";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        SessionHelper.forceLoginWithPriorityLevelOf(DEFAULT_NRIC,
                PriorityLevelEnum.ADMINISTRATOR.getPriorityLevelCode());
        expectedModel = model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_notLoggedIn_throwsCommandException() {
        SessionHelper.logoutOfSession();
        assertCommandFailure(new ScheduleCommand(INDEX_FIRST_PERSON), model,
                commandHistory, SessionManager.NOT_LOGGED_IN);
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ScheduleCommand(null);
    }

    @Test
    public void execute_emptyModel_failure() {
        // Empty Address Book
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        assertCommandFailure(new ScheduleCommand(INDEX_FIRST_PERSON), emptyModel,
                commandHistory, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndex_failure() {
        Index index = Index.fromOneBased(8);
        // Index 8 (Person does not exist) in 7 people address book -> failure
        assertCommandFailure(new ScheduleCommand(index), model,
                commandHistory, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_otherUserAsBasic_failure() {
        SessionHelper.forceLoginWithPriorityLevelOf(DEFAULT_NRIC,
                PriorityLevelEnum.BASIC.getPriorityLevelCode());
        assertCommandFailure(new ScheduleCommand(INDEX_FIRST_PERSON), model,
                commandHistory, String.format(PriorityLevel.INSUFFICIENT_PRIORITY_LEVEL,
                        PriorityLevelEnum.ADMINISTRATOR));
    }

    @Test
    public void execute_otherUserAsAdmin_success() {
        List<Person> personList = model.getFilteredPersonList();
        Person expectedPerson = personList.get(INDEX_FIRST_PERSON.getZeroBased());
        Schedule expectedSchedule = expectedPerson.getSchedule();
        String expectedResponse = expectedPerson.getName().toString() + "'s schedule:\n" + expectedSchedule.toString();

        assertCommandSuccess(new ScheduleCommand(INDEX_FIRST_PERSON), model,
                commandHistory, expectedResponse, expectedModel);
    }

    @Test
    public void execute_otherUserWithNoSchedule_success() {
        Index index = Index.fromOneBased(6);
        String expectedResponse = "Fiona Kunz's schedule:\nNo schedule allocated.";

        assertCommandSuccess(new ScheduleCommand(index), model,
                commandHistory, expectedResponse, expectedModel);
    }

    @Test
    public void equals() {
        ScheduleCommand cleanerScheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON);
        ScheduleCommand clerkScheduleCommand = new ScheduleCommand(INDEX_SECOND_PERSON);

        // same object
        assertEquals(cleanerScheduleCommand, cleanerScheduleCommand);

        // same values
        ScheduleCommand cleanerScheduleCommandCopy = new ScheduleCommand(INDEX_FIRST_PERSON);
        assertNotEquals(cleanerScheduleCommand, cleanerScheduleCommandCopy);

        // different types
        assertNotEquals(cleanerScheduleCommand, 1);

        // null
        assertNotEquals(cleanerScheduleCommand, null);

        // different index
        assertNotEquals(cleanerScheduleCommand, clerkScheduleCommand);
    }

    @After
    public void tearDown() {
        SessionHelper.logoutOfSession();
    }
}
