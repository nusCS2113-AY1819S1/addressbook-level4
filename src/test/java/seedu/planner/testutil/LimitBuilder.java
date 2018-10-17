package seedu.planner.testutil;

import seedu.planner.model.record.Date;
import seedu.planner.model.record.Limit;
import seedu.planner.model.record.MoneyFlow;


/**
 * A utility class to help with building Limit objects.
 */
public class LimitBuilder {

    public static final String DEFAULT_DATE_START = "01-01-2001";
    public static final String DEFAULT_DATE_END = "01-02-2001";
    public static final String DEFAULT_MONEYFLOW = "-11.30";


    private Date dateStart;
    private Date dateEnd;
    private MoneyFlow moneyFlow;

    public LimitBuilder () {
        dateStart = new Date(DEFAULT_DATE_START);
        dateEnd = new Date(DEFAULT_DATE_END);
        moneyFlow = new MoneyFlow(DEFAULT_MONEYFLOW);
    }

    /**
     * Initializes the LimitBuilder with the data of {@code limitToCopy}.
     */
    public LimitBuilder (Limit limitToCopy) {
        dateStart = limitToCopy.getDateStart();
        dateEnd = limitToCopy.getDateEnd();
        moneyFlow = limitToCopy.getLimitMoneyFlow();
    }

    /**
     * Sets the {@code dateStart} of the {@code Limit} that we are building.
     */
    public LimitBuilder withDateStart(String dateStart) {
        this.dateStart = new Date(dateStart);
        return this;
    }

    /**
     * Parses the date and set it to the {@code Limti} that we are building.
     */
    public LimitBuilder withDateEnd(String dateEnd) {
        this.dateEnd = new Date(dateEnd);
        return this;
    }

    /**
     * Sets the {@code MoneyFlow} of the {@code Limit} that we are building.
     */
    public LimitBuilder withMoneyFlow(String moneyFlow) {
        this.moneyFlow = new MoneyFlow(moneyFlow);
        return this;
    }


    public Limit build() {
        return new Limit(dateStart, dateEnd, moneyFlow);
    }

}
