package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_DISCHARGE_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_OLD_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

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
import seedu.address.model.medhistory.Allergy;
import seedu.address.model.medhistory.DischargeStatus;
import seedu.address.model.medhistory.MedHistDate;
import seedu.address.model.medhistory.MedHistory;
import seedu.address.model.medhistory.MedHistoryComparator;
import seedu.address.model.medhistory.PrevCountry;
import seedu.address.model.person.Person;
import seedu.address.model.util.DateTimeUtil;

//@@author xShaivan
/**
 * Edits an existing medical history entry for a patient.
 */
public class EditHistCommand extends Command {

    public static final String COMMAND_WORD = "edithist";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits a medical history entry of the person identified "
            + "by the index number used in the displayed person list and the unique "
            + "medical history date for the entry to be edited."
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_HISTORY_OLD_DATE + "OLD_DATE] "
            + "[" + PREFIX_HISTORY_DATE + "DATE] "
            + "[" + PREFIX_HISTORY_ALLERGY + "ALLERGY] "
            + "[" + PREFIX_HISTORY_COUNTRY + "PREVCOUNTRY] "
            + "[" + PREFIX_HISTORY_DISCHARGE_STATUS + "DISCHARGE_STATUS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_HISTORY_OLD_DATE + "10-10-2010 "
            + PREFIX_HISTORY_DATE + "20-05-2011 "
            + PREFIX_HISTORY_ALLERGY + "grass "
            + PREFIX_HISTORY_COUNTRY + "Canada "
            + PREFIX_HISTORY_DISCHARGE_STATUS + "e";

    public static final String MESSAGE_EDIT_MEDHISTORY_SUCCESS = "Edited medical history entry for Person: %1$s";
    public static final String MESSAGE_MEDHISTORY_NOT_FOUND =
            "The medical history entry you are trying to edit does not exist.";
    public static final String MESSAGE_MEDHISTORY_NOT_EDITED =
            "One field must be specified for editing. Please include at least one field to edit.";
    public static final String MESSAGE_DUPLICATE_MEDHISTDATE =
            "The new date you have specified is already taken. Please edit that entry instead.";
    public static final String MESSAGE_INVALID_MEDHISTDATE =
            "New Medical History date entries must be today or before the current day.";

    private final Index index;
    private final MedHistDate medHistDate;
    private EditHistDescriptor editHistDescriptor;

    public EditHistCommand(Index index, MedHistDate medHistDate, EditHistDescriptor editHistDescriptor) {
        requireAllNonNull(index, medHistDate, editHistDescriptor);
        this.index = index;
        this.medHistDate = medHistDate;
        this.editHistDescriptor = editHistDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireAllNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        MedHistory editedMedHistory = null;
        boolean medHistoryFound = false;

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Set<MedHistory> medHistoriesCopy = new TreeSet<>(new MedHistoryComparator());

        for (MedHistory medHistory : personToEdit.getMedHistory()) {
            medHistoriesCopy.add(medHistory);
            if (medHistory.getMedHistDate().equals(medHistDate)) {
                editedMedHistory = createEditedMedHistory(medHistory, editHistDescriptor);
                medHistoriesCopy.remove(medHistory);
                medHistoryFound = true;
            }
        }
        if (!medHistoryFound) {
            throw new CommandException(MESSAGE_MEDHISTORY_NOT_FOUND);
        }

        for (MedHistory fullmedHistory : medHistoriesCopy) {
            if (isDuplicateMedHistDate(fullmedHistory, editedMedHistory)) {
                throw new CommandException(MESSAGE_DUPLICATE_MEDHISTDATE);
            }
        }

        if (DateTimeUtil.isInvalidEditMedHistDate(editHistDescriptor)) {
            throw new CommandException(MESSAGE_INVALID_MEDHISTDATE);
        }

        medHistoriesCopy.add(editedMedHistory);

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getMedicalReports(), medHistoriesCopy, personToEdit.getAppts(),
                personToEdit.getNric(), personToEdit.getDateOfBirth(), personToEdit.getHeight(),
                personToEdit.getWeight(), personToEdit.getGender(), personToEdit.getBloodType(),
                personToEdit.getOccupation(), personToEdit.getMaritalStatus(), personToEdit.getTags());

        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * This method checks if input has a duplicate medical history date.
     */
    private boolean isDuplicateMedHistDate(MedHistory medHistory1, MedHistory medHistory2) {
        String medHistDate1 = medHistory1.getMedHistDate().toString();
        String medHistDate2 = medHistory2.getMedHistDate().toString();

        return (medHistDate1.equals(medHistDate2));
    }

