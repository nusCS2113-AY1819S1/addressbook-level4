package seedu.address.testutil;

import seedu.address.model.DistributorBook;
import seedu.address.model.distributor.Distributor;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ProductDatabase ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class DistributorBookBuilder {

    private DistributorBook distributorBook;

    public DistributorBookBuilder() {
        distributorBook = new DistributorBook();
    }

    public DistributorBookBuilder(DistributorBook distributorBook) {
        this.distributorBook = distributorBook;
    }

    /**
     * Adds a new {@code Distributor} to the {@code DistributorBook} that we are building.
     */
    public DistributorBookBuilder withPerson(Distributor distributor) {
        distributorBook.addDistributor(distributor);
        return this;
    }

    public DistributorBook build() {
        return distributorBook;
    }
}
