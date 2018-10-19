package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE_RANGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.recruit.model.Model.PREDICATE_SHOW_ALL_JOBOFFERS;

import java.util.List;
import java.util.Optional;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.commons.util.CollectionUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.model.candidate.Education;
import seedu.recruit.model.candidate.Gender;
import seedu.recruit.model.company.CompanyName;
import seedu.recruit.model.joboffer.AgeRange;
import seedu.recruit.model.joboffer.Job;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.model.joboffer.Salary;

/**
 * Edits the job details of a selected company
 */
public class EditJobDetailsCommand extends Command {

    public static final String COMMAND_WORD = "editj";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the job offer identified "
            + "by the company name and index number used in the displayed job offer list for that specific company. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: COMPANY NAME(must exist in the Company Book) "
            + "INDEX (must be a positive integer) "
            + "[" + PREFIX_JOB + "Job Title] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_AGE_RANGE + "AGE RANGE] "
            + "[" + PREFIX_EDUCATION + "EDUCATION] "
            + "[" + PREFIX_SALARY + "SALARY] "
            + "Example: " + COMMAND_WORD + " KFC" + " 1 "
            + PREFIX_JOB + "KFC "
            + PREFIX_GENDER + "M "
            + PREFIX_AGE_RANGE + "21-42"
            + PREFIX_EDUCATION + "A LEVELS";

    public static final String MESSAGE_EDIT_JOB_OFFER_SUCCESS = "Edited job offer: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be included";
    public static final String MESSAGE_DUPLICATE_JOB_OFFER = "The edited job offer already exists within the company";

    private final Index index;
    private final CompanyName companyName;
    private final EditJobOfferDescriptor editJobOfferDescriptor;

    /**
     * @param companyName of the company
     * @param index of the job offer in the filtered company job list to edit
     * @param editJobOfferDescriptor details to edit the job offer with
     */
    public EditJobDetailsCommand(CompanyName companyName, Index index, EditJobDetailsCommand.EditJobOfferDescriptor editJobOfferDescriptor) {
        requireNonNull(companyName);
        requireNonNull(index);
        requireNonNull(editJobOfferDescriptor);

        this.companyName = companyName;
        this.index = index;
        this.editJobOfferDescriptor = new EditJobDetailsCommand.EditJobOfferDescriptor(editJobOfferDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<JobOffer> lastShownList = model.getFilteredCompanyJobList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_JOB_OFFER_DISPLAYED_INDEX);
        }

        JobOffer jobOfferToEdit = lastShownList.get(index.getZeroBased());
        JobOffer editedJobOffer = createEditedJobOffer(jobOfferToEdit, editJobOfferDescriptor);

        if (!jobOfferToEdit.isSameJobOffer(editedJobOffer) && model.hasJobOffer(companyName, editedJobOffer)) {
            throw new CommandException(MESSAGE_DUPLICATE_JOB_OFFER);
        }

        model.updateJobOffer(companyName, jobOfferToEdit, editedJobOffer);
        model.updateFilteredCompanyJobList(PREDICATE_SHOW_ALL_JOBOFFERS);
        model.commitCompanyBook();
        return new CommandResult(String.format(MESSAGE_EDIT_JOB_OFFER_SUCCESS, editedJobOffer));
    }

    /**
     * Creates and returns a {@code jobOffer} with the details of {@code jobOfferToEdit}
     * edited with {@code EditJobOfferDescriptor}.
     */
    private static JobOffer createEditedJobOffer(JobOffer jobOfferToEdit, EditJobOfferDescriptor editJobOfferDescriptor) {
        assert jobOfferToEdit != null;

        CompanyName companyName = jobOfferToEdit.getCompanyName();
        Job updatedJob = editJobOfferDescriptor.getJob().orElse(jobOfferToEdit.getJob());
        Gender updatedGender = editJobOfferDescriptor.getGender().orElse(jobOfferToEdit.getGender());
        AgeRange updatedAgeRange = editJobOfferDescriptor.getAgeRange().orElse(jobOfferToEdit.getAgeRange());
        Education updatedEducation = editJobOfferDescriptor.getEducation().orElse(jobOfferToEdit.getEducation());
        Salary updatedSalary = editJobOfferDescriptor.getSalary().orElse(jobOfferToEdit.getSalary());
        return new JobOffer(companyName, updatedJob, updatedGender, updatedAgeRange, updatedEducation, updatedSalary);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditJobDetailsCommand)) {
            return false;
        }

        // state check
        EditJobDetailsCommand e = (EditJobDetailsCommand) other;
        return index.equals(e.index)
                && editJobOfferDescriptor.equals(e.editJobOfferDescriptor);
    }

    /**
     * Stores the details to edit the job offer with. Each non-empty field value will replace the
     * corresponding field value of the job offer.
     */

    public static class EditJobOfferDescriptor {
        private Job job;
        private Gender gender;
        private AgeRange ageRange;
        private Education education;
        private Salary salary;

        public EditJobOfferDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditJobOfferDescriptor(EditJobDetailsCommand.EditJobOfferDescriptor toCopy) {
            setJob(toCopy.job);
            setGender(toCopy.gender);
            setAgeRange(toCopy.ageRange);
            setEducation(toCopy.education);
            setSalary(toCopy.salary);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(job, gender, ageRange, education, salary);
        }

        public void setJob(Job job) {
            this.job = job;
        }

        public Optional<Job> getJob() {
            return Optional.ofNullable(job);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setAgeRange(AgeRange ageRange) {
            this.ageRange = ageRange;
        }

        public Optional<AgeRange> getAgeRange() {
            return Optional.ofNullable(ageRange);
        }

        public void setEducation(Education education) {
            this.education = education;
        }

        public Optional<Education> getEducation() {
            return Optional.ofNullable(education);
        }

        public void setSalary(Salary salary) {
            this.salary = salary;
        }

        public Optional<Salary> getSalary() {
            return Optional.ofNullable(salary);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditJobDetailsCommand.EditJobOfferDescriptor)) {
                return false;
            }

            // state check
            EditJobDetailsCommand.EditJobOfferDescriptor e = (EditJobDetailsCommand.EditJobOfferDescriptor) other;

            return getJob().equals(e.getJob())
                    && getGender().equals(e.getGender())
                    && getAgeRange().equals(e.getAgeRange())
                    && getEducation().equals(e.getEducation())
                    && getSalary().equals(e.getSalary());
        }
    }
}

}
