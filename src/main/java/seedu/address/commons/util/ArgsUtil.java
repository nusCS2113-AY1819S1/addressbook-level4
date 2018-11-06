package seedu.address.commons.util;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;

/**
 * A container for Args check utility functions
 */
public class ArgsUtil {

    public static Book getBookToEdit (Model model, List<Book> lastShownList, String argsType, String findBookBy)
            throws CommandException {
        if (argsType.equals("Isbn")) {
            return model.getBook(findBookBy);
        } else if (argsType.equals("Index") && Integer.parseInt(findBookBy) < lastShownList.size()) {
            return lastShownList.get(Integer.parseInt(findBookBy));
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }
    }
}
