package seedu.address.model;

import java.util.LinkedList;


/**
 * {@code LinkedList} subclass to keep track of AddressBook and EventList changes for undo and redo commands
 */
public class StateHistoryList extends LinkedList<Integer> {

    static final int STATE_ADDRESSBOOK = 0;
    static final int STATE_EVENTLIST = 1;
    static final int STATE_RESET = 2;
    static final int STATE_NONE = -1;

    private int pointer;

    StateHistoryList() {
        super();
        this.pointer = -1;
    }

    private void removeFutureStates() {
        subList(pointer + 1, size()).clear();
    }

    /**
     * Adds a new AddressBook state
     */
    public void addAddressBookState() {
        addState(STATE_ADDRESSBOOK);
    }

    /**
     * Adds a new EventList state to the history list.
     */
    public void addEventListState() {
        addState(STATE_EVENTLIST);
    }

    /**
     * Adds a new ResetData state to the history list.
     */
    public void addResetState() {
        addState(STATE_RESET);
    }

    /**
     * Adds a new state to the history list. Undone states in front of the current pointer will be removed.
     *
     * @param state
     */
    private void addState(Integer state) {
        // Future states exist
        if (pointer < size() - 1 && pointer >= 0) {
            removeFutureStates();
        }
        add(state);
        pointer++;
    }

    /**
     * Returns the current changed state type at the current pointer position.
     */
    public Integer getCurrentState() {
        if (size() == 0) {
            return STATE_NONE;
        }
        if (pointer == -1) {
            return STATE_NONE;
        }
        return get(pointer);
    }

    /**
     * Returns the current changed state type at the current pointer position.
     */
    public Integer getNextState() {
        if (size() == 0) {
            return STATE_NONE;
        }
        // Has states ahead of current pointer
        if (pointer > size() - 2) {
            return STATE_NONE;
        }
        return get(pointer + 1);
    }

    /**
     * Increment pointer position via {@code redo}
     */
    public void incrementPointer() {
        if (pointer >= size() - 1) {
            throw new IndexOutOfBoundsException("No more states to redo!");
        }
        pointer++;
    }

    /**
     * Increment pointer position via {@code redo}
     */
    public void decrementPointer() {
        if (pointer < 0) {
            throw new IndexOutOfBoundsException("No more states to undo!");
        }
        pointer--;
    }
}
