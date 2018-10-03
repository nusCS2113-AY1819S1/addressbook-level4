package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.BookInventory;
import seedu.address.model.ReadOnlyBookInventory;
import seedu.address.model.book.Book;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Name;
import seedu.address.model.book.Price;
import seedu.address.model.book.Quantity;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code BookInventory} with sample data.
 */
public class SampleDataUtil {
    public static Book[] getSamplePersons() {
        return new Book[] {
            new Book(new Name("Alex Yeoh"), new Isbn("8743880700"), new Price("19.99"),
                new Quantity("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Book(new Name("Bernice Yu"), new Isbn("9927275800"), new Price("19.99"),
                new Quantity("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Book(new Name("Charlotte Oliveiro"), new Isbn("9321028300"), new Price("19.99"),
                new Quantity("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Book(new Name("David Li"), new Isbn("9103128200"), new Price("19.99"),
                new Quantity("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Book(new Name("Irfan Ibrahim"), new Isbn("9249202100"), new Price("19.99"),
                new Quantity("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Book(new Name("Roy Balakrishnan"), new Isbn("9262441700"), new Price("19.99"),
                new Quantity("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyBookInventory getSampleAddressBook() {
        BookInventory sampleAb = new BookInventory();
        for (Book sampleBook : getSamplePersons()) {
            sampleAb.addPerson(sampleBook);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
