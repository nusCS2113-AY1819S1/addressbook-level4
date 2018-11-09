package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.model.product.Product;
import seedu.address.model.product.UniqueProductList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameProduct comparison)
 */
public class ProductDatabase implements ReadOnlyProductDatabase {

    private final UniqueProductList products;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */

    {
        products = new UniqueProductList();
    }

    public ProductDatabase() {}

    /**
     * Creates an ProductDatabase using the products in the {@code toBeCopied}
     */
    public ProductDatabase(ReadOnlyProductDatabase toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations


    /**
     * Replaces the contents of the product list with {@code products}.
     * {@code products} must not contain duplicate products.
     */
    public void setProducts(List<Product> products) {
        this.products.setProducts(products);
    }

    //// product-level operations

    /**
     * Resets the existing data of this {@code ProductDatabase} with {@code newData}.
     */
    public void resetData(ReadOnlyProductDatabase newData) {
        requireNonNull(newData);
        setProducts(newData.getProductList());
    }

    /**
     * Returns true if a product with the same identity as {@code product} exists in the product database.
     */
    public boolean hasProduct(Product product) {
        requireNonNull(product);
        return products.contains(product);
    }

    /**
     * Returns true if a product with the same name as {@code product} exists in the Product database.
     */
    public boolean hasProductName(String name) {
        for (Product product : products) {
            System.out.print(product.getName().fullName);
            if (product.getName().fullName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a product to the address book.
     * The product must not already exist in the address book.
     */
    public void addProduct(Product p) {
        products.add(p);
    }

    /**
     * Replaces the given product {@code target} in the list with {@code editedProduct}.
     * {@code target} must exist in the address book.
     * product identity of {@code editedProduct} must not be the same as another existing product in the address book.
     */
    public void updateProducts(Product target, Product editedProduct) {
        requireNonNull(editedProduct);
        products.setProduct(target, editedProduct);
    }

    /**
     * Removes {@code key} from this {@code ProductDatabase}.
     * {@code key} must exist in the address book.
     */
    public void removeProduct(Product key) {
        products.remove(key);
    }
    //// util methods

    @Override
    public String toString() {
        return products.asUnmodifiableObservableList().size() + " products";
        // TODO: refine later
    }

    @Override
    public ObservableList<Product> getProductList() {
        return products.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProductDatabase // instanceof handles nulls
                && products.equals(((ProductDatabase) other).products));

    }

    @Override
    public int hashCode() {
        return products.hashCode();
    }
}
