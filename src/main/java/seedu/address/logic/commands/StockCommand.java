package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
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
 * Stocks the details of an existing book in the quantity book.
 */
public class StockCommand extends Command {

    public static final String COMMAND_WORD = "stock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Stocks the book identified "
            + "by the index number used in the displayed book list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX(must be a positive integer) "
            + "[" + PREFIX_QUANTITY + "QUANTITY] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_QUANTITY + "5";

    public static final String MESSAGE_STOCK_PERSON_SUCCESS = "Stocked Book: %1$s";
    public static final String MESSAGE_NOT_STOCKED = "Increase to stock quantity must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This book already exists in the book inventory.";

    private final Index index;
    private final StockBookDescriptor stockBookDescriptor;

    /**
     * @param index of the book in the filtered book list to stock
     * @param stockBookDescriptor details to stock the book with
     */
    public StockCommand(Index index, StockBookDescriptor stockBookDescriptor) {
        requireNonNull(index);
        requireNonNull(stockBookDescriptor);

        this.index = index;
        this.stockBookDescriptor = new StockBookDescriptor(stockBookDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Book> lastShownList = model.getFilteredBookList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        Book bookToStock = lastShownList.get(index.getZeroBased());
        Book stockedBook = createStockedBook(bookToStock, stockBookDescriptor);

        if (!bookToStock.isSameBook(stockedBook) && model.hasBook(stockedBook)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.updateBook(bookToStock, stockedBook);
        model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        model.commitBookInventory();
        return new CommandResult(String.format(MESSAGE_STOCK_PERSON_SUCCESS, stockedBook));
    }

    /**
     * Creates and returns a {@code Book} with the details of {@code bookToStock}
     * stocked with {@code stockBookDescriptor}.
     */
    private static Book createStockedBook(Book bookToStock, StockBookDescriptor stockBookDescriptor) {
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
        return index.equals(e.index)
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
        public boolean isAnyFieldStocked() {
            return CollectionUtil.isAnyNonNull(name, isbn, price, quantity, tags);
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
