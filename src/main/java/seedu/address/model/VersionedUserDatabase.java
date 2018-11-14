package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ProductDatabase} that keeps track of its own history.
 */
public class VersionedUserDatabase extends UserDatabase {

    private final List<ReadOnlyUserDatabase> userDatabasesStateList;
    private int currentStatePointer;

    public VersionedUserDatabase(ReadOnlyUserDatabase initialState) {
        super(initialState);

        userDatabasesStateList = new ArrayList<>();
        userDatabasesStateList.add(new UserDatabase(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code ProductDatabase} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        userDatabasesStateList.add(new UserDatabase(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        userDatabasesStateList.subList(currentStatePointer + 1, userDatabasesStateList.size()).clear();
    }
}
