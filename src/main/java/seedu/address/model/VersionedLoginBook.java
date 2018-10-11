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
}
