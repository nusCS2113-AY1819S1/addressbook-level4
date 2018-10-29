# chelseyong
###### \java\seedu\address\logic\commands\AddTaskCommand.java
``` java
/**
 * Adds a task to the task book
 */
public class AddTaskCommand extends Command implements CommandParser {
    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task book. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_MODULE_CODE + "MODULE CODE "
            + PREFIX_PRIORITY + "PRIORITY "
            + PREFIX_HOURS + "HOURS \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Complete code refactoring "
            + PREFIX_DESCRIPTION + "refer to notes "
            + PREFIX_MODULE_CODE + "CS2113 "
            + PREFIX_PRIORITY + "high "
            + PREFIX_HOURS + "2";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task book";
    public static final int MAX_HOURS_TO_COMPLETE = 24;
    private final Task toAdd;
    public AddTaskCommand() {
        toAdd = null;
    }
    /**
     * Creates an AddCommand to add the specified {@code Task}
     */
    public AddTaskCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.hasTask(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } else if (toAdd.getExpectedNumOfHours() == 0) {
            throw new CommandException(MESSAGE_ZERO_HOURS_COMPLETION);
        } else if (toAdd.getExpectedNumOfHours() >= MAX_HOURS_TO_COMPLETE) {
            throw new CommandException(MESSAGE_MAX_HOURS);
        }

        toAdd.setDeadline(model.getDeadline());
        model.addTask(toAdd);
        model.commitTaskBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new AddTaskCommandParser().parse(arguments);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
```
###### \java\seedu\address\logic\commands\CommandParser.java
``` java
/**
 * CommandParser is able to
 * pass in arguments
 */
public interface CommandParser {
    public Command parse(String arguments) throws ParseException;

    public String getCommandWord();
}
```
###### \java\seedu\address\logic\commands\CompleteTaskCommand.java
``` java
/**
 * Completes a task in the Task Book
 */
public class CompleteTaskCommand extends Command implements CommandParser {
    public static final String COMMAND_WORD = "complete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Completes the task identified by the index number used in the displayed task list,"
            + " under a certain number of hours\n"
            + "Parameters: " + PREFIX_INDEX + " INDEX(must be a positive integer) "
            + PREFIX_HOURS + "HOURS\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_HOURS + "2";

    public static final String MESSAGE_SUCCESS = "Task completed: %1$s";

    private final Index targetIndex;
    private final int completedNumOfHours;
    public CompleteTaskCommand() {
        // Null so that it can be initialized in LogicManager
        // Check in JUnit test
        targetIndex = null;
        completedNumOfHours = 0;
    }
    /**
     * Creates an CompleteTaskCommand to add the specified {@code Task}
     */
    public CompleteTaskCommand(Index targetIndex, int completedNumOfHours) {
        this.targetIndex = targetIndex;
        this.completedNumOfHours = completedNumOfHours;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } else if (completedNumOfHours == 0) {
            throw new CommandException(Messages.MESSAGE_ZERO_HOURS_COMPLETION);
        } else if (completedNumOfHours >= MAX_HOURS_TO_COMPLETE) {
            throw new CommandException(MESSAGE_MAX_HOURS);
        }
        Task taskToComplete = lastShownList.get(targetIndex.getZeroBased());
        if (taskToComplete.isCompleted()) {
            throw new CommandException(Messages.MESSAGE_COMPLETED_TASK);
        }
        model.completeTask(taskToComplete, completedNumOfHours);
        model.commitTaskBook();
        Task completedTask = lastShownList.get(targetIndex.getZeroBased());
        return new CommandResult(String.format(MESSAGE_SUCCESS, completedTask));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompleteTaskCommand // instanceof handles nulls
                && targetIndex.equals(((CompleteTaskCommand) other).targetIndex));
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new CompleteTaskCommandParser().parse(arguments);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
/**
 * Deletes a task identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command implements CommandParser {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the displayed task list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    private final Index targetIndex;

    public DeleteCommand() {
        // Null so that it can be initialized in LogicManager
        // Check in JUnit test
        targetIndex = null;
    }
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteTask(taskToDelete);
        model.commitTaskBook();
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new DeleteCommandParser().parse(arguments);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
```
###### \java\seedu\address\logic\commands\TrackProductivityCommand.java
``` java
/**
 * Tracks the productivity of tasks
 * for the previous week based on hours
 */
public class TrackProductivityCommand extends Command implements CommandParser {
    public static final String COMMAND_WORD = "track";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tracks your productivity.";

    public static final String MESSAGE_SUCCESS = "Recent productvity: %1$s";

    public static final String MESSAGE_NO_COMPLETED_TASK = "There are no completed tasks yet. Start working!";
    //private static final Logger logger = LogsCenter.getLogger(TrackProductivityCommand.class);

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        // filter out Completed tasks
        model.trackProductivity();
        ObservableList<Task> tasks = model.getFilteredTaskList();
        if (tasks.size() == 0) {
            throw new CommandException(MESSAGE_NO_COMPLETED_TASK);
        }
        double productivity = calculateProductivity(tasks);
        String result = Integer.toString((int) (productivity * 100)) + " %";
        return new CommandResult(String.format(MESSAGE_SUCCESS, result));
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new TrackProductivityCommand();
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    /**
     * Calculates the overall productivity for completed
     * @param tasks
     * @return the average productivity
     */
    public double calculateProductivity(ObservableList<Task> tasks) {
        double averageProductivity;
        double totalProductivity = 0;
        for (Task task: tasks) {
            double taskProductivity = (double) task.getExpectedNumOfHours() / task.getCompletedNumOfHours();
            totalProductivity += taskProductivity;
        }
        //if (totalProductivity == 0)
        averageProductivity = totalProductivity / tasks.size();
        return averageProductivity;
    }
}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        // need to add the commands into the list<CommandParser> commands in TaskBookParser
        taskBookParser = new TaskBookParser(new AddTaskCommand(),
                new ClearCommand(),
                new CompleteTaskCommand(),
                new DeferDeadlineCommand(),
                new DeleteCommand(),
                new ListCommand(),
                new TrackProductivityCommand(),
                new SelectDeadlineCommand(),
                new SortTaskCommand(),
                new HelpCommand(),
                new ExitCommand(),
                new HistoryCommand(),
                new UndoCommand(),
                new RedoCommand(),
                new AddMilestoneCommand()
        );
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = taskBookParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }
```
###### \java\seedu\address\logic\parser\AddTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {
    private static final Logger logger = LogsCenter.getLogger(AddTaskCommandParser.class);
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    protected static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Override
    public AddTaskCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_MODULE_CODE, PREFIX_TITLE, PREFIX_DESCRIPTION,
                        PREFIX_PRIORITY, PREFIX_HOURS);
        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE, PREFIX_TITLE, PREFIX_DESCRIPTION,
                PREFIX_PRIORITY, PREFIX_HOURS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        String moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get());
        String title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        String description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        PriorityLevel priority = ParserUtil.parsePriorityLevel(argMultimap.getValue(PREFIX_PRIORITY).get());
        int expectedNumOfHours = ParserUtil.parseHours(argMultimap.getValue(PREFIX_HOURS).get());
        Task task = new Task(moduleCode, title, description, priority, expectedNumOfHours);

        return new AddTaskCommand(task);
    }
}
```
###### \java\seedu\address\logic\parser\CompleteTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new CompleteTaskCommand object
 */
public class CompleteTaskCommandParser implements Parser<CompleteTaskCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the CompleteTaskCommand
     * and returns an CompleteTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public CompleteTaskCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_INDEX, PREFIX_HOURS);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_HOURS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteTaskCommand.MESSAGE_USAGE));
        }
        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        int expectedNumOfHours = ParserUtil.parseHours(argMultimap.getValue(PREFIX_HOURS).get());

        return new CompleteTaskCommand(index, expectedNumOfHours);
    }
}


```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Leading and trailing whitespaces will be trimmed from {@code String hours}
     * If hours is not an integer or is too big to be an integer,
     * @throws ParseException
     */
    public static int parseHours(String hours) throws ParseException {
        requireNonNull(hours);
        String trimmedHours = hours.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedHours)) {
            throw new ParseException(MESSAGE_INVALID_HOURS);
        }
        return Integer.parseInt(trimmedHours);
    }

    /**
     * Leading and trailing whitespaces will be trimmed from {@code String moduleCode}
     */
    public static String parseModuleCode(String moduleCode) {
        requireNonNull(moduleCode);
        String trimmedModuleCode = moduleCode.trim();
        return trimmedModuleCode;
    }

    /**
     * Parses a {@code String deadline} into an {@code Deadline}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code deadline} is invalid.
     */
    public static Deadline parseDeadline(String deadline) throws ParseException {
        requireNonNull(deadline);
        String trimmedDeadline = deadline.trim();
        //TODO prevent 1/1
        if (!Deadline.isValidDeadline(trimmedDeadline)) {
            throw new ParseException(Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
        }
        return new Deadline(trimmedDeadline);
    }

    /**
     * Parses a {@code String priority} into an {@code Priority}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code priority} is invalid.
     */
    public static PriorityLevel parsePriorityLevel(String priority) throws ParseException {
        requireNonNull(priority);
        String trimmedPriority = priority.trim();
        if (!PriorityLevel.isValidPriorityLevel(trimmedPriority)) {
            throw new ParseException(PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS);
        }
        //        return new PriorityLevel(trimmedPriority);
        return new PriorityLevel(trimmedPriority);
    }

    /**
     * Leading and trailing whitespaces will be trimmed from {@code String title}
     */
    public static String parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        return trimmedTitle;
    }

    /**
     * Leading and trailing whitespaces will be trimmed from {@code String description}
     */
    public static String parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        return trimmedDescription;
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void deleteTask(Task target) {
        versionedTaskBook.removeTask(target);
        indicateTaskBookChanged();
    }

    @Override
    public void completeTask(Task target, int hours) {
        versionedTaskBook.completeTask(target, hours);
        indicateTaskBookChanged();
    }

    @Override
    public void addTask(Task task) {
        versionedTaskBook.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateTaskBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Updates the task to completed tasks only
     * So that productivity can be correctly calculated
     */
    public void trackProductivity() {
        updateFilteredTaskList(predicateShowCompletedTasks);
        indicateTaskBookChanged();
    }
}
```
