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
import seedu.address.model.saleshistory.ReadOnlySalesHistory;
import seedu.address.model.saleshistory.SalesHistory;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ProductDatabase} with sample data.
 */
public class SampleDataUtil {


    public static Product[] getSamplePersons() {
        return new Product[] {
            new Product(new Name("Apple"), new SerialNumber("001"),
                    new DistributorName("Farm"), new Address("fruit"),
                getTagSet("healthy")),
            new Product(new Name("Beef"), new SerialNumber("002"),
                    new DistributorName("Butcher"), new Address("meat"),
                getTagSet("redmeat")),
            new Product(new Name("Cheezles"), new SerialNumber("003"),
                    new DistributorName("Snack Shack"), new Address("snack"),
                getTagSet("unhealthy")),
            new Product(new Name("Dog Biscuits"), new SerialNumber("004"),
                    new DistributorName("Pet Store"), new Address("pets"),
                getTagSet("food")),
            new Product(new Name("Toilet Paper"), new SerialNumber("005"),
                    new DistributorName("Toiletries"), new Address("toilet"),
                getTagSet("tolet")),
            new Product(new Name("Ice Cream"), new SerialNumber("006"),
                    new DistributorName("Ben Harrys"), new Address("snack"),
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

    public static ReadOnlySalesHistory getSampleSalesHistory() {
        return new SalesHistory();
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
