package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.XmlAdaptedBook.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalBooks.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.Cost;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Name;
import seedu.address.model.book.Price;
import seedu.address.model.book.Quantity;
import seedu.address.testutil.Assert;

public class XmlAdaptedBookTest {
    private static final String INVALID_NAME = "*;";
    private static final String INVALID_ISBN = "+651234";
    private static final String INVALID_QUANTITY = "";
    private static final String INVALID_PRICE = "124.124";
    private static final String INVALID_COST = "0.124";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_ISBN = BENSON.getIsbn().toString();
    private static final String VALID_PRICE = BENSON.getPrice().toString();
    private static final String VALID_COST = BENSON.getCost().toString();
    private static final String VALID_QUANTITY = BENSON.getQuantity().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validBookDetails_returnsBook() throws Exception {
        XmlAdaptedBook book = new XmlAdaptedBook(BENSON);
        assertEquals(BENSON.toString(), book.toModelType().toString());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedBook book =
                new XmlAdaptedBook(INVALID_NAME, VALID_ISBN, VALID_PRICE, VALID_COST, VALID_QUANTITY, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(null, VALID_ISBN, VALID_PRICE,
                VALID_COST, VALID_QUANTITY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_invalidIsbn_throwsIllegalValueException() {
        XmlAdaptedBook book =
                new XmlAdaptedBook(VALID_NAME, INVALID_ISBN, VALID_PRICE,
                        VALID_COST, VALID_QUANTITY, VALID_TAGS);
        String expectedMessage = Isbn.MESSAGE_ISBN_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullIsbn_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(VALID_NAME, null, VALID_PRICE,
                VALID_COST, VALID_QUANTITY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Isbn.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        XmlAdaptedBook book =
                new XmlAdaptedBook(VALID_NAME, VALID_ISBN, INVALID_PRICE, VALID_COST, VALID_QUANTITY, VALID_TAGS);
        String expectedMessage = Price.MESSAGE_PRICE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(VALID_NAME, VALID_ISBN, null,
                VALID_COST, VALID_QUANTITY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_invalidCost_throwsIllegalValueException() {
        XmlAdaptedBook book =
                new XmlAdaptedBook(VALID_NAME, VALID_ISBN, VALID_PRICE, INVALID_COST, VALID_QUANTITY, VALID_TAGS);
        String expectedMessage = Cost.MESSAGE_COST_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullCost_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(VALID_NAME, VALID_ISBN, VALID_PRICE,
                null, VALID_QUANTITY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Cost.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        XmlAdaptedBook book =
                new XmlAdaptedBook(VALID_NAME, VALID_ISBN, VALID_PRICE, VALID_COST, INVALID_QUANTITY, VALID_TAGS);
        String expectedMessage = Quantity.MESSAGE_QUANTITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        XmlAdaptedBook book = new XmlAdaptedBook(VALID_NAME, VALID_ISBN, VALID_PRICE, VALID_COST, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedBook person =
                new XmlAdaptedBook(VALID_NAME, VALID_ISBN, VALID_PRICE, VALID_COST, VALID_QUANTITY, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

}
