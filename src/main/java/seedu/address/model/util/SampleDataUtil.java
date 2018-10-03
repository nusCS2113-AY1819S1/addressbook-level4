package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
//import seedu.address.model.person.Address;
//import seedu.address.model.person.Email;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.distributor.DistributorPhone;
//import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Distributor[] getSamplePersons() {
        return new Distributor[] {
            new Distributor(new DistributorName("Alex Yeoh"), new DistributorPhone("87438807")),
            new Distributor(new DistributorName("Bernice Yu"), new DistributorPhone("99272758")),
            new Distributor(new DistributorName("Charlotte Oliveiro"), new DistributorPhone("93210283")),
            new Distributor(new DistributorName("David Li"), new DistributorPhone("91031282")),
            new Distributor(new DistributorName("Irfan Ibrahim"), new DistributorPhone("92492021")),
            new Distributor(new DistributorName("Roy Balakrishnan"), new DistributorPhone("92624417"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Distributor samplePerson : getSamplePersons()) {
            sampleAb.addDistributor(samplePerson);
        }
        return sampleAb;
    }

    /*
    /**
     * Returns a tag set containing the list of strings given.

    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }*/

}
