package seedu.planner.model;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.logic.commands.LimitCommand.MESSAGE_BASIC;
import static seedu.planner.logic.commands.LimitCommand.MESSAGE_EXCEED;
import static seedu.planner.logic.commands.LimitCommand.MESSAGE_NOT_EXCEED;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.planner.commons.core.ComponentManager;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.model.FinancialPlannerChangedEvent;
import seedu.planner.commons.events.model.LimitListChangedEvent;
import seedu.planner.commons.events.model.SummaryMapChangedEvent;
import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.LimitCommand;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.Limit;
import seedu.planner.model.record.Record;
import seedu.planner.model.summary.Summary;

/**
 * Represents the in-memory model of the financial planner data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedFinancialPlanner versionedFinancialPlanner;
    private final FilteredList<Record> filteredRecords;
    private final FilteredList<Limit> limits;
    /**
     * Initializes a ModelManager with the given financialPlanner and userPrefs.
     */
    public ModelManager(ReadOnlyFinancialPlanner financialPlanner, UserPrefs userPrefs) {
        super();
        requireAllNonNull(financialPlanner, userPrefs);

        logger.fine("Initializing with financial planner: " + financialPlanner
                + " and user prefs " + userPrefs);

        versionedFinancialPlanner = new VersionedFinancialPlanner(financialPlanner);
        filteredRecords = new FilteredList<>(versionedFinancialPlanner.getRecordList());
        limits = new FilteredList<Limit>(versionedFinancialPlanner.getLimitList());
    }

    public ModelManager() {
        this(new FinancialPlanner(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyFinancialPlanner newData) {
        versionedFinancialPlanner.resetData(newData);
        indicateFinancialPlannerChanged();
        indicateSummaryMapChanged();
    }

    @Override
    public ReadOnlyFinancialPlanner getFinancialPlanner() {
        return versionedFinancialPlanner;
    }

    //=========== Event management methods =========================================================

    /** Raises an event to indicate the model has changed */
    private void indicateFinancialPlannerChanged() {
        raise(new FinancialPlannerChangedEvent(versionedFinancialPlanner));
        autoLimitCheck();
    }

    /** Raises an event to indicate the summary map has changed */
    private void indicateSummaryMapChanged() {
        raise(new SummaryMapChangedEvent(versionedFinancialPlanner));
    }


    /** Raises an event to indicate the limit list has changed */
    private void indicateLimitListChanged() {
        raise(new LimitListChangedEvent(versionedFinancialPlanner));
    }

    //=========== Financial planner standard operations ============================================

    @Override
    public boolean hasRecord(Record record) {
        requireNonNull(record);
        return versionedFinancialPlanner.hasRecord(record);
    }

    @Override
    public void deleteRecord(Record target) {
        requireNonNull(target);
        versionedFinancialPlanner.removeRecord(target);
        versionedFinancialPlanner.removeRecordFromSummary(target);
        indicateFinancialPlannerChanged();
        indicateSummaryMapChanged();
    }

    @Override
    public void deleteListRecord(List<Record> targetList) {
        for (Record target : targetList) {
            versionedFinancialPlanner.removeRecord(target);
        }
        indicateFinancialPlannerChanged();
    }

    @Override
    public void addRecord(Record record) {
        requireNonNull(record);
        versionedFinancialPlanner.addRecord(record);
        versionedFinancialPlanner.addRecordToSummary(record);
        updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORDS);
        indicateFinancialPlannerChanged();
        indicateSummaryMapChanged();
    }

    @Override
    public void updateRecord(Record target, Record editedRecord) {
        requireAllNonNull(target, editedRecord);

        versionedFinancialPlanner.updateRecord(target, editedRecord);
        versionedFinancialPlanner.updateSummary(target, editedRecord);
        indicateFinancialPlannerChanged();
        indicateSummaryMapChanged();
    }

    //=========== Limit related methods =====================================================
    @Override
    public boolean hasSameDateLimit(Limit limitIn) {
        requireNonNull(limitIn);
        return versionedFinancialPlanner.hasSameDateLimit(limitIn);
    }
    @Override
    public void deleteLimit(Limit target) {
        versionedFinancialPlanner.removeLimit(target);
        indicateLimitListChanged();
    }

    @Override
    public void addLimit(Limit limitIn) {
        versionedFinancialPlanner.addLimit(limitIn);

        indicateLimitListChanged();
    }
    @Override
    public boolean isExceededLimit (Limit limitIn) {
        requireNonNull(limitIn);
        return (versionedFinancialPlanner.isExceededLimit(limitIn));
    }
    @Override
    public void updateLimit(Limit target, Limit editedLimit) {
        requireAllNonNull(target, editedLimit);
        versionedFinancialPlanner.updateLimit(target, editedLimit);
        indicateLimitListChanged();
    }

    @Override
    public String autoLimitCheck () {
       String output = "\n";
        int count = 1;
        for (Limit i: limits) {
            output += String.format("%d.",count++) + generateLimitOutput(isExceededLimit(i),i) + "\n";
        }
       // new LimitCommand(output).execute( null , new CommandHistory()) ;
        return output;
    }

    /**
     * This function is to do the auto check whenever the record changed.
     * @param isExceeded
     * @param limit
     * @return
     */
    public String generateLimitOutput (boolean isExceeded, Limit limit) {
        String output;
        if (isExceeded) {
            output = String.format(MESSAGE_BASIC,
                    limit.getDateStart(), limit.getDateEnd(), limit.getLimitMoneyFlow().toDouble())
                    + MESSAGE_EXCEED;
        } else {
            output = String.format(MESSAGE_BASIC,
                    limit.getDateStart(), limit.getDateEnd(), limit.getLimitMoneyFlow().toDouble())
                    + MESSAGE_NOT_EXCEED;
        }
        return output;
    }


    //=========== Filtered Record List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Record} backed by the internal list of
     * {@code versionedFinancialPlanner}
     */
    @Override
    public ObservableList<Record> getFilteredRecordList() {
        return FXCollections.unmodifiableObservableList(filteredRecords);
    }

    //show all the records
    @Override
    public ObservableList<Limit> getLimitList() {
        return FXCollections.unmodifiableObservableList(limits);
    }

    @Override
    public void updateFilteredRecordList(Predicate<Record> predicate) {
        requireNonNull(predicate);
        filteredRecords.setPredicate(predicate);
    }

    //=========== Modifying Record List Accessors =============================================================

    @Override
    public void sortFilteredRecordList(String category, Boolean ascending) {
        requireAllNonNull(category, ascending);
        versionedFinancialPlanner.sortRecords(category, ascending);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoFinancialPlanner() {
        return versionedFinancialPlanner.canUndo();
    }

    @Override
    public boolean canRedoFinancialPlanner() {
        return versionedFinancialPlanner.canRedo();
    }

    @Override
    public void undoFinancialPlanner() {
        versionedFinancialPlanner.undo();
        indicateFinancialPlannerChanged();
        indicateSummaryMapChanged();
        indicateLimitListChanged();
    }

    @Override
    public void redoFinancialPlanner() {
        versionedFinancialPlanner.redo();
        indicateFinancialPlannerChanged();
        indicateSummaryMapChanged();
        indicateLimitListChanged();
    }

    @Override
    public void commitFinancialPlanner() {
        versionedFinancialPlanner.commit();
    }

    //=========== Summary Display =================================================================================

    public ObservableList<Summary> getSummaryList(Date startDate, Date endDate) {
        return versionedFinancialPlanner.getSummaryList(startDate, endDate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedFinancialPlanner.equals(other.versionedFinancialPlanner)
                && filteredRecords.equals(other.filteredRecords);
    }
}
