package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MINIMUM_EXPERIENCE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECRUITMENT;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recruitment.JobDescription;
import seedu.address.model.recruitment.Post;
import seedu.address.model.recruitment.Recruitment;
import seedu.address.model.recruitment.WorkExp;

/**
 * Edits the details of an existing recruitment post in the address book.
 */
public class EditRecruitmentPostCommand extends Command {

    public static final String COMMAND_WORD = "editRecruitmentPost";
    public static final String COMMAND_ALIAS = "erp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the recruitment post identified "
            + "by the index number used in the displayed recruitment list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_JOB_POSITION + "Job Position] "
            + "[" + PREFIX_MINIMUM_EXPERIENCE + "Minimal Working experience] "
            + "[" + PREFIX_JOB_DESCRIPTION + "Job description]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_JOB_POSITION + "IT Manager "
            + PREFIX_MINIMUM_EXPERIENCE + "3 "
            + PREFIX_JOB_DESCRIPTION + "To maintain the company server ";

    public static final String MESSAGE_EDIT_RECRUITMENT_POST_SUCCESS = "Edited Recruitment Post: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_JOB_POSITION + "IT Manager "
            + PREFIX_MINIMUM_EXPERIENCE + "3 "
            + PREFIX_JOB_DESCRIPTION + "To maintain the company server ";

    public static final String MESSAGE_DUPLICATE_POSITION = "This job position already exists in the address book";
    public static final String MESSAGE_DUPLICATE_WORK_EXP = "This working experience already exists in the "
            + "address book";
    public static final String MESSAGE_DUPLICATE_JOB_DESCRIPTION = "This job description already exists in "
            + "the address book";

    private static boolean isPostDuplicated = false;
    private static boolean isWorkExpDuplicated = false;
    private static boolean isJobDescriptionDuplicated = false;
    private final Index index;
    private final EditPostDescriptor editPostDescriptor;

    /**
     * @param index of the recruitment post in the filtered recruitment list to edit
     * @param editPostDescriptor details to edit the post with
     */
    public EditRecruitmentPostCommand(Index index, EditPostDescriptor editPostDescriptor) {
        requireNonNull(index);
        requireNonNull(editPostDescriptor);

        this.index = index;
        this.editPostDescriptor = new EditPostDescriptor(editPostDescriptor);
    }


    public static void setIsPostDuplicated(boolean verifyPostDuplication) {
        isPostDuplicated = verifyPostDuplication;
    }

    public static void setIsWorkExpDuplicated(boolean verifyWorkExpDuplication) {
        isWorkExpDuplicated = verifyWorkExpDuplication;
    }

    public static void setIsJobDescriptionDuplicated(boolean verifyJobDescriptionDuplication) {
        isJobDescriptionDuplicated = verifyJobDescriptionDuplication;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Recruitment> lastShownList = model.getFilteredRecruitmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECRUITMENT_POST_DISPLAYED_INDEX);
        }

        Recruitment postToEdit = lastShownList.get(index.getZeroBased());
        Recruitment editedPost = createEditedPost(postToEdit, editPostDescriptor);

        if (model.hasRecruitment(editedPost) && isPostDuplicated
                && !isWorkExpDuplicated && !isJobDescriptionDuplicated) {
            throw new CommandException(MESSAGE_DUPLICATE_POSITION);
        } else if (model.hasRecruitment(editedPost) && !isPostDuplicated
                && isWorkExpDuplicated && !isJobDescriptionDuplicated) {
            throw new CommandException(MESSAGE_DUPLICATE_WORK_EXP);
        } else if (model.hasRecruitment(editedPost) && !isPostDuplicated
                && !isWorkExpDuplicated && isJobDescriptionDuplicated) {
            throw new CommandException(MESSAGE_DUPLICATE_JOB_DESCRIPTION);
        }


        model.updateRecruitment(postToEdit, editedPost);
        model.updateFilteredRecruitmentList(PREDICATE_SHOW_ALL_RECRUITMENT);
        model.commitRecruitmentPostList();
        return new CommandResult(String.format(MESSAGE_EDIT_RECRUITMENT_POST_SUCCESS, editedPost));
    }


    /**
     * Creates and returns a {@code recruitmentPost} with the details of {@code postToEdit}
     * edited with {@code editPostDescriptor}.
     */
    private static Recruitment createEditedPost(Recruitment postToEdit, EditPostDescriptor editPostDescriptor) {
        assert postToEdit != null;

        Post updatedPost = editPostDescriptor.getPost().orElse(postToEdit.getPost());
        WorkExp updatedWorkExp = editPostDescriptor.getWorkExp().orElse(postToEdit.getWorkExp());
        JobDescription updatedJobDescription = editPostDescriptor.getJobDescription().orElse(
                postToEdit.getJobDescription());

        return new Recruitment(updatedPost, updatedWorkExp, updatedJobDescription);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditRecruitmentPostCommand)) {
            return false;
        }

        // state check
        EditRecruitmentPostCommand e = (EditRecruitmentPostCommand) other;
        return index.equals(e.index)
                && editPostDescriptor.equals(e.editPostDescriptor);
    }

    /**
     * Stores the details to edit the recruitmentPost with. Each non-empty field value will replace the
     * corresponding field value of the recruitmentPost.
     */
    public static class EditPostDescriptor {
        private Post post;
        private WorkExp workExp;
        private JobDescription jobDescription;

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPostDescriptor(EditPostDescriptor toCopy) {
            setPost(toCopy.post);
            setWorkExp(toCopy.workExp);
            setJobDescription(toCopy.jobDescription);
        }

        public EditPostDescriptor() {

        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(post, workExp, jobDescription);
        }

        public void setPost(Post post) {
            this.post = post;
        }

        public Optional<Post> getPost() {
            return Optional.ofNullable(post);
        }

        public void setWorkExp(WorkExp workExp) {
            this.workExp = workExp;
        }

        public Optional<WorkExp> getWorkExp() {
            return Optional.ofNullable(workExp);
        }

        public void setJobDescription(JobDescription jobDescription) {
            this.jobDescription = jobDescription;
        }

        public Optional<JobDescription> getJobDescription() {
            return Optional.ofNullable(jobDescription);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPostDescriptor)) {
                return false;
            }

            // state check
            EditPostDescriptor e = (EditPostDescriptor) other;

            return getPost().equals(e.getPost())
                    && getWorkExp().equals(e.getWorkExp())
                    && getJobDescription().equals(e.getJobDescription());
        }
    }
}
