package seedu.planner.model.summary;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.planner.commons.util.CompareUtil;
import seedu.planner.commons.util.MoneyUtil;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Record;
import seedu.planner.ui.SummaryEntry;
//@@author tenvinc
/**
 * This class represents a list containing all Summary objects computed from a given list of records
 * and a predicate criteria. The internal implementation is a HashMap but it returns a list
 * and implements only list functions
 */
public class SummaryByDateList extends SummaryList {
    private HashMap<Date, Summary<Date>> summaryMap = new HashMap<>();

    private MoneyFlow total = new MoneyFlow("-0");
    private MoneyFlow totalIncome = new MoneyFlow("-0");
    private MoneyFlow totalExpense = new MoneyFlow("-0");

    public SummaryByDateList(List<Record> recordList) {
        requireNonNull(recordList);
        for (Record r : recordList) {
            addRecordToMap(r);
            updateTotals(r);
        }
    }

    @Override
    public ObservableList<SummaryEntry> getSummaryList() {

        List<SummaryEntry> list = summaryMap.keySet().stream().sorted(CompareUtil.compareDate())
                .map(k -> SummaryEntry.convertToUiFriendly(summaryMap.get(k)))
                .collect(Collectors.toList());
        return FXCollections.observableList(list);
    }

    public HashMap<Date, Summary<Date>> getSummaryMap() {
        return summaryMap;
    }


    @Override
    protected void addRecordToMap(Record record) {
        Date date = record.getDate();
        if (summaryMap.containsKey(date)) {
            summaryMap.get(date).add(record);
        } else {
            summaryMap.put(date, new Summary<>(record, date));
        }
    }

    /** Update the total moneyflow, total income and total expense */
    private void updateTotals(Record record) {
        MoneyFlow money = record.getMoneyFlow();
        if (isExpense(money)) {
            totalExpense = MoneyUtil.add(totalExpense, money);
        } else {
            totalIncome = MoneyUtil.add(totalIncome, money);
        }
        total = MoneyUtil.add(total, money);
    }

    private boolean isExpense(MoneyFlow money) {
        return money.toDouble() < 0;
    }

    @Override
    public int size() {
        return summaryMap.size();
    }

    @Override
    public boolean isEmpty() {
        return summaryMap.size() == 0;
    }

    @Override
    public MoneyFlow getTotal() {
        return total;
    }

    @Override
    public MoneyFlow getTotalIncome() {
        return totalIncome;
    }

    @Override
    public MoneyFlow getTotalExpense() {
        return totalExpense;
    }

    @Override
    public String getIdentifierName() {
        return Date.class.getSimpleName();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SummaryByDateList // instanceof handles nulls
                && summaryMap.equals(((SummaryByDateList) other).summaryMap));
    }
}
