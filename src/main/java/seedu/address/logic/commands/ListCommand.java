package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALL_PEOPLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST_DEPARTMENT;
import static seedu.address.model.EventModel.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;

/**
 * Lists all persons in the address book to the user depending on the parameter.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all persons by the given parameter\n"
            + "Parameters: "
            + PREFIX_LIST_DEPARTMENT + " DEPARTMENT "
            + "or " + PREFIX_ALL_PEOPLE + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_LIST_DEPARTMENT + " Admin";

    public static final String MESSAGE_SUCCESS_PEOPLE = "All people listed!";
    public static final String MESSAGE_SUCCESS_EVENT = "All events listed!";

    public static final String LIST_KEY_DEPARTMENT = "dep";
    public static final String LIST_KEY_ALL = "all people";
    public static final String LIST_KEY_EVENT = "all events";

    private final String listKey;
    private final DepartmentContainsKeywordsPredicate predicate;

    public ListCommand(String sortByParam, DepartmentContainsKeywordsPredicate predicate) {
        requireNonNull(sortByParam);
        //requireNonNull(predicate);
        this.predicate = predicate;
        listKey = sortByParam;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        String message = null;

        switch (listKey) {
        case (LIST_KEY_DEPARTMENT): model.updateFilteredPersonList(predicate);
           return new CommandResult(
                        String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));

        case (LIST_KEY_EVENT): model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
            message = MESSAGE_SUCCESS_EVENT;
            break;
        case (LIST_KEY_ALL): model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            message = MESSAGE_SUCCESS_PEOPLE;
            break;
        default:
        }
        return new CommandResult(message);


    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand
                && ((predicate == null && ((ListCommand) other).predicate == null) // instanceof handles nulls
                || predicate.equals(((ListCommand) other).predicate)));
    }
}
