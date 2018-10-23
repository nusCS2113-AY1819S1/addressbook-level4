package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.IsNotSelfOrMergedPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.TimeSlots;

/**
 * Edits the details of an existing person in the address book.
 */
public class ChangeTimeSlotCommand extends Command {

    public static final String COMMAND_WORD = "change";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the selected time slot "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX " +
            "DAY(mon, tue, wed, thu, fri) "
            + "TIME(8am, 9am, 10am, 11am, 12am, 1pm, 2pm, 3pm, 4pm, 5pm, 6pm, 7pm) "
            + "Activity "
            + "Example: " + COMMAND_WORD + "1 " + "mon "
            + "8am "
            + "CS2107";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Time slot changed: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Parameters out of range.";
    public static final String MESSAGE_NOTHING_CHANGED = "No time slot was changed";

    private final Index index;
    private final String[] actions;

    public ChangeTimeSlotCommand(Index index, String[] actions) {
        requireNonNull(actions);
        requireNonNull(index);

        this.index = index;
        this.actions = actions;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        lastShownList = ((ObservableList<Person>) lastShownList).filtered(new IsNotSelfOrMergedPredicate());

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToChange = lastShownList.get(index.getZeroBased());

        Map<String, List<TimeSlots>> timeSlots = personToChange.getTimeSlots();
        String day = null;
        String time = null;

        for (int i = 1; i < actions.length; i++) {

            String activity;
            if (i % 3 == 1) {
                day = actions[i];
            }
            if (i % 3 == 2) {
                time = actions[i];
            }
            if (i % 3 == 0) {
                activity = actions[i];
                if (actions[i].equalsIgnoreCase("free")) {
                    timeSlots.get(day).set(changeTimeToIndex(time),
                            new TimeSlots(day.toLowerCase() + time.toLowerCase()));
                } else {
                    timeSlots.get(day).set(changeTimeToIndex(time), new TimeSlots(activity));
                }
            }
        }

        Person newPerson= new Person(personToChange.getName(),personToChange.getPhone(),personToChange.getEmail(),
                personToChange.getAddress(),personToChange.getTags(),personToChange.getEnrolledModules(),
                timeSlots);

        if (!personToChange.isSamePerson(newPerson) && model.hasPerson(newPerson)) {
            throw new CommandException(MESSAGE_NOTHING_CHANGED);
        }

        model.updatePerson(personToChange, newPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, newPerson));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }
        return false;
    }

    public int changeTimeToIndex(String time) {
        int index;
        if (time.equalsIgnoreCase("8am")) {
            index = 0;
        } else if (time.equalsIgnoreCase("9am")) {
            index = 1;
        } else if (time.equalsIgnoreCase("10am")) {
            index = 2;
        } else if (time.equalsIgnoreCase("11am")) {
            index = 3;
        } else if (time.equalsIgnoreCase("12am")) {
            index = 4;
        } else if (time.equalsIgnoreCase("1pm")) {
            index = 5;
        } else if (time.equalsIgnoreCase("2pm")) {
            index = 6;
        } else if (time.equalsIgnoreCase("3pm")) {
            index = 7;
        } else if (time.equalsIgnoreCase("4pm")) {
            index = 8;
        } else if (time.equalsIgnoreCase("5pm")) {
            index = 9;
        } else if (time.equalsIgnoreCase("6pm")) {
            index = 10;
        } else if (time.equalsIgnoreCase("7pm")) {
            index = 11;
        } else {
            return 11;
        }
        return index;
    }
}