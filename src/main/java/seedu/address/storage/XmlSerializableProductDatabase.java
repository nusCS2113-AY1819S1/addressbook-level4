package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ProductDatabase;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.product.Product;

/**
 * An Immutable ProductDatabase that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableProductDatabase {

    public static final String MESSAGE_DUPLICATE_PRODUCT = "Product list contains duplicate product(s).";

    @XmlElement
    private List<XmlAdaptedProduct> products;

    /**
     * Creates an empty XmlSerializableProductDatabase.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableProductDatabase() {
        products = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableProductDatabase(ReadOnlyAddressBook src) {
        this();
        products.addAll(src.getPersonList().stream().map(XmlAdaptedProduct::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code ProductDatabase} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedProduct}.
     */
    public ProductDatabase toModelType() throws IllegalValueException {
        ProductDatabase productDatabase = new ProductDatabase();

        for (XmlAdaptedProduct p : products) {
            Product product = p.toModelType();

            if (productDatabase.hasPerson(product)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PRODUCT);
            }
            productDatabase.addPerson(product);
        }
        return productDatabase;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableProductDatabase)) {
            return false;
        }

        return products.equals(((XmlSerializableProductDatabase) other).products);
    }
}
