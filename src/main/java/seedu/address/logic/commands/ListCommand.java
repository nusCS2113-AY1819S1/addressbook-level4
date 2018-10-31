package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
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
            + PREFIX_DEPARTMENT + " DEPARTMENT "
            + "or " + PREFIX_ALL + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_DEPARTMENT + " Admin";

    public static final String MESSAGE_SUCCESS = "All persons listed!";

    public static final String LIST_KEY_DEPARTMENT = "dep";
    public static final String LIST_KEY_ALL = "all";

    private final String listKey;
    private final DepartmentContainsKeywordsPredicate predicate;

    public ListCommand(String sortByParam, DepartmentContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
        requireNonNull(sortByParam);
        listKey = sortByParam;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        switch (listKey) {
            case (LIST_KEY_DEPARTMENT): model.updateFilteredPersonList(predicate);
               return new CommandResult(
                        String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
            case (LIST_KEY_ALL): model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                break;
            default:
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand
                && ((predicate == null && ((ListCommand) other).predicate == null) // instanceof handles nulls
                || predicate.equals(((ListCommand) other).predicate)));
    }
}
