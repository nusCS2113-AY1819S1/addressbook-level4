package seedu.planner.model;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.planner.commons.core.ComponentManager;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.model.FinancialPlannerChangedEvent;
import seedu.planner.model.record.Record;

/**
 * Represents the in-memory model of the financial planner data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedFinancialPlanner versionedFinancialPlanner;
    private final FilteredList<Record> filteredRecords;

    /**
     * Initializes a ModelManager with the given financialPlanner and userPrefs.
     */
    public ModelManager(ReadOnlyFinancialPlanner financialPlanner, UserPrefs userPrefs) {
        super();
        requireAllNonNull(financialPlanner, userPrefs);

        logger.fine("Initializing with financial planner: " + financialPlanner + " and user prefs " + userPrefs);

        versionedFinancialPlanner = new VersionedFinancialPlanner(financialPlanner);
        filteredRecords = new FilteredList<>(versionedFinancialPlanner.getRecordList());
    }

    public ModelManager() {
        this(new FinancialPlanner(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyFinancialPlanner newData) {
        versionedFinancialPlanner.resetData(newData);
        indicateFinancialPlannerChanged();
    }

    @Override
    public ReadOnlyFinancialPlanner getFinancialPlanner() {
        return versionedFinancialPlanner;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateFinancialPlannerChanged() {
        raise(new FinancialPlannerChangedEvent(versionedFinancialPlanner));
    }

    @Override
    public boolean hasRecord(Record record) {
        requireNonNull(record);
        return versionedFinancialPlanner.hasRecord(record);
    }

    @Override
    public void deleteRecord(Record target) {
        versionedFinancialPlanner.removeRecord(target);
        versionedFinancialPlanner.removeRecordFromSummary(target);
        indicateFinancialPlannerChanged();
    }

    @Override
    public void addRecord(Record record) {
        versionedFinancialPlanner.addRecord(record);
        versionedFinancialPlanner.addRecordToSummary(record);
        updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORDS);
        indicateFinancialPlannerChanged();
    }

    @Override
    public void updateRecord(Record target, Record editedRecord) {
        requireAllNonNull(target, editedRecord);

        versionedFinancialPlanner.updateRecord(target, editedRecord);
        indicateFinancialPlannerChanged();
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

    @Override
    public void updateFilteredRecordList(Predicate<Record> predicate) {
        requireNonNull(predicate);
        filteredRecords.setPredicate(predicate);
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
    }

    @Override
    public void redoFinancialPlanner() {
        versionedFinancialPlanner.redo();
        indicateFinancialPlannerChanged();
    }

    @Override
    public void commitFinancialPlanner() {
        versionedFinancialPlanner.commit();
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
