package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ProductDatabase;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.product.Address;
import seedu.address.model.product.Name;
import seedu.address.model.product.Product;
import seedu.address.model.product.SerialNumber;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ProductDatabase} with sample data.
 */
public class SampleDataUtil {


    public static Product[] getSamplePersons() {
        return new Product[] {
            new Product(new Name("Apple"), new SerialNumber("87807"),
                    new DistributorName("Farm"), new Address("fruit"),
                getTagSet("friends")),
            new Product(new Name("Beef"), new SerialNumber("92758"),
                    new DistributorName("Butcher"), new Address("meat"),
                getTagSet("red meat")),
            new Product(new Name("Cheezles"), new SerialNumber("90283"),
                    new DistributorName("Snack Shack"), new Address("snack"),
                getTagSet("unhealthy")),
            new Product(new Name("Dog Biscuits"), new SerialNumber("91282"),
                    new DistributorName("Pet Store"), new Address("pets"),
                getTagSet("food")),
            new Product(new Name("Toilet Paper"), new SerialNumber("92021"),
                    new DistributorName("Toiletries"), new Address("toilet"),
                getTagSet("toilet")),
            new Product(new Name("Ice Cream"), new SerialNumber("92617"),
                    new DistributorName("Ben & Harry's"), new Address("snack"),
                getTagSet("cold"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        ProductDatabase sampleAb = new ProductDatabase();

        for (Product sampleProduct : getSamplePersons()) {
            sampleAb.addPerson(sampleProduct);
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
