package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.exceptions.NoEmployeeException;

//Reused from https://github.com/CS2103JAN2018-F14-B1/main/pull/57/files with minor modifications
//@@author Woonhian
/**
 * Sort all employees in address book by name or department in ascending or descending order.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String BY_ASCENDING = "asc";
    public static final String BY_DESCENDING = "desc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all employees in Bank Address Book "
            + "by name or department in ascending or descending order.\n"
            + "Parameters: FIELD ORDER\n"
            + "Example to sort by name in ascending order: " + COMMAND_WORD + " name " + BY_ASCENDING
            + "\nExample to sort department in descending order: " + COMMAND_WORD + " department " + BY_DESCENDING;

    public static final String MESSAGE_SUCCESS = "Bank Address Book has been sorted!";
    public static final String MESSAGE_EMPTY_BOOK = "No person to sort";

    private final String field;
    private final String order;

    public SortCommand(String field, String order) {
        requireNonNull(field);
        requireNonNull(order);

        this.field = field;
        this.order = order;
    }

    public String getField() {
        return this.field;
    }

    public String getOrder() {
        return this.order;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            model.sortEmployee(getField(), getOrder());
        } catch (NoEmployeeException nee) {
            throw new CommandException(MESSAGE_EMPTY_BOOK);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
