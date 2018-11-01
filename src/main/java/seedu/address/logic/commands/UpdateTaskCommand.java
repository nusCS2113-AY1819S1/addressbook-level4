package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

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
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Body;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;

/**
 * Update the details of an existing task in the task book.
 */
public class UpdateTaskCommand extends Command {
    public static final String COMMAND_WORD = "updateTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Update the details of an existing task "
            + "by the index number used in the displayed task list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "TASKNAME] "
            + "[" + PREFIX_BODY + "BODY] "
            + "[" + PREFIX_START + "START DATETIME] "
            + "[" + PREFIX_END + "END DATETIME] "
            + "[" + PREFIX_PRIORITY + "PRIORITY] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_END + "2/11 "
            + PREFIX_TAG + "hardcopy";

    public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Updated Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task book.";


    private final Index index;
    private final UpdateTaskDescriptor updateTaskDescriptor;

    /**
     * @param index of the task in the filtered task list to edit
     * @param updateTaskDescriptor details to edit the task with
     */
    public UpdateTaskCommand(Index index, UpdateTaskDescriptor updateTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(updateTaskDescriptor);

        this.index = index;
        this.updateTaskDescriptor = new UpdateTaskDescriptor(updateTaskDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToUpdate = lastShownList.get(index.getZeroBased());
        Task updatedTask = createUpdatedTask(taskToUpdate, updateTaskDescriptor);

        model.updateTask(taskToUpdate, updatedTask);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.commitAddressBook();

        if (!taskToUpdate.isSameTask(updatedTask) && model.hasTask(updatedTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS, updatedTask));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToUpdate}
     * updated with {@code updateTaskDescriptor}.
     */
    private static Task createUpdatedTask(Task taskToUpdate, UpdateTaskDescriptor updateTaskDescriptor) {
        assert taskToUpdate != null;

        TaskName updatedTaskName = updateTaskDescriptor.getTaskName().orElse(taskToUpdate.getTaskName());
        Body updatedBody = updateTaskDescriptor.getBody().orElse(taskToUpdate.getBody());
        DateTime updatedStartDateTime = updateTaskDescriptor.getStartDateTime().orElse(taskToUpdate.getStartDateTime());
        DateTime updatedEndDateTime = updateTaskDescriptor.getEndDateTime().orElse(taskToUpdate.getEndDateTime());
        Priority updatedPriority = updateTaskDescriptor.getPriority().orElse(taskToUpdate.getPriority());
        Set<Tag> updatedTags = updateTaskDescriptor.getTags().orElse(taskToUpdate.getTags());

        return new Task(updatedTaskName, updatedBody, updatedStartDateTime, updatedEndDateTime,
                updatedPriority, updatedTags);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class UpdateTaskDescriptor {
        private TaskName taskName;
        private Body body;
        private DateTime startDateTime;
        private DateTime endDateTime;
        private Priority priority;
        private Set<Tag> tags;

        public UpdateTaskDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public UpdateTaskDescriptor(UpdateTaskDescriptor toCopy) {
            setTaskName(toCopy.taskName);
            setBody(toCopy.body);
            setStartDateTime(toCopy.startDateTime);
            setEndDateTime(toCopy.endDateTime);
            setPriority(toCopy.priority);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(taskName, body, startDateTime, endDateTime, priority, tags);
        }

        public void setTaskName(TaskName taskName) {
            this.taskName = taskName;
        }

        public Optional<TaskName> getTaskName() {
            return Optional.ofNullable(taskName);
        }

        public void setBody(Body body) {
            this.body = body;
        }

        public Optional<Body> getBody() {
            return Optional.ofNullable(body);
        }

        public void setStartDateTime(DateTime startDateTime) {
            this.startDateTime = startDateTime;
        }

        public Optional<DateTime> getStartDateTime() {
            return Optional.ofNullable(startDateTime);
        }

        public void setEndDateTime(DateTime endDateTime) {
            this.endDateTime = endDateTime;
        }

        public Optional<DateTime> getEndDateTime() {
            return Optional.ofNullable(endDateTime);
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
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
            if (!(other instanceof UpdateTaskDescriptor)) {
                return false;
            }

            // state check
            UpdateTaskDescriptor e = (UpdateTaskDescriptor) other;

            return getTaskName().equals(e.getTaskName())
                    && getBody().equals(e.getBody())
                    && getStartDateTime().equals(e.getStartDateTime())
                    && getEndDateTime().equals(e.getEndDateTime())
                    && getPriority().equals(e.getPriority())
                    && getTags().equals(e.getTags());
        }
    }
}
