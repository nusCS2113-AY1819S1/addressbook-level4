package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedItem.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalItems.ARDUINO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;
import seedu.address.model.item.Status;
import seedu.address.testutil.Assert;

//    public static final Item INVALID_ARDUINO = new ItemBuilder().withName("Ardu@ino")
//            .withTags("#Lab1").withMinQuantity("-3")
//            .withQuantity("-4")
//            .withTags("#friends").build();
public class XmlAdaptedItemTest {
    private static final String INVALID_NAME = "Ardu@ino";
    private static final String INVALID_QUANTITY = "-1";
    private static final String INVALID_MIN_QUANTITY = "-1";
    private static final String INVALID_TAG = "#Lab1";

    private static final String VALID_NAME = ARDUINO.getName().toString();
    private static final String VALID_QUANTITY = ARDUINO.getQuantity().toString();
    private static final Status VALID_STATUS = ARDUINO.getStatus();
    private static final String VALID_MIN_QUANTITY = ARDUINO.getMinQuantity().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = ARDUINO.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validItemDetails_returnsItem() throws Exception {
        XmlAdaptedItem item = new XmlAdaptedItem(ARDUINO);
        assertEquals(ARDUINO, item.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedItem item =
                new XmlAdaptedItem(INVALID_NAME, VALID_QUANTITY, VALID_MIN_QUANTITY, VALID_STATUS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedItem item = new XmlAdaptedItem(null, VALID_QUANTITY, VALID_MIN_QUANTITY, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        XmlAdaptedItem item =
                new XmlAdaptedItem(VALID_NAME, INVALID_QUANTITY, VALID_MIN_QUANTITY, VALID_STATUS, VALID_TAGS);
        String expectedMessage = Quantity.MESSAGE_QUANTITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        XmlAdaptedItem item = new XmlAdaptedItem(VALID_NAME, null, VALID_MIN_QUANTITY, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Quantity");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidMinQuantity_throwsIllegalValueException() {
        XmlAdaptedItem item =
                new XmlAdaptedItem(VALID_NAME, VALID_QUANTITY, INVALID_MIN_QUANTITY, VALID_STATUS, VALID_TAGS);
        String expectedMessage = Quantity.MESSAGE_QUANTITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }


    @Test

    public void toModelType_nullMinQuantity_throwsIllegalValueException() {
        XmlAdaptedItem item = new XmlAdaptedItem(VALID_NAME, VALID_QUANTITY, null, VALID_STATUS, VALID_TAGS);
        String expectedMessage =
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "Minimum " + Quantity.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedItem item =
                new XmlAdaptedItem(VALID_NAME, VALID_QUANTITY, VALID_MIN_QUANTITY, VALID_STATUS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, item::toModelType);
    }

}
