package seedu.planner.storage;

import seedu.planner.commons.exceptions.IllegalValueException;

/**
 * This represents a typical class which can be serialized into XML
 */
public abstract class XmlSerializableClass<T> {

    public abstract T toModelType() throws IllegalValueException;
}
