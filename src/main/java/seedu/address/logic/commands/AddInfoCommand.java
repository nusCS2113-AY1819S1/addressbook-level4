package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Edits optional details of an existing patient in the address book.
 */

public class AddInfoCommand extends Command {
    public static final String COMMAND_WORD = "addinfo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the additional information of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing information will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_ADD_INFO_SUCCESS = "Added additional info to Person: %1$s";
    public static final String MESSAGE_DELETE_INFO_SUCCESS = "Removed additional info to person: %1$s";

    private final Index index;
    private final Nric nric;


    /**
     *
     * @param index of the person in the filter person list to edit the additional info
     * @param nric of the person to be updated to
     */
    public AddInfoCommand(Index index, Nric nric) {
        requireAllNonNull(index, nric);

        this.index = index;
        this.nric = nric;
    }

    private String generateSuccessMessage(Person personToEdit) {
        String message = !nric.value.isEmpty() ? MESSAGE_ADD_INFO_SUCCESS : MESSAGE_DELETE_INFO_SUCCESS;
        return String.format(message, personToEdit);
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireAllNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getMedicalReport(), personToEdit.getMedHistory(),
                personToEdit.getAppt(), nric, personToEdit.getTags());

        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddInfoCommand)) {
            return false;
        }

        // state check
        AddInfoCommand e = (AddInfoCommand) other;
        return index.equals(e.index) && nric.equals(e.nric);
    }
}
