package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Schedule;
import seedu.address.model.tag.Tag;

/**
 * Clears the schedule of an existing person in the address book.
 */

public class ClearScheduleCommand extends Command {
    public static final String COMMAND_WORD = "clearSchedule";
    public static final String COMMAND_ALIAS = "cs";


    public static final String COMMAND_PARAMETERS = "Parameters: INDEX (must be a positive integer) \n";

    public static final String COMMAND_EXAMPLE = "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears the schedule of the person identified "
            + "by the index number used in the displayed person list.\n"
            + COMMAND_PARAMETERS
            + COMMAND_EXAMPLE;

    public static final String MESSAGE_CLEAR_SCHEDULE_SUCCESS = "Schedule cleared";

    private final Index index;

    /**
     * @param index of the person in the filtered person list to clear schedule
     */
    public ClearScheduleCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToClearSchedule = lastShownList.get(index.getZeroBased());
        Person scheduledPerson = clearedSchedule(personToClearSchedule);
        model.updatePerson(personToClearSchedule, scheduledPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_CLEAR_SCHEDULE_SUCCESS, scheduledPerson));

    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person clearedSchedule(Person personToClearSchedule) {
        assert personToClearSchedule != null;

        Name updatedName = personToClearSchedule.getName();
        Phone updatedPhone = personToClearSchedule.getPhone();
        Email updatedEmail = personToClearSchedule.getEmail();
        Address updatedAddress = personToClearSchedule.getAddress();
        Set<Tag> updatedTags = personToClearSchedule.getTags();
        Set<Schedule> updatedSchedule = new HashSet<>(); //clears schedule

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags, updatedSchedule);
    }

}
