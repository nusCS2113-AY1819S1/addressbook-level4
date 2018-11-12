package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTrackedDataList;
import seedu.address.model.TrackedDataList;
import seedu.address.model.workout.Parameter;

/**
 * An Immutable tracked data list that is serializable to XML format
 */
@XmlRootElement(name = "trackeddatalist")
public class XmlSerializableTrackedDataList {

    public static final String MESSAGE_DUPLICATE_PARAMETER = "Tracked data list contains duplicate parameter(s).";

    @XmlElement
    private List<XmlAdaptedParameter> parameters;

    /**
     * Creates an empty XmlSerializableTrackedData.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableTrackedDataList() {
        parameters = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableTrackedDataList(ReadOnlyTrackedDataList src) {
        this();
        parameters.addAll(src.getTrackedDataList().stream().map(XmlAdaptedParameter::new).collect(Collectors.toList()));
    }

    /**
     * Converts this tracked data list into the model's {@code TrackedDataList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedParameter}.
     */
    public TrackedDataList toModelType() throws IllegalValueException {
        TrackedDataList trackedDataList = new TrackedDataList();
        for (XmlAdaptedParameter p : parameters) {
            Parameter parameter = p.toModelType();
            if (trackedDataList.hasParameter(parameter)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PARAMETER);
            }
            trackedDataList.addParameter(parameter);
        }
        return trackedDataList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableTrackedDataList)) {
            return false;
        }
        return parameters.equals(((XmlSerializableTrackedDataList) other).parameters);
    }
}
