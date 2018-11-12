package seedu.divelog.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.divelog.commons.core.index.Index;
import seedu.divelog.logic.CommandHistory;
import seedu.divelog.logic.commands.exceptions.CommandException;
import seedu.divelog.model.DiveLog;
import seedu.divelog.model.Model;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.LocationContainsKeywordPredicate;
import seedu.divelog.testutil.EditDiveDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_LOCATION_TIOMAN = "Tioman";
    public static final String VALID_LOCATION_BALI = "Bali";
    public static final String VALID_START = "0700";
    public static final String VALID_SAFETY_STOP = "0745";
    public static final String VALID_END = "0800";
    public static final String VALID_PG_START = "A";
    public static final String VALID_PG_END = "F";

    public static final float VALID_DEPTH = 5;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditDiveDescriptor DESC_DAY_BALI;
    public static final EditCommand.EditDiveDescriptor DESC_DAY_TIOMAN;

    static {
        DESC_DAY_BALI = new EditDiveDescriptorBuilder()
                .withLocation(VALID_LOCATION_BALI)
                .withStart(VALID_START)
                .withSafetyStop(VALID_SAFETY_STOP)
                .withEnd(VALID_END)
                .withDepth(VALID_DEPTH)
                .withStartingPressureGroup(VALID_PG_START)
                .withEndingPressureGroup(VALID_PG_END)
                .build();
        DESC_DAY_TIOMAN = new EditDiveDescriptorBuilder()
                .withLocation(VALID_LOCATION_TIOMAN)
                .withStart(VALID_START)
                .withSafetyStop(VALID_SAFETY_STOP)
                .withEnd(VALID_END)
                .withDepth(VALID_DEPTH)
                .withStartingPressureGroup(VALID_PG_START)
                .withEndingPressureGroup(VALID_PG_END)
                .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);

        } catch (CommandException ce) {

            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the divelog book and the filtered dive session list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        DiveLog expectedDiveLog = new DiveLog(actualModel.getDiveLog());
        List<DiveSession> expectedFilteredList = new ArrayList<>(actualModel.getFilteredDiveList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());

            assertEquals(expectedDiveLog, actualModel.getDiveLog());
            assertEquals(expectedFilteredList, actualModel.getFilteredDiveList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the dive at the given {@code targetIndex} in the
     * {@code model}'s divelog book.
     */
    public static void showDiveAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredDiveList().size());

        DiveSession diveSession = model.getFilteredDiveList().get(targetIndex.getZeroBased());
        final String[] splitName = diveSession.getLocation().getLocationName().split("\\s+");
        model.updateFilteredDiveList(new LocationContainsKeywordPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredDiveList().size());
    }

    /**
     * Deletes the first dive in {@code model}'s filtered list from {@code model}'s divelog book.
     */
    public static void deleteFirstDive(Model model) {
        DiveSession firstDive = model.getFilteredDiveList().get(0);
        try {
            model.deleteDiveSession(firstDive);
        } catch (seedu.divelog.model.dive.exceptions.DiveNotFoundException e) {
            e.printStackTrace();
        }
        model.commitDiveLog();
    }

}
