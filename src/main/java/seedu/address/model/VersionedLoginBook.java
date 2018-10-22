package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code LoginBook} that keeps track of its own history.
 */
public class VersionedLoginBook extends LoginBook {

    private final List<ReadOnlyLoginBook> loginBookStateList;
    private int currentStatePointer;

    public VersionedLoginBook(ReadOnlyLoginBook initialState) {
        super(initialState);

        loginBookStateList = new ArrayList<>();
        loginBookStateList.add(new LoginBook(initialState));
        currentStatePointer = 0;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedLoginBook)) {
            return false;
        }

        VersionedLoginBook otherVersionedLoginBook = (VersionedLoginBook) other;

        // state check
        return super.equals(otherVersionedLoginBook)
                && loginBookStateList.equals(otherVersionedLoginBook.loginBookStateList)
                && currentStatePointer == otherVersionedLoginBook.currentStatePointer;
    }
}
