package seedu.address.request.requeststorage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Quantity;
import seedu.address.request.Email;
import seedu.address.request
        .Request;

/**
 * JAXB-friendly version of the Request.
 */
public class XmlAdaptedRequest {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Request's %s field is missing!";

    @XmlElement(required = true)
    private String isbn;
    @XmlElement(required = true)
    private String quantity;
    @XmlElement(required = true)
    private String email;

    /**
     * Constructs an XmlAdaptedRequest.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRequest() {}

    /**
     * Constructs an {@code XmlAdaptedRequest} with the given request details.
     */
    public XmlAdaptedRequest(String isbn, String quantity, String email) {
        this.isbn = isbn;
        this.quantity = quantity;
        this.email = email;
    }

    /**
     * Converts a given Request into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedRequest
     */
    public XmlAdaptedRequest(Request source) {
        isbn = source.getIsbn().value;
        quantity = source.getQuantity().getValue();
        email = source.getEmail().value;
    }

    /**
     * Converts this jaxb-friendly adapted request object into the requestModel's Request object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted request
     */
    public Request toModelType() throws IllegalValueException {
        if (isbn == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Isbn.class.getSimpleName()));
        }
        if (!Isbn.isValidIsbn(isbn)) {
            throw new IllegalValueException(isbn.trim());
        }
        final Isbn modelIsbn = new Isbn(isbn);

        if (quantity == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName()));
        }
        if (!Quantity.isValidQuantity(quantity)) {
            throw new IllegalValueException(quantity.trim());
        }
        final Quantity modelQuantity = new Quantity(quantity);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        return new Request(modelIsbn, modelEmail, modelQuantity);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedRequest)) {
            return false;
        }

        XmlAdaptedRequest otherRequest = (XmlAdaptedRequest) other;
        return Objects.equals(isbn, otherRequest.isbn)
                && Objects.equals(quantity, otherRequest.quantity)
                && Objects.equals(email, otherRequest.email);
    }
}
