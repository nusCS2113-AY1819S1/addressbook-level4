package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.request.ReadOnlyRequests;
import seedu.address.model.request.Request;
import seedu.address.model.request.RequestList;

/**
 * An Immutable RequestList that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableRequestList {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate request(s).";

    @XmlElement
    private List<XmlAdaptedRequest> persons;

    /**
     * Creates an empty XmlSerializableRequestList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableRequestList() {
        persons = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableRequestList(ReadOnlyRequests src) {
        this();
        persons.addAll(src.getRequestList().stream().map(XmlAdaptedRequest::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the requestModel's {@code RequestList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedRequest}.
     */
    public RequestList toModelType() throws IllegalValueException {
        RequestList requestList = new RequestList();
        for (XmlAdaptedRequest p : persons) {
            Request request = p.toModelType();
            if (requestList.hasRequest(request)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            requestList.addRequest(request);
        }
        return requestList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableRequestList)) {
            return false;
        }
        return persons.equals(((XmlSerializableRequestList) other).persons);
    }
}
