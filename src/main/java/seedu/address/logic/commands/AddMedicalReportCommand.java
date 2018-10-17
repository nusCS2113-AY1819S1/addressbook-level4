package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFORMATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicalreport.MedicalReport;
import seedu.address.model.person.Person;

/**
 * Adds a medical report to a person.
 */
public class AddMedicalReportCommand extends Command {

    public static final String COMMAND_WORD = "addreport";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person's medical report by the index number"
            + " used in the list.\n"
            + "Parameters: [INDEX] (must be a positive integer) "
            + PREFIX_TITLE + "[TITLE] "
            + PREFIX_DATE + "[DATE] "
            + PREFIX_INFORMATION + "[INFORMATION]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TITLE + "Asthma "
            + PREFIX_DATE + "01/01/2018 "
            + PREFIX_INFORMATION + "prescribed XXX medicine, next appointment on 02/02/2018. ";

    public static final String MESSAGE_ADD_REPORT_SUCCESS = "Added medical report to Person: %1$s";

    private final Index index;
    private final MedicalReport report;

    /**
     * Creates an AddMedicalReportCommand to add the specified {@code index and report}
     * @param index of the person in the filtered person list to add medical report
     * @param report details of medical report
     */
    public AddMedicalReportCommand(Index index, MedicalReport report) {
        requireNonNull(index);
        requireNonNull(report);
        this.index = index;
        this.report = report;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Set<MedicalReport> oldReports = personToEdit.getMedicalReports();
        Set<MedicalReport> newReports = new HashSet<>();
        for (MedicalReport report : oldReports) {
            newReports.add(report);
        }
        newReports.add(report);
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), newReports, personToEdit.getMedHistory(), personToEdit.getAppts(),
                personToEdit.getNric(), personToEdit.getDateOfBirth(), personToEdit.getTags());

        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    private String generateSuccessMessage(Person personToEdit) {
        return String.format(MESSAGE_ADD_REPORT_SUCCESS, personToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof AddMedicalReportCommand)) {
            return false;
        }
        // state check
        AddMedicalReportCommand e = (AddMedicalReportCommand) other;
        return index.equals(e.index)
                && report.equals(e.report);
    }
}
