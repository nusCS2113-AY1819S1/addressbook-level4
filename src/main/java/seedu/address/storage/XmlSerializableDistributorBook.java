package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.DistributorBook;
import seedu.address.model.ReadOnlyDistributorBook;
import seedu.address.model.distributor.Distributor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable DistributorBook
 * that is serializable to XML format
 */
@XmlRootElement(name = "distributorbook")
public class XmlSerializableDistributorBook {

    public static final String MESSAGE_DUPLICATE_DISTRIBUTOR = "Distributor list contains duplicate distributors(s).";

    @XmlElement
    private List<XmlAdaptedDistributor> distributors;

    /**
     * Creates an empty XmlSerializableDistributorBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableDistributorBook() {
        distributors = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableDistributorBook(ReadOnlyDistributorBook src) {
        this();
        distributors.addAll(src.getDistributorList().stream().map(XmlAdaptedDistributor::new).collect(Collectors.toList()));
    }

    /**
     * Converts this distributorbook into the model's {@code DistributorBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedDistributor}.
     */
    public DistributorBook toModelType() throws IllegalValueException {
        DistributorBook distributorBook = new DistributorBook();

        for (XmlAdaptedDistributor p : distributors) {
            Distributor distributor = p.toModelType();
            if (distributorBook.hasDistributor(distributor)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DISTRIBUTOR);
            }
            distributorBook.addDistributor(distributor);
        }
        return distributorBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableDistributorBook)) {
            return false;
        }

        return distributors.equals(((XmlSerializableDistributorBook) other).distributors);
    }
}
