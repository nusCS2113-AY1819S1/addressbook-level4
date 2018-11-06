package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.distributor.Distributor;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.distributor.DistributorPhone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDistributorsUtil;

/**
 * A utility class to help with building Distributor objects.
 */
public class DistributorBuilder {

    public static final String DEFAULT_NAME = "Ah Bee";
    public static final String DEFAULT_PHONE = "00000000";


    private DistributorName name;
    private DistributorPhone phone;
    private Set<Tag> tags;


    public DistributorBuilder() {
        name = new DistributorName(DEFAULT_NAME);
        phone = new DistributorPhone(DEFAULT_PHONE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the DistributorBuilder with the data of {@code distributorToCopy}.
     */
    public DistributorBuilder(Distributor distributorToCopy) {
        name = distributorToCopy.getDistName();
        phone = distributorToCopy.getDistPhone();
        tags = new HashSet<>(distributorToCopy.getTags());
    }

    /**
     * Sets the {@code DistributorName} of the {@code Distributor} that we are building.
     */
    public DistributorBuilder withName(String name) {
        this.name = new DistributorName(name);
        return this;
    }


    /**
     * Sets the {@code DistributorPhone} of the {@code Distributor} that we are building.
     */
    public DistributorBuilder withPhone(String phone) {
        this.phone = new DistributorPhone(phone);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Product} that we are building.
     */
    public DistributorBuilder withTags(String ... tags) {
        this.tags = SampleDistributorsUtil.getTagSet(tags);
        return this;
    }


    public Distributor build() {
        return new Distributor(name, phone, tags);
    }

}
