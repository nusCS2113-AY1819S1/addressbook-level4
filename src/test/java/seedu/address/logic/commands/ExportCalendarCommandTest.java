package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.ALICE;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.AttendanceContainsUserPredicate;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;
import seedu.address.testutil.UserBuilder;

public class ExportCalendarCommandTest {
    private Model model;
    private Model expectedModel = new ModelManager(getTypicalEventManager(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private User user = new UserBuilder().build();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalEventManager(), new UserPrefs());
        model.logUser(user);
        expectedModel.logUser(new UserBuilder().build());
    }

    @Test
    public void equals() {
        ExportCalendarCommand exportCalendarCommand = new ExportCalendarCommand("mycal");
        ExportCalendarCommand exportCalendarCommandEqual = new ExportCalendarCommand("mycal");
        ExportCalendarCommand exportCalendarCommandNotEqual = new ExportCalendarCommand("mycal2");

        assertTrue(exportCalendarCommand.equals(exportCalendarCommand));

        assertTrue(exportCalendarCommand.equals(exportCalendarCommandEqual));

        assertFalse(exportCalendarCommand.equals(exportCalendarCommandNotEqual));
    }

    //********************************************Success test case**************************************************
    //Command success when filtered list match expected list
    @Test
    public void execute_exportCommand_success() {
        String filename = "mycal";
        String expectedMessage = String.format(ExportCalendarCommand.MESSAGE_EXPORT_SUCCESS, 1, filename);
        AttendanceContainsUserPredicate predicate = new AttendanceContainsUserPredicate(user.getUsername());
        ExportCalendarCommand command = new ExportCalendarCommand(filename);

        expectedModel.updateFilteredEventList(predicate);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredEventList());
    }


    @Test
    public void execute_exportCommand_with_filteredList_success() {
        String filename = "myCal";
        String expectedMessage = String.format(ExportCalendarCommand.MESSAGE_EXPORT_SUCCESS, 1, filename);
        AttendanceContainsUserPredicate predicate = new AttendanceContainsUserPredicate(user.getUsername());
        ExportCalendarCommand command = new ExportCalendarCommand(filename);

        model.updateFilteredEventList(new AttendanceContainsUserPredicate(new Username("Alice")));
        expectedModel.updateFilteredEventList(predicate);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredEventList());
    }

    //**********************************************Fail test case***************************************************
    @Test
    public void execute_exportCommand_IOinput_failure() {
        String filename = "C:/myCal";
        String expectedMessage = String.format(ExportCalendarCommand.MESSAGE_FILE_ERROR, filename);
        AttendanceContainsUserPredicate predicate = new AttendanceContainsUserPredicate(user.getUsername());
        ExportCalendarCommand command = new ExportCalendarCommand(filename);

        model.updateFilteredEventList(predicate);

        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }
}

