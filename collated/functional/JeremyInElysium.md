# JeremyInElysium
###### \java\seedu\address\commons\events\model\AddMilestoneChangedEvent.java
``` java
/** Indicates the AddressBook in the model has changed*/
public class AddMilestoneChangedEvent extends BaseEvent {

    public final ReadOnlyTaskBook data;

    public AddMilestoneChangedEvent(ReadOnlyTaskBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size();
    }
}
```
###### \java\seedu\address\logic\commands\AddMilestoneCommand.java
``` java
/**
 * Adds a milestone to a task in the taskbook
 */
public class AddMilestoneCommand extends Command implements CommandParser {
    public static final String COMMAND_WORD = "add_milestone";
    public static final String MESSAGE_SUCCESS = "New milestone added: %1$s";
    public static final String MESSAGE_TASK_NOT_FOUND = "This task does not exist in the task book";
    public static final String MESSAGE_DUPLICATE_RANK = "Invalid rank entered.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds milestone(s) to selected task. "
            + "Parameters: "
            + PREFIX_INDEX + "INDEX "
            + PREFIX_MILESTONE + "MILESTONE "
            + PREFIX_RANK + "RANK \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_MILESTONE + "Complete Sections 8.1 to 8.5 "
            + PREFIX_RANK + "1";

    private final Index index;
    private final Milestone toAdd;

    /**
     * Creates a AddMilestoneCommand to serve the purpose of the LogicManager
     */
    public AddMilestoneCommand() {
        index = null;
        toAdd = null;
    }

    /**
     * Creates a AddMilestoneCommand to add the specified {@code Milestone}
     */
    public AddMilestoneCommand(Index index, Milestone milestone) {
        requireNonNull(index);
        requireNonNull(milestone);
        this.index = index;
        toAdd = milestone;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_TASK_NOT_FOUND);
        }

        Task taskToEdit = lastShownList.get(index.getZeroBased());

        //TODO: ensure rank of milestone that is being added does not collide with existing milestones' ranks
        /*
        if(taskToEdit.milestoneSet.size() <= rank) {
            throw new CommandException(MESSAGE_DUPLICATE_RANK);
        }
        */

        Task editedTask = taskToEdit.addMilestone(toAdd);
        model.updateTask(taskToEdit, editedTask);
        //model.addMilestone(toAdd);
        model.commitTaskBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.toString()));
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new AddMilestoneCommandParser().parse(arguments);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

}
```
###### \java\seedu\address\logic\parser\AddMilestoneCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddMilestoneCommand object
 */
public class AddMilestoneCommandParser implements Parser<AddMilestoneCommand> {

    /**
     * Returns true if none of the prefixes contain empty {@code Optional} values in the given
     * {@code ArgumentMultiMap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Override
    public AddMilestoneCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_INDEX, PREFIX_MILESTONE, PREFIX_RANK);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_MILESTONE, PREFIX_RANK)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMilestoneCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        String milestoneDescription = ParserUtil.parseMilestoneDescription(
                argMultimap.getValue(PREFIX_MILESTONE).get());
        String rank = ParserUtil.parseRank(argMultimap.getValue(PREFIX_RANK).get());

        Milestone milestone = new Milestone(new MilestoneDescription(milestoneDescription), new Rank(rank));

        return new AddMilestoneCommand(index, milestone);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Leading and trailing whitespaces will be trimmed from {@code String milestoneDescription}
     */
    public static String parseMilestoneDescription(String milestoneDescription) throws ParseException {
        requireNonNull(milestoneDescription);
        String trimmedMilestoneDescription = milestoneDescription.trim();
        return trimmedMilestoneDescription;
    }

    /**
     * Leading and trailing whitespaces will be trimmed from {@code String rank}
     */
    public static String parseRank(String rank) throws ParseException {
        requireNonNull(rank);
        String trimmedRank = rank.trim();
        return trimmedRank;
    }

}
```
###### \java\seedu\address\model\task\Milestone.java
``` java
/**
 * Represents a Milestone for any Task in the TaskBook
 */
public class Milestone {
    private final MilestoneDescription milestoneDescription;
    private final Rank rank;


    public Milestone(MilestoneDescription milestoneDescription, Rank rank) {
        //super(title, milestoneDescription, new PriorityLevel("high"));
        this.milestoneDescription = milestoneDescription;
        this.rank = rank;
    }

    public MilestoneDescription getMilestoneDescription() {
        return milestoneDescription;
    }

    public String getMilestoneDescriptionString() {
        return milestoneDescription.milestoneDescription;
    }


    public Rank getRank() {
        return rank;
    }

    public String getRankString() {
        return rank.rank;
    }


