package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.product.Name;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedProd {

    @XmlValue
    private String prodName;

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted product
     */
    public String toModelType() throws IllegalValueException {
        if (!Name.isValidName(prodName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new String(prodName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedProd)) {
            return false;
        }

        return prodName.equals(((XmlAdaptedProd) other).prodName);
    }
}
