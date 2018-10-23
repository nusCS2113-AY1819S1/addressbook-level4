package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.Model;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DirectoryPath;
import seedu.planner.model.record.Limit;
import seedu.planner.model.record.Record;
import seedu.planner.model.summary.Summary;
import seedu.planner.testutil.RecordBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_recordAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRecordAdded modelStub = new ModelStubAcceptingRecordAdded();
        Record validRecord = new RecordBuilder().build();

        CommandResult commandResult = new AddCommand(validRecord).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validRecord), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validRecord), modelStub.recordsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateRecord_throwsCommandException() throws Exception {
        Record validRecord = new RecordBuilder().build();
        AddCommand addCommand = new AddCommand(validRecord);
        ModelStub modelStub = new ModelStubWithRecord(validRecord);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_RECORD);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Record alice = new RecordBuilder().withName("Alice").build();
        Record bob = new RecordBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different record -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addRecord(Record record) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRecord(Record target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateRecord(Record target, Record editedRecord) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyFinancialPlanner newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Summary> getSummaryList(Date date1, Date date2) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Limit> getLimitList() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ReadOnlyFinancialPlanner getFinancialPlanner() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addLimit(Limit limit) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteLimit(Limit limit) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public boolean hasRecord(Record record) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSameDateLimit(Limit limit) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public boolean isExceededLimit (Limit limit) {
            throw new AssertionError("This method should not be called.");
        }

        public int deleteListRecordSameDate(List<Record> targetList, Date targetDate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Record> getFilteredRecordList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRecordList(Predicate<Record> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredRecordList(String category, Boolean ascending) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoFinancialPlanner() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoFinancialPlanner() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoFinancialPlanner() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoFinancialPlanner() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitFinancialPlanner() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDirectoryPath(String path) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeDirectoryPath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public DirectoryPath getDirectoryPath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isDirectorySet(DirectoryPath path) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single record.
     */
    private class ModelStubWithRecord extends ModelStub {
        private final Record record;

        ModelStubWithRecord(Record record) {
            requireNonNull(record);
            this.record = record;
        }

        @Override
        public boolean hasRecord(Record record) {
            requireNonNull(record);
            return this.record.isSameRecord(record);
        }
    }

    /**
     * A Model stub that always accept the record being added.
     */
    private class ModelStubAcceptingRecordAdded extends ModelStub {
        final ArrayList<Record> recordsAdded = new ArrayList<>();

        @Override
        public boolean hasRecord(Record record) {
            requireNonNull(record);
            return recordsAdded.stream().anyMatch(record::isSameRecord);
        }

        @Override
        public void addRecord(Record record) {
            requireNonNull(record);
            recordsAdded.add(record);
        }

        @Override
        public void commitFinancialPlanner() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyFinancialPlanner getFinancialPlanner() {
            return new FinancialPlanner();
        }
    }

}
