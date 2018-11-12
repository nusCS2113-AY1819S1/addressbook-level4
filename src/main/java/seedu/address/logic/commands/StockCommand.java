//@@author kohjunkiat
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.StatisticCenter;
import seedu.address.commons.util.ArgsUtil;
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
 * Stocks the details of an existing book in the BookInventory.
 */
public class StockCommand extends Command {

    public static final String COMMAND_WORD = "stock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Stocks the book identified "
            + "by the index number used in the displayed book list. "
            + "Existing values will be added by the input values.\n"
            + "Parameters: INDEX(must be a positive integer) "
            + "[" + PREFIX_QUANTITY + "QUANTITY] OR "
            + PREFIX_ISBN + "ISBN13 " + "[" + PREFIX_QUANTITY + "QUANTITY]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_QUANTITY + "5 OR " + COMMAND_WORD + " " + PREFIX_ISBN + "978-3-16-148410-0 "
            + PREFIX_QUANTITY + "5";


    public static final String MESSAGE_QUANTITY_STOCK = "Number of Book Stocked: ";
    public static final String MESSAGE_STOCK_BOOK_SUCCESS = "Stocked Book: %1$s";
    public static final String MESSAGE_NOT_STOCKED = "Quantity provided must be a non-zero integer of max. 999.";
    public static final String MESSAGE_DUPLICATE_BOOK = "This book already exists in the book inventory.";
    public static final String MESSAGE_MAX_QUANTITY =
            "Quantity of books cannot be more than 999.";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + PREFIX_QUANTITY + " "
            + PREFIX_ISBN;


    private final String findBookBy;
    private final String argsType;
    private final StockBookDescriptor stockBookDescriptor;

    /**
     * @param findBookBy of the book in the filtered book list to stock
     * @param argsType of argument use to find book
     * @param stockBookDescriptor details to stock the book with
     */
    public StockCommand(String findBookBy, String argsType, StockBookDescriptor stockBookDescriptor) {
        requireNonNull(findBookBy);
        requireNonNull(argsType);
        requireNonNull(stockBookDescriptor);

        this.findBookBy = findBookBy;
        this.argsType = argsType;
        this.stockBookDescriptor = new StockBookDescriptor(stockBookDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Book> lastShownList = model.getFilteredBookList();

        Book bookToStock;

        bookToStock = ArgsUtil.getBookToEdit(model, lastShownList, argsType, findBookBy);

        Book stockedBook = createStockedBook(bookToStock, stockBookDescriptor);

        if (!bookToStock.isSameBook(stockedBook) && model.hasBook(stockedBook)) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOK);
        }

        StatisticCenter.getInstance().getStatistic().getInventory().increase(
                bookToStock.getCost().toString(), stockBookDescriptor.getQuantity().getValue());

        model.updateBook(bookToStock, stockedBook);
        model.commitBookInventory();
        return new CommandResult(MESSAGE_QUANTITY_STOCK + stockBookDescriptor.getQuantity().getValue()
                + "\n" + String.format(MESSAGE_STOCK_BOOK_SUCCESS, stockedBook));
    }

    /**
     * Creates and returns a {@code Book} with the details of {@code bookToStock}
     * stocked with {@code stockBookDescriptor}.
     */
    private static Book createStockedBook(Book bookToStock, StockBookDescriptor stockBookDescriptor)
            throws CommandException {
        assert bookToStock != null;

        Name updatedName = (bookToStock.getName());
        Isbn updatedIsbn = (bookToStock.getIsbn());
        Price updatedPrice = stockBookDescriptor.getPrice().orElse(bookToStock.getPrice());
        Cost updatedCost = bookToStock.getCost();
        Quantity updatedQuantity = bookToStock.increaseQuantity(stockBookDescriptor.getQuantity());
        Set<Tag> updatedTags = (bookToStock.getTags());

        return new Book(updatedName, updatedIsbn, updatedPrice, updatedCost, updatedQuantity, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StockCommand)) {
            return false;
        }

        // state check
        StockCommand e = (StockCommand) other;
        return findBookBy.equals(e.findBookBy)
                && stockBookDescriptor.equals(e.stockBookDescriptor);
    }

    /**
     * Stores the details to stock the book with. Each non-empty field value will replace the
     * corresponding field value of the book.
     */
    public static class StockBookDescriptor {
        private Name name;
        private Isbn isbn;
        private Price price;
        private Cost cost;
        private Quantity quantity;
        private Set<Tag> tags;

        public StockBookDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public StockBookDescriptor(StockBookDescriptor toCopy) {
            setName(toCopy.name);
            setIsbn(toCopy.isbn);
            setPrice(toCopy.price);
            setCost(toCopy.cost);
            setQuantity(toCopy.quantity);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is stocked.
         */
        public boolean isQuantityFieldStocked() {
            return CollectionUtil.isAnyNonNull(quantity);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setIsbn(Isbn isbn) {
            this.isbn = isbn;
        }

        public Optional<Isbn> getIsbn() {
            return Optional.ofNullable(isbn);
        }

        public void setPrice(Price price) {
            this.price = price;
        }

        public Optional<Price> getPrice() {
            return Optional.ofNullable(price);
        }

        public void setCost(Cost cost) {
            this.cost = cost;
        }

        public void setQuantity(Quantity quantity) {
            this.quantity = quantity;
        }

        public Quantity getQuantity() {
            return quantity;
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof StockBookDescriptor)) {
                return false;
            }

            // state check
            StockBookDescriptor e = (StockBookDescriptor) other;

            return getName().equals(e.getName())
                    && getIsbn().equals(e.getIsbn())
                    && getPrice().equals(e.getPrice())
                    && getQuantity().equals(e.getQuantity())
                    && getTags().equals(e.getTags());
        }
    }
}
