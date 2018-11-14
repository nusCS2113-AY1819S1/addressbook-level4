package seedu.address.model.util;

import static seedu.address.model.util.SampleDataUtil.getTagSet;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.DistributorBook;
import seedu.address.model.ReadOnlyDistributorBook;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.distributor.DistributorPhone;
import seedu.address.model.distributor.DistributorProduct;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code DistributorBook} with sample data.
 */
public class SampleDistributorsUtil {

    public static Distributor[] getSampleDistributors() {
        return new Distributor[] {
            new Distributor(new DistributorName("Farm"), new DistributorPhone("87438807"),
                    getProdSet("Apple"), getTagSet("fruits")),
            new Distributor(new DistributorName("Butcher"), new DistributorPhone("99272758"),
                    getProdSet("Beef"), getTagSet("meat")),
            new Distributor(new DistributorName("Snack Shack"), new DistributorPhone("93210283"),
                    getProdSet("Cheezles"), getTagSet("snacks")),
            new Distributor(new DistributorName("Pet Store"), new DistributorPhone("91031282"),
                    getProdSet("Dog Biscuits"), getTagSet("pets")),
            new Distributor(new DistributorName("Toiletries"), new DistributorPhone("92492021"),
                    getProdSet("Toilet Paper"), getTagSet("toiletries")),
            new Distributor(new DistributorName("Ben Harrys"), new DistributorPhone("92624417"),
                    getProdSet("Ice Cream"), getTagSet("dessert"))
        };
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
    public static Set<DistributorProduct> getProdSet(String... strings) {
        return Arrays.stream(strings)
                .map(DistributorProduct::new)
                .collect(Collectors.toSet());
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
