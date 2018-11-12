package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_DRNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_ORIGINAL_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_VENUE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appt.Appt;
import seedu.address.model.appt.ApptComparator;
import seedu.address.model.appt.ApptDateTime;
import seedu.address.model.appt.ApptDrName;
import seedu.address.model.appt.ApptInfo;
import seedu.address.model.appt.ApptVenue;
import seedu.address.model.person.Person;

//@@author brandonccm1996
/**
 * Edits the details of an existing appt for a person in the address book
 */
public class EditApptCommand extends Command {

    public static final String COMMAND_WORD = "editappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the appt identified by the index number used in the displayed person list"
            + " and the start time provided. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_APPT_ORIGINAL_START + "ORIGINAL START "
            + "[" + PREFIX_APPT_START + "START] "
            + "[" + PREFIX_APPT_END + "END] "
            + "[" + PREFIX_APPT_VENUE + "VENUE] "
            + "[" + PREFIX_APPT_INFO + "INFO] "
            + "[" + PREFIX_APPT_DRNAME + "DRNAME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_APPT_ORIGINAL_START + "16-09-2018 15:00 "
            + PREFIX_APPT_START + "16-09-2018 14:00 "
            + PREFIX_APPT_END + "16-09-2018 14:30 "
            + PREFIX_APPT_VENUE + "Consultation Room 13";

    public static final String MESSAGE_EDIT_APPT_SUCCESS = "Edited appt for Person: %1$s";
    public static final String MESSAGE_APPT_CLASH = "The appt you are editing will clash with the timing of another "
            + "appt for the specified patient.";
    public static final String MESSAGE_INVALID_TIME = "The end date and time of an appt must be after the start date "
            + "and time.";
    public static final String MESSAGE_APPT_NOT_FOUND = "The appt you are trying to edit cannot be found for the "
            + "patient.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final ApptDateTime originalApptStart;
    private final EditApptDescriptor editApptDescriptor;

    public EditApptCommand(Index index, ApptDateTime originalApptStart, EditApptDescriptor editApptDescriptor) {
        requireAllNonNull(index, editApptDescriptor);
        this.index = index;
        this.originalApptStart = originalApptStart;
        this.editApptDescriptor = editApptDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        Appt editedAppt = null;
        boolean apptFound = false;

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        // A copy of the person's appts to do time clash checks on
        Set<Appt> apptsCopy = new TreeSet<>(new ApptComparator());

        for (Appt appt : personToEdit.getAppts()) {
            apptsCopy.add(appt);

            if (appt.getStart().equals(originalApptStart)) {
                editedAppt = createEditedAppt(appt, editApptDescriptor);
                if (hasInvalidTiming(editedAppt)) {
                    throw new CommandException(MESSAGE_INVALID_TIME);
                }
                apptsCopy.remove(appt);
                apptFound = true;
            }
        }

        if (!apptFound) {
            throw new CommandException(MESSAGE_APPT_NOT_FOUND);
        }

        for (Appt appt : apptsCopy) {
            if (hasApptClash(appt, editedAppt)) {
                throw new CommandException(MESSAGE_APPT_CLASH);
            }
        }
        apptsCopy.add(editedAppt);

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getMedicalReports(), personToEdit.getMedHistory(), apptsCopy,
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
        return String.format(MESSAGE_EDIT_APPT_SUCCESS, personToEdit);
    }

    /**
     * Creates and returns a {@code Appt} with the details of {@code apptToEdit}
     * edited with {@code editApptDescriptor}.
     */
    private static Appt createEditedAppt(Appt apptToEdit, EditApptDescriptor editApptDescriptor) {
        assert apptToEdit != null;

        ApptDateTime updatedStart = editApptDescriptor.getStart().orElse(apptToEdit.getStart());
        ApptDateTime updatedEnd = editApptDescriptor.getEnd().orElse(apptToEdit.getEnd());
        ApptVenue updatedVenue = editApptDescriptor.getVenue().orElse(apptToEdit.getVenue());
        ApptInfo updatedInfo = editApptDescriptor.getInfo().orElse(apptToEdit.getInfo());
        ApptDrName updatedDrName = editApptDescriptor.getDrName().orElse(apptToEdit.getDrName());

        return new Appt(updatedStart, updatedEnd, updatedVenue, updatedInfo, updatedDrName);
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
        if (!(other instanceof EditApptCommand)) {
            return false;
        }

        // state check
        EditApptCommand e = (EditApptCommand) other;
        return index.equals(e.index)
                && editApptDescriptor.equals(e.editApptDescriptor);
    }

    /**
     * Stores the details to edit the appt with. Each non-empty field value will replace the
     * corresponding field value of the appt.
     */
    public static class EditApptDescriptor {
        private ApptDateTime apptStart;
        private ApptDateTime apptEnd;
        private ApptVenue apptVenue;
        private ApptInfo apptInfo;
        private ApptDrName apptDrName;

        public EditApptDescriptor() {}

        public EditApptDescriptor(EditApptDescriptor toCopy) {
            setStart(toCopy.apptStart);
            setEnd(toCopy.apptEnd);
            setVenue(toCopy.apptVenue);
            setInfo(toCopy.apptInfo);
            setDrName(toCopy.apptDrName);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(apptStart, apptEnd, apptVenue, apptInfo, apptDrName);
        }

        public void setStart(ApptDateTime apptStart) {
            this.apptStart = apptStart;
        }

        public Optional<ApptDateTime> getStart() {
            return Optional.ofNullable(apptStart);
        }

        public void setEnd(ApptDateTime apptEnd) {
            this.apptEnd = apptEnd;
        }

        public Optional<ApptDateTime> getEnd() {
            return Optional.ofNullable(apptEnd);
        }

        public void setVenue(ApptVenue apptVenue) {
            this.apptVenue = apptVenue;
        }

        public Optional<ApptVenue> getVenue() {
            return Optional.ofNullable(apptVenue);
        }

        public void setInfo(ApptInfo apptInfo) {
            this.apptInfo = apptInfo;
        }

        public Optional<ApptInfo> getInfo() {
            return Optional.ofNullable(apptInfo);
        }

        public void setDrName(ApptDrName apptDrName) {
            this.apptDrName = apptDrName;
        }

        public Optional<ApptDrName> getDrName() {
            return Optional.ofNullable(apptDrName);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditApptDescriptor)) {
                return false;
            }

            // state check
            EditApptDescriptor e = (EditApptDescriptor) other;

            return getStart().equals(e.getStart())
                    && getEnd().equals(e.getEnd())
                    && getVenue().equals(e.getVenue())
                    && getInfo().equals(e.getInfo())
                    && getDrName().equals(e.getDrName());
        }
    }
}
