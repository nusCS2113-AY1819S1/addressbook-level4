package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedItem.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalItems.ARDUINO;
import static seedu.address.testutil.TypicalItems.INVALID_ARDUINO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Quantity;
import seedu.address.model.item.Name;
import seedu.address.testutil.Assert;

public class XmlAdaptedItemTest {
    private static final String INVALID_NAME = INVALID_ARDUINO.getName().toString();
    private static final Quantity INVALID_QUANTITY = INVALID_ARDUINO.getQuantity();
    private static final Quantity INVALID_MIN_QUANTITY = INVALID_ARDUINO.getMinQuantity();
    private static final List<Integer> INVALID_STATUS = INVALID_ARDUINO.getStatus();
    private static final List<XmlAdaptedTag> INVALID_TAGS = INVALID_ARDUINO.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    private static final String VALID_NAME = ARDUINO.getName().toString();
    private static final Quantity VALID_QUANTITY = ARDUINO.getQuantity();
    private static final List<Integer> VALID_STATUS = ARDUINO.getStatus();
    private static final Quantity VALID_MIN_QUANTITY = ARDUINO.getMinQuantity();
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
                new XmlAdaptedItem(VALID_NAME, INVALID_QUANTITY, VALID_MIN_QUANTITY, VALID_STATUS,  VALID_TAGS);
        String expectedMessage = Quantity.MESSAGE_QUANTITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        XmlAdaptedItem item = new XmlAdaptedItem(VALID_NAME, null,  VALID_MIN_QUANTITY, VALID_STATUS,VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        XmlAdaptedItem item =
                new XmlAdaptedItem(VALID_NAME, VALID_QUANTITY, VALID_MIN_QUANTITY, INVALID_STATUS, VALID_TAGS);
        String expectedMessage = "Invalid Status";
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        XmlAdaptedItem item = new XmlAdaptedItem(VALID_NAME, VALID_QUANTITY,VALID_MIN_QUANTITY, null,  VALID_TAGS);
        String expectedMessage = "Missing Status";
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
        XmlAdaptedItem item = new XmlAdaptedItem(VALID_NAME, VALID_QUANTITY,  null, VALID_STATUS,VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(INVALID_TAGS);
        XmlAdaptedItem item =
                new XmlAdaptedItem(VALID_NAME, VALID_QUANTITY,  VALID_MIN_QUANTITY, VALID_STATUS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, item::toModelType);
    }

}
