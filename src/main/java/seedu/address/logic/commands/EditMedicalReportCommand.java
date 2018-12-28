package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORIGINAL_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORIGINAL_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDate;
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
import seedu.address.model.medicalreport.Information;
import seedu.address.model.medicalreport.MedicalReport;
import seedu.address.model.medicalreport.ReportComparator;
import seedu.address.model.medicalreport.ReportDate;
import seedu.address.model.medicalreport.Title;
import seedu.address.model.person.Person;

//@@author chewkahmeng
/**
 * Edits the details of an existing medical report in the address book.
 */
public class EditMedicalReportCommand extends Command {

    public static final String COMMAND_WORD = "editreport";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the medical report identified by the index number used in the displayed person list"
            + " and the original title and date provided. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ORIGINAL_TITLE + "ORIGINAL TITLE "
            + PREFIX_ORIGINAL_DATE + "ORIGINAL DATE "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_INFO + "INFORMATION]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ORIGINAL_TITLE + "Asthma "
            + PREFIX_ORIGINAL_DATE + "01-01-2018 "
            + PREFIX_TITLE + "Depression "
            + PREFIX_DATE + "02-02-2018 "
            + PREFIX_INFO + "Prescribed AAA medicine, next appointment on 03-03-2018.";

    public static final String MESSAGE_EDIT_REPORT_SUCCESS = "Edited report for Person: %1$s";
    public static final String MESSAGE_TITLE_DATE_CLASH = "Unable to edit medical report"
            + " as there already exists a medical report of same title and date.";
    public static final String MESSAGE_REPORT_NOT_FOUND = "The report you are trying to edit cannot be found.";
    public static final String MESSAGE_REPORT_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final Title title;
    private final ReportDate reportDate;
    private final EditReportDescriptor editReportDescriptor;

    public EditMedicalReportCommand(Index index,
                                    Title title, ReportDate reportDate, EditReportDescriptor editReportDescriptor) {
        requireAllNonNull(index, editReportDescriptor);
        this.index = index;
        this.title = title;
        this.reportDate = reportDate;
        this.editReportDescriptor = editReportDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        MedicalReport editedReport = null;
        boolean reportFound = false;

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        // A copy of the person's medical report to do date clash checks on
        Set<MedicalReport> reportCopy = new TreeSet<>(new ReportComparator());

        for (MedicalReport report : personToEdit.getMedicalReports()) {
            reportCopy.add(report);

            if (report.getDate().equals(reportDate) && report.getTitle().equals(title)) {
                editedReport = createEditedReport(report, editReportDescriptor);

                reportCopy.remove(report);
                reportFound = true;
            }
        }

        if (!reportFound) {
            throw new CommandException(MESSAGE_REPORT_NOT_FOUND);
        }

        for (MedicalReport report : reportCopy) {
            if (hasTitleDateClash(report, editedReport)) {
                throw new CommandException(MESSAGE_TITLE_DATE_CLASH);
            }
        }
        reportCopy.add(editedReport);

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), reportCopy, personToEdit.getMedHistory(), personToEdit.getAppts(),
                personToEdit.getNric(), personToEdit.getDateOfBirth(), personToEdit.getHeight(),
                personToEdit.getWeight(), personToEdit.getGender(), personToEdit.getBloodType(),
                personToEdit.getOccupation(), personToEdit.getMaritalStatus(), personToEdit.getTags());

        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message when a medical report is added to
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        return String.format(MESSAGE_EDIT_REPORT_SUCCESS, personToEdit);
    }

    /**
     * Creates and returns a {@code MedicalReport} with the details of {@code reportToEdit}
     * edited with {@code editReportDescriptor}.
     */
    private static MedicalReport createEditedReport(MedicalReport reportToEdit,
                                                    EditReportDescriptor editReportDescriptor) {
        assert reportToEdit != null;

        Title updatedTitle = editReportDescriptor.getTitle().orElse(reportToEdit.getTitle());
        ReportDate updatedDate = editReportDescriptor.getReportDate().orElse(reportToEdit.getDate());
        Information updatedInfo = editReportDescriptor.getInformation().orElse(reportToEdit.getInformation());

        return new MedicalReport(updatedTitle, updatedDate, updatedInfo);
    }

    /**
     * Returns true if report1 and report2 have same title and date
     */
    private boolean hasTitleDateClash(MedicalReport report1, MedicalReport report2) {
        String title1 = report1.getTitle().fullTitle;
        String title2 = report2.getTitle().fullTitle;
        LocalDate date1 = report1.getDate().fullDate;
        LocalDate date2 = report2.getDate().fullDate;

        return ((title1.equals(title2)) && (date1.equals(date2)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditMedicalReportCommand)) {
            return false;
        }

        // state check
        EditMedicalReportCommand e = (EditMedicalReportCommand) other;
        return index.equals(e.index)
                && editReportDescriptor.equals(e.editReportDescriptor);
    }

    /**
     * Stores the details to edit the report with. Each non-empty field value will replace the
     * corresponding field value of the report.
     */
    public static class EditReportDescriptor {
        private Title title;
        private ReportDate reportDate;
        private Information information;

        public EditReportDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditReportDescriptor(EditReportDescriptor toCopy) {
            setTitle(toCopy.title);
            setReportDate(toCopy.reportDate);
            setInformation(toCopy.information);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, reportDate, information);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setReportDate(ReportDate reportDate) {
            this.reportDate = reportDate;
        }

        public Optional<ReportDate> getReportDate() {
            return Optional.ofNullable(reportDate);
        }

        public void setInformation(Information information) {
            this.information = information;
        }

        public Optional<Information> getInformation() {
            return Optional.ofNullable(information);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditReportDescriptor)) {
                return false;
            }

            // state check
            EditReportDescriptor e = (EditReportDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getReportDate().equals(e.getReportDate())
                    && getInformation().equals(e.getInformation());
        }
    }
}
