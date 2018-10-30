# chelseyong
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String VALID_DEADLINE_1ST_JAN = "1/1/2018";
    public static final String VALID_DEADLINE_31ST_MARCH = "31/3/2018";
    public static final String VALID_DEADLINE_12TH_MAY = "12/5/2018";
    public static final String VALID_MODULE_CODE_CS2113 = "CS2113";
    public static final String VALID_MODULE_CODE_CG2271 = "CG2271";
    public static final String VALID_TITLE_1 = "Complete CS2113 Homework";
    public static final String VALID_TITLE_2 = "Start coding test units";
    public static final String VALID_TITLE_3 = "Prepare OP2";
    public static final String VALID_DESCRIPTION_1 = "Refer to notes";
    public static final String VALID_DESCRIPTION_2 = "Do this before integration tests";
    public static final String VALID_DESCRIPTION_3 = "OP2 has high weightage";
    public static final String VALID_PRIORITY_LEVEL_LOW = "low";
    public static final String VALID_PRIORITY_LEVEL_HIGH = "high";
    public static final String VALID_PRIORITY_LEVEL_MEDIUM = "medium";
    public static final String VALID_1_HOUR = "1";
    public static final String VALID_2_HOURS = "2";

```
###### \java\seedu\address\logic\commands\CompleteTaskCommandTest.java
``` java
public class CompleteTaskCommandTest {

