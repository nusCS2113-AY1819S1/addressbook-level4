package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.distributor.UniqueDistributorList;
import seedu.address.model.product.Product;
import seedu.address.model.product.UniquePersonList;
import seedu.address.model.saleshistory.SalesHistory;
import seedu.address.model.timeidentifiedclass.Reminder;
import seedu.address.model.timeidentifiedclass.TimeIdentifiedClass;
import seedu.address.model.timeidentifiedclass.Transaction;
import seedu.address.model.timeidentifiedclass.exceptions.DuplicateReminderException;
import seedu.address.model.timeidentifiedclass.exceptions.DuplicateTransactionException;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class ProductDatabase implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final SalesHistory salesHistory;
    private Transaction lastTransaction;
    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */

    {
        persons = new UniquePersonList();
        salesHistory = new SalesHistory();
        lastTransaction = null;
    }

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

    public ProductDatabase() {}

    /**
     * Creates an ProductDatabase using the Persons in the {@code toBeCopied}
     */
    public ProductDatabase(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations


    /**
     * Replaces the contents of the product list with {@code products}.
     * {@code products} must not contain duplicate products.
     */
    public void setPersons(List<Product> products) {
        this.persons.setPersons(products);
    }


    /**
     * Replaces the contents of the product list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setDistributors(List<Distributor> distributors) {
        this.distributors.setDistributors(distributors);
    }


    /**
     * Resets the existing data of this {@code ProductDatabase} with {@code newData}.
    */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setDistributors(newData.getDistributorList());
    }

    //// product-level operations


    /**
     * Returns true if a product with the same identity as {@code product} exists in the address book.
     */
    public boolean hasPerson(Product product) {
        requireNonNull(product);
        return persons.contains(product);
    }


    /**
     * Returns true if a product with the same identity as {@code product} exists in the address book.
     */
    public boolean hasDistributor(Distributor distributor) {
        requireNonNull(distributor);
        return distributors.contains(distributor);
    }


    /**
     * Adds a product to the address book.
     * The product must not already exist in the address book.
     */
    public void addPerson(Product p) {
        persons.add(p);
    }


    /**
     * Adds a distributor to the address book.
     * The distributor must not already exist in the address book.
     */
    public void addDistributor(Distributor d) {
        distributors.add(d);
    }



    /**
     * Replaces the given product {@code target} in the list with {@code editedProduct}.
     * {@code target} must exist in the address book.
 product identity of {@code editedProduct} must not be the same as another existing product in the address book.
     */
    public void updatePerson(Product target, Product editedProduct) {
        requireNonNull(editedProduct);
        persons.setPerson(target, editedProduct);
    }


    /**
     * Replaces the given distrbutor {@code target} in the list with {@code editedDistributor}.
     * {@code target} must exist in the address book.
     * The distributor identity of {@code editedDistributor} must not be the same as another existing distributor
     * in the Inventarie.
     */
    public void updateDistributor(Distributor target, Distributor editedDistributor) {
        requireNonNull(editedDistributor);

        distributors.setDistributor(target, editedDistributor);
    }

    /**
     * Getter for {@code salesHistory}
     */
    public SalesHistory getSalesHistory() {
        return salesHistory;
    }

    /**
     * Adds a transaction to the active {@code salesHistory}.
     * @param transaction
     * @throws InvalidTimeFormatException
     * @throws DuplicateTransactionException
     */
    public void addTransaction(Transaction transaction) throws InvalidTimeFormatException,
            DuplicateTransactionException {
        try {
            salesHistory.addTransaction(transaction);
        } catch (InvalidTimeFormatException e) {
            throw e;
        } catch (DuplicateTransactionException e) {
            throw e;
        }
        lastTransaction = transaction;
    }

    public String getDaysTransactions(String day) throws InvalidTimeFormatException {
        ArrayList<Transaction> daysTransactions;
        try {
            daysTransactions = salesHistory.getDaysTransactions(day);
        } catch (InvalidTimeFormatException e) {
            throw e;
        }
        if (daysTransactions == null || daysTransactions.isEmpty()) {
            return "No transactions found on the specified date!";
        }

        StringBuilder ret = new StringBuilder();
        ret.append("TIMINGS FOR TRANSACTIONS ON " + day + "\n");
        for (Transaction transaction : daysTransactions) {
            ret.append(transaction.getTransactionTime() + "\n");
        }

        return ret.toString();
    }

    public Transaction getLastTransaction() {
        return lastTransaction;
    }

    /**
     * This method adds a reminder to the {@code salesHistory}.
     * @param reminder
     * @throws InvalidTimeFormatException
     * @throws DuplicateReminderException
     */
    public void addReminder(Reminder reminder) throws InvalidTimeFormatException, DuplicateReminderException {
        try {
            salesHistory.addReminder(reminder);
        } catch (InvalidTimeFormatException e) {
            throw e;
        } catch (DuplicateReminderException e) {
            throw e;
        }
    }

    /**
     * Removes a reminder from the {@code salesHistory}.
     * @param reminderTime
     * @throws InvalidTimeFormatException
     * @throws NoSuchElementException
     */
    public void removeReminder(String reminderTime) throws InvalidTimeFormatException, NoSuchElementException {
        try {
            salesHistory.removeReminder(reminderTime);
        } catch (InvalidTimeFormatException e) {
            throw e;
        } catch (NoSuchElementException e) {
            throw e;
        }
    }

    /**
     * Returns the reminders which are due in the active day.
     * @return reminder list.
     */
    public ArrayList<Reminder> getOverdueReminders() {
        final String currentTime = TimeIdentifiedClass.getCurrentDateAndTime();

        Set reminderSet = salesHistory.getReminderRecord().entrySet();
        Iterator it = reminderSet.iterator();

        ArrayList<Reminder> remindersToReturn = new ArrayList<>();

        // set to true in order to enter subsequent while-loop
        boolean isLesserTime = true;

        while (it.hasNext() && isLesserTime) {
            Map.Entry reminderEntry = (Map.Entry) it.next();
            String reminderTime = (String) reminderEntry.getKey();
            Reminder reminderToAdd = (Reminder) reminderEntry.getValue();

            // checking if reminder time is lesser than current time
            isLesserTime = (reminderTime.compareTo(currentTime) <= 0);
            if (isLesserTime) {
                remindersToReturn.add(reminderToAdd);
            }
        }
        return remindersToReturn;
    }

    /**
     * Returns the reminders which are due and have not been shown by the thread, and declares them as shown by the
     * thread.
     * @return reminder list.
     */
    public ArrayList<Reminder> getOverDueRemindersForThread() {
        final String currentTime = TimeIdentifiedClass.getCurrentDateAndTime();

        Set reminderSet = salesHistory.getReminderRecord().entrySet();
        Iterator it = reminderSet.iterator();

        ArrayList<Reminder> remindersToReturn = new ArrayList<>();

        // set to true to enter the following while block
        boolean isLesserTime = true;

        while (it.hasNext() && isLesserTime) {
            Map.Entry entry = (Map.Entry) it.next();
            String reminderTime = (String) entry.getKey();
            Reminder reminderToAdd = (Reminder) entry.getValue();

            // true if reminder time is lesser than or equal to the current time.
            isLesserTime = (reminderTime.compareTo(currentTime) <= 0);

            if (isLesserTime && !reminderToAdd.hasBeenShownByThread()) {
                remindersToReturn.add(reminderToAdd);
                reminderToAdd.declareAsShownByThread();
            }
        }
        return remindersToReturn;
    }

    /**
     * Removes {@code key} from this {@code ProductDatabase}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Product key) {
        persons.remove(key);
    }


    /**
     * Removes {@code key} from this {@code ProductDatabase}.
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

    @Override
    public ObservableList<Product> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Distributor> getDistributorList() {
        return distributors.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProductDatabase // instanceof handles nulls
                && distributors.equals(((ProductDatabase) other).distributors));
    }

    @Override
    public int hashCode() {
        return distributors.hashCode();
    }
}
