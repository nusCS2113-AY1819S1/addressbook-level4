package seedu.address.storage;

import static seedu.address.storage.XmlAdaptedBook.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalBooks.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Name;
import seedu.address.model.book.Price;
import seedu.address.model.book.Quantity;
import seedu.address.testutil.Assert;

public class XmlAdaptedBookTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String VALID_COST = "19.99";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getIsbn().toString();
    private static final String VALID_EMAIL = BENSON.getPrice().toString();
    private static final String VALID_ADDRESS = BENSON.getQuantity().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedBook person = new XmlAdaptedBook(BENSON);
        // assertEquals(BENSON, person.toModelType());
    }
    /*
    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedBook person =
                new XmlAdaptedBook(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COST, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
    */
    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedBook person = new XmlAdaptedBook(null, VALID_PHONE, VALID_EMAIL,
                VALID_COST, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedBook person =
                new XmlAdaptedBook(VALID_NAME, INVALID_PHONE, VALID_EMAIL,
                        VALID_COST, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Isbn.MESSAGE_ISBN_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedBook person = new XmlAdaptedBook(VALID_NAME, null, VALID_EMAIL,
                VALID_COST, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Isbn.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedBook person =
                new XmlAdaptedBook(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_COST, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Price.MESSAGE_PRICE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedBook person = new XmlAdaptedBook(VALID_NAME, VALID_PHONE, null,
                VALID_COST, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedBook person =
                new XmlAdaptedBook(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COST, INVALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Quantity.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedBook person = new XmlAdaptedBook(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COST, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedBook person =
                new XmlAdaptedBook(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COST, VALID_ADDRESS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

}
