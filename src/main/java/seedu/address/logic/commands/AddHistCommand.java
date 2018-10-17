package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medhistory.MedHistory;
import seedu.address.model.person.Person;

/**
 * Adds/Edits medical history of a patient in the Health Book.
 */

public class AddHistCommand extends Command {

    public static final String COMMAND_WORD = "addhist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds/Edits medical history of a patient "
            + "by their index number."
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_HISTORY_DATE + " 24/08/1993 "
            + PREFIX_HISTORY_ALLERGY + " Alcohol "
            + PREFIX_HISTORY_COUNTRY + " Kuwait ";

    public static final String MESSAGE_ADD_MEDHISTORY_SUCCESS = "Added medical history to Person: %1$s";
    public static final String MESSAGE_DELETE_MEDHISTORY_SUCCESS = "Removed medical history from Person: %1$s";
    private final Index index;
    private final MedHistory medHistory;
    /**
     * @param index of the patient in the filtered patient list to add medical history
     * @param medHistory of the person to be updated to
     */
    public AddHistCommand(Index index, MedHistory medHistory) {
        requireAllNonNull(index, medHistory);
        this.index = index;
        this.medHistory = medHistory;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getMedicalReport(), medHistory, personToEdit.getAppt(),
                personToEdit.getNric(), personToEdit.getTags());
        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether the medical history is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !medHistory.toString().isEmpty() ? MESSAGE_ADD_MEDHISTORY_SUCCESS
                : MESSAGE_DELETE_MEDHISTORY_SUCCESS;
        return String.format(message, personToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof AddHistCommand)) {
            return false;
        }
        // state check
        AddHistCommand e = (AddHistCommand) other;
        return index.equals(e.index)
                && medHistory.equals(e.medHistory);
    }
}
