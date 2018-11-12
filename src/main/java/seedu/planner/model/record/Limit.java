package seedu.planner.model.record;

import static java.lang.Math.abs;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.commons.util.DateUtil.isEarlierThan;
import static seedu.planner.commons.util.DateUtil.isLaterThan;
//@@Author OscarZeng
/**
* Limit used to offer a function as a reminder to user about their money usage.
* */
public class Limit {
    public static final String LIMIT_FORMAT = "dateStart: %s, dateEnd%s, moneyFlow:%s";
    private Date dateStart;
    private Date dateEnd;
    private MoneyFlow limitMoneyFlow;
    public Limit (Date dateStart , Date dateEnd, MoneyFlow limitMoneyFlow) {
        requireAllNonNull(dateEnd, dateStart, limitMoneyFlow);
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.limitMoneyFlow = limitMoneyFlow;
    }

    public Limit (Date date, MoneyFlow limitMoneyFlow) {
        requireAllNonNull(date, limitMoneyFlow);
        this.dateStart = date;
        this.dateEnd = date;
        this.limitMoneyFlow = limitMoneyFlow;
    }

    @Override
    public String toString () {
        return String.format(LIMIT_FORMAT, dateStart, dateEnd, limitMoneyFlow);
    }


    /**
     * This function is used to check whether
     * @param limitIn
     * @return
     */
    public boolean isSameLimitDates (Limit limitIn) {
        return (limitIn != null)
                && ((dateEnd.equals(limitIn.getDateEnd()))
                && (dateStart.equals(limitIn.getDateStart())));
    }
    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public MoneyFlow getLimitMoneyFlow() {
        return limitMoneyFlow;
    }

    /**
     * To test whether the given record is inside the limit date period.
     * return true if it is.
     * @param record
     * @return
     */
    public boolean isInsideDatePeriod (Record record) {
        Date recordDate;
        recordDate = record.getDate();
        return ((isEarlierThan(dateStart, recordDate) && isLaterThan(dateEnd, recordDate))
            || dateEnd.equals(recordDate) || dateStart.equals(recordDate));
    }

    /**
     * To test whether the money amount has already exceeded the limit.
     * return true if it exceeds.
     * @param money
     * @return
     */
    public boolean isExceeded (Double money) {

        return (money != null) && (money < 0) && (abs(limitMoneyFlow.toDouble()) < abs(money));

    }
}
