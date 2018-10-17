package seedu.planner.storage.xmljaxb;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.model.record.Date;
import seedu.planner.model.summary.Summary;
import seedu.planner.model.summary.SummaryMap;

/**
 * An Immutable SummaryMap that is serializable to XML format
 */
@XmlRootElement(name = "summarymap")
public class XmlSerializableSummaryMap extends XmlSerializableClass<SummaryMap> {

    @XmlElement
    private Map<String, XmlAdaptedSummary> summaryMap;

    /**
     * This creates an empty {@code XmlSerializableSummaryMap}
     * This empty constructor is needed for marshalling
     */
    public XmlSerializableSummaryMap() {
        summaryMap = new HashMap(); }

    /**
     * Conversion
     */
    public XmlSerializableSummaryMap(SummaryMap summaryMap) {
        this.summaryMap = new HashMap();
        Map<Date, Summary> map = summaryMap.getMap();
        for (Date key : map.keySet()) {
            Summary summary = map.get(key);
            this.summaryMap.put(key.toString(), new XmlAdaptedSummary(summary));
        }
    }

    @Override
    public SummaryMap toModelType() throws IllegalValueException {
        SummaryMap summaryMap = new SummaryMap();
        for (String key : this.summaryMap.keySet()) {
            if (key == null) {
                throw new IllegalValueException(String.format(XmlAdaptedSummary.MISSING_FIELD_MESSAGE_FORMAT,
                        Date.class.getSimpleName()));
            }
            Summary summary = this.summaryMap.get(key).toModelType();
            summaryMap.add(summary);
        }
        return summaryMap;
    }

    //TODO: change this to follow the others
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableSummaryMap)) {
            return false;
        }
        return summaryMap.equals(((XmlSerializableSummaryMap) other).summaryMap);
    }
}
