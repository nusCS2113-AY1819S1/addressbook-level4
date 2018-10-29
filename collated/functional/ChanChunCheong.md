# ChanChunCheong
###### \java\seedu\address\logic\commands\DeferDeadlineCommand.java
``` java
/**
 * Defer deadline of a specific task in the taskbook.
 */

public class DeferDeadlineCommand extends Command implements CommandParser {

    public static final String COMMAND_WORD = "defer";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Defers the deadline of the selected task in the taskbook. "
            + "Existing deadline will be overwritten by the input. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DEADLINE + "deadline \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DEADLINE + "04011996";
    /*
    + PREFIX_DAY + "DAY"
    + PREFIX_MONTH + "MONTH"
    + PREFIX_YEAR +  "YEAR \n"
    + "Example: " + COMMAND_WORD + "1"
    + PREFIX_DAY + "01"
    + PREFIX_MONTH + "01"
    + PREFIX_YEAR + "2018";
    */

    public static final String MESSAGE_INVALID_DEADLINE = "The date selected does not exist";
    public static final String MESSAGE_NONEXISTENT_TASK = "This task does not exist in the task book";
    public static final String MESSAGE_SUCCESS = "Date deferred for task: %1$s";
    //public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Defer deadline command not implemented yet";

    private final Index taskIndex;
    private final Deadline deadline;

    public DeferDeadlineCommand() {
        // Null so that it can be initialized in LogicManager
        // Check in JUnit test
        taskIndex = null;
        deadline = null;
    }

    /**
     * Creates an DeferDeadlineCommand to add the specified {@code Task & @code Deadline}
     */
    public DeferDeadlineCommand(Index taskIndex, Deadline deadline) {
        requireNonNull(taskIndex);
        requireNonNull(deadline);
        this.taskIndex = taskIndex;
        this.deadline = deadline;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (taskIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_NONEXISTENT_TASK);
        }

        Task taskToDefer = lastShownList.get(taskIndex.getZeroBased()); // get the task from the filteredtasklist;
        model.deferTaskDeadline(taskToDefer, deadline);
        model.commitTaskBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToDefer));

        /*
        requireNonNull(model);
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
        */
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new DeferDeadlineCommandParser().parse(arguments);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
```
###### \java\seedu\address\logic\commands\SortTaskCommand.java
``` java
/**
 * Sorts the tasks list in the task book based on the method chosen
 */
public class SortTaskCommand extends Command implements CommandParser {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sort the tasks in the task book by preferred way. "
            + "Parameters: "
            + PREFIX_SORT + "METHOD \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SORT + "modules";

    //public static final String MESSAGE_NOT_IMPLEMENTED_YET = "SortTask command not implemented yet";
    public static final String MESSAGE_ARGUMENTS = "method: %1$s";
    public static final String MESSAGE_SUCCESS = "Sort task based on: %1$s";
    private final String method;

    public SortTaskCommand() {
        // Null so that it can be initialized in LogicManager
        // Check in JUnit test
        method = null;
    }

    /**
     * Creates an DeferDeadlineCommand to add the specified {@code Task & @code Deadline}
     */
    public SortTaskCommand(String method) {
        requireNonNull(method);
        this.method = method.toLowerCase();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        requireNonNull(model);
        //throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
        //throw new CommandException(String.format(MESSAGE_ARGUMENTS, method));
        if (method.equals("modules") || method.equals("deadlines") || method.equals("priority")
                || method.equals("title")) {
            model.sortTask(method);
            model.commitTaskBook();
            return new CommandResult(String.format(MESSAGE_SUCCESS, method));
        } else {
            //if the methods called are not within the list of methods called then throw CommandException
            throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortTaskCommand.MESSAGE_USAGE));
        }

    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new SortTaskCommandParser().parse(arguments);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    /*
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof SortTaskCommand)) {
            return false;
        }
        // state check
        SortTaskCommand e = (SortTaskCommand) other;
        return method.equals(e.method);
    }
    */
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Defer {@code key} previous deadline with (@code deadline) from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void deferDeadline(Task key, Deadline deadline) {
        requireNonNull(deadline);
        tasks.defer(key, deadline);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeTask(Task key) {
        tasks.remove(key);
    }

```
###### \java\seedu\address\model\Model.java
``` java
    void sortTask(String method);

    /** Gets deadline previously selected from the TaskBook.*/
    Deadline getDeadline();

    /** Returns true if input deadline is valid.*/
    boolean validDeadline(Deadline deadline);

    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     * {@code target} must exist in the task book.
     * The task identity of {@code editedTask} must not be the same as another existing task in the task book.
     */
    void updateTask(Task target, Task editedTask);

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    /**
     * Returns true if the model has previous task book states to restore.
     */
    boolean canUndoTaskBook();

    /**
     * Returns true if the model has undone task book states to restore.
     */
    boolean canRedoTaskBook();

    /**
     * Restores the model's task book to its previous state.
     */
    void undoTaskBook();

    /**
     * Restores the model's task book to its previously undone state.
     */
    void redoTaskBook();

    /**
     * Saves the current task book state for undo/redo.
     */
    void commitTaskBook();

    /**
     * Updates task list to
     * contain only completed tasks
     */
    public void trackProductivity();
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void deferTaskDeadline(Task target, Deadline deadline) {
        versionedTaskBook.deferDeadline(target, deadline);
        indicateTaskBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortTask(String method) {
        versionedTaskBook.sortTask(method);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateTaskBookChanged();
    }

```
###### \java\seedu\address\model\task\PriorityLevel.java
``` java
        switch(priority) {
        case ("low"): {
            priorityLevelInt = 3;
            break;
        }
        case ("medium"): {
            priorityLevelInt = 2;
            break;
        }
        case ("high"): {
            priorityLevelInt = 1;
            break;
        }
        default:
            priorityLevelInt = 0;
        }
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidPriorityLevel(String test) {
        String testInLowerCase = test.toLowerCase();
        if (testInLowerCase.equals("low") || testInLowerCase.equals("medium")
                || testInLowerCase.equals("high")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return priorityLevel;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PriorityLevel // instanceof handles nulls
                && priorityLevel.equals(((PriorityLevel) other).priorityLevel)); // state check
    }

    @Override
    public int hashCode() {
        return priorityLevel.hashCode();
    }
}
```
###### \java\seedu\address\model\task\SortTaskList.java
``` java
/**
 * SortTaskList is a comparator for the task book to sort according to lexicographical order
 */
public class SortTaskList {
    /**
     * Return 0 if self = other. Return 1 if self > other. Return -1 if self < other.
     * @param internalList
     * @param method
     * @return SortedList
     */
    public ObservableList<Task> sortTask(ObservableList<Task> internalList, String method) {

        FXCollections.sort(internalList, new Comparator<Task>() {
            @Override
            public int compare(Task self, Task other) {
                switch(method) {
                    case ("modules"): {
                        return self.getModuleCode().toLowerCase().compareTo(other.getModuleCode().toLowerCase());
                    }
                    case ("deadlines"): {
                        return self.getDeadline().toString().compareTo(other.getDeadline().toString());
                    }
                    case ("priority"): {
                        return self.getPriorityLevelInt() - other.getPriorityLevelInt();
                    }
                    case ("title"): {
                        return self.getTitle().toLowerCase().compareTo(other.getTitle().toLowerCase());
                    }
                    default:
                        return 0;
                }
            }

        });
        return internalList;
    }
}
```
###### \java\seedu\address\model\task\Task.java
``` java
    public int getPriorityLevelInt() {
        return priorityLevel.priorityLevelInt;
    }
```
###### \java\seedu\address\model\task\Task.java
``` java
    /**
     * Defers the task to a later
     * @param deadline
     * @return the new Task
     */
    public Task deferred(Deadline deadline) {
        Task deferredTask = new Task(this);
        deferredTask.deadline = deadline;
        return deferredTask;
    }

    //@@JeremyInElysium
    /**
     * Add a milestone to the task.
     */
    public Task addMilestone(Milestone milestone) {
        Task taskWithMilestones = new Task(this);
        taskWithMilestones.milestoneList.add(milestone);
        return taskWithMilestones;
    }

    /**
     * @return list of milestones for the task.
     */
    public Set<Milestone> getMilestoneList() {
        return Collections.unmodifiableSet(milestoneList);
    }
    /**
     * Returns true if both tasks have the same data fields.
     * This defines a stronger notion of equality between two tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getTitle().equals(getTitle())
                && otherTask.getDeadline().equals(getDeadline())
                && otherTask.getDescription().equals(getDescription())
                && otherTask.getPriorityLevel().equals(getPriorityLevel())
                && otherTask.isCompleted() == isCompleted()
                && otherTask.getExpectedNumOfHours() == getExpectedNumOfHours()
                && otherTask.getCompletedNumOfHours() == getCompletedNumOfHours();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(deadline, title, description, priorityLevel, expectedNumOfHours,
                completedNumOfHours, isCompleted);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDeadline())
                .append(" | ")
                .append(getTitle())
                .append(" : ")
                .append(getDescription())
                .append(" Priority: ")
                .append(getPriorityLevel());
        /*builder.append(" Expected: ");
        builder.append(expectedNumOfHours);
        builder.append(" completed? ");
        builder.append(isCompleted);
        builder.append(" completed hours? ");
        builder.append(completedNumOfHours);
        builder.append(" Module code: ");
        builder.append(moduleCode);*/
        return builder.toString();
    }
}
```
###### \java\seedu\address\model\task\UniqueTaskList.java
``` java
    /**
     * Defer the deadline of the task (@code target) in the list with (@code deadline).
     * (@code target) must exist in the list.
     */
    public void defer(Task target, Deadline deadline) {
        requireNonNull(target);
        requireNonNull(deadline);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }
        Task deferredTask = target.deferred(deadline);
        internalList.set(index, deferredTask);
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The task identity of {@code editedPerson} must not be the same as another existing task in the list.
     */
    public void setTask(Task target, Task editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        if (!target.isSameTask(editedPerson) && contains(editedPerson)) {
            throw new DuplicateTaskException();
        }
        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent task from the list.
     * The task must exist in the list.
     */
    public void remove(Task toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TaskNotFoundException();
        }
    }

    /**
     * Complete a task in the list.
     * The task must exist in the list.
     */
    public void complete(Task toComplete, int hours) {
        requireNonNull(toComplete);
        int index = internalList.indexOf(toComplete);
        if (index == -1) {
            throw new TaskNotFoundException();
        }
        Task completedTask = toComplete.completed(hours);
        internalList.set(index, completedTask);
    }

    public void setTasks(UniqueTaskList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setTasks(List<Task> tasks) {
        requireAllNonNull(tasks);
        if (!tasksAreUnique(tasks)) {
            throw new DuplicateTaskException();
        }
        internalList.setAll(tasks);
    }
    /*
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }


    public ObservableList<Task> obtainObservableList() {
        return internalList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code tasks} contains only unique tasks.
     */
    private boolean tasksAreUnique(List<Task> tasks) {
        for (int i = 0; i < tasks.size() - 1; i++) {
            for (int j = i + 1; j < tasks.size(); j++) {
                if (tasks.get(i).isSameTask(tasks.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
```
