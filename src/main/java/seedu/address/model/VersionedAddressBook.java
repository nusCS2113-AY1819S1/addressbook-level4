package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code AddressBook} that keeps track of its own history.
 */
public class VersionedAddressBook extends AddressBook {

    private final List<ReadOnlyAddressBook> addressBookStateList;
    private int currentStatePointer;

    public VersionedAddressBook(ReadOnlyAddressBook initialState) {
        super(initialState);

        addressBookStateList = new ArrayList<>();
        addressBookStateList.add(new AddressBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code AddressBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        addressBookStateList.add(new AddressBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        addressBookStateList.subList(currentStatePointer + 1, addressBookStateList.size()).clear();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedAddressBook)) {
            return false;
        }

        VersionedAddressBook otherVersionedAddressBook = (VersionedAddressBook) other;

        // state check
        return super.equals(otherVersionedAddressBook)
                && addressBookStateList.equals(otherVersionedAddressBook.addressBookStateList)
                && currentStatePointer == otherVersionedAddressBook.currentStatePointer;
    }
}
