package seedu.address.model;

import java.util.ArrayList;
import java.util.List;


/**
 * {@code TaskBook} that keeps track of its own history.
 */
public class VersionedTaskBook extends TaskBook {
    private final List<ReadOnlyTaskBook> taskBookStateList;
    private int currentStatePointer;

    public VersionedTaskBook(ReadOnlyTaskBook initialState) {
        super(initialState);

        taskBookStateList = new ArrayList<>();
        taskBookStateList.add(new TaskBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code TaskBook} state at the end of the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        taskBookStateList.add(new TaskBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        taskBookStateList.subList(currentStatePointer + 1, taskBookStateList.size()).clear();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedTaskBook)) {
            return false;
        }

        VersionedTaskBook otherVersionedTaskBook = (VersionedTaskBook) other;

        // state check
        return super.equals(otherVersionedTaskBook)
                && taskBookStateList.equals(otherVersionedTaskBook.taskBookStateList)
                && currentStatePointer == otherVersionedTaskBook.currentStatePointer;
    }
}
