# emobeany
###### \java\seedu\address\logic\commands\SelectDeadlineCommand.java
``` java
/**
 * Selects a date as a deadline for tasks to be added to
 */
public class SelectDeadlineCommand extends Command implements CommandParser {
    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Selects a date. "
            + "Parameters: "
            + PREFIX_DAY + "DAY "
            + PREFIX_MONTH + "MONTH "
            + PREFIX_YEAR + "YEAR \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DAY + "01 "
            + PREFIX_MONTH + "01 "
            + PREFIX_YEAR + "2018 ";

    public static final String MESSAGE_SUCCESS = "New date selected: %1$s";
    public static final String MESSAGE_INVALID_DEADLINE = "The date selected does not exist";

    private final Deadline toSelect;

    /**
     * Creates a SelectDeadline to select the specified {@code Deadline}
     */
    public SelectDeadlineCommand (Deadline deadline) {
        requireNonNull(deadline);
        toSelect = deadline;
    }

    public SelectDeadlineCommand() {
        toSelect = null;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.validDeadline(toSelect)) {
            throw new CommandException(MESSAGE_INVALID_DEADLINE);
        }

        model.selectDeadline(toSelect);
        model.commitTaskBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toSelect));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectDeadlineCommand // instanceof handles nulls
                && toSelect.equals(((SelectDeadlineCommand) other).toSelect));
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new SelectDeadlineCommandParser().parse(arguments);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Leading and trailing whitespaces will be trimmed from {@code String day}
     */
    public static String parseDay(String day) throws ParseException {
        requireNonNull(day);
        String trimmedDay = day.trim();
        return trimmedDay;
    }

    /**
     * Leading and trailing whitespaces will be trimmed from {@code String month}
     */
    public static String parseMonth(String month) throws ParseException {
        requireNonNull(month);
        String trimmedMonth = month.trim();
        return trimmedMonth;
    }

    /**
     * Leading and trailing whitespaces will be trimmed from {@code String year}
     */
    public static String parseYear(String year) throws ParseException {
        requireNonNull(year);
        String trimmedYear = year.trim();
        return trimmedYear;
    }
```
###### \java\seedu\address\logic\parser\SelectDeadlineCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SelectDeadlineCommand object
 */
public class SelectDeadlineCommandParser implements Parser<SelectDeadlineCommand> {

    @Override
    public SelectDeadlineCommand parse(String userInput) throws ParseException {
        Deadline deadlineWithoutPrefixes = parseWithoutPrefixes(userInput);
        if (deadlineWithoutPrefixes != null) {
            return new SelectDeadlineCommand(deadlineWithoutPrefixes);
        }
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR);

        if (!arePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SelectDeadlineCommand.MESSAGE_USAGE));
        }

        String day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get());
        String month = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_MONTH).get());
        String year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
        Deadline deadline = new Deadline(day, month, year);

        return new SelectDeadlineCommand(deadline);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    protected static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Alternative parsing method:
     * @param userInput without date, month and year prefixes
     * @return the parsed Deadline
     */
    public Deadline parseWithoutPrefixes(String userInput) {
        try {
            return ParserUtil.parseDeadline(userInput);
        } catch (ParseException e) {
            return null;
        }
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    /** Selects the input date as deadline.*/
    void selectDeadline(Deadline deadline);

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void selectDeadline(Deadline deadline) {
        versionedTaskBook.selectDeadline(deadline);
        updateFilteredTaskList(predicateShowTasksWithSameDate(deadline));
        indicateTaskBookChanged();
    }

    /**{@code Predicate} that returns true when the date is equal*/
    private Predicate<Task> predicateShowTasksWithSameDate(Deadline deadline) {
        return task -> task.getDeadline().equals(deadline);
    }

    public Deadline getDeadline() {
        return versionedTaskBook.getDeadline();
    }

    @Override
    public boolean validDeadline(Deadline deadline) {
        return versionedTaskBook.validDeadline(deadline);
    }

```
###### \java\seedu\address\model\task\Deadline.java
``` java

/**
 * Represents a deadline in the task book.
 * Guarantees: field values are validated, immutable, details are present and not null.
 */

public class Deadline {
    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
        "Deadline can only have dd/mm/yyyy format";

    private final String day;
    private final String month;
    private final String year;

    public Deadline(String day, String month, String year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Deadline(String deadline) {
        String[] entries = deadline.split("/");
        this.day = entries[0];
        this.month = entries[1];
        this.year = entries[2];
    }

    /*
    public Deadline(String day, String month) {
        this.day = day;
        this.month = month;
    }
    */

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    /**
     * Returns false if any fields are not within the limits (not a valid date).
     */

    public static boolean isValidDeadline(String test) {
        String[] entries = test.split("/");
        if (entries.length != 3) {
            return false;
        }
        String day = entries[0];
        String month = entries[1];
        String year = entries[2];

        // Check that all the characters are numeric first.
        if (!isNumeric(day) || !isNumeric(month) || !isNumeric(year)) {
            return false;
        } else if (Integer.parseInt(day) < 1 || Integer.parseInt(day) > 31) {
            return false;
        } else if (Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12) {
            return false;
        } else if (Integer.parseInt(year) < 2018 || Integer.parseInt(year) > 9999) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        // custom fields hashing
        return Objects.hash(day, month, year);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDay())
                .append("/")
                .append(getMonth())
                .append("/")
                .append(getYear());
        return builder.toString();
    }

    /**
     * Referenced online: Checking if String is numeric
     * @param s
     * @return true if String is completely numeric
     */
    public static boolean isNumeric(String s) {
        //s.matches("[-+]?\\d*\\.?\\d+");
        return s != null && s.matches("-?\\d+(\\.\\d+)?");
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        } else if (object instanceof Deadline) {
            Deadline otherDeadline = (Deadline) object;
            return otherDeadline.day.equals(this.day) && otherDeadline.month.equals(this.month)
                    && otherDeadline.year.equals(this.year);
        }
        return false;
    }
}
```
