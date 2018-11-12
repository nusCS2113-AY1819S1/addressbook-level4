package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_DRNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_VENUE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appt.Appt;
import seedu.address.model.appt.ApptComparator;
import seedu.address.model.person.Person;

//@@author brandonccm1996
/**
 * Adds an appointment to a person's schedule.
 */
public class AddApptCommand extends Command {

    public static final String COMMAND_WORD = "addappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to a patient's schedule, the"
            + " patient will be identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_APPT_START + "START "
            + PREFIX_APPT_END + "END "
            + PREFIX_APPT_VENUE + "VENUE "
            + PREFIX_APPT_INFO + "INFO "
            + PREFIX_APPT_DRNAME + "DOCTOR'S NAME\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_APPT_START + "16-09-2018 15:00 "
            + PREFIX_APPT_END + "16-09-2018 15:30 "
            + PREFIX_APPT_VENUE + "Consultation Room 12 "
            + PREFIX_APPT_INFO + "Diabetes Checkup "
            + PREFIX_APPT_DRNAME + "Dr Tan";

    public static final String MESSAGE_ADD_APPT_SUCCESS = "Added appt to Person: %1$s";
    public static final String MESSAGE_APPT_CLASH = "The appt you are adding clashes with the timing of another appt "
            + "for the specified patient.";
    public static final String MESSAGE_INVALID_TIME = "The end date and time of an appt must be after the start date "
            + "and time.";

    private final Index index;
    private final Appt appt;

    /**
     * @param index of the person in the filtered person list to add appt
     * @param appt of the person to be updated to
     */
    public AddApptCommand(Index index, Appt appt) {
        requireAllNonNull(index, appt);
        this.index = index;
        this.appt = appt;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Set<Appt> oldAppts = personToEdit.getAppts();

        if (hasInvalidTiming(appt)) {
            throw new CommandException(MESSAGE_INVALID_TIME);
        }

        for (Appt oldAppt : oldAppts) {
            if (hasApptClash(oldAppt, appt)) {
                throw new CommandException(MESSAGE_APPT_CLASH);
            }
        }

        Set<Appt> newAppts = new TreeSet<>(new ApptComparator());
        for (Appt appt : oldAppts) {
            newAppts.add(appt);
        }
        newAppts.add(appt);
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getMedicalReports(), personToEdit.getMedHistory(), newAppts,
                personToEdit.getNric(), personToEdit.getDateOfBirth(), personToEdit.getHeight(),
                personToEdit.getWeight(), personToEdit.getGender(), personToEdit.getBloodType(),
                personToEdit.getOccupation(), personToEdit.getMaritalStatus(), personToEdit.getTags());

        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message when the appt is added to
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        return String.format(MESSAGE_ADD_APPT_SUCCESS, personToEdit);
    }

    /**
     * Checks if an appt has end time before or equal to start time
     */
    private boolean hasInvalidTiming(Appt appt) {
        LocalDateTime start = appt.getStart().value;
        LocalDateTime end = appt.getEnd().value;

        if (!end.isAfter(start)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if appt1 and appt2 have timings which clash
     */
    private boolean hasApptClash(Appt appt1, Appt appt2) {
        LocalDateTime start1 = appt1.getStart().value;
        LocalDateTime end1 = appt1.getEnd().value;
        LocalDateTime start2 = appt2.getStart().value;
        LocalDateTime end2 = appt2.getEnd().value;

        // Example:
        // if appt1 is from 1600-1700, appt2 cannot start between 1600-1759, and cannot end between 1601-1800
        // if appt2 is from 1600-1700, appt1 cannot start between 1600-1759, and cannot end between 1601-1800
        if (((start1.isEqual(start2) || start1.isAfter(start2)) && start1.isBefore(end2))
                || ((start2.isEqual(start1) || start2.isAfter(start1)) && start2.isBefore(end1))
                || (end1.isAfter(start2) && (end1.isBefore(end2) || end1.isEqual(end2)))
                || (end2.isAfter(start1) && (end2.isBefore(end1) || end2.isEqual(end1)))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddApptCommand)) {
            return false;
        }

        // state check
        AddApptCommand e = (AddApptCommand) other;
        return index.equals(e.index) && appt.equals(e.appt);
    }
}
