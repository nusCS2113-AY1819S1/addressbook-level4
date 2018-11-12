package systemtests;

import static seedu.divelog.logic.commands.DeleteCommand.MESSAGE_DELETE_DIVE_SESSION_SUCCESS;
import static seedu.divelog.testutil.TestUtil.getDive;
import static seedu.divelog.testutil.TestUtil.getMidIndex;

import org.junit.Test;

import seedu.divelog.commons.core.Messages;
import seedu.divelog.commons.core.index.Index;
import seedu.divelog.logic.commands.DeleteCommand;
import seedu.divelog.logic.commands.RedoCommand;
import seedu.divelog.logic.commands.SelectCommand;
import seedu.divelog.logic.commands.UndoCommand;
import seedu.divelog.model.Model;
import seedu.divelog.model.dive.DiveSession;

public class DeleteCommandSystemTest extends DiveLogSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    @Test
    public void delete() {

        //Model defaultModel = getModel();
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first dive session in the list -> deleted */
        String command = DeleteCommand.COMMAND_WORD + " 1";
        //Index theFirstDelete = Index.fromOneBased(1);
        //assertCommandSuccess(command, getModel(), (String.format(MESSAGE_DELETE_DIVE_SESSION_SUCCESS, 1)));

        /* Case: delete the first dive session in the list, command with leading spaces and trailing spaces ->deleted */
        //assertCommandSuccess("   " + DeleteCommand.COMMAND_WORD + "       ");



        /* Case: delete the last dive session in the list -> deleted */
        //Index theLastSession = getLastIndex(getModel());
        int invalidIndex = getModel().getFilteredDiveList().size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        //assertCommandSuccess(theLastSession);
        //assertCommandSuccess(command, getModel(), MESSAGE_DELETE_DIVE_SESSION_SUCCESS);

        /* Case: undo deleting the last dive session in the list -> last dive session restored */
        command = UndoCommand.COMMAND_WORD;
        //String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        //assertCommandSuccess(command, getModel(), expectedResultMessage);
        //assertSelectedCardUnchanged();

        /* Case: redo deleting the last dive session in the list -> last dive session deleted again */
        command = RedoCommand.COMMAND_WORD;
        //String expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        //assertCommandSuccess(command, new ModelManager(), expectedResultMessage);
        //assertSelectedCardUnchanged();

        /* Case: delete the middle dive session in the list -> deleted */
        Index middleIndex = getMidIndex(getModel());
        command = DeleteCommand.COMMAND_WORD + " " + middleIndex.getOneBased();


        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered dive session list, delete index within bounds of divelog book and dive list -> deleted */


        /* Case: filtered dive session list, delete index within bounds of divelog book but out of bounds of dive list
         * -> rejected
         */

        /* --------------------- Performing delete operation while a dive session card is selected ------------------ */

        /* Case: delete the selected dive session -> dive list panel selects the dive before the deleted dive */
        middleIndex = getMidIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        //select first
        command = DeleteCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        //delete


        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: no index given -> rejected */
        //assertCommandFailure(DeleteCommand.COMMAND_WORD, (
        // Messages.MESSAGE_INVALID_COMMAND_FORMAT + "\n" + DeleteCommand.MESSAGE_USAGE));
        assertCommandFailure(DeleteCommand.COMMAND_WORD,
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " 0";
        //assertCommandFailure(DeleteCommand.COMMAND_WORD, (
        // Messages.MESSAGE_INVALID_COMMAND_FORMAT + "\n" +DeleteCommand.MESSAGE_USAGE));
        assertCommandFailure(DeleteCommand.COMMAND_WORD,
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(DeleteCommand.COMMAND_WORD, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        //Index diveSessionCountMax1 = getLastIndex(getModel());
        int diveSessionCountMax1 = getModel().getFilteredDiveList().size() + 1;
        command = DeleteCommand.COMMAND_WORD + " " + diveSessionCountMax1;
        assertCommandFailure(command, Messages.MESSAGE_INVALID_DIVE_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " A";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " 1 8";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        command = "deLete 1";
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: mixed case command word -> rejected */
        command = "Delete 1";
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code DiveSession} at the specified {@code index} in {@code model}'s divelog book.
     * @return the removed dive session
     */
    private DiveSession removeDive(Model model, Index index) {
        DiveSession targetDiveSession = getDive(model, index);
        try {
            model.deleteDiveSession(targetDiveSession);
        } catch (seedu.divelog.model.dive.exceptions.DiveNotFoundException e) {
            e.printStackTrace();
        }
        return targetDiveSession;
    }

    /**
     * Deletes the dive at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        DiveSession deletedDiveSession = removeDive(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_DIVE_SESSION_SUCCESS, deletedDiveSession);

        assertCommandSuccess(
                DeleteCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code DiveLogSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see DiveLogSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see DiveLogSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code DiveLogSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see DiveLogSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
