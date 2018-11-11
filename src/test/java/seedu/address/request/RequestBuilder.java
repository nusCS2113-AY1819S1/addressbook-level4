package seedu.address.request;

import seedu.address.model.book.Isbn;
import seedu.address.model.book.Quantity;

/**
 * A utility class to help with building Book objects.
 */
public class RequestBuilder {


    public static final String DEFAULT_ISBN = "978-3-16-148410-0";
    public static final String DEFAULT_QUANTITY = "1";
    public static final String DEFAULT_EMAIL = "test@yahoo.com.sg";

    private Email email;
    private Isbn isbn;
    private Quantity quantity;

    public RequestBuilder() {
        isbn = new Isbn(DEFAULT_ISBN);
        quantity = new Quantity(DEFAULT_QUANTITY);
        email = new Email(DEFAULT_EMAIL);
    }

    /**
     * Initializes the BookBuilder with the data of {@code bookToCopy}.
     */
    public RequestBuilder(Request requestToCopy) {
        isbn = requestToCopy.getIsbn();
        quantity = requestToCopy.getQuantity();
        email = requestToCopy.getEmail();
    }

    /**
     * Sets the {@code Name} of the {@code Book} that we are building.
     */
    public RequestBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Book} that we are building.
     */
    public RequestBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    /**
     * Sets the {@code Isbn} of the {@code Book} that we are building.
     */
    public RequestBuilder withIsbn(String isbn) {
        this.isbn = new Isbn(isbn);
        return this;
    }

    public Request build() {
        return new Request(isbn, email, quantity);
    }

}
