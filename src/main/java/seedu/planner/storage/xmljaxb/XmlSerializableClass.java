package seedu.planner.storage.xmljaxb;

import seedu.planner.commons.exceptions.IllegalValueException;

/**
 * This represents a typical class which can be serialized into XML by JAXB
 */
public abstract class XmlSerializableClass<T> {

    public abstract T toModelType() throws IllegalValueException;
}
