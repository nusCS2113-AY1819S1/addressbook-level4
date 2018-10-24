package seedu.address.model;

import javafx.collections.ObservableList;

import seedu.address.model.distributor.Distributor;

import seedu.address.model.product.Product;


/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    ObservableList<Product> getPersonList();

}
