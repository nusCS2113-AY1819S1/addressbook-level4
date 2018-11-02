package seedu.planner.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.record.Record;

/**
 * An Immutable FinancialPlanner that is serializable to XML format
 */
@XmlRootElement(name = "financialplanner")
public class XmlSerializableFinancialPlanner {

    public static final String MESSAGE_DUPLICATE_RECORD = "Records list contains duplicate record(s).";

    @XmlElement
    private List<XmlAdaptedRecord> records;

    /**
     * Creates an empty XmlSerializableFinancialPlanner.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableFinancialPlanner() {
        records = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableFinancialPlanner(ReadOnlyFinancialPlanner src) {
        this();
        records.addAll(src.getRecordList().stream().map(XmlAdaptedRecord::new).collect(Collectors.toList()));
    }

    /**
     * Converts this financialplanner into the model's {@code FinancialPlanner} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedRecord}.
     */
    public FinancialPlanner toModelType() throws IllegalValueException {
        FinancialPlanner financialPlanner = new FinancialPlanner();
        for (XmlAdaptedRecord p : records) {
            Record record = p.toModelType();
            if (financialPlanner.hasRecord(record)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RECORD);
            }
            financialPlanner.addRecord(record);
        }
        return financialPlanner;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableFinancialPlanner)) {
            return false;
        }
        return records.equals(((XmlSerializableFinancialPlanner) other).records);
    }
}
