package seedu.planner.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.record.DateBasedLimitList;
import seedu.planner.model.record.Limit;
import seedu.planner.storage.xmljaxb.XmlSerializableClass;




/**
 * The limit list that is serializable to XML format
 */
@XmlRootElement(name = "LimitList")
public class XmlSerializableLimitList extends XmlSerializableClass<DateBasedLimitList> {
    public static final String MESSAGE_DUPLICATE_LIMIT = "There are redundant limits for the same period of time";

    @XmlElement
    private List<XmlAdaptedLimit> limits;

    public XmlSerializableLimitList() {
        limits = new ArrayList<>();
    }

    public XmlSerializableLimitList(ReadOnlyFinancialPlanner src) {
        this();
        limits.addAll(src.getLimitList().stream().map(XmlAdaptedLimit::new).collect(Collectors.toList()));
    }

    /**
     *
     * @return
     * @throws IllegalValueException
     */
    public DateBasedLimitList toModelType() throws IllegalValueException {
        DateBasedLimitList dateBasedLimitList = new DateBasedLimitList();

        for (XmlAdaptedLimit l : limits) {
            Limit limit = l.toModelType();
            if (dateBasedLimitList.hasSameDatesLimit(limit)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LIMIT);
            }
            dateBasedLimitList.add(limit);
        }
        return dateBasedLimitList;
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
