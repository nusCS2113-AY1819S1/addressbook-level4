package seedu.planner.model;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.logic.commands.LimitCommand.MESSAGE_BASIC_EARNED;
import static seedu.planner.logic.commands.LimitCommand.MESSAGE_BASIC_SPEND;
import static seedu.planner.logic.commands.LimitCommand.MESSAGE_EXCEED;
import static seedu.planner.logic.commands.LimitCommand.MESSAGE_NOT_EXCEED;

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
    private static final int STARTING_ELEMENT = 0;

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
        autoLimitCheck();
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
        indicateFinancialPlannerChanged();
    }

    @Override
    public int deleteListRecordSameDate(List<Record> targetList, Date targetDate) {
        int count;
        requireNonNull(targetList);
        count = versionedFinancialPlanner.removeRecordsSameDate(targetList, targetDate);
        versionedFinancialPlanner.removeRecordsFromSummarySameDate(targetList, targetDate);
        indicateFinancialPlannerChanged();
        indicateSummaryMapChanged();
        return count;
    }

    @Override
    public void deleteListRecord(List<Record> records) {
        requireNonNull(records);
        for (Record record : records) {
            versionedFinancialPlanner.removeRecord(record);
            versionedFinancialPlanner.removeRecordFromSummary(record);
        }
        indicateFinancialPlannerChanged();
        indicateSummaryMapChanged();
    }

    @Override
    public void addRecord(Record record) {
        requireNonNull(record);
        versionedFinancialPlanner.addRecord(record);
        updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORDS);
        indicateFinancialPlannerChanged();
    }

    @Override
    public void addListUniqueRecord(List<Record> records) {
        requireNonNull(records);
        for (Record record : records) {
            if (hasRecord(record)) {
                continue;
            }
            versionedFinancialPlanner.addRecord(record);
            versionedFinancialPlanner.addRecordToSummary(record);
        }
        updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORDS);
        indicateFinancialPlannerChanged();
        indicateSummaryMapChanged();
    }

    @Override
    public void updateRecord(Record target, Record editedRecord) {
        requireAllNonNull(target, editedRecord);

        versionedFinancialPlanner.updateRecord(target, editedRecord);
        indicateFinancialPlannerChanged();
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
    public Limit getSameDatesLimit (Date dateStart, Date dateEnd) {
        requireAllNonNull(dateStart, dateEnd);
        return versionedFinancialPlanner.getSameDatesLimit(dateStart, dateEnd);
    }
    @Override
    public Double getTotalSpend (Limit limitIn) {
        requireNonNull(limitIn);
        return versionedFinancialPlanner.getTotalSpend(limitIn);
    }
    @Override
    public String autoLimitCheck () {
        String output = "";
        int count = 1;
        for (Limit i: limits) {
            if (isExceededLimit(i)) {
                output += "\n" + String.format("%d.", count++) + generateLimitOutput(true, getTotalSpend(i), i);
            }
        }
        return output;
    }

    @Override
    public String manualLimitCheck () {
        String output = "";
        int count = 1;
        for (Limit i: limits) {
            output += "\n" + String.format("%d.", count++)
                    + generateLimitOutput(isExceededLimit(i), getTotalSpend(i), i);

        }
        return output;
    }

    /**
     * This function is to do the auto check whenever the record changed.
     * @param isExceeded
     * @param limit
     * @return
     */
    public String generateLimitOutput (boolean isExceeded, Double totalMoney, Limit limit) {
        String output;
        if (totalMoney > 0) {
            output = String.format(MESSAGE_BASIC_EARNED,
                    limit.getDateStart(), limit.getDateEnd(),
                    -1 * limit.getLimitMoneyFlow().toDouble(), totalMoney)
                    + MESSAGE_NOT_EXCEED;
        } else if (isExceeded) {
            output = String.format(MESSAGE_BASIC_SPEND,
                    limit.getDateStart(), limit.getDateEnd(),
                    -1 * limit.getLimitMoneyFlow().toDouble(), -1 * totalMoney)
                    + MESSAGE_EXCEED;
        } else {
            output = String.format(MESSAGE_BASIC_SPEND,
                    limit.getDateStart(), limit.getDateEnd(),
                    -1 * limit.getLimitMoneyFlow().toDouble(), -1 * totalMoney)
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
        indicateLimitListChanged();
    }

    @Override
    public void redoFinancialPlanner() {
        versionedFinancialPlanner.redo();
        indicateFinancialPlannerChanged();
        indicateLimitListChanged();
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
