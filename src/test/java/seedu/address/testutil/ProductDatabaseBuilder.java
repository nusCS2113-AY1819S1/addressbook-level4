package seedu.address.testutil;

import seedu.address.model.ProductDatabase;
import seedu.address.model.product.Product;

//@@author Gara
/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ProductDatabase ab = new ProductDatabaseBuilder().withProduct("John", "Doe").build();}
 */
public class ProductDatabaseBuilder {

    private ProductDatabase productDatabase;

    public ProductDatabaseBuilder() {
        productDatabase = new ProductDatabase();
    }

    public ProductDatabaseBuilder(ProductDatabase productDatabase) {
        this.productDatabase = productDatabase;
    }

    /**
     * Adds a new {@code Product} to the {@code ProductDatabase} that we are building.
     */
    public ProductDatabaseBuilder withProduct(Product product) {
        productDatabase.addProduct(product);
        return this;
    }

    public ProductDatabase build() {
        return productDatabase;
    }
}
