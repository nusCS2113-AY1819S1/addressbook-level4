package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
//import seedu.address.model.person.Person;
//import seedu.address.model.person.UniquePersonList;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.distributor.UniqueDistributorList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    /*private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.

    {
        persons = new UniquePersonList();
    }*/

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

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /*
    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.

    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }
    */

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setDistributors(List<Distributor> distributors) {
        this.distributors.setDistributors(distributors);
    }

    /*
    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.

    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }
    */

    //// person-level operations

    /*
    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.

    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }
    */

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasDistributor(Distributor distributor) {
        requireNonNull(distributor);
        return distributors.contains(distributor);
    }

    /*
    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.

    public void addPerson(Person p) {
        persons.add(p);
    }
    */

    /**
     * Adds a distributor to the address book.
     * The distributor must not already exist in the address book.
     */
    public void addDistributor(Distributor d) {
        distributors.add(d);
    }


    /*
    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.

    public void updatePerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }
    */

    /**
     * Replaces the given distrbutor {@code target} in the list with {@code editedDistributor}.
     * {@code target} must exist in the address book.
     * The distributor identity of {@code editedDistributor} must not be the same as another existing distributor in the Inventarie.
     */
    public void updateDistributor(Distributor target, Distributor editedDistributor) {
        requireNonNull(editedDistributor);

        distributors.setDistributor(target, editedDistributor);
    }

    /*
    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.

    public void removePerson(Person key) {
        persons.remove(key);
    }
    */

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
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

    /*@Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }
    */

    @Override
    public ObservableList<Distributor> getDistributorList() {
        return distributors.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && distributors.equals(((AddressBook) other).distributors));
    }

    @Override
    public int hashCode() {
        return distributors.hashCode();
    }
}
