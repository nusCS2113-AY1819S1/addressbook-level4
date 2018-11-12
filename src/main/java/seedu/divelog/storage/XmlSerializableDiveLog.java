package seedu.divelog.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.divelog.commons.exceptions.IllegalValueException;
import seedu.divelog.logic.pressuregroup.exceptions.LimitExceededException;
import seedu.divelog.model.DiveLog;
import seedu.divelog.model.ReadOnlyDiveLog;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.exceptions.DiveOverlapsException;
import seedu.divelog.model.dive.exceptions.InvalidTimeException;

/**
 * An Immutable DiveLog that is serializable to XML format
 */
@XmlRootElement(name = "divelog")
public class XmlSerializableDiveLog {


    @XmlElement
    private List<XmlAdaptedDiveSession> dives;

    /**
     * Creates an empty XmlSerializableDiveLog.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableDiveLog() {
        dives = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableDiveLog(ReadOnlyDiveLog src) {
        this();
        dives.addAll(src.getDiveList().stream().map(XmlAdaptedDiveSession::new).collect(Collectors.toList()));
    }

    /**
     * Converts this divelog into the model's {@code DiveLog} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedDiveSession}.
     */
    public DiveLog toModelType() throws IllegalValueException {
        DiveLog diveLog = new DiveLog();
        for (XmlAdaptedDiveSession p : dives) {
            DiveSession dive = p.toModelType();
            try {
                diveLog.addDive(dive);
            } catch (LimitExceededException le) {
                throw new IllegalValueException("A dive that would result in decompression was found in the ");
            } catch (InvalidTimeException ive) {
                throw new IllegalValueException("Time was misformated");
            } catch (DiveOverlapsException dv) {
                throw new IllegalValueException("Found overlapping dives in XML file");
            }
        }
        return diveLog;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableDiveLog)) {
            return false;
        }
        return dives.equals(((XmlSerializableDiveLog) other).dives);
    }
}
