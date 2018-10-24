package seedu.address.model.util;

import seedu.address.model.DistributorBook;
import seedu.address.model.ReadOnlyDistributorBook;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.distributor.DistributorPhone;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Contains utility methods for populating {@code DistributorBook} with sample data.
 */
public class SampleDistributorsUtil {

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

    public static ReadOnlyDistributorBook getSampleDistributorBook() {
        DistributorBook sampleDb = new DistributorBook();

        for (Distributor sampleDistributor : getSampleDistributors()) {
            sampleDb.addDistributor(sampleDistributor);
        }

        return sampleDb;
    }

}
