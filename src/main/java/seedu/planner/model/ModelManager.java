package seedu.planner.model;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.planner.commons.core.ComponentManager;
import seedu.planner.commons.core.EventsCenter;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.model.FinancialPlannerChangedEvent;
import seedu.planner.commons.events.model.LimitListChangedEvent;
import seedu.planner.commons.events.model.TagMapChangedEvent;
import seedu.planner.commons.events.ui.UpdateWelcomePanelEvent;
import seedu.planner.commons.util.DateUtil;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;
import seedu.planner.model.record.Limit;
import seedu.planner.model.record.Record;
import seedu.planner.model.summary.CategoryStatisticsList;

/**
 * Represents the in-memory model of the financial planner data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedFinancialPlanner versionedFinancialPlanner;
    private final FilteredList<Record> filteredRecords;
    private final FilteredList<Limit> limits;

    private final Month currentMonth;
    private final FilteredList<Record> recordsInCurrentMonth;

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
        currentMonth = getCurrentMonth();
        recordsInCurrentMonth = new FilteredList<>(versionedFinancialPlanner.getRecordList(),
                new DateIsWithinIntervalPredicate(DateUtil.generateFirstOfMonth(currentMonth),
                        DateUtil.generateLastOfMonth(currentMonth)));
        recordsInCurrentMonth.addListener((InvalidationListener) observable -> {
            CategoryStatisticsList statsList = new CategoryStatisticsList(recordsInCurrentMonth);
            EventsCenter.getInstance().post(new UpdateWelcomePanelEvent(statsList.getReadOnlyStatsList()));
        });
        EventsCenter.getInstance().registerHandler(this);
    }

    public ModelManager() {
        this(new FinancialPlanner(), new UserPrefs());
    }

    private Month getCurrentMonth() {
        Date currentDate = DateUtil.getDateToday();
        return new Month(currentDate.getMonth(), currentDate.getYear());
    }

    @Override
    public void resetData(ReadOnlyFinancialPlanner newData) {
        versionedFinancialPlanner.resetData(newData);
        indicateFinancialPlannerChanged();
        indicateTagMapChanged();
    }

    @Override
    public ReadOnlyFinancialPlanner getFinancialPlanner() {
        return versionedFinancialPlanner;
    }

    public ObservableList<Record> getRecordsThisMonth() {
        return recordsInCurrentMonth;
    }

    //=========== Event management methods =========================================================

    /** Raises an event to indicate the model has changed */
    private void indicateFinancialPlannerChanged() {
        raise(new FinancialPlannerChangedEvent(versionedFinancialPlanner));
    }

    /** Raises an event to indicate the limit list has changed */
    private void indicateLimitListChanged() {
        raise(new LimitListChangedEvent(versionedFinancialPlanner));
    }

    /** Raises an event to indicate the limit list has changed */
    private void indicateTagMapChanged() {
        raise(new TagMapChangedEvent(versionedFinancialPlanner));
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
        versionedFinancialPlanner.removeRecordFromTagMap(target);
        indicateFinancialPlannerChanged();
        indicateTagMapChanged();
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
        versionedFinancialPlanner.addRecordToTagMap(record);
        updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORDS);
        indicateFinancialPlannerChanged();
        indicateTagMapChanged();
    }

    @Override
    public void updateRecord(Record target, Record editedRecord) {
        requireAllNonNull(target, editedRecord);
        versionedFinancialPlanner.updateRecord(target, editedRecord);
        versionedFinancialPlanner.updateRecordInTagMap(target, editedRecord);
        indicateFinancialPlannerChanged();
        indicateTagMapChanged();
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
        indicateLimitListChanged();
        indicateTagMapChanged();
    }

    @Override
    public void redoFinancialPlanner() {
        versionedFinancialPlanner.redo();
        indicateFinancialPlannerChanged();
        indicateLimitListChanged();
        indicateTagMapChanged();
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
