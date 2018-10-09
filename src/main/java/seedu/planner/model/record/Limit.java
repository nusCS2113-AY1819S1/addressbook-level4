package seedu.planner.model.record;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

/**
* Limit used to offer a function as a reminder to user about their money usage.
* */
public class Limit {
    private Date dateStart;
    private Date dateEnd;
    private MoneyFlow limitMoneyFlow;
    public Limit (Date dateStart , Date dateEnd, MoneyFlow limitMoneyFlow) {
        requireAllNonNull(dateEnd, dateStart, limitMoneyFlow);
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.limitMoneyFlow = limitMoneyFlow;
    }

    @Override
    public String toString () {
        return String.format("LIMIT_FORMAT", dateStart, dateEnd, limitMoneyFlow);
    }

    /**
     * This function is used to check whether
     * @param limitin
     * @return
     */
    public boolean isSameLimitDates (Limit limitIn) {
        if (limitIn.dateEnd == dateEnd && limitIn.dateStart == dateStart) {
            return true;
        }
        return false;
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
}
