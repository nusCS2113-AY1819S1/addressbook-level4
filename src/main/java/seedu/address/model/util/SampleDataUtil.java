package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.record.Expense;
import seedu.address.model.record.Income;
import seedu.address.model.record.Name;
import seedu.address.model.record.Record;
import seedu.address.model.record.Date;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Record[] getSampleRecords() {
        return new Record[] {
            new Record(new Name("Alex Yeoh"), new Date("17-9-2004"), new Income("101.30"),
                new Expense("100.30"),
                getTagSet("friends")),
            new Record(new Name("Bernice Yu"), new Date("23-4-1869"), new Income("140.40"),
                new Expense("139.40"),
                getTagSet("colleagues", "friends")),
            new Record(new Name("Charlotte Oliveiro"), new Date("29-5-1999"), new Income("24.49"),
                new Expense("23.49"),
                getTagSet("neighbours")),
            new Record(new Name("David Li"), new Date("30-6-2014"), new Income("437.49"),
                new Expense("436.56"),
                getTagSet("family")),
            new Record(new Name("Irfan Ibrahim"), new Date("31-9-2010"), new Income("437.58"),
                new Expense("436.58"),
                getTagSet("classmates")),
            new Record(new Name("Roy Balakrishnan"), new Date("15-7-2005"), new Income("24.50"),
                new Expense("23.50"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Record sampleRecord : getSampleRecords()) {
            sampleAb.addRecord(sampleRecord);
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
