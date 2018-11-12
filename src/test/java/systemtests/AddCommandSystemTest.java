package systemtests;

import org.junit.Test;

import seedu.divelog.commons.core.Messages;
import seedu.divelog.logic.commands.AddCommand;
import seedu.divelog.logic.pressuregroup.exceptions.LimitExceededException;
import seedu.divelog.model.Model;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.exceptions.DiveOverlapsException;
import seedu.divelog.model.dive.exceptions.InvalidTimeException;
import seedu.divelog.testutil.DiveSessionBuilder;
import seedu.divelog.testutil.DiveUtil;

public class AddCommandSystemTest extends DiveLogSystemTest {
    public static final String HARDCODE_MAX_TIME = "Max time you can spend at 40.0m is 9.0 minutes";

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a simple dive to the logbook
         * -> added
         */
        DiveSession diveSession = new DiveSessionBuilder()
                .withStart("1000")
                .withStartDate("01012018")
                .withSafetyStop("1025")
                .withEnd("1030")
                .withEndDate("01012018")
                .withTimeZone("+8")
                .withDepth(5)
                .build();
        assertCommandSuccess(diveSession);

        /*
         * Case: Safety stop is outside time interval
         */
        diveSession = new DiveSessionBuilder()
                .withStart("1000")
                .withStartDate("01012018")
                .withSafetyStop("1100")
                .withEnd("1030")
                .withEndDate("01012018")
                .withTimeZone("+8")
                .withDepth(5)
                .build();
        assertCommandFailure(DiveUtil.getAddCommand(diveSession), Messages.MESSAGE_INVALID_DATE_LIMITS);

        /*
         * Case: Overlapping dives
         */
        diveSession = new DiveSessionBuilder()
                .withStart("1015")
                .withStartDate("01012018")
                .withSafetyStop("1020")
                .withEnd("1030")
                .withEndDate("01012018")
                .withTimeZone("+8")
                .withDepth(5)
                .build();
        assertCommandFailure(DiveUtil.getAddCommand(diveSession), Messages.MESSAGE_ERROR_DIVES_OVERLAP);

        /*
         * Case: Overlapping dives due to different timezones
         */
        diveSession = new DiveSessionBuilder()
                .withStart("1115")
                .withStartDate("01012018")
                .withSafetyStop("1125")
                .withEnd("1130")
                .withEndDate("01012018")
                .withTimeZone("+9")
                .withDepth(5)
                .build();
        assertCommandFailure(DiveUtil.getAddCommand(diveSession), Messages.MESSAGE_ERROR_DIVES_OVERLAP);

        /*
         * Case: Dive is too deep
         */
        diveSession = new DiveSessionBuilder()
                .withStart("1500")
                .withStartDate("11012018")
                .withSafetyStop("1545")
                .withEnd("1600")
                .withEndDate("11012018")
                .withTimeZone("+8")
                .withDepth(80)
                .build();
        assertCommandFailure(DiveUtil.getAddCommand(diveSession), (
                Messages.MESSAGE_ERROR_LIMIT_EXCEED + "\n" + Messages.MESSAGE_MAX_DEPTH_EXCEEDED));

        /*
         * Case: Dive is too long
         */
        diveSession = new DiveSessionBuilder()
                .withStart("1500")
                .withStartDate("11012018")
                .withSafetyStop("1545")
                .withEnd("1600")
                .withEndDate("11012018")
                .withTimeZone("+8")
                .withDepth(40)
                .build();
        assertCommandFailure(DiveUtil.getAddCommand(diveSession), (
                Messages.MESSAGE_ERROR_LIMIT_EXCEED + " " + HARDCODE_MAX_TIME));


        /*
         * Case: Time Zone invalid as it is too big
         */
        diveSession = new DiveSessionBuilder()
                .withStart("1500")
                .withStartDate("11012018")
                .withSafetyStop("1545")
                .withEnd("1600")
                .withEndDate("11012018")
                .withTimeZone("+13")
                .withDepth(10)
                .build();
        assertCommandFailure(DiveUtil.getAddCommand(diveSession), Messages.MESSAGE_INVALID_TIMEZONE_FORMAT);

        /*
         * Case: Time Zone invalid as it is too small
         */
        diveSession = new DiveSessionBuilder()
                .withStart("1500")
                .withStartDate("11012018")
                .withSafetyStop("1545")
                .withEnd("1600")
                .withEndDate("11012018")
                .withTimeZone("-13")
                .withDepth(10)
                .build();
        assertCommandFailure(DiveUtil.getAddCommand(diveSession), Messages.MESSAGE_INVALID_TIMEZONE_FORMAT);

    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code DiveListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code DiveLogSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see DiveLogSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(DiveSession toAdd) {
        assertCommandSuccess(DiveUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(DiveSession)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(DiveSession)
     */
    private void assertCommandSuccess(String command, DiveSession toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addDiveSession(toAdd);
        } catch (DiveOverlapsException | LimitExceededException | InvalidTimeException e) {
            throw new AssertionError("Expected model is incorrectly constructed");
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, DiveSession)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code DiveListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, DiveSession)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code DiveListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
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
