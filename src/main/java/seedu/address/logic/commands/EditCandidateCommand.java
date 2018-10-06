package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.candidate.Address;
import seedu.address.model.candidate.Age;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.candidate.Education;
import seedu.address.model.candidate.Email;
import seedu.address.model.candidate.Gender;
import seedu.address.model.candidate.Name;
import seedu.address.model.candidate.Phone;
import seedu.address.model.joboffer.Job;
import seedu.address.model.joboffer.Salary;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing candidate in the address book.
 */
public class EditCandidateCommand extends Command {

    public static final String COMMAND_WORD = "editc";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the candidate identified "
            + "by the index number used in the displayed candidate list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_AGE + "AGE] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_JOB + "JOB] "
            + "[" + PREFIX_EDUCATION + "EDUCATION] "
            + "[" + PREFIX_SALARY + "SALARY] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Candidate: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This candidate already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the candidate in the filtered candidate list to edit
     * @param editPersonDescriptor details to edit the candidate with
     */
    public EditCandidateCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Candidate> lastShownList = model.getFilteredCandidateList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Candidate candidateToEdit = lastShownList.get(index.getZeroBased());
        Candidate editedCandidate = createEditedPerson(candidateToEdit, editPersonDescriptor);

        if (!candidateToEdit.isSamePerson(editedCandidate) && model.hasCandidate(editedCandidate)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.updateCandidate(candidateToEdit, editedCandidate);
        model.updateFilteredCandidateList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitCandidateBook();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedCandidate));
    }

    /**
     * Creates and returns a {@code Candidate} with the details of {@code candidateToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Candidate createEditedPerson(Candidate candidateToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert candidateToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(candidateToEdit.getName());
        Gender updatedGender = editPersonDescriptor.getGender().orElse(candidateToEdit.getGender());
        Age updatedAge = editPersonDescriptor.getAge().orElse(candidateToEdit.getAge());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(candidateToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(candidateToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(candidateToEdit.getAddress());
        Job updatedJob = editPersonDescriptor.getJob().orElse(candidateToEdit.getJob());
        Education updatedEducation = editPersonDescriptor.getEducation().orElse(candidateToEdit.getEducation());
        Salary updatedSalary = editPersonDescriptor.getSalary().orElse(candidateToEdit.getSalary());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(candidateToEdit.getTags());

        return new Candidate(updatedName, updatedGender, updatedAge, updatedPhone, updatedEmail, updatedAddress,
            updatedJob, updatedEducation, updatedSalary, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCandidateCommand)) {
            return false;
        }

        // state check
        EditCandidateCommand e = (EditCandidateCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the candidate with. Each non-empty field value will replace the
     * corresponding field value of the candidate.
     */

    public static class EditPersonDescriptor {
        private Name name;
        private Gender gender;
        private Age age;
        private Phone phone;
        private Email email;
        private Address address;
        private Job job;
        private Education education;
        private Salary salary;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setGender(toCopy.gender);
            setAge(toCopy.age);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setJob(toCopy.job);
            setEducation(toCopy.education);
            setSalary(toCopy.salary);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, gender, age, phone, email, address, job, education, salary, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setAge(Age age) {
            this.age = age;
        }

        public Optional<Age> getAge() {
            return Optional.ofNullable(age);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setJob(Job job) {
            this.job = job;
        }

        public Optional<Job> getJob() {
            return Optional.ofNullable(job);
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

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getGender().equals(e.getGender())
                    && getAge().equals(e.getAge())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getJob().equals(e.getJob())
                    && getEducation().equals(e.getEducation())
                    && getSalary().equals(e.getSalary())
                    && getTags().equals(e.getTags());
        }
    }
}
