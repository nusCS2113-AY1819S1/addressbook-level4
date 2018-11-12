package seedu.divelog.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.divelog.commons.enums.SortingMethod;
import seedu.divelog.logic.CommandHistory;
import seedu.divelog.model.DiveLog;
import seedu.divelog.model.Model;
import seedu.divelog.model.ReadOnlyDiveLog;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.exceptions.DiveNotFoundException;
import seedu.divelog.testutil.DiveSessionBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullDiveSession_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_diveSessionAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingDiveSessionAdded modelStub = new ModelStubAcceptingDiveSessionAdded();
        DiveSession validDive = new DiveSessionBuilder().build();

        CommandResult commandResult = new AddCommand(validDive).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validDive), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validDive), modelStub.diveAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        DiveSession nightDive = new DiveSessionBuilder().withStart("2100").withEnd("2200").withSafetyStop("2145")
                .build();
        DiveSession morningDive = new DiveSessionBuilder().withStart("0800").withEnd("0900").withSafetyStop("0845")
                .build();
        AddCommand addNightDiveCommand = new AddCommand(nightDive);
        AddCommand addMorningDiveCommand = new AddCommand(morningDive);

        // same object -> returns true
        assertTrue(addNightDiveCommand.equals(addNightDiveCommand));

        // same values -> returns true
        AddCommand addNightDiveCommandCopy = new AddCommand(nightDive);
        assertTrue(addNightDiveCommand.equals(addNightDiveCommandCopy));

        // different types -> returns false
        assertFalse(addNightDiveCommand.equals(1));

        // null -> returns false
        assertFalse(addNightDiveCommand.equals(null));

        // different diveSession -> returns false
        assertFalse(addNightDiveCommand.equals(addMorningDiveCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public DiveSession getMostRecent() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void recalculatePressureGroups() {
            //Do nothing
        }

        @Override
        public void addDiveSession(DiveSession diveSession) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyDiveLog newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyDiveLog getDiveLog() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDiveSession(DiveSession target) throws DiveNotFoundException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateDiveSession(DiveSession target, DiveSession editedDiveSession) throws DiveNotFoundException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<DiveSession> getFilteredDiveList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDiveList(Predicate<DiveSession> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortDiveSession(SortingMethod sortByCategory) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoDiveLog() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoDiveLog() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoDiveLog() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoDiveLog() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void zeroPlannerCount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitDiveLog() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean getPlanningMode() {
            return false;
        }

        @Override
        public void setPlanningMode() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void plannerCountPlus() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void plannerCountMinus() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getPlannerCount() {
            return 0;
        }


    }

    /**
     * A Model stub that contains a single diveSession.
     */
    private class ModelStubWithDive extends ModelStub {
        private final DiveSession diveSession;

        ModelStubWithDive(DiveSession diveSession) {
            requireNonNull(diveSession);
            this.diveSession = diveSession;
        }
    }

    /**
     * A Model stub that always accept the diveSession being added.
     */
    private class ModelStubAcceptingDiveSessionAdded extends ModelStub {
        final ArrayList<DiveSession> diveAdded = new ArrayList<>();

        @Override
        public void addDiveSession(DiveSession diveSession) {
            requireNonNull(diveSession);
            diveAdded.add(diveSession);
        }

        @Override
        public void commitDiveLog() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyDiveLog getDiveLog() {
            return new DiveLog();
        }
        @Override
        public ObservableList<DiveSession> getFilteredDiveList() {
            return FXCollections.observableArrayList(diveAdded);
        }
    }

}
