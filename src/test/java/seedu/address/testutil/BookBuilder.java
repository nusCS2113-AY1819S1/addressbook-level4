package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.book.Book;
import seedu.address.model.book.Cost;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Name;
import seedu.address.model.book.Price;
import seedu.address.model.book.Quantity;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Book objects.
 */
public class BookBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_ISBN = "978-3-16-148410-0";
    public static final String DEFAULT_PRICE = "9.99";
    public static final String DEFAULT_COST = "5.99";
    public static final String DEFAULT_QUANTITY = "1";

    private Name name;
    private Isbn isbn;
    private Price price;
    private Cost cost;
    private Quantity quantity;
    private Set<Tag> tags;

    public BookBuilder() {
        name = new Name(DEFAULT_NAME);
        isbn = new Isbn(DEFAULT_ISBN);
        price = new Price(DEFAULT_PRICE);
        cost = new Cost(DEFAULT_COST);
        quantity = new Quantity(DEFAULT_QUANTITY);
        tags = new HashSet<>();
    }

    /**
     * Initializes the BookBuilder with the data of {@code bookToCopy}.
     */
    public BookBuilder(Book bookToCopy) {
        name = bookToCopy.getName();
        isbn = bookToCopy.getIsbn();
        price = bookToCopy.getPrice();
        cost = bookToCopy.getCost();
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
    public BookBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    /**
     * Sets the {@code Isbn} of the {@code Book} that we are building.
     */
    public BookBuilder withIsbn(String isbn) {
        this.isbn = new Isbn(isbn);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Book} that we are building.
     */
    public BookBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code Cost} of the {@code Book} that we are building
     */
    public BookBuilder withCost(String cost) {
        this.cost = new Cost(cost);
        return this;
    }

    public Book build() {
        return new Book(name, isbn, price, cost, quantity, tags);
    }

}
