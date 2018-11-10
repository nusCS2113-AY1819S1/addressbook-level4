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
import seedu.address.request.Email;
import seedu.address.request.ReadOnlyRequests;
import seedu.address.request.Request;
import seedu.address.request.RequestList;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code BookInventory} with sample data.
 * Sample data taken from https://www.abebooks.com/books/Textbooks/index.shtml
 */
public class SampleDataUtil {
    public static Book[] getSampleBooks() {
        return new Book[] {
            new Book(new Name("Biology: A Global Approach"), new Isbn("9780321775658"), new Price("73.76"),
                    new Cost("19.99"), new Quantity("4"),
                getTagSet("nonfiction")),
            new Book(new Name("Elementary Statistics: Picturing the World"), new Isbn("9780321693624"),
                    new Price("58.98"), new Cost("19.99"), new Quantity("11"),
                getTagSet("nonfiction")),
            new Book(new Name("Essentials of Sociology, A Down-to-Earth Approach (9th Edition)"),
                    new Isbn("9780205763122"), new Price("59.99"), new Cost("19.99"), new Quantity("7"),
                getTagSet("nonfiction")),
            new Book(new Name("World of Art, A (6th Edition)"), new Isbn("9780205677207"), new Price("15.19"),
                    new Cost("19.99"), new Quantity("22"),
                getTagSet("nonfiction")),
            new Book(new Name("Chemistry: A Molecular Approach (2nd US Edition)"), new Isbn("9780321651785"),
                    new Price("6.58"), new Cost("19.99"), new Quantity("29"),
                getTagSet("nonfiction")),
            new Book(new Name("Brief Answers to the Big Questions"), new Isbn("9781473695986"),
                    new Price("23.90"), new Cost("13.90"), new Quantity("15"),
                    getTagSet("nonfiction")),
            new Book(new Name("Billion Dollar Whale : The Man Who Fooled Wall Street, Hollywood, and the World"),
                    new Isbn("9780316453479"),
                    new Price("29.96"), new Cost("19.96"), new Quantity("40"),
                    getTagSet("nonfiction", "bestseller")),
            new Book(new Name("21 Lessons for the 21st Century (English Language Edition)"), new Isbn("9781787330870"),
                    new Price("25.46"), new Cost("15.46"), new Quantity("18"),
                    getTagSet("nonfiction")),
            new Book(new Name("Robert Kuok : A Memoir"), new Isbn("9789814189736"),
                    new Price("39.90"), new Cost("49.90"), new Quantity("9"),
                    getTagSet("nonfiction")),
            new Book(new Name("The Next Person You Meet In Heaven"), new Isbn("9780751571899"),
                    new Price("33.45"), new Cost("23.43"), new Quantity("0"),
                    getTagSet("fiction")),
            new Book(new Name("Travelling Cat Chronicles"), new Isbn("9780857524195"),
                    new Price("25.41"), new Cost("15.41"), new Quantity("11"),
                    getTagSet("fiction")),
            new Book(new Name("China Rich Girlfriend"), new Isbn("9781101973394"),
                    new Price("16.05"), new Cost("6.05"), new Quantity("19"),
                    getTagSet("fiction")),
            new Book(new Name("Crazy Rich Asians"), new Isbn("9780804171588"),
                    new Price("16.05"), new Cost("6.05"), new Quantity("4"),
                    getTagSet("fiction", "bestseller")),
            new Book(new Name("The Fall of Gondolin"), new Isbn("9780008302757"),
                    new Price("22.80"), new Cost("42.80"), new Quantity("3"),
                    getTagSet("fiction")),
            new Book(new Name("Laws of Human Nature"), new Isbn("9781781259191"),
                    new Price("34.08"), new Cost("24.08"), new Quantity("2"),
                    getTagSet("nonfiction", "bestseller")),
            new Book(new Name("Fear"), new Isbn("9781501175510"),
                    new Price("36.05"), new Cost("16.55"), new Quantity("7"),
                    getTagSet("nonfiction", "bestseller")),
        };
    }
    public static Request[] getSampleRequests() {
        return new Request[] {
            new Request(new Isbn("978-3-16-148410-0"), new Email("testing@gmail.com"),
                    new Quantity("2")),
            new Request(new Isbn("978-3-16-148410-0"), new Email("testing1@gmail.com"),
                    new Quantity("3")),
        };
    }
    public static ReadOnlyBookInventory getSampleAddressBook() {
        BookInventory sampleAb = new BookInventory();
        for (Book sampleBook : getSampleBooks()) {
            sampleAb.addBook(sampleBook);
        }
        return sampleAb;
    }

    public static ReadOnlyRequests getSampleRequestList() {
        RequestList requestBi = new RequestList();
        for (Request sampleRequest : getSampleRequests()) {
            requestBi.addRequest(sampleRequest);
        }
        return requestBi;
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
