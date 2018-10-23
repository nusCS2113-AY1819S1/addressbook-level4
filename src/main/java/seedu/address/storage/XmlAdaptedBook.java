package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.Book;
import seedu.address.model.book.Cost;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Name;
import seedu.address.model.book.Price;
import seedu.address.model.book.Quantity;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Book.
 */
public class XmlAdaptedBook {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Book's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String isbn;
    @XmlElement(required = true)
    private String price;
    @XmlElement(required = true)
    private String cost;
    @XmlElement(required = true)
    private String quantity;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedBook.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedBook() {}

    /**
     * Constructs an {@code XmlAdaptedBook} with the given book details.
     */
    public XmlAdaptedBook(String name, String isbn, String price,
                          String cost, String quantity, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.isbn = isbn;
        this.price = price;
        this.cost = cost;
        this.quantity = quantity;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Book into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedBook
     */
    public XmlAdaptedBook(Book source) {
        name = source.getName().fullName;
        isbn = source.getIsbn().value;
        price = source.getPrice().value;
        cost = source.getCost().value;
        quantity = source.getQuantity().getValue();
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted book object into the model's Book object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted book
     */
    public Book toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (isbn == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Isbn.class.getSimpleName()));
        }
        if (!Isbn.isValidIsbn(isbn)) {
            throw new IllegalValueException(Isbn.MESSAGE_ISBN_CONSTRAINTS);
        }
        final Isbn modelIsbn = new Isbn(isbn);

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_PRICE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);

        if (cost == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Cost.class.getSimpleName()));
        }
        if (!Cost.isValidCost(cost)) {
            throw new IllegalValueException(Cost.MESSAGE_COST_CONSTRAINTS);
        }
        final Cost modelCost = new Cost(cost);

        if (quantity == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName())
            );
        }
        if (!Quantity.isValidQuantity(quantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
        }
        final Quantity modelQuantity = new Quantity(quantity);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Book(modelName, modelIsbn, modelPrice, modelCost, modelQuantity, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedBook)) {
            return false;
        }

        XmlAdaptedBook otherPerson = (XmlAdaptedBook) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(isbn, otherPerson.isbn)
                && Objects.equals(price, otherPerson.price)
                && Objects.equals(cost, otherPerson.cost)
                && Objects.equals(quantity, otherPerson.quantity)
                && tagged.equals(otherPerson.tagged);
    }
}