    /**
     * Generates a command execution success message when the medical history is added to
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        return String.format(MESSAGE_EDIT_MEDHISTORY_SUCCESS, personToEdit);
    }

    /**
     * Creates and returns a {@code Appt} with the details of {@code apptToEdit}
     * edited with {@code editApptDescriptor}.
     */
    private static MedHistory createEditedMedHistory(
            MedHistory medHistoryToEdit, EditHistDescriptor editHistDescriptor) {
        assert medHistoryToEdit != null;

        MedHistDate updatedMedHistDate = editHistDescriptor.getMedHistDate();
        Allergy updatedAllergy =
                editHistDescriptor.getAllergy().orElse(medHistoryToEdit.getAllergy().orElse(new Allergy("")));
        PrevCountry updatedPrevCountry = editHistDescriptor.getPrevCountry()
                .orElse(medHistoryToEdit.getPrevCountry().orElse(new PrevCountry("")));
        DischargeStatus updatedDischargeStatus = editHistDescriptor.getDischargeStatus()
                .orElse(medHistoryToEdit.getDischargeStatus().orElse(new DischargeStatus("")));

        return new MedHistory(updatedMedHistDate, updatedAllergy, updatedPrevCountry, updatedDischargeStatus);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditHistCommand)) {
            return false;
        }

        // state check
        EditHistCommand e = (EditHistCommand) other;
        return index.equals(e.index)
                && editHistDescriptor.equals(e.editHistDescriptor);
    }

    /**
     * Stores the details to edit the medical history with. Each non-empty field value will replace the
     * corresponding field value of the medical history.
     */
    public static class EditHistDescriptor {
        private MedHistDate medHistDate;
        private Allergy allergy;
        private PrevCountry prevCountry;
        private DischargeStatus dischargeStatus;

        public EditHistDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditHistDescriptor(EditHistDescriptor toCopy) {
            setMedHistDate(toCopy.medHistDate);
            setAllergy(toCopy.allergy);
            setPrevCountry(toCopy.prevCountry);
            setDischargeStatus(toCopy.dischargeStatus);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(medHistDate, allergy, prevCountry, dischargeStatus);
        }

        public void setMedHistDate(MedHistDate medHistDate) {
            this.medHistDate = medHistDate;
        }

        public MedHistDate getMedHistDate() {
            return medHistDate;
        }

        public void setAllergy(Allergy allergy) {
            this.allergy = allergy;
        }

        public Optional<Allergy> getAllergy() {
            return Optional.ofNullable(allergy);
        }

        public void setPrevCountry(PrevCountry prevCountry) {
            this.prevCountry = prevCountry;
        }

        public Optional<PrevCountry> getPrevCountry() {
            return Optional.ofNullable(prevCountry);
        }

        public void setDischargeStatus(DischargeStatus dischargeStatus) {
            this.dischargeStatus = dischargeStatus;
        }

        public Optional<DischargeStatus> getDischargeStatus() {
            return Optional.ofNullable(dischargeStatus);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditHistDescriptor)) {
                return false;
            }

            // state check
            EditHistDescriptor e = (EditHistDescriptor) other;

            return getMedHistDate().equals(e.getMedHistDate())
                    && getAllergy().equals(e.getAllergy())
                    && getPrevCountry().equals(e.getPrevCountry())
                    && getDischargeStatus().equals(getDischargeStatus());
        }
    }
}
