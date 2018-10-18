package seedu.planner.storage;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;

import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.Limit;
import seedu.planner.model.record.MoneyFlow;




/**
 * Xml Adapted Limit
 */
public class XmlAdaptedLimit {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Limit's %s field is missing!";

    @XmlElement(required = true)
    private String dateStart;
    @XmlElement(required = true)
    private String dateEnd;
    @XmlElement(required = true)
    private String moneyFlow;

    /**
     * No argument constructor for the XmlAdaptedLimit as required
     */
    public XmlAdaptedLimit() {}
    /**
     * Detailed input strings constructor.
     */
    public XmlAdaptedLimit (String dateStart, String dateEnd, String moneyFlow) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.moneyFlow = moneyFlow;
    }

    /**
     * Given Limit type constructor.
     * @param limit
     */
    public XmlAdaptedLimit(Limit limit) {
        this.dateStart = limit.getDateStart().value;
        this.dateEnd = limit.getDateEnd().value;
        this.moneyFlow = limit.getLimitMoneyFlow().value;
    }

    /**
     * Change the xml file storage into the actual limit module.
     * @return
     * @throws IllegalValueException
     */
    public Limit toModelType() throws IllegalValueException {
        if (dateStart == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDateFormat(dateStart)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        final Date modelDateStart = new Date(dateStart);

        if (dateEnd == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDateFormat(dateEnd)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        final Date modelDateEnd = new Date(dateEnd);

        if (moneyFlow == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, MoneyFlow
                    .class.getSimpleName()));
        }
        if (!MoneyFlow.isValidMoneyFlow(moneyFlow)) {
            throw new IllegalValueException(MoneyFlow.MESSAGE_MONEY_FLOW_CONSTRAINTS);
        }
        final MoneyFlow modelMoneyFlow = new MoneyFlow(moneyFlow);

        return new Limit(modelDateStart, modelDateEnd, modelMoneyFlow);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof XmlAdaptedLimit)) {
            return false;
        }
        XmlAdaptedLimit otherLimit = (XmlAdaptedLimit) other;
        return Objects.equals(dateStart, otherLimit.dateStart)
                && Objects.equals(dateEnd, otherLimit.dateEnd)
                && Objects.equals(moneyFlow, otherLimit.moneyFlow);

    }


}
