package seedu.planner.storage.xml_jaxb;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.record.Record;
import seedu.planner.model.record.UniqueRecordList;
import seedu.planner.model.record.exceptions.DuplicateRecordException;

/**
 * An Immutable FinancialPlanner that is serializable to XML format
 */
@XmlRootElement(name = "financialplanner")
public class XmlSerializableFinancialPlanner extends XmlSerializableClass<UniqueRecordList> {

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
     * Converts this RecordList into the model's {@code RecordList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedRecord}.
     */
    public UniqueRecordList toModelType() throws IllegalValueException {
        UniqueRecordList recordList = new UniqueRecordList();
        for (XmlAdaptedRecord p : records) {
            Record record = p.toModelType();
            try {
                recordList.add(record);
            } catch (DuplicateRecordException e) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RECORD);
            }
        }
        return recordList;
    }
    // TODO: change this to follow the others
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
