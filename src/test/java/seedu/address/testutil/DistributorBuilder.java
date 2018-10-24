package seedu.address.testutil;

import seedu.address.model.distributor.Distributor;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.distributor.DistributorPhone;

/**
 * A utility class to help with building Product objects.
 */
public class DistributorBuilder {

    public static final String DEFAULT_NAME = "Ah Bee Distributors";
    public static final String DEFAULT_SERIAL_NUMBER = "85355255";


    private DistributorName name;
    private DistributorPhone phone;


    public DistributorBuilder() {
        name = new DistributorName(DEFAULT_NAME);
        phone = new DistributorPhone(DEFAULT_SERIAL_NUMBER);

    }

    /**
     * Initializes the DistributorBuilder with the data of {@code distributorToCopy}.
     */
    public DistributorBuilder(Distributor distributorToCopy) {
        name = distributorToCopy.getDistName();
        phone = distributorToCopy.getDistPhone();
    }

    /**
     * Sets the {@code Name} of the {@code Distributor} that we are building.
     */
    public DistributorBuilder withName(String name) {
        this.name = new DistributorName(name);
        return this;
    }


    /**
     * Sets the {@code SerialNumber} of the {@code Distributor} that we are building.
     */
    public DistributorBuilder withPhone(String phone) {
        this.phone = new DistributorPhone(phone);
        return this;
    }


    public Distributor build() {
        return new Distributor(name, phone);
    }

}