    private static final Logger logger = LogsCenter.getLogger(CompleteTaskCommand.class);
    private Model model = new ModelManager(getTypicalTaskBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        int completedHours = 1;
        Task taskToComplete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task completedTask = new TaskBuilder(taskToComplete).withCompletedNumOfHours(completedHours).build();
        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(INDEX_FIRST_TASK, completedHours);

        String expectedMessage = String.format(CompleteTaskCommand.MESSAGE_SUCCESS, completedTask);

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.completeTask(taskToComplete, completedHours);
        expectedModel.commitTaskBook();

        assertCommandSuccess(completeTaskCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(outOfBoundIndex, 1);

        assertCommandFailure(completeTaskCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_taskCompletedAlready_throwsCommandException() {
        int completedHours = 1;
        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(INDEX_FIRST_TASK, 1);
        Task taskToComplete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task completedTask = new TaskBuilder(taskToComplete).withCompletedNumOfHours(completedHours).build();

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(taskToComplete, completedTask);
        assertCommandFailure(completeTaskCommand, expectedModel, commandHistory, Messages.MESSAGE_COMPLETED_TASK);
    }

    @Test
    public void execute_taskCompletedZeroHours_throwsCommandException() {
        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(INDEX_FIRST_TASK, 0);
        assertCommandFailure(completeTaskCommand, model, commandHistory, Messages.MESSAGE_ZERO_HOURS_COMPLETION);
    }

    @Test
    public void execute_taskCompletedMaxHours_throwsCommandException() {
        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(INDEX_FIRST_TASK, MAX_HOURS);
        assertCommandFailure(completeTaskCommand, model, commandHistory, Messages.MESSAGE_MAX_HOURS);
    }

    @Test
    public void executeUndoRedo_validIndexFilteredList_success() throws Exception {
        int completedNumOfHours = 1;
        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(INDEX_FIRST_TASK, completedNumOfHours);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Task taskToComplete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        expectedModel.completeTask(taskToComplete, completedNumOfHours);
        expectedModel.commitTaskBook();

        // complete -> completes first task
        completeTaskCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoTaskBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.redoTaskBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        int completedNumOfHours = 1;
        CompleteTaskCommand completeFirstCommand = new CompleteTaskCommand(INDEX_FIRST_TASK, completedNumOfHours);
        CompleteTaskCommand completeSecondCommand = new CompleteTaskCommand(INDEX_SECOND_TASK, 2);

        // same object -> returns true
        assertTrue(completeFirstCommand.equals(completeFirstCommand));

        // same values -> returns true
        CompleteTaskCommand completeFirstCommandCopy = new CompleteTaskCommand(INDEX_FIRST_TASK, completedNumOfHours);
        assertTrue(completeFirstCommand.equals(completeFirstCommandCopy));

        // different types -> returns false
        assertFalse(completeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(completeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(completeFirstCommand.equals(completeSecondCommand));
    }

}
```
###### \java\seedu\address\logic\commands\TrackProductivityCommandTest.java
``` java
public class TrackProductivityCommandTest {
    private Model model = new ModelManager(getTypicalTaskBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_noCompletedTask_commandException() {
        TrackProductivityCommand trackProductivityCommand = new TrackProductivityCommand();
        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.trackProductivity();
        assertCommandFailure(trackProductivityCommand, expectedModel, commandHistory, MESSAGE_NO_COMPLETED_TASK);
    }

    @Test
    public void execute_withCompletedTask_success() {
        TrackProductivityCommand trackProductivityCommand = new TrackProductivityCommand();
        Task taskToComplete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task completedTask = new TaskBuilder(taskToComplete).withCompletedNumOfHours(1).build();
        model.updateTask(taskToComplete, completedTask);
        model.trackProductivity();
        double productivity = trackProductivityCommand.calculateProductivity(model.getFilteredTaskList());
        String prodInPercentage = Integer.toString((int) (productivity * 100)) + " %";
        String expectedMessage = String.format(TrackProductivityCommand.MESSAGE_SUCCESS, prodInPercentage);

        try {
            CommandResult result = trackProductivityCommand.execute(model, commandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }
}
```
###### \java\seedu\address\logic\parser\AddTaskCommandParserTest.java
``` java
public class AddTaskCommandParserTest {
    private static final Logger logger = LogsCenter.getLogger(AddTaskCommandParserTest.class);
    private AddTaskCommandParser parser = new AddTaskCommandParser();
    @Test
    public void parse_allFieldsPresent_success() {
        ParserWithDate parser = new ParserWithDate();
        Deadline selectedDeadline = new Deadline(VALID_DEADLINE_1ST_JAN);
        Task expectedTask = new TaskBuilder(CS2113_TASK_2).withDeadline(VALID_DEADLINE_1ST_JAN).build();
        //AddTaskCommand commandWithDate = new AddTaskCommand(expectedTask);
        // whitespace only preamble
        assertParseSuccessWithDate(parser, selectedDeadline, PREAMBLE_WHITESPACE
                        + MODULE_CODE_CS2113_DESC + TITLE_DESC_2 + DESCRIPTION_DESC_2
                        + PRIORITY_LEVEL_DESC_HIGH + HOURS_DESC_1,
                new AddTaskCommand(expectedTask));

        // multiple module codes - last module code accepted
        assertParseSuccessWithDate(parser, selectedDeadline, MODULE_CODE_CG2271_DESC + MODULE_CODE_CS2113_DESC
                + TITLE_DESC_2 + DESCRIPTION_DESC_2 + PRIORITY_LEVEL_DESC_HIGH + HOURS_DESC_1,
                new AddTaskCommand(expectedTask));

        // multiple titles - last title accepted
        assertParseSuccessWithDate(parser, selectedDeadline, MODULE_CODE_CS2113_DESC + TITLE_DESC_1 + TITLE_DESC_2
                + DESCRIPTION_DESC_2 + PRIORITY_LEVEL_DESC_HIGH + HOURS_DESC_1, new AddTaskCommand(expectedTask));

        // multiple descriptions - last description accepted
        assertParseSuccessWithDate(parser, selectedDeadline, MODULE_CODE_CS2113_DESC + TITLE_DESC_2
                + DESCRIPTION_DESC_1 + DESCRIPTION_DESC_2 + PRIORITY_LEVEL_DESC_HIGH + HOURS_DESC_1,
                new AddTaskCommand(expectedTask));

        // multiple priorities - last priority accepted
        assertParseSuccessWithDate(parser, selectedDeadline, MODULE_CODE_CS2113_DESC + TITLE_DESC_2
                + DESCRIPTION_DESC_2 + PRIORITY_LEVEL_DESC_LOW + PRIORITY_LEVEL_DESC_HIGH + HOURS_DESC_1,
                new AddTaskCommand(expectedTask));

        // multiple hours - last hour accepted
        assertParseSuccessWithDate(parser, selectedDeadline, MODULE_CODE_CS2113_DESC + TITLE_DESC_2
                        + DESCRIPTION_DESC_2 + PRIORITY_LEVEL_DESC_HIGH + HOURS_DESC_2 + HOURS_DESC_1,
                new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing module code prefix
        assertParseFailure(parser, VALID_MODULE_CODE_CS2113 + VALID_TITLE_1 + DESCRIPTION_DESC_1
                + PRIORITY_LEVEL_DESC_LOW + HOURS_DESC_1, expectedMessage);

        // missing title prefix
        assertParseFailure(parser, MODULE_CODE_CS2113_DESC + VALID_TITLE_1 + DESCRIPTION_DESC_1
                + PRIORITY_LEVEL_DESC_LOW + HOURS_DESC_1, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, MODULE_CODE_CS2113_DESC + TITLE_DESC_1 + VALID_DESCRIPTION_1
                + PRIORITY_LEVEL_DESC_LOW + HOURS_DESC_1, expectedMessage);

        // missing priority prefix
        assertParseFailure(parser, MODULE_CODE_CS2113_DESC + TITLE_DESC_1 + DESCRIPTION_DESC_1
                + VALID_PRIORITY_LEVEL_LOW + HOURS_DESC_1, expectedMessage);

        // missing hour prefix
        assertParseFailure(parser, MODULE_CODE_CS2113_DESC + TITLE_DESC_1 + DESCRIPTION_DESC_1
                + PRIORITY_LEVEL_DESC_LOW + VALID_1_HOUR, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid Priority Level
        assertParseFailure(parser, MODULE_CODE_CS2113_DESC + TITLE_DESC_1 + DESCRIPTION_DESC_1
                + INVALID_PRIORITY_LEVEL_DESC + HOURS_DESC_1, PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS);
        // invalid Hours
        assertParseFailure(parser, MODULE_CODE_CS2113_DESC + TITLE_DESC_1 + DESCRIPTION_DESC_1
                        + PRIORITY_LEVEL_DESC_LOW + INVALID_HOURS_DESC, MESSAGE_INVALID_HOURS);
        // hours > INT_MAX
        assertParseFailure(parser, MODULE_CODE_CS2113_DESC + TITLE_DESC_1 + DESCRIPTION_DESC_1
                        + PRIORITY_LEVEL_DESC_LOW + INVALID_HOURS_OVERFLOW, MESSAGE_INVALID_HOURS);
        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + MODULE_CODE_CS2113_DESC + TITLE_DESC_1
                        + DESCRIPTION_DESC_1 + PRIORITY_LEVEL_DESC_LOW,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

    }
    /**
     * Since AddTaskCommand can only work with ModelManager
     * which sets the deadline, parsing has to do the adding
     * of deadline here.
     */
    public static class ParserWithDate extends AddTaskCommandParser {
        private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
        /**
         * An overloading method that parses
         * @param userInput with task inputs to add
         * @param date will be set in the task
         * @return AddTaskCommand
         * @throws ParseException if parsing is invalid
         */
        public Command parse(String userInput, Deadline date) throws ParseException {
            logger.info(userInput);
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(userInput, PREFIX_MODULE_CODE, PREFIX_TITLE, PREFIX_DESCRIPTION,
                            PREFIX_PRIORITY, PREFIX_HOURS);
            if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE, PREFIX_TITLE, PREFIX_DESCRIPTION,
                    PREFIX_PRIORITY, PREFIX_HOURS) || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
            }

            String title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
            String description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
            PriorityLevel priority = ParserUtil.parsePriorityLevel(argMultimap.getValue(PREFIX_PRIORITY).get());
            int expectedNumOfHours = ParserUtil.parseHours(argMultimap.getValue(PREFIX_HOURS).get());
            String moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get());
            Task task = new Task(moduleCode, title, description, priority, expectedNumOfHours);
            task.setDeadline(date);
            return new AddTaskCommand(task);
        }
        /**
         * Parses user input with deadline into command for execution.
         *
         * @param userInput full user input string
         * @return the command based on the user input
         * @throws ParseException if the user input does not conform the expected format
         */
        public Command parseCommand(String userInput, Deadline deadline) throws ParseException {
            final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
            if (!matcher.matches()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
            }
            final String commandWord = matcher.group("commandWord");
            final String arguments = matcher.group("arguments");
            return parse(arguments, deadline);
        }
    }
}
```
###### \java\seedu\address\logic\parser\CommandParserTestUtil.java
``` java
    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful
     * by setting the deadline
     * and the command created equals to {@code expectedCommand}.
     * Only applicable for AddTaskCommandParserTest
     */
    public static void assertParseSuccessWithDate(AddTaskCommandParserTest.ParserWithDate parser, Deadline date,
                                                  String userInput, Command expectedCommand) {
        try {
            Command command = parser.parse(userInput, date);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getTaskList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalTaskBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsDuplicateTaskException() {
        // Two tasks with the same title and deadline fields
        Task editedTask1 = new TaskBuilder(CS2113_TASK_1).withDescription(VALID_DESCRIPTION_2)
                .withPriority(VALID_PRIORITY_LEVEL_HIGH).build();
        List<Task> newTasks = Arrays.asList(CS2113_TASK_1, editedTask1);
        TaskBookStub newData = new TaskBookStub(newTasks);

        thrown.expect(DuplicateTaskException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasTask_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasTask(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasTask(CS2113_TASK_1));
    }

    @Test
    public void hasTask_taskInTaskBook_returnsTrue() {
        addressBook.addTask(CS2113_TASK_1);
        assertTrue(addressBook.hasTask(CS2113_TASK_1));
    }

    @Test
    public void hasTask_taskWithSameTitleAndSameDeadlineInTaskBook_returnsTrue() {
        addressBook.addTask(CS2113_TASK_1);
        Task editedTask1 = new TaskBuilder(CS2113_TASK_1).withDescription(VALID_DESCRIPTION_2)
                .withPriority(VALID_PRIORITY_LEVEL_HIGH).build();
        assertTrue(addressBook.hasTask(editedTask1));
    }

    @Test
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTaskList().remove(0);
    }

    /**
     * A stub ReadOnlyTaskBook whose tasks list can violate interface constraints.
     */
    private static class TaskBookStub implements ReadOnlyTaskBook {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        TaskBookStub(Collection<Task> tasks) {
            this.tasks.setAll(tasks);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedTaskTest.java
``` java
public class XmlAdaptedTaskTest {
    private static final String INVALID_DEADLINE = "#$@(";
    private static final String INVALID_PRIORITY_LEVEL = "midhigh";
    private static final String INVALID_EXPECTED_NUM_OF_HOURS = "one";
    //private static final String INVALID_TAG = "#friend";

    private static final String VALID_DEADLINE = CS2102_HOMEWORK.getDeadline().toString();
    private static final String VALID_MODULECODE = CS2102_HOMEWORK.getModuleCode();
    private static final String VALID_TITLE = CS2102_HOMEWORK.getTitle();
    private static final String VALID_DESCRIPTION = CS2102_HOMEWORK.getDescription();
    private static final String VALID_PRIORITY_LEVEL = CS2102_HOMEWORK.getPriorityLevel().toString();
    private static final String VALID_EXPECTED_NUM_OF_HOURS = Integer.toString(CS2102_HOMEWORK.getExpectedNumOfHours());
    /*private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());
    */

    @Test
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        XmlAdaptedTask task = new XmlAdaptedTask(CS2102_HOMEWORK);
        assertEquals(CS2102_HOMEWORK, task.toModelType());
    }

    @Test
    public void toModelType_invalidDeadline_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(INVALID_DEADLINE, VALID_MODULECODE, VALID_TITLE, VALID_DESCRIPTION,
                        VALID_PRIORITY_LEVEL, VALID_EXPECTED_NUM_OF_HOURS);
        String expectedMessage = Deadline.MESSAGE_DEADLINE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullDeadline_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(null, VALID_MODULECODE, VALID_TITLE, VALID_DESCRIPTION,
                VALID_PRIORITY_LEVEL, VALID_EXPECTED_NUM_OF_HOURS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Deadline.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidPriorityLevel_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_DEADLINE, VALID_MODULECODE, VALID_TITLE, VALID_DESCRIPTION,
                        INVALID_PRIORITY_LEVEL, VALID_EXPECTED_NUM_OF_HOURS);
        String expectedMessage = PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullPriorityLevel_throwsIllegalValueException() {
        XmlAdaptedTask person = new XmlAdaptedTask(VALID_DEADLINE, VALID_MODULECODE, VALID_TITLE,
                VALID_DESCRIPTION, null, VALID_EXPECTED_NUM_OF_HOURS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PriorityLevel.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidExpectedNumOfHours_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_DEADLINE, VALID_MODULECODE, VALID_TITLE, VALID_DESCRIPTION,
                        VALID_PRIORITY_LEVEL, INVALID_EXPECTED_NUM_OF_HOURS);
        String expectedMessage = "Expected number of hours have to be an integer";
        Assert.assertThrows(NumberFormatException.class, task::toModelType);
    }

    @Test
    public void toModelType_nullExpectedNumOfHours_throwsIllegalValueException() {
        XmlAdaptedTask person = new XmlAdaptedTask(VALID_DEADLINE, VALID_MODULECODE, VALID_TITLE, VALID_DESCRIPTION,
                VALID_PRIORITY_LEVEL, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                "Expected number of hours expected to complete");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(VALID_DEADLINE, VALID_MODULECODE, null, VALID_DESCRIPTION,
                VALID_PRIORITY_LEVEL, VALID_EXPECTED_NUM_OF_HOURS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Title");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(VALID_DEADLINE, VALID_MODULECODE, VALID_TITLE, null,
                VALID_PRIORITY_LEVEL, VALID_EXPECTED_NUM_OF_HOURS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Description");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    /*@Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedTask person =
                new XmlAdaptedTask(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }*/

}
```
###### \java\seedu\address\storage\XmlSerializableTaskBookTest.java
``` java
public class XmlSerializableTaskBookTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableTaskBookTest");
    private static final Path TYPICAL_TASKS_FILE = TEST_DATA_FOLDER.resolve("typicalTaskBook.xml");
    private static final Path INVALID_TASK_FILE = TEST_DATA_FOLDER.resolve("invalidTaskBook.xml");
    private static final Path DUPLICATE_TASK_FILE = TEST_DATA_FOLDER.resolve("duplicateTasksInTaskBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableTaskBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_TASKS_FILE,
                XmlSerializableTaskBook.class);
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = getTypicalTaskBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableTaskBook dataFromFile = XmlUtil.getDataFromFile(INVALID_TASK_FILE,
                XmlSerializableTaskBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        XmlSerializableTaskBook dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_TASK_FILE,
                XmlSerializableTaskBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableTaskBook.MESSAGE_DUPLICATE_TASK);
        dataFromFile.toModelType();
    }

}
```
###### \java\seedu\address\testutil\TaskUtil.java
``` java
/**
 * A utility class for Task.
 */
public class TaskUtil {
    /**
     * Returns an add command string for adding the {@code task}.
     */
    public static String getAddTaskCommand(Task task) {
        return AddTaskCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTaskDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_MODULE_CODE + task.getModuleCode() + " ");
        sb.append(PREFIX_TITLE + task.getTitle() + " ");
        sb.append(PREFIX_DESCRIPTION + task.getDescription() + " ");
        sb.append(PREFIX_PRIORITY + task.getPriorityLevel().priorityLevel + " ");
        sb.append(PREFIX_HOURS + Integer.toString(task.getExpectedNumOfHours()) + " ");
        return sb.toString();
    }

}
```
###### \java\seedu\address\testutil\TypicalTasks.java
``` java
/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {
    // Manually added
    public static final Task CS2113_HOMEWORK = new TaskBuilder().withDeadline("1/1/2018").withModuleCode("CS2113")
            .withTitle("Complete code refactoring").withDescription("Refer to notes!")
            .withPriority("low").build();

    public static final Task CS2101_HOMEWORK = new TaskBuilder().withDeadline("9/10/2018").withModuleCode("CS2101")
        .withTitle("Plan a OP2 meeting").withDescription("OP2 is 40% of the grade")
        .withPriority("high").build();

    public static final Task CS2102_HOMEWORK = new TaskBuilder().withDeadline("11/11/2018").withModuleCode("CS2102")
        .withTitle("Set up the backend framework").withDescription("Using Flask")
            .withPriority("medium").build();

    public static final Task CG2271_HOMEWORK = new TaskBuilder().withDeadline("5/6/2018").withModuleCode("CG2271")
        .withTitle("Implement message passing").withDescription("Symmetric & indirect naming scheme")
            .withDescription("low").build();

    public static final Task CG1112_HOMEWORK = new TaskBuilder().withDeadline("2/5/2018").withModuleCode("CG1112")
        .withTitle("Write buffer class").withDescription("refer to api")
            .withDescription("high").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Task CS2113_TASK_1 = new TaskBuilder().withDeadline(VALID_DEADLINE_31ST_MARCH)
        .withModuleCode(VALID_MODULE_CODE_CS2113).withTitle(VALID_TITLE_1).withDescription(VALID_DESCRIPTION_1)
        .withPriority(VALID_PRIORITY_LEVEL_LOW).build();
    public static final Task CS2113_TASK_2 = new TaskBuilder().withDeadline(VALID_DEADLINE_1ST_JAN)
        .withModuleCode(VALID_MODULE_CODE_CS2113).withTitle(VALID_TITLE_2).withDescription(VALID_DESCRIPTION_2)
        .withPriority(VALID_PRIORITY_LEVEL_HIGH).build();
    public static final Task CS2113_TASK_3 = new TaskBuilder().withDeadline(VALID_DEADLINE_12TH_MAY)
        .withModuleCode(VALID_MODULE_CODE_CS2113).withTitle(VALID_TITLE_3).withDescription(VALID_DESCRIPTION_3)
        .withPriority(VALID_PRIORITY_LEVEL_MEDIUM).build();
    // public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalTaskBook() {
        AddressBook ab = new AddressBook();
        for (Task task : getTypicalTasks()) {
            ab.addTask(task);
        }
        return ab;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(CS2101_HOMEWORK, CS2102_HOMEWORK, CS2113_HOMEWORK));
    }
}
```
