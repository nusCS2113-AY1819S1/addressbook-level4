package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ProductDatabase;
import seedu.address.model.DistributorBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyDistributorBook;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.distributor.DistributorPhone;
import seedu.address.model.product.Address;
import seedu.address.model.product.Name;
import seedu.address.model.product.Product;
import seedu.address.model.product.SerialNumber;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ProductDatabase} with sample data.
 */
public class SampleDataUtil {

    public static Distributor[] getSampleDistributors() {
        return new Distributor[] {
            new Distributor(new DistributorName("Alex Yeoh"), new DistributorPhone("87438807")),
            new Distributor(new DistributorName("Bernice Yu"), new DistributorPhone("99272758")),
            new Distributor(new DistributorName("Charlotte Oliveiro"), new DistributorPhone("93210283")),
            new Distributor(new DistributorName("David Li"), new DistributorPhone("91031282")),
            new Distributor(new DistributorName("Irfan Ibrahim"), new DistributorPhone("92492021")),
            new Distributor(new DistributorName("Roy Balakrishnan"), new DistributorPhone("92624417"))
        };
    }


    public static Product[] getSamplePersons() {
        return new Product[] {
            new Product(new Name("Alex Yeoh"), new SerialNumber("87438807"), new DistributorName("Alexa"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Product(new Name("Bernice Yu"), new SerialNumber("99272758"), new DistributorName("Bernetta"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Product(new Name("Charlotte Oliveiro"), new SerialNumber("93210283"), new DistributorName("Charlie"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Product(new Name("David Li"), new SerialNumber("91031282"), new DistributorName("Davis"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Product(new Name("Irfan Ibrahim"), new SerialNumber("92492021"), new DistributorName("Irfie"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Product(new Name("Roy Balakrishnan"), new SerialNumber("92624417"), new DistributorName("Royal"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))

        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        ProductDatabase sampleAb = new ProductDatabase();

        for (Product sampleProduct : getSamplePersons()) {
            sampleAb.addPerson(sampleProduct);
        }
        return sampleAb;
    }

    public static ReadOnlyDistributorBook getSampleDistributorBook() {
        DistributorBook sampleDb = new DistributorBook();

        for (Distributor sampleDistributor : getSampleDistributors()) {
            sampleDb.addDistributor(sampleDistributor);
        }

        return sampleDb;
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
