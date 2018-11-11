package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ProductDatabase;
import seedu.address.model.ReadOnlyProductDatabase;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.product.Name;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductInfo;
import seedu.address.model.product.RemainingItems;
import seedu.address.model.product.SerialNumber;
import seedu.address.model.saleshistory.ReadOnlySalesHistory;
import seedu.address.model.saleshistory.SalesHistory;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ProductDatabase} with sample data.
 */
public class SampleDataUtil {


    public static Product[] getSampleProducts() {
        return new Product[] {
            new Product(new Name("Apple"), new SerialNumber("001"),
                    new DistributorName("Farm"), new ProductInfo("fruit"), new RemainingItems("12"),
                    getTagSet("healthy")),
            new Product(new Name("Beef"), new SerialNumber("002"),
                    new DistributorName("Butcher"), new ProductInfo("meat"), new RemainingItems("12"),
                    getTagSet("redmeat")),
            new Product(new Name("Cheezles"), new SerialNumber("003"),
                    new DistributorName("Snack Shack"), new ProductInfo("snack"),
                    new RemainingItems("12"), getTagSet("unhealthy")),
            new Product(new Name("Dog Biscuits"), new SerialNumber("004"),
                    new DistributorName("Pet Store"), new ProductInfo("pets"),
                    new RemainingItems("12"), getTagSet("food")),
            new Product(new Name("Toilet Paper"), new SerialNumber("005"),
                    new DistributorName("Toiletries"), new ProductInfo("toilet"),
                    new RemainingItems("12"), getTagSet("tolet")),
            new Product(new Name("Ice Cream"), new SerialNumber("006"),
                    new DistributorName("Ben Harrys"), new ProductInfo("snack"),
                    new RemainingItems("12"), getTagSet("cold"))
        };
    }

    public static ReadOnlyProductDatabase getSampleProductDatabase() {
        ProductDatabase sampleAb = new ProductDatabase();

        for (Product sampleProduct : getSampleProducts()) {
            sampleAb.addProduct(sampleProduct);
        }
        return sampleAb;
    }

    public static ProductDatabase getSampleProductDatabaseForTest() {
        ProductDatabase sampleAb = new ProductDatabase();
        
        for (Product sampleProduct : getSampleProducts()) {
            sampleAb.addProduct(sampleProduct);
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
