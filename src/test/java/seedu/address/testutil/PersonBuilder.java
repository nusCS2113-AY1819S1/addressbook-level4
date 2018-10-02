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
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private ISBN ISBN;
    private Price price;
    private Quantity quantity;
    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        ISBN = new ISBN(DEFAULT_PHONE);
        price = new Price(DEFAULT_EMAIL);
        quantity = new Quantity(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code bookToCopy}.
     */
    public PersonBuilder(Book bookToCopy) {
        name = bookToCopy.getName();
        ISBN = bookToCopy.getISBN();
        price = bookToCopy.getPrice();
        quantity = bookToCopy.getQuantity();
        tags = new HashSet<>(bookToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Book} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Book} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Book} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.quantity = new Quantity(address);
        return this;
    }

    /**
     * Sets the {@code ISBN} of the {@code Book} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.ISBN = new ISBN(phone);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Book} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.price = new Price(email);
        return this;
    }

    public Book build() {
        return new Book(name, ISBN, price, quantity, tags);
    }

}
