package seedu.planner.storage;

import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.ReadOnlyLimitList;
import seedu.planner.model.record.Limit;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The limit list that is serializable to XML format
 */
@XmlRootElement(name = "LimitList")
public class XmlSerializableLimitList {
    public static final String MESSAGE_DUPLICATE_LIMIT = "There are redundant limits for the same period of time";

    @XmlElement
    private List<XmlAdaptedLimit> limits;

    public XmlSerializableLimitList() {
        limits = new ArrayList<>();
    }

    public XmlSerializableLimitList(ReadOnlyLimitList src) {
        this();
        limits.addAll(src.getLimitList().stream().map(XmlAdaptedLimit::new).collect(Collectors.toList()));
    }

    /**
     *
     * @return
     * @throws IllegalValueException
     */
    public FinancialPlanner toModelType() throws IllegalValueException {
        FinancialPlanner financialPlanner = new FinancialPlanner();

        for (XmlAdaptedLimit l : limits) {
            Limit limit = l.toModelType();
            if (financialPlanner.hasSameDateLimit(limit)){
                throw new IllegalValueException(MESSAGE_DUPLICATE_LIMIT);
            }
            financialPlanner.addLimit(limit);
        }
        return financialPlanner;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableLimitList)) {
            return false;
        }
        return limits.equals(((XmlSerializableLimitList) other).limits);
    }
}
