package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;

/**
 * Deletes a book identified using it's displayed index from the BookInventory.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the book identified by the index number used in the displayed book list.\n"
            + "Parameters: INDEX (must be a positive integer) OR i/ISBN13\n"
            + "Example: " + COMMAND_WORD + " 1 OR " + COMMAND_WORD + " "
            + PREFIX_ISBN + "978-3-16-148410-0";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Book: %1$s";

    private final String findBookBy;
    private final String argsType;

    public DeleteCommand(String findBookBy, String argsType) {
        this.findBookBy = findBookBy;
        this.argsType = argsType;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Book> lastShownList = model.getFilteredBookList();
        Book bookToDelete;

        if (argsType.equals("Isbn")) {
            bookToDelete = model.getBook(findBookBy);
        } else if (Integer.parseInt(findBookBy) < lastShownList.size() && argsType.equals("Index")) {
            bookToDelete = lastShownList.get(Integer.parseInt(findBookBy));
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        model.deleteBook(bookToDelete);
        model.commitBookInventory();
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, bookToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && findBookBy.equals(((DeleteCommand) other).findBookBy)); // state check
    }
}
