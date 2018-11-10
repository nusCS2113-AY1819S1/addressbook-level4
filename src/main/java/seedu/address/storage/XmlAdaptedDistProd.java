package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.distributor.DistributorProduct;

/**
 * JAXB-friendly adapted version of the DistribtorProduct.
 */
public class XmlAdaptedDistProd {

    @XmlValue
    private String distProd;

    /**
     * Constructs an XmlAdaptedDistProd.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedDistProd() {}

    /**
     * Constructs a {@code XmlAdaptedDistProd} with the given {@code distProd}.
     */
    public XmlAdaptedDistProd(String distProd) {
        this.distProd = distProd;
    }

    /**
     * Converts a given DistributorProduct into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedDistProd(DistributorProduct source) {
        distProd = source.distributorProducts;
    }

    /**
     * Converts this jaxb-friendly adapted distprod object into the model's DistributorProduct object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted product
     */
    public DistributorProduct toModelType() throws IllegalValueException {
        if (!DistributorProduct.isValidName(distProd)) {
            throw new IllegalValueException(DistributorProduct.MESSAGE_DISTPROD_CONSTRAINTS);
        }
        return new DistributorProduct(distProd);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedDistProd)) {
            return false;
        }

        return distProd.equals(((XmlAdaptedDistProd) other).distProd);
    }
}
