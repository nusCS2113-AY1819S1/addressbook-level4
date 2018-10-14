package seedu.address.model;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model extends PersonModel, EventModel {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData, ReadOnlyEventList newEventList);

}
