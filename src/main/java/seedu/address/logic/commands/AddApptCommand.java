package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_DRNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_VENUE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.timetable.Appt;

/**
 * Adds an appointment to a person's timetable.
 */
public class AddApptCommand extends Command {

    public static final String COMMAND_WORD = "addappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to a patient's timetable, the"
            + " patient will be identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_APPT_START + "[START] "
            + PREFIX_APPT_END + "[END] "
            + PREFIX_APPT_VENUE + "[VENUE] "
            + PREFIX_APPT_INFO + "[INFO] "
            + PREFIX_APPT_DRNAME + "[DOCTOR'S NAME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_APPT_START + "16/09/2018 1500 "
            + PREFIX_APPT_END + "16/09/2018 1530 "
            + PREFIX_APPT_VENUE + "Consultation Room 12 "
            + PREFIX_APPT_INFO + "Diabetes Checkup "
            + PREFIX_APPT_DRNAME + "Dr Tan";

    public static final String MESSAGE_ADD_APPT_SUCCESS = "Added appt to Person: %1$s";
    public static final String MESSAGE_DELETE_APPT_SUCCESS = "Removed appt from Person: %1$s";

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
        Set<Appt> newAppts = new HashSet<>();
        for (Appt appt : oldAppts) {
            newAppts.add(appt);
        }
        newAppts.add(appt);
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getMedicalReports(), personToEdit.getMedHistory(), newAppts,
                personToEdit.getNric(), personToEdit.getDateOfBirth(), personToEdit.getTags());

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
