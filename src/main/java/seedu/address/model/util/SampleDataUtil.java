package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.BookInventory;
import seedu.address.model.ReadOnlyBookInventory;
import seedu.address.model.book.Book;
import seedu.address.model.book.Cost;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Name;
import seedu.address.model.book.Price;
import seedu.address.model.book.Quantity;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code BookInventory} with sample data.
 * Sample data taken from https://www.abebooks.com/books/Textbooks/index.shtml
 */
public class SampleDataUtil {
    public static Book[] getSamplePersons() {
        return new Book[] {
            new Book(new Name("Biology: A Global Approach"), new Isbn("9780321775658"), new Price("73.76"),
                    new Cost("19.99"), new Quantity("4"),
                getTagSet("friends")),
            new Book(new Name("Elementary Statistics: Picturing the World"), new Isbn("9780321693624"),
                    new Price("58.98"), new Cost("19.99"), new Quantity("11"),
                getTagSet("colleagues", "friends")),
            new Book(new Name("Essentials of Sociology, A Down-to-Earth Approach (9th Edition)"),
                    new Isbn("9780205763122"), new Price("59.99"), new Cost("19.99"), new Quantity("7"),
                getTagSet("neighbours")),
            new Book(new Name("World of Art, A (6th Edition)"), new Isbn("9780205677207"), new Price("15.19"),
                    new Cost("19.99"), new Quantity("22"),
                getTagSet("family")),
            new Book(new Name("Chemistry: A Molecular Approach (2nd US Edition)"), new Isbn("9780321651785"),
                    new Price("6.58"), new Cost("19.99"), new Quantity("29"),
                getTagSet("classmates")),
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
