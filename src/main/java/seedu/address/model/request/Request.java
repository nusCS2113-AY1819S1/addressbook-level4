package seedu.address.model.request;

import java.util.Objects;

import seedu.address.model.book.Isbn;
import seedu.address.model.book.Quantity;

/**
 * Represents a Request in the BookInventory.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Request {

    // Identity fields
    private final Isbn isbn;
    private final Email email;
    private final Quantity quantity;

    public Request(Isbn isbn, Email email, Quantity quantity) {
        this.isbn = isbn;
        this.email = email;
        this.quantity = quantity;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    public Email getEmail() {
        return email;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    /**
     * compares request made with existing request
     * @param otherRequest request made by the user
     * @return boolean by comparing results
     */
    public boolean isSameRequest(Request otherRequest) {
        if (otherRequest == this) {
            return true;
        }

        return otherRequest != null
                && otherRequest.getEmail().equals(getEmail())
                && (otherRequest.getIsbn().equals(getIsbn()) || otherRequest.getQuantity().equals(getQuantity()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Request request = (Request) o;
        return Objects.equals(isbn, request.isbn)
                && Objects.equals(email, request.email)
                && Objects.equals(quantity, request.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, email, quantity);
    }

    @Override
    public String toString() {
        return "Request{"
                + "isbn=" + isbn
                + ", email=" + email
                + ", quantity=" + quantity
                + '}';
    }
}
