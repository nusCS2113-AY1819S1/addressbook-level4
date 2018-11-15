package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.product.Name;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductInfo;
import seedu.address.model.product.ProductsDistributorName;
import seedu.address.model.product.RemainingItems;
import seedu.address.model.product.SerialNumber;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Product.
 */
public class XmlAdaptedProduct {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Product's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String serialNumber;
    @XmlElement(required = true)
    private String distributor;
    @XmlElement(required = true)
    private String info;
    @XmlElement(required = true)
    private String remainingItems;


    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an {@code XmlAdaptedProduct}.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedProduct() {}

    /**
     * Constructs an {@code XmlAdaptedProduct} with the given product details.
     */
    public XmlAdaptedProduct(String name, String serialNumber, String distributor,
                             String info, List<XmlAdaptedTag> tagged, String remainingItems) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.distributor = distributor;
        this.info = info;
        this.remainingItems = remainingItems;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Product into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedProduct
     */
    public XmlAdaptedProduct(Product source) {
        name = source.getName().fullName;
        serialNumber = source.getSerialNumber().value;
        distributor = source.getDistributor().fullDistName;
        info = source.getProductInfo().value;
        remainingItems = source.getRemainingItems().value;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted product object into the model's Product object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted product
     */
    public Product toModelType() throws IllegalValueException {
        final List<Tag> productTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            productTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (serialNumber == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, SerialNumber.class.getSimpleName()));
        }
        if (!SerialNumber.isValidPhone(serialNumber)) {
            throw new IllegalValueException(SerialNumber.MESSAGE_PHONE_CONSTRAINTS);
        }
        final SerialNumber modelSerialNumber = new SerialNumber(serialNumber);

        if (distributor == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ProductsDistributorName.class.getSimpleName()));
        }
        if (!DistributorName.isValidName(distributor)) {
            throw new IllegalValueException(DistributorName.MESSAGE_NAME_CONSTRAINTS);
        }
        final DistributorName modelDistName = new DistributorName(distributor);

        if (info == null) {
            throw new IllegalValueException(String.format
                    (MISSING_FIELD_MESSAGE_FORMAT, ProductInfo.class.getSimpleName()));
        }
        if (!ProductInfo.isValidAddress(info)) {
            throw new IllegalValueException(ProductInfo.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final ProductInfo modelProductInfo = new ProductInfo(info);

        if (remainingItems == null) {
            throw new IllegalValueException(String.format
                    (MISSING_FIELD_MESSAGE_FORMAT, RemainingItems.class.getSimpleName()));
        }
        if (!RemainingItems.isValidRemainingItems(remainingItems)) {
            throw new IllegalValueException(RemainingItems.MESSAGE_REMAINING_ITEMS_CONSTRAINTS);
        }
        final RemainingItems modelRemainingItems = new RemainingItems(remainingItems);

        final Set<Tag> modelTags = new HashSet<>(productTags);
        return new Product(modelName, modelSerialNumber,
                modelDistName, modelProductInfo, modelRemainingItems, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedProduct)) {
            return false;
        }

        XmlAdaptedProduct otherProduct = (XmlAdaptedProduct) other;
        return Objects.equals(name, otherProduct.name)
                && Objects.equals(serialNumber, otherProduct.serialNumber)
                && Objects.equals(distributor, otherProduct.distributor)
                && Objects.equals(info, otherProduct.info)
                && tagged.equals(otherProduct.tagged)
              && Objects.equals(remainingItems, otherProduct.remainingItems);
    }
}
