package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;

import seedu.address.model.User;
import seedu.address.model.person.Person;
import seedu.address.model.person.TimeTable;
import seedu.address.testutil.TypicalPersons;

/**
 * A Model stub explicitly for import and export command testing.
 */
public class ModelStubImportExportCommand extends ModelStub {
    private Person person;
    private User user;
    private final TimeTable timeTable;

    public ModelStubImportExportCommand() {
        person = TypicalPersons.TEST;
        user = new User(person.getData());
        timeTable = new TimeTable();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        if (person.equals(target)) {
            person = editedPerson;
            user = new User(person.getData());
            updateTimeTable(person.getTimeTable());
        }
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        //do nothing...?
    }

    @Override
    public void commitAddressBook() {
        //do nothing
    }

    @Override
    public void updateTimeTable(TimeTable newTimeTable) {
        requireNonNull(newTimeTable);
        this.timeTable.updateTimeTable(newTimeTable);
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public TimeTable getTimeTable() {
        return timeTable;
    }
}
