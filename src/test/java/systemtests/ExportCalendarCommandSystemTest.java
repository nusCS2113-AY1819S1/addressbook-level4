package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADMIN_PASSWORD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ADMIN_USERNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADMIN_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADMIN_USERNAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.testutil.TypicalEvents.ALICE;
import static seedu.address.testutil.TypicalEvents.CARL;
import static seedu.address.testutil.TypicalEvents.DANIEL;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExportCalendarCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.RegisterCommand;
import seedu.address.logic.commands.UnregisterCommand;
import seedu.address.model.Model;
import seedu.address.model.event.AttendanceContainsUserPredicate;
import seedu.address.model.event.Event;
import seedu.address.model.user.User;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.UserBuilder;

public class ExportCalendarCommandSystemTest extends EventManagerSystemTest {
    @Test
    public void export() {
        User toLogin = new UserBuilder().withUsername(VALID_ADMIN_USERNAME).withPassword(VALID_ADMIN_PASSWORD).build();
        String command = "   " + LoginCommand.COMMAND_WORD + "  "
                + ADMIN_USERNAME_DESC + "  " + ADMIN_PASSWORD_DESC + "  ";
        assertCommandSuccess(command, toLogin);

        //valid input test -> accepted
        String filename = "mycal";
        command = "   " + ExportCalendarCommand.COMMAND_WORD + " " + filename;
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ALICE); // event names of Benson and Daniel include "Tryouts"
        assertCommandSuccess(command, expectedModel, filename);
        assertSelectedCardUnchanged();

        //register for new event, then export -> accepted
        Index index = INDEX_FIRST_EVENT;
        showAllEvents();
        command = RegisterCommand.COMMAND_WORD + " " + index.getOneBased();
        Event registeredEvent = new EventBuilder(DANIEL).withAttendees("admin", "Scarlet Witch").build();
        assertCommandSuccess(command, expectedModel, registeredEvent, index,
                String.format(RegisterCommand.MESSAGE_REGISTER_EVENT_SUCCESS, index.getOneBased()));


        command = "   " + ExportCalendarCommand.COMMAND_WORD + " " + filename;
        ModelHelper.setFilteredList(expectedModel, ALICE, CARL); // event names of Benson and Daniel include "Tryouts"
        assertCommandSuccess(command, expectedModel, filename);


        //unregister for an event then export -> accepted
        index = INDEX_FIRST_EVENT;
        showAllEvents();
        command = UnregisterCommand.COMMAND_WORD + " " + index.getOneBased();
        Event unregisteredEvent = new EventBuilder(DANIEL).withAttendees("Scarlet Witch").build();
        assertCommandSuccess(command, expectedModel, unregisteredEvent, index,
                String.format(UnregisterCommand.MESSAGE_UNREGISTER_EVENT_SUCCESS, index.getOneBased()));

        command = "   " + ExportCalendarCommand.COMMAND_WORD + " " + filename;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ALICE); // event names of Benson and Daniel include "Tryouts"
        assertCommandSuccess(command, expectedModel, filename);

        //*********************************************Fail test case***************************************************
        //invalid system filename input
        filename = "C:/mycal";
        command = "   " + ExportCalendarCommand.COMMAND_WORD + " " + filename;
        assertCommandFailure(command, String.format(ExportCalendarCommand.MESSAGE_FILE_ERROR, filename));
        assertSelectedCardUnchanged();

        //filename is too long -> rejected
        filename = "myCalendarFileNameIsTooLong ggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg" +
                "gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg" +
                "gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg";
        command = "   " + ExportCalendarCommand.COMMAND_WORD + " " + filename;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCalendarCommand.MESSAGE_USAGE));
        assertSelectedCardUnchanged();

        //empty filename -> rejected
        filename = "";
        command = ExportCalendarCommand.COMMAND_WORD + " " + filename;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCalendarCommand.MESSAGE_USAGE));
        assertSelectedCardUnchanged();

        //not login -> rejected
        command = "   " + LogoutCommand.COMMAND_WORD;
        executeCommand(command);
        assertEquals(LogoutCommand.MESSAGE_SUCCESS, getResultDisplay().getText());

        filename = "mycal";
        command = "   " + ExportCalendarCommand.COMMAND_WORD + " " + filename;
        assertCommandFailure(command, Command.MESSAGE_LOGIN);
        assertSelectedCardUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_EVENTS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code EventManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see EventManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String filename) {
        String expectedResultMessage = String.format(
                ExportCalendarCommand.MESSAGE_EXPORT_SUCCESS, expectedModel.getFilteredEventList().size(), filename);

        executeCommand(command);
        expectedModel.updateFilteredEventList(new AttendanceContainsUserPredicate(expectedModel.getUsername()));
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    private void assertCommandSuccess(String command, Model expectedModel, Event registeredEvent,
                                      Index index, String expectedMessage) {
        executeCommand(command);
        expectedModel.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        expectedModel.updateEvent(expectedModel.getFilteredEventList().get(index.getZeroBased()), registeredEvent);
        assertApplicationDisplaysExpected("", expectedMessage, expectedModel);
    }


    /**
     * Performs a verification as {@code assertCommandSuccess(User)}. Executes {@code command}.
     */
    private void assertCommandSuccess(String command, User toLogin) {
        Model expectedModel = getModel();
        expectedModel.logUser(toLogin);
        String expectedResultMessage = String.format(LoginCommand.MESSAGE_SUCCESS, toLogin.getUsername().toString());

        assertCommandSuccessLogin(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Event)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code EventListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     */
    private void assertCommandSuccessLogin(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code EventManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see EventManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
