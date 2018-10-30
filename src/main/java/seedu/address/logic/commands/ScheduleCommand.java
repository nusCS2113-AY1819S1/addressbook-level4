package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
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

//@@Author: driedmelon
/**
 * Adds the schedule of a person to the address book
 */

public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";
    public static final String COMMAND_ALIAS = "sc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a schedule to a person. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + "DATE(DDMMYYYY) "
            + PREFIX_START_TIME + "TIME(24HRS) "
            + PREFIX_END_TIME + "TIME(24HRS) "
            + PREFIX_EVENT_NAME + "EVENT NAME \n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_DATE + "08112018 "
            + PREFIX_START_TIME + "1000 "
            + PREFIX_END_TIME + "1200 "
            + PREFIX_EVENT_NAME + "GER1000 Class ";

    public static final String MESSAGE_SUCCESS = "Schedule Added!";
    public static final String MESSAGE_FAILURE = "Unable to add schedule";

    private Schedule toSchedule;
    private final Index index;
    //private final SchedulePersonDescriptor schedulePersonDescriptor;

    public ScheduleCommand(Schedule schedule, Index index) {
        requireNonNull(schedule);
        this.index = index;
        this.toSchedule = schedule;
        //this.schedulePersonDescriptor = new SchedulePersonDescriptor(schedulePersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddSchedule = lastShownList.get(index.getZeroBased());
        System.out.println(personToAddSchedule.getSchedules().toString());
        Person scheduledPerson = addScheduleToPerson(personToAddSchedule, this.toSchedule);
        System.out.println(scheduledPerson.getSchedules().toString());

        model.updatePerson(personToAddSchedule, scheduledPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, scheduledPerson));

    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person addScheduleToPerson(Person personToAddSchedule, Schedule schedule) {
        assert personToAddSchedule != null;

        Name updatedName = personToAddSchedule.getName();
        Phone updatedPhone = personToAddSchedule.getPhone();
        Email updatedEmail = personToAddSchedule.getEmail();
        Address updatedAddress = personToAddSchedule.getAddress();
        Set<Tag> updatedTags = personToAddSchedule.getTags();
        Set<Schedule> oldSchedule = personToAddSchedule.getSchedules();

        Set<Schedule> updatedSchedule = new HashSet<>(oldSchedule);
        updatedSchedule.add(schedule);
        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags, updatedSchedule);
    }

    //THIS IS CURRENTLY UNUSED
    //    /**
    //     * Stores the details to edit the person with. Each non-empty field value will replace the
    //     * corresponding field value of the person.
    //     */
    //    public static class SchedulePersonDescriptor {
    //        private Set<Schedule> schedules;
    //
    //        public SchedulePersonDescriptor() {}
    //
    //        /**
    //         * Copy constructor.
    //         * A defensive copy of {@code tags} is used internally.
    //         */
    //        public SchedulePersonDescriptor (SchedulePersonDescriptor toCopy) {
    //            setSchedules(toCopy.schedules);
    //        }
    //
    //        /**
    //         * Sets {@code schedules} to this object's {@code schedules}.
    //         * A defensive copy of {@code tags} is used internally.
    //         */
    //        public void setSchedules(Set<Schedule> schedules) {
    //            this.schedules = (schedules != null) ? new HashSet<>(schedules) : null;
    //        }
    //
    //        //TODO TODO HOW TO MAKE NOT OPTIONAL
    //        public Optional<Set<Schedule>> getSchedules() {
    //            return (schedules != null) ? Optional.of(Collections.unmodifiableSet(schedules)) : Optional.empty();
    //        }
    //
    //        @Override
    //        public boolean equals(Object other) {
    //            // short circuit if same object
    //            if (other == this) {
    //                return true;
    //            }
    //
    //            // instanceof handles nulls
    //            if (!(other instanceof ScheduleCommand.SchedulePersonDescriptor)) {
    //                return false;
    //            }
    //
    //            // state check
    //            ScheduleCommand.SchedulePersonDescriptor e = (ScheduleCommand.SchedulePersonDescriptor) other;
    //
    //            return getSchedules().equals(e.getSchedules());
    //        }
    //    }
}
