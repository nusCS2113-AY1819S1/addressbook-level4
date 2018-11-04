package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.ADMIN_PASSWORD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ADMIN_USERNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ATTENDEE_DESC_HAN;
import static seedu.address.logic.commands.CommandTestUtil.ATTENDEE_DESC_TED;
import static seedu.address.logic.commands.CommandTestUtil.COMMENT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.CONTACT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.CONTACT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ATTENDEE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTACT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADMIN_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADMIN_USERNAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDEE_TED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.testutil.TypicalEvents.*;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EVENT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.*;
import seedu.address.model.Model;
import seedu.address.model.attendee.Attendee;
import seedu.address.model.event.Contact;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Email;
import seedu.address.model.event.Event;
import seedu.address.model.event.Name;
import seedu.address.model.event.Phone;
import seedu.address.model.event.Venue;
import seedu.address.model.tag.Tag;
import seedu.address.model.user.User;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.EventUtil;
import seedu.address.testutil.UserBuilder;

public class CommentCommandSystemTest extends EventManagerSystemTest {

    @Test
    public void comment() {
        Model model = getModel();

        //Login
        User toLogin = new UserBuilder().withUsername(VALID_ADMIN_USERNAME).withPassword(VALID_ADMIN_PASSWORD).build();
        String command = "   " + LoginCommand.COMMAND_WORD + "  "
                + ADMIN_USERNAME_DESC + "  " + ADMIN_PASSWORD_DESC + "  ";
        assertCommandSuccess(command, toLogin);

        Index index = INDEX_FIRST_EVENT;
        command = " " + AddCommentCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_COMMENT + "Hi";
        Event editedEvent = new EventBuilder(DANIEL).withComment("{span}Comment Section{/span}{ol}{li}admin : Hi{li}{/ol}").build();
        assertCommandSuccess(command, index, editedEvent);

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Event, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Event, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Event editedEvent) {
        assertCommandSuccess(command, toEdit, editedEvent, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the event at index {@code toEdit} being
     * updated to values specified {@code editedEvent}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Event editedEvent,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.updateEvent(expectedModel.getFilteredEventList().get(toEdit.getZeroBased()), editedEvent);
        expectedModel.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent), expectedSelectedCardIndex);
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
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code EventManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see EventManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see EventManagerSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
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
