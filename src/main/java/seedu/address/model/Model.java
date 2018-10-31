package seedu.address.model;

/**
 * The API of the Model component.
 */
public interface Model extends PersonModel, EventModel {

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData, ReadOnlyEventList newEventList);

}
