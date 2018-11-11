# kennethcsj
###### \java\seedu\address\commons\util\ArgsUtil.java
``` java
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
            if (model.getBook(findBookBy) != null) {
                return model.getBook(findBookBy);
            } else {
                throw new CommandException(Messages.MESSAGE_ISBN_NOT_IN_BOOK);
            }
        } else if (argsType.equals("Index") && Integer.parseInt(findBookBy) < lastShownList.size()) {
            return lastShownList.get(Integer.parseInt(findBookBy));
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }
    }
}
```
###### \java\seedu\address\logic\commands\CheckCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.book.QuantityContainsNumberPredicate;

/**
 * Finds and lists all books in BookInventory with quantity less than or equal to given number
 */
public class CheckCommand extends Command {

    public static final String COMMAND_WORD = "check";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks all book with quantity less than or "
            + "equal to the quantity specified and displays them as a list with index numbers.\n"
            + "Parameters: NUMBER\n"
            + "Example: " + COMMAND_WORD + " 4";
    public static final String MESSAGE_EXCEEDED_QUANTITY = "Maximum input value cannot exceed 999";

    private final QuantityContainsNumberPredicate predicate;

    public CheckCommand(QuantityContainsNumberPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.sortBooksUsingQuantity();
        model.updateFilteredBookList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_BOOKS_LISTED_OVERVIEW, model.getFilteredBookList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CheckCommand // instanceof handles nulls
                && predicate.equals(((CheckCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\SellCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.List;
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
 * Decrease the quantity of an existing book in the inventory book.
 */
public class SellCommand extends Command {

    public static final String COMMAND_WORD = "sell";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Decrease the quantity of book identified "
            + "by the index number used in the displayed book list. "
            + "Existing values will be subtracted by the input value.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_QUANTITY + "QUANTITY] OR "
            + PREFIX_ISBN + "ISBN13 " + "[" + PREFIX_QUANTITY + "QUANTITY]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_QUANTITY + "5 OR " + COMMAND_WORD + " " + PREFIX_ISBN + "978-3-16-148410-0 "
            + PREFIX_QUANTITY + "5";

    public static final String MESSAGE_QUANTITY_SOLD = "Number of Book Sold: ";
    public static final String MESSAGE_SELL_BOOK_SUCCESS = "Sold Book: %1$s";
    public static final String MESSAGE_MIN_QUANTITY =
            "Quantity of books left cannot be less than 0.";
    public static final String COMMAND_SYNTAX = COMMAND_WORD
            + " " + PREFIX_QUANTITY + " "
            + PREFIX_ISBN;
    private final String findBookBy;
    private final String argsType;
    private final SellBookDescriptor sellBookDescriptor;

    /**
     * @param findBookBy the index or isbn in the filtered book list to edit
     * @param sellBookDescriptor number to sell the book with
     */
    public SellCommand(String findBookBy, String argsType, SellBookDescriptor sellBookDescriptor) {
        requireNonNull(findBookBy);
        requireNonNull(sellBookDescriptor);

        this.findBookBy = findBookBy;
        this.argsType = argsType;
        this.sellBookDescriptor = new SellBookDescriptor(sellBookDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Book> lastShownList = model.getFilteredBookList();
        Book bookToSell;

        bookToSell = ArgsUtil.getBookToEdit(model, lastShownList, argsType, findBookBy);

        Book sellBook = createSoldBook(bookToSell, sellBookDescriptor);

        StatisticCenter.getInstance().getStatistic().sell(
                bookToSell.getPrice().toString(),
                bookToSell.getCost().toString(),
                sellBookDescriptor.getQuantity().getValue());


        model.updateBook(bookToSell, sellBook);
        model.commitBookInventory();
        return new CommandResult(MESSAGE_QUANTITY_SOLD + sellBookDescriptor.getQuantity().getValue()
                + "\n" + String.format(MESSAGE_SELL_BOOK_SUCCESS, sellBook));


    }

    /**
     * Creates and returns a {@code Book} with the details of {@code bookToSell}
     * edited with {@code sellBookDescriptor}
     */
    private static Book createSoldBook(Book bookToSell, SellBookDescriptor sellBookDescriptor) throws CommandException {
        assert bookToSell != null;

        Name name = bookToSell.getName();
        Isbn isbn = bookToSell.getIsbn();
        Price price = bookToSell.getPrice();
        Cost cost = bookToSell.getCost();
        Quantity updatedQuantity = bookToSell.deductQuantity(sellBookDescriptor.getQuantity());
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
                && sellBookDescriptor.equals(s.sellBookDescriptor);
    }

    /**
     * Stores the quantity to edit the book with. Quantity of book will be subtracted.
     */
    public static class SellBookDescriptor {
        private Quantity quantity;

        public SellBookDescriptor() {}

        /**
         * Copy constructor.
         */
        public SellBookDescriptor(SellBookDescriptor toCopy) {
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
            if (!(other instanceof SellBookDescriptor)) {
                return false;
            }

            // state check
            SellBookDescriptor s = (SellBookDescriptor) other;

            return getQuantity().equals(s.getQuantity());
        }
    }
}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public Queue<String> getCompleteIsbn(String isbnText) {
        return model.getCompleteIsbn(isbnText);
    }

```
###### \java\seedu\address\logic\parser\CheckCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.CheckCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.QuantityContainsNumberPredicate;

/**
 * Parses input arguments and creates a new CheckCommand object
 */
public class CheckCommandParser implements Parser<CheckCommand> {

    private List<String> quantities = new ArrayList<>();

    /**
     * Parses the given {@code String} of arguments in the context of the CheckCommand
     */
    public CheckCommand parse(String args) throws ParseException {
        quantities.clear();

        String trimmedArgs = args.trim();
        if (isNotNumeric(trimmedArgs) || trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE));
        }

        Integer number = Integer.parseInt(trimmedArgs);
        if (number > 999) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_EXCEEDED_QUANTITY));
        }

        createsListOfNumbers(number);

        return new CheckCommand(new QuantityContainsNumberPredicate(quantities));
    }

    private void createsListOfNumbers(Integer number) {
        for (Integer i = number; i >= 0; i--) {
            quantities.add(Integer.toString(i));
        }
    }

    public boolean isNotNumeric(String trimmedArgs) {
        return trimmedArgs != null && !trimmedArgs.matches("[+]?\\d*");
    }
}
```
###### \java\seedu\address\logic\parser\SellCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.model.book.Isbn.MESSAGE_ISBN_CONSTRAINTS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SellCommand;
import seedu.address.logic.commands.SellCommand.SellBookDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Quantity;

/**
 * Parses input arguments and creates a new SellCommand object
 */
public class SellCommandParser implements Parser<SellCommand> {

    /**
     * Parses the given (@code String} of arguments in the context of the SellCommand
     * and returns a SellCommand object for execution.
     * @throw ParseException if the user input does not conform the expected format
     */
    public SellCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ISBN, PREFIX_QUANTITY);

        Index index;
        Isbn isbn;
        String argsType = argMultimap.getArgsType();

        String findBookBy;
        switch(argsType) {
        case("Isbn"):
            try {
                isbn = ParserUtil.parseIsbn(argMultimap.getValue(PREFIX_ISBN).get());
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_ISBN_CONSTRAINTS, SellCommand.MESSAGE_USAGE), pe);
            }
            findBookBy = isbn.value;
            break;

        case("Index"):
            try {
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE), pe);
            }
            findBookBy = Integer.toString(index.getZeroBased());
            break;

        case ("Both"):
        case ("None"):
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE));
        }

        SellBookDescriptor sellBookDescriptor = new SellCommand.SellBookDescriptor();
        if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            Quantity quantity = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());
            sellBookDescriptor.setQuantity(quantity);
        }

        if (!sellBookDescriptor.isQuantityFieldSpecified() || sellBookDescriptor.getQuantity().getValue().equals("0")) {
            throw new ParseException(Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
        }


        return new SellCommand(findBookBy, argsType, sellBookDescriptor);
    }
}
```
###### \java\seedu\address\model\book\Cost.java
``` java
package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a book's cost price in the book inventory
 * Guarantee: immutable; is a valid as declared in (@link #isValidCost(String)
 */
public class Cost {

    public static final String MESSAGE_COST_CONSTRAINTS =
            "Costs should be numerical and in 2 decimal places or none at all\n"
            + "E.g. $4, $3.02";
    // "$" can be omitted and is optional, prices can be in 2 decimal places or none at all
    // e.g. $4 or $3.02 is accepted
    public static final String COST_VALIDATION_REGEX = "(\\$)?\\d+(\\.\\d{2})?";

    public final String value;

    /**
     * Constructs an {@code Cost}.
     *
     * @param cost A valid cost
     */
    public Cost(String cost) {
        requireNonNull(cost);
        checkArgument(isValidCost(cost), MESSAGE_COST_CONSTRAINTS);
        value = cost;
    }

    /**
     * Returns if a given string is a valid cost
     */
    public static boolean isValidCost(String test) {
        return test.matches(COST_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Price // instanceof handles nulls
                && value.equals(((Price) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\book\QuantityContainsNumberPredicate.java
``` java
package seedu.address.model.book;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Book}'s {@code Quantity} matches any of the quantities given
 */
public class QuantityContainsNumberPredicate implements Predicate<Book> {
    private final List<String> quantities;

    public QuantityContainsNumberPredicate(List<String> quantities) {
        this.quantities = quantities;
    }

    @Override
    public boolean test(Book book) {
        return quantities.stream()
                .anyMatch(quantity -> StringUtil.containsWordIgnoreCase(book.getQuantity().getValue(), quantity));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuantityContainsNumberPredicate // instanceof handles nulls
                && quantities.equals(((QuantityContainsNumberPredicate) other).quantities)); // state checks
    }
}
```
###### \java\seedu\address\model\BookInventory.java
``` java
    /**
     * Sorts the {@code BookInventory} according to quantity
     */
    public void sortBooks() {
        this.books.sortBooks();
    }

    //// get data operations

    public Queue<String> getCompleteIsbn(String isbnText) {
        return books.getIsbnList(isbnText);
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortBooksUsingQuantity() {
        versionedBookInventory.sortBooks();
    }
    //=========== Filtered Book List Accessors =============================================================

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public Queue<String> getCompleteIsbn(String isbnText) {
        return versionedBookInventory.getCompleteIsbn(isbnText);
    }

    //=========== Undo/Redo =================================================================================

    //@author
    @Override
    public boolean canUndoAddressBook() {
        return versionedBookInventory.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedBookInventory.canRedo();
    }

    @Override
    public void undoBookInventory() {
        versionedBookInventory.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoBookInventory() {
        versionedBookInventory.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitBookInventory() {
        versionedBookInventory.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedBookInventory.equals(other.versionedBookInventory)
                && filteredBooks.equals(other.filteredBooks);
    }

}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        case TAB:
            keyEvent.consume();
            navigateToNextIsbn();
            commandTextField.requestFocus();
            commandTextField.positionCaret(commandTextField.getLength());
            break;
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Updates the text field with the next isbn found in inventory list,
     * if there exists a next isbn in inventory list
     */
    private void navigateToNextIsbn() {
        String curr = commandTextField.getText();
        if (curr.trim().isEmpty() || !curr.contains("i/")) {
            return;
        }
        String isbnText = curr.substring(curr.indexOf("i/") + LENGTH_OF_PREFIX);
        String isbn;
        raise(new NewResultAvailableEvent(""));

        // Switches to the next Isbn in the queue if user did not edit the original substring
        // Else clears and remake the queue with the new substring
        if (isbnText.equals(isbnList.peek())) {
            isbn = isbnList.remove();
            isbnList.add(isbn);
        } else {
            isbnList.clear();
            isbnList = logic.getCompleteIsbn(isbnText);
        }
        isbn = isbnList.peek();

        // Adds the isbn found to the end of the original string if user left the field empty
        // Replaces the substring to a full isbn containing it if complete string is found
        if (isbnText.isEmpty()) {
            replaceText(curr + isbn);
        } else if (isbn != null) {
            String updated = curr.replaceFirst(isbnText, isbn);
            replaceText(updated);
        }
    }

```
