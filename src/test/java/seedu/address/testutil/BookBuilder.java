package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.book.*;
import seedu.address.model.book.Book;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Book objects.
 */
public class BookBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Isbn isbn;
    private Price price;
    private Quantity quantity;
    private Set<Tag> tags;

    public BookBuilder() {
        name = new Name(DEFAULT_NAME);
        isbn = new Isbn(DEFAULT_PHONE);
        price = new Price(DEFAULT_EMAIL);
        quantity = new Quantity(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the BookBuilder with the data of {@code bookToCopy}.
     */
    public BookBuilder(Book bookToCopy) {
        name = bookToCopy.getName();
        isbn = bookToCopy.getIsbn();
        price = bookToCopy.getPrice();
        quantity = bookToCopy.getQuantity();
        tags = new HashSet<>(bookToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Book} that we are building.
     */
    public BookBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Book} that we are building.
     */
    public BookBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Book} that we are building.
     */
    public BookBuilder withAddress(String address) {
        this.quantity = new Quantity(address);
        return this;
    }

    /**
     * Sets the {@code Isbn} of the {@code Book} that we are building.
     */
    public BookBuilder withPhone(String phone) {
        this.isbn = new Isbn(phone);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Book} that we are building.
     */
    public BookBuilder withEmail(String email) {
        this.price = new Price(email);
        return this;
    }

    public Book build() {
        return new Book(name, isbn, price, quantity, tags);
    }

}
