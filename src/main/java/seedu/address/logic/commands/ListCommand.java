package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all books in the book inventory to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : Lists all books in the BookInventory.";
    public static final String MESSAGE_SUCCESS = "Listed all books";

    public static final String MESSAGE_NO_BOOK_FOUND = "There is no book to display.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        if (model.getFilteredBookList().isEmpty()) {
            return new CommandResult(MESSAGE_NO_BOOK_FOUND);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
