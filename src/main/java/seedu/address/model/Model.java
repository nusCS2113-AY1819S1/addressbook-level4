package seedu.address.model;

import seedu.address.model.person.Email;

/**
 * The API of the Model component.
 */
public interface Model extends PersonModel, EventModel {

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData, ReadOnlyEventList newEventList);

    /** Returns true if there are available undo states. */
    boolean canUndo();

    /** Undoes previous state. */
    void undo();

    /** Returns true if there are available redo states. */
    boolean canRedo();

    /** Redoes next state. */
    void redo();

    /** Undoes a resetData command */
    void undoBothState();

    /** Redoes a resetData command */
    void redoBothState();

    /** check if the email is present */
    boolean hasEmail(Email email);
}
