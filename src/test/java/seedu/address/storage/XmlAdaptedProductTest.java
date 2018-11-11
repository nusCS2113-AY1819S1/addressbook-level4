package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedProduct.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalProducts.GRAPE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.product.Email;
import seedu.address.model.product.ProductInfo;
import seedu.address.model.product.Name;
import seedu.address.model.product.SerialNumber;
import seedu.address.model.product.RemainingItems;
import seedu.address.testutil.Assert;

public class XmlAdaptedProductTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_SERIAL_NUMBER = " ";
    private static final String INVALID_DISTRIBUTOR = " ";
    private static final String INVALID_PRODUCT_INFO = "+a!";
    private static final String INVALID_REMAINING_ITEMS = "abc";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = GRAPE.getName().toString();
    private static final String VALID_SERIAL_NUMBER = GRAPE.getSerialNumber().toString();
    private static final String VALID_DISTRIBUTOR = GRAPE.getDistributor().toString();
    private static final String VALID_PRODUCT_INFO = GRAPE.getProductInfo().toString();
    private static final String VALID_REMAINING_ITEMS = GRAPE.getRemainingItems().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = GRAPE.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validProductDetails_returnsProduct() throws Exception {
        XmlAdaptedProduct product = new XmlAdaptedProduct(GRAPE);
        assertEquals(GRAPE, product.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedProduct product =
                new XmlAdaptedProduct(INVALID_NAME, VALID_SERIAL_NUMBER, VALID_DISTRIBUTOR, VALID_PRODUCT_INFO,
                        VALID_TAGS, VALID_REMAINING_ITEMS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, product::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedProduct product = new XmlAdaptedProduct(null, VALID_SERIAL_NUMBER, VALID_DISTRIBUTOR,
                VALID_PRODUCT_INFO, VALID_TAGS, VALID_REMAINING_ITEMS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, product::toModelType);
    }

    @Test
    public void toModelType_invalidSerialNumber_throwsIllegalValueException() {
        XmlAdaptedProduct product =
                new XmlAdaptedProduct(VALID_NAME, INVALID_SERIAL_NUMBER, VALID_DISTRIBUTOR,
                        VALID_PRODUCT_INFO, VALID_TAGS, VALID_REMAINING_ITEMS);
        String expectedMessage = SerialNumber.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, product::toModelType);
    }

    @Test
    public void toModelType_nullSerialNumber_throwsIllegalValueException() {
        XmlAdaptedProduct product = new XmlAdaptedProduct(VALID_NAME, null, VALID_DISTRIBUTOR,
                VALID_PRODUCT_INFO, VALID_TAGS, VALID_REMAINING_ITEMS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, SerialNumber.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, product::toModelType);
    }

    @Test
    public void toModelType_invalidDistributorName_throwsIllegalValueException() {
        XmlAdaptedProduct product =
                new XmlAdaptedProduct(VALID_NAME, VALID_SERIAL_NUMBER, INVALID_DISTRIBUTOR, VALID_PRODUCT_INFO,
                        VALID_TAGS, VALID_REMAINING_ITEMS);
        String expectedMessage = DistributorName.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, product::toModelType);
    }

    @Test
    public void toModelType_nullDistributorName_throwsIllegalValueException() {
        XmlAdaptedProduct product = new XmlAdaptedProduct(VALID_NAME, VALID_SERIAL_NUMBER, null,
                VALID_PRODUCT_INFO, VALID_TAGS, VALID_REMAINING_ITEMS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, product::toModelType);
    }

    @Test
    public void toModelType_invalidProductInfo_throwsIllegalValueException() {
        XmlAdaptedProduct product =
                new XmlAdaptedProduct(VALID_NAME, VALID_SERIAL_NUMBER, VALID_DISTRIBUTOR, INVALID_PRODUCT_INFO,
                        VALID_TAGS, VALID_REMAINING_ITEMS);
        String expectedMessage = ProductInfo.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, product::toModelType);
    }

    @Test
    public void toModelType_nullProductInfo_throwsIllegalValueException() {
        XmlAdaptedProduct product = new XmlAdaptedProduct(VALID_NAME, VALID_SERIAL_NUMBER, VALID_DISTRIBUTOR, null,
                VALID_TAGS, VALID_REMAINING_ITEMS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ProductInfo.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, product::toModelType);
    }


    @Test
    public void toModelType_invalidRemainingItems_throwsIllegalValueException() {
        XmlAdaptedProduct product =
                new XmlAdaptedProduct(VALID_NAME, VALID_SERIAL_NUMBER, VALID_DISTRIBUTOR,
                        VALID_PRODUCT_INFO, VALID_TAGS, INVALID_REMAINING_ITEMS);
        String expectedMessage = RemainingItems.MESSAGE_REMAINING_ITEMS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, product::toModelType);
    }

    @Test
    public void toModelType_nullRemainingItems_throwsIllegalValueException() {
        XmlAdaptedProduct product = new XmlAdaptedProduct(VALID_NAME, VALID_SERIAL_NUMBER, VALID_DISTRIBUTOR,
                VALID_PRODUCT_INFO, VALID_TAGS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, RemainingItems.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, product::toModelType);
    }


    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedProduct product =
                new XmlAdaptedProduct(VALID_NAME, VALID_SERIAL_NUMBER, VALID_DISTRIBUTOR, VALID_PRODUCT_INFO,
                        invalidTags, VALID_REMAINING_ITEMS);
        Assert.assertThrows(IllegalValueException.class, product::toModelType);
    }
}
