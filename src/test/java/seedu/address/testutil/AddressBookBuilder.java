package seedu.address.testutil;

import seedu.address.model.ProductDatabase;
import seedu.address.model.product.Product;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ProductDatabase ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private ProductDatabase productDatabase;

    public AddressBookBuilder() {
        productDatabase = new ProductDatabase();
    }

    public AddressBookBuilder(ProductDatabase productDatabase) {
        this.productDatabase = productDatabase;
    }

    /**
     * Adds a new {@code Product} to the {@code ProductDatabase} that we are building.
     */
    public AddressBookBuilder withPerson(Product product) {
        productDatabase.addPerson(product);
        return this;
    }

    public ProductDatabase build() {
        return productDatabase;
    }
}
