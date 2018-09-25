package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code AddressBook} that keeps track of its own history.
 */
public class VersionedLoginBook extends LoginBook {

    public VersionedLoginBook(ReadOnlyLoginBook initialState) {
        super(initialState);

        List<ReadOnlyLoginBook> loginBookStateList = new ArrayList<>();
        loginBookStateList.add(new LoginBook(initialState));
    }
}
