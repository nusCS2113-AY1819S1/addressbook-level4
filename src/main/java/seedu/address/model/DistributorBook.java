package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.model.distributor.Distributor;
import seedu.address.model.distributor.UniqueDistributorList;

/**
 * Wraps all data at the distributor-book level
 * Duplicates are not allowed (by .isSameProduct comparison)
 */
public class DistributorBook implements ReadOnlyDistributorBook {

    private final UniqueDistributorList distributors;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        distributors = new UniqueDistributorList();
    }

    public DistributorBook() {}

    /**
     * Creates an DistributorBook using the Distirbutors in the {@code toBeCopied}
     */
    public DistributorBook(ReadOnlyDistributorBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the product data base with {@code products}.
     * {@code Distributor} must not contain duplicate Distributors.
     */
    public void setDistributors(List<Distributor> distributors) {
        this.distributors.setDistributors(distributors);
    }

    /**
     * Resets the existing data of this {@code DistributorBook} with {@code newData}.
    */
    public void resetData(ReadOnlyDistributorBook newData) {
        requireNonNull(newData);
        setDistributors(newData.getDistributorList());
    }

    //// product-level operations

    /**
     * Returns true if a distributor with the same identity as {@code distributor} exists in the distributor book.
     */
    public boolean hasDistributor(Distributor distributor) {
        requireNonNull(distributor);
        return distributors.contains(distributor);
    }

    /**
     * Returns true if a distributor with the same name as {@code distributor} exists in the distributor book.
     */
    public boolean hasDistributorName(Distributor distributor) {
        requireNonNull(distributor);
        return distributors.containsName(distributor);
    }

    /**
     * Returns true if a distributor with the same phone as {@code distributor} exists in the distributor book.
     */
    public boolean hasDistributorPhone(Distributor distributor) {
        requireNonNull(distributor);
        return distributors.containsPhone(distributor);
    }

    /**
     * Adds a distributor to the address book.
     * The distributor must not already exist in the distributor book.
     */
    public void addDistributor(Distributor d) {
        distributors.add(d);
    }

    /**
     * Replaces the given distrbutor {@code target} in the list with {@code editedDistributor}.
     * {@code target} must exist in the distributor book.
     * The distributor identity of {@code editedDistributor} must not be the same as another existing distributor
     * in the Inventarie.
     */
    public void updateDistributor(Distributor target, Distributor editedDistributor) {
        requireNonNull(editedDistributor);
        distributors.setDistributor(target, editedDistributor);
    }

    /**
     * Removes {@code key} from this {@code DistributorBook}.
     * {@code key} must exist in the distributor book.
     */
    public void removeDistributor(Distributor key) {
        distributors.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return distributors.asUnmodifiableObservableList().size() + " distributors";
        // TODO: refine later
    }

    @Override
    public ObservableList<Distributor> getDistributorList() {
        return distributors.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DistributorBook // instanceof handles nulls
                && distributors.equals(((DistributorBook) other).distributors));
    }

    @Override
    public int hashCode() {
        return distributors.hashCode();
    }
}
