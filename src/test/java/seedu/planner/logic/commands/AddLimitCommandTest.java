package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.planner.logic.commands.AddLimitCommand.MESSAGE_SUCCESS;

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
import seedu.planner.model.Month;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.Limit;
import seedu.planner.model.record.Record;
import seedu.planner.testutil.LimitBuilder;
//@@Author OscarZeng

public class AddLimitCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullLimit_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddLimitCommand(null);
    }

    @Test
    public void execute_limitAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingLimitAdded modelStub = new ModelStubAcceptingLimitAdded();
        Limit validLimit = new LimitBuilder().build();

        CommandResult commandResult = new AddLimitCommand(validLimit).execute(modelStub, commandHistory);

        assertEquals(MESSAGE_SUCCESS + modelStub.generateLimitOutput(false,
                modelStub.getTotalSpend(validLimit), validLimit), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validLimit), modelStub.limitsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_sameDatesLimit_throwsCommandException() throws Exception {
        Limit validLimit = new LimitBuilder().build();
        AddLimitCommand limitCommand = new AddLimitCommand(validLimit);
        ModelStub modelStub = new ModelStubWithLimit(validLimit);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddLimitCommand.MESSAGE_LIMITS_SAME_DATE);
        limitCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Limit limit = new LimitBuilder().withMoneyFlow("-100").build();
        Limit limitD = new LimitBuilder().withDateStart("16-12-1998").build();
        AddLimitCommand limitCommand = new AddLimitCommand(limit);
        AddLimitCommand limitDCommand = new AddLimitCommand(limitD);

        // same object -> returns true
        assertTrue(limitCommand.equals(limitCommand));

        // same values -> returns true
        AddLimitCommand limitCommandCopy = new AddLimitCommand(limit);
        assertTrue(limitCommand.equals(limitCommandCopy));

        // different types -> returns false
        assertFalse(limitCommand.equals(1));

        // null -> returns false
        assertFalse(limitCommand.equals(null));

        // different limit -> returns false
        assertFalse(limitCommand.equals(limitDCommand));
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
        public void addListUniqueRecord(List<Record> records) {
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
        public void updateLimit(Limit target, Limit editedLimit) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyFinancialPlanner newData) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public ObservableList<Record> getRecordsThisMonth() {
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
        public Double getTotalSpend(Limit limit) {
            return 0.0;
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
            return false;
        }

        @Override
        public Limit getSameDatesLimit(Date dateStart, Date dateEnd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isExceededLimit (Limit limit) {
            return false;
        }

        @Override
        public void deleteListRecord(List<Record> targetList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String autoLimitCheck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String manualLimitCheck() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String generateLimitOutput(boolean isExceeded, Double totalSpend, Limit limit) {
            return "";
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
        public Month getCurrentMonth() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single limit.
     */
    private class ModelStubWithLimit extends ModelStub {
        private final Limit limit;

        ModelStubWithLimit(Limit limit) {
            requireNonNull(limit);
            this.limit = limit;
        }

        @Override
        public boolean hasSameDateLimit(Limit limit) {
            requireNonNull(limit);
            return this.limit.isSameLimitDates(limit);
        }

        @Override
        public Double getTotalSpend(Limit limit) {
            requireNonNull(limit);
            return this.getTotalSpend(limit);
        }
    }

    /**
     * A Model stub that always accept the record being added.
     */
    private class ModelStubAcceptingLimitAdded extends ModelStub {
        final ArrayList<Limit> limitsAdded = new ArrayList<>();


        public boolean hasSameDatesLimit(Limit limit) {
            requireNonNull(limit);
            return limitsAdded.stream().anyMatch(limit::isSameLimitDates);
        }

        @Override
        public void addLimit(Limit limit) {
            requireNonNull(limit);
            limitsAdded.add(limit);
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
