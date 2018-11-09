package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.EditTaskCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Milestone;
import seedu.address.model.task.ModuleCode;
import seedu.address.model.task.PriorityLevel;
import seedu.address.model.task.Task;

//@@author emobeany
/**
 * Edits the details of an existing task that has been added to task book.
 */
public class EditTaskCommand extends Command implements CommandParser {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the displayed task list. "
            + "Existing values will be overwritten by user's input values.\n"
            + "Parameters: " + PREFIX_INDEX + "INDEX "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_MODULE_CODE + "MODULE CODE] "
            + "[" + PREFIX_PRIORITY + "PRIORITY] "
            + "[" + PREFIX_HOURS + "HOURS] \n"
            + "Example: " + COMMAND_WORD + PREFIX_INDEX + "1"
            + PREFIX_DESCRIPTION + "write your own notes"
            + PREFIX_PRIORITY + "high";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "There is nothing to edit. All the fields are unchanged";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the Task book";
    public static final String MESSAGE_NO_FIELD_PROVIDED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditTaskDescriptor editTaskDescriptor;

    public EditTaskCommand() {
        // Null so that it can be initialized in LogicManager
        // Check in JUnit test
        index = null;
        editTaskDescriptor = null;
    }

    /**
     * @param index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditTaskCommand(Index index, EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);

        this.index = index;
        this.editTaskDescriptor = editTaskDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        Task taskToEdit = lastShownList.get(index.getZeroBased());
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        if (taskToEdit.equals(editedTask)) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }

        if (model.isTheExactSameTaskAs(editedTask) || (!taskToEdit.isSameTask(editedTask)
                && model.hasTask(editedTask))) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.updateTask(taskToEdit, editedTask);
        model.updateFilteredTaskList(model.PREDICATE_SHOW_ALL_TASKS);
        model.commitTaskBook();

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}
     */
    private static Task createEditedTask(Task taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Deadline deadline = taskToEdit.getDeadline();
        ModuleCode updatedModuleCode = editTaskDescriptor.getModuleCode().orElse(taskToEdit.getModuleCode());
        String updatedTitle = editTaskDescriptor.getTitle().orElse(taskToEdit.getTitle());
        String updatedDescription = editTaskDescriptor.getDescription().orElse(taskToEdit.getDescription());
        PriorityLevel updatedPriority = editTaskDescriptor.getPriorityLevel().orElse(taskToEdit.getPriorityLevel());
        int updatedHours = editTaskDescriptor.getExpectedNumOfHours().orElse(taskToEdit.getExpectedNumOfHours());
        int completedNumOfHours = taskToEdit.getCompletedNumOfHours();
        boolean isCompleted = taskToEdit.isCompleted();
        List<Milestone> milestoneList = taskToEdit.getMilestoneList();
        Set<Tag> tag = taskToEdit.getTags();

        return new Task(deadline, updatedModuleCode, updatedTitle, updatedDescription, updatedPriority, updatedHours,
                completedNumOfHours, isCompleted, milestoneList, tag);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instance of handles nulls
        if (!(other instanceof EditTaskCommand)) {
            return false;
        }

        // state check
        EditTaskCommand edit = (EditTaskCommand) other;
        return index.equals(edit.index)
                && editTaskDescriptor.equals(edit.editTaskDescriptor);
    }

    /**
     * Stores details to be edited into existing Task
     * Each non-empty field value will replace the existing corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private String title;
        private String description;
        private ModuleCode moduleCode;
        private PriorityLevel priorityLevel;
        private Integer expectedNumOfHours;

        public EditTaskDescriptor() {}

        /**
         * Copy constructor
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setTitle(toCopy.title);
            setDescription(toCopy.description);
            setModuleCode(toCopy.moduleCode);
            setPriorityLevel(toCopy.priorityLevel);
            setExpectedNumOfHours(toCopy.expectedNumOfHours);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, description, moduleCode, priorityLevel, expectedNumOfHours);
        }

        public Optional<String> getTitle() {
            return Optional.ofNullable(title);
        }

        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }

        public Optional<ModuleCode> getModuleCode() {
            return Optional.ofNullable(moduleCode);
        }

        public Optional<PriorityLevel> getPriorityLevel() {
            return Optional.ofNullable(priorityLevel);
        }

        public Optional<Integer> getExpectedNumOfHours() {
            return Optional.ofNullable(expectedNumOfHours);
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setModuleCode(ModuleCode moduleCode) {
            this.moduleCode = moduleCode;
        }

        public void setPriorityLevel(PriorityLevel priorityLevel) {
            this.priorityLevel = priorityLevel;
        }

        public void setExpectedNumOfHours(Integer expectedNumOfHours) {
            this.expectedNumOfHours = expectedNumOfHours;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }

            // state check
            EditTaskDescriptor edit = (EditTaskDescriptor) other;

            return getTitle().equals(edit.getTitle())
                    && getDescription().equals((edit.getDescription()))
                    && getModuleCode().equals(edit.getModuleCode())
                    && getPriorityLevel().equals(edit.getPriorityLevel())
                    && getExpectedNumOfHours().equals((edit.getExpectedNumOfHours()));
        }
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new EditTaskCommandParser().parse(arguments);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
