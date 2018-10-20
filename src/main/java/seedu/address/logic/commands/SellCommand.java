package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.StatisticCenter;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.Cost;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Name;
import seedu.address.model.book.Price;
import seedu.address.model.book.Quantity;
import seedu.address.model.tag.Tag;

/**
 * Decrease the quantity of an existing book in the inventory book.
 */
public class SellCommand extends Command {

    public static final String COMMAND_WORD = "sell";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Decrease the quantity of book identified "
            + "by the index number used in the displayed book list. "
            + "Existing values will be subtracted by the input value.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_QUANTITY + "QUANTITY]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_QUANTITY + "5";

    public static final String MESSAGE_SELL_BOOK_SUCCESS = "Sold Book: %1$s";
    public static final String MESSAGE_NOT_SOLD = "Decrease to stock quantity must be provided.";
    public static final String MESSAGE_INVALID_QUANTITY = "Quantity of books left cannot be less than 0.";
    private final String findBookBy;
    private final DecreaseQuantity decreaseQuantity;

    /**
     * @param findBookBy the index or isbn in the filtered book list to edit
     * @param decreaseQuantity number to sell the book with
     */
    public SellCommand(String findBookBy, DecreaseQuantity decreaseQuantity) {
        requireNonNull(findBookBy);
        requireNonNull(decreaseQuantity);

        this.findBookBy = findBookBy;
        this.decreaseQuantity = new DecreaseQuantity(decreaseQuantity);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Book> lastShownList = model.getFilteredBookList();
        Book bookToSell;

        if (findBookBy.length() == 1 && Integer.parseInt(findBookBy) >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        } else if (findBookBy.length() == 1) {
            bookToSell = lastShownList.get(Integer.parseInt(findBookBy));
        } else {
            bookToSell = model.getBook(findBookBy);
        }

        Book sellBook = createSoldBook(bookToSell, decreaseQuantity);

        StatisticCenter.getInstance().getStatistic().increaseRevenue(
                bookToSell.getPrice().toString(), decreaseQuantity.getQuantity().getValue());

        model.updateBook(bookToSell, sellBook);
        model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        model.commitBookInventory();
        return new CommandResult(String.format(MESSAGE_SELL_BOOK_SUCCESS, sellBook));


    }

    /**
     * Creates and returns a {@code Book} with the details of {@code bookToSell}
     * edited with {@code decreaseQuantity}
     */
    private static Book createSoldBook(Book bookToSell, DecreaseQuantity decreaseQuantity) throws CommandException {
        assert bookToSell != null;

        Name name = bookToSell.getName();
        Isbn isbn = bookToSell.getIsbn();
        Price price = bookToSell.getPrice();
        Cost cost = bookToSell.getCost();
        Quantity updatedQuantity = bookToSell.deductQuantity(decreaseQuantity.getQuantity());
        Set<Tag> tags = bookToSell.getTags();

        return new Book(name, isbn, price, cost, updatedQuantity, tags);
    }

    @Override
    public boolean equals(Object other) {
        // instanceof handles nulls
        if (!(other instanceof SellCommand)) {
            return false;
        }

        // state check
        SellCommand s = (SellCommand) other;
        return findBookBy.equals(s.findBookBy)
                && decreaseQuantity.equals(s.decreaseQuantity);
    }

    /**
     * Stores the quantity to edit the book with. Quantity of book will be subtracted.
     */
    public static class DecreaseQuantity {
        private Quantity quantity;

        public DecreaseQuantity() {}

        /**
         * Copy constructor.
         */
        public DecreaseQuantity(DecreaseQuantity toCopy) {
            setQuantity(toCopy.quantity);
        }

        public boolean isQuantityFieldSpecified() {
            return CollectionUtil.isAnyNonNull(quantity);
        }

        public void setQuantity(Quantity quantity) {
            this.quantity = quantity;
        }

        public Quantity getQuantity() {
            return quantity;
        }

        @Override
        public boolean equals(Object other) {
            // instanceof handles nulls
            if (!(other instanceof DecreaseQuantity)) {
                return false;
            }

            // state check
            DecreaseQuantity s = (DecreaseQuantity) other;

            return getQuantity().equals(s.getQuantity());
        }
    }
}