    /**
     * Returns true if both tasks have the same deadline and title.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameMilestone(Milestone otherMilestone) {
        if (otherMilestone == this) {
            return true;
        }

        return otherMilestone != null
                && otherMilestone.getMilestoneDescription().equals(getMilestoneDescription())
                && otherMilestone.getRank().equals(getRank());
    }

    //need to edit this also
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Milestone ")
                .append(getRank())
                .append(": ")
                .append(getMilestoneDescription());
        return builder.toString();
    }

}
```
###### \java\seedu\address\model\task\MilestoneDescription.java
``` java
/**
 * Represents a description in the milestone of a task.
 */
public class MilestoneDescription {

    public static final String MESSAGE_MILESTONEDESCRIPTION_CONSTRAINTS =
            "Milestone description can only contain alphanumeric characters and spaces, and it should not be blank.";

    /**
     * The first character of the milestone description must not be a whitespace,
     * otherwise " " (a blank string) will become a valid input
     */
    public static final String MILESTONEDESCRIPTION_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String milestoneDescription;

    /**
     * Creates a constructor for the milestone description
     * Guarantees that the milestone description is not null
     * @param milestoneDescription a valid milestone description
     */
    public MilestoneDescription(String milestoneDescription) {
        requireNonNull(milestoneDescription);
        //checkArgument(isValidMilestoneDescription(milestoneDescription), MESSAGE_MILESTONEDESCRIPTION_CONSTRAINTS);
        this.milestoneDescription = milestoneDescription;
    }

    /**
     * Checks whether milestone description entered by the user is valid
     * @param milestoneDescription
     * @return true if valid
     */
    public static boolean isValidMilestoneDescription(String milestoneDescription) {
        return true;
        //milestoneDescription.matches(MILESTONEDESCRIPTION_VALIDATION_REGEX);
    }

    public String getMilestoneDescription() {
        return this.milestoneDescription;
    }

    @Override
    public String toString() {
        return milestoneDescription;
    }
}
```
###### \java\seedu\address\model\task\Rank.java
``` java
/**
 * Represents a description in the milestone of a task.
 */
public class Rank {

    public static final String MESSAGE_RANK_CONSTRAINTS =
            "Rank can only contain positive integers greater than zero, and it should not be blank.";

    /**
     * The input must not be a whitespace, zero or a negative integer
     */
    public static final String RANK_VALIDATION_REGEX = "[1-9]{1,2}";

    public final String rank;

    /**
     * Creates a constructor for the rank
     * Guarantees that the rank is not null
     * @param rank a valid rank
     */
    public Rank(String rank) {
        requireNonNull(rank);
        checkArgument(isValidRank(rank), MESSAGE_RANK_CONSTRAINTS);
        this.rank = rank;
    }

    /**
     * Checks whether rank entered by the user is valid
     * @param rank
     * @return true if valid
     */
    public static boolean isValidRank(String rank) {
        return rank.matches(RANK_VALIDATION_REGEX);
    }

    public String getRank() {
        return this.rank;
    }

    @Override
    public String toString() {
        return rank;
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedMilestone.java
``` java
/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedMilestone {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Milestone's %s field is missing.";

    @XmlElement
    private String descrip;
    @XmlElement
    private String rank;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */

    public XmlAdaptedMilestone() {}

    /**
     * Constructs a {@code XmlAdaptedMilestone} with the given {@code milestone}.
     */
    public XmlAdaptedMilestone(MilestoneDescription milestoneDescription, Rank rank) {
        this.descrip = milestoneDescription.getMilestoneDescription();
        this.rank = rank.getRank();
    }

    /**
     * Converts a given Milestone into this class for JAXB use.
     * @param source future changes to this will not affect the created
     */

    public XmlAdaptedMilestone(Milestone source) {
        descrip = source.getMilestoneDescriptionString();
        rank = source.getRankString();
    }

    /**
     * Converts this jaxb-friendly adapted milestone object into the model's Milestone object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */

    public Milestone toModelType() throws IllegalValueException {
        if (descrip == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, MilestoneDescription.class.getSimpleName()));
        }

        if (!MilestoneDescription.isValidMilestoneDescription(descrip)) {
            throw new IllegalValueException(MilestoneDescription.MESSAGE_MILESTONEDESCRIPTION_CONSTRAINTS);
        }
        final MilestoneDescription modelMilestoneDescription = new MilestoneDescription(descrip);

        if (rank == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Rank.class.getSimpleName()));
        }

        if (!Rank.isValidRank(rank)) {
            throw new IllegalValueException(Rank.MESSAGE_RANK_CONSTRAINTS);
        }
        final Rank modelRank = new Rank(rank);


        return new Milestone(modelMilestoneDescription, modelRank);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMilestone)) {
            return false;
        }

        XmlAdaptedMilestone otherMilestone = (XmlAdaptedMilestone) other;
        return Objects.equals(descrip, otherMilestone.descrip)
                && Objects.equals(rank, otherMilestone.rank);
    }
}

```
