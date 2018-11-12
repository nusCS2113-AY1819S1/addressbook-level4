package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.todo.Todo;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_TODO = "Todo tasks list contains duplicate todo task(s).";
    public static final String MESSAGE_DUPLICATE_REMINDER = "Reminders list contains duplicate reminder(s).";

    @XmlElement
    private List<XmlAdaptedPerson> persons;

    @XmlElement
    private List<XmlAdaptedTodo> todoTasks;

    @XmlElement
    private List<XmlAdaptedReminder> reminders;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        persons = new ArrayList<>();
        todoTasks = new ArrayList<>();
        reminders = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        todoTasks.addAll(src.getTodoList().stream().map(XmlAdaptedTodo::new).collect(Collectors.toList()));
        reminders.addAll(src.getReminderList().stream().map(XmlAdaptedReminder::new).collect(Collectors.toList()));
    }

    /**
     * Conversion with filtered Persons instead of the whole address book.
     */
    public XmlSerializableAddressBook(ObservableList<Person> filteredPersons) {
        this();
        persons.addAll(filteredPersons.stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson}.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (XmlAdaptedPerson p : persons) {
            Person person = p.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }

        for (XmlAdaptedTodo td : todoTasks) {
            Todo todoTask = td.toModelType();
            if (addressBook.hasTodo(todoTask)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TODO);
            }
            addressBook.addTodo(todoTask);
        }

        for (XmlAdaptedReminder rm : reminders) {
            Reminder reminder = rm.toModelType();
            if (addressBook.hasReminder(reminder)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_REMINDER);
            }
        }

        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAddressBook)) {
            return false;
        }
        return persons.equals(((XmlSerializableAddressBook) other).persons)
                && todoTasks.equals(((XmlSerializableAddressBook) other).todoTasks)
                && reminders.equals(((XmlSerializableAddressBook) other).reminders);
    }
}
