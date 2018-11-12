# kohjunkiat
###### \java\seedu\address\commons\core\StatisticCenter.java
``` java
package seedu.address.commons.core;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import seedu.address.model.ReadOnlyBookInventory;
import seedu.address.model.statistic.Statistic;


/**
 * Manages the statistics' version the app.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class StatisticCenter implements Serializable {

    private static Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
    private static volatile StatisticCenter instance;
    private static final int month = localCalendar.get(Calendar.MONTH) + 1;
    private static final int year = localCalendar.get(Calendar.YEAR);
    private volatile Statistic statistic;

    private StatisticCenter () {
        //Prevent form the reflection api.
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        statistic = new Statistic(month, year);
    }

    public static StatisticCenter getInstance() {
        if (instance == null) { //if there is no instance available... create new one
            synchronized (StatisticCenter.class) {
                if (instance == null) {
                    instance = new StatisticCenter();
                }
            }
        }
        return instance;
    }

    protected StatisticCenter readResolve() {
        return getInstance();
    }
    public Statistic getStatistic() {
        return statistic;
    }

    public void loadStatistic(Statistic copy) {
        this.statistic = copy;
    }

    public void calibrateInventory(ReadOnlyBookInventory readOnlyBookInventory) {
        statistic.getInventory().calibrate(readOnlyBookInventory);
    }

    public void resetData() {
        statistic = new Statistic(month, year);
    }
}
```
###### \java\seedu\address\logic\commands\StockCommand.java
``` java
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
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " " + PREFIX_ISBN + " "
            + PREFIX_QUANTITY;


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
```
###### \java\seedu\address\logic\commands\ViewStatisticCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.StatisticCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all books in the book inventory to the user.
 */
public class ViewStatisticCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(StatisticCenter.getInstance().getStatistic().toString());
    }
}
```
###### \java\seedu\address\model\statistic\Expense.java
``` java
package seedu.address.model.statistic;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents Inventory in the month's Statistic
 */
public class Expense {
    public static final String MESSAGE_EXPENSE_CONSTRAINTS =
            "Expense should be numerical and in 2 decimal places or none at all\n"
                    + "E.g. 4, 3.02";
    private static final String QUANTITY_VALIDATION_REGEX = "\\d+(\\.\\d{2})?";
    private volatile String value;

    /**
     * Constructor for Json Jackson
     */
    public Expense() {
        super();
    }

    public Expense(String expense) {
        requireNonNull(expense);
        checkArgument(isValidExpense(expense), MESSAGE_EXPENSE_CONSTRAINTS);
        value = expense;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static boolean isValidExpense(String test) {
        return test.matches(QUANTITY_VALIDATION_REGEX);
    }

    /**
     * Increase expense
     * @param price
     * @param quantity
     */
    public void increase(String price, String quantity) {
        this.value = Float.toString(
                Float.parseFloat(value) + (Float.parseFloat(price) * Float.parseFloat(quantity)));
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Expense // instanceof handles nulls
                && value.equals(((Expense) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\statistic\Inventory.java
``` java
package seedu.address.model.statistic;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import javafx.collections.ObservableList;
import seedu.address.model.ReadOnlyBookInventory;
import seedu.address.model.book.Book;

/**
 * Represents Revenue in the month's Statistic
 */
public class Inventory {
    public static final String MESSAGE_INVENTORY_CONSTRAINTS =
            "Inventory should be numerical and in 2 decimal places or none at all\n"
                    + "E.g. 4, 3.02";
    private static final String QUANTITY_VALIDATION_REGEX = "\\d+(\\.\\d{2})?";
    private volatile String value;

    /**
     * Constructor for Json Jackson
     */
    public Inventory() {
        super();
    }

    public Inventory(String inventory) {
        requireNonNull(inventory);
        checkArgument(isValidInventory(inventory), MESSAGE_INVENTORY_CONSTRAINTS);
        value = inventory;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static boolean isValidInventory(String test) {
        return test.matches(QUANTITY_VALIDATION_REGEX);
    }


    /**
     * increase inventory
     * @param price
     * @param quantity
     */
    public void increase(String price, String quantity) {
        this.value = Float.toString(
                Float.parseFloat(value) + (Float.parseFloat(price) * Float.parseFloat(quantity)));
    }

    /**
     * decrease inventory
     * @param price
     * @param quantity
     */
    public void decrease(String price, String quantity) {
        this.value = Float.toString(
                Float.parseFloat(value) - (Float.parseFloat(price) * Float.parseFloat(quantity)));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Inventory // instanceof handles nulls
                && value.equals(((Inventory) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Calibrates Inventory according to current inventory.
     * @param readOnlyBookInventory
     */
    public void calibrate(ReadOnlyBookInventory readOnlyBookInventory) {
        this.value = "0";
        ObservableList<Book> bookList = readOnlyBookInventory.getBookList();
        bookList.forEach((book) -> {
            this.increase(book.getCost().toString(), book.getQuantity().getValue());
        });
    }
}

```
###### \java\seedu\address\model\statistic\Revenue.java
``` java
package seedu.address.model.statistic;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents Revenue in the month's Statistic
 */
public class Revenue {
    public static final String MESSAGE_REVENUE_CONSTRAINTS =
            "Revenue should be numerical and in 2 decimal places or none at all\n"
                    + "E.g. 4, 3.02";
    private static final String QUANTITY_VALIDATION_REGEX = "\\d+(\\.\\d{2})?";
    private volatile String value;


    /**
     * Constructor for Json Jackson
     */
    public Revenue () {
        super();
    }

    public Revenue(String revenue) {
        requireNonNull(revenue);
        checkArgument(isValidRevenue(revenue), MESSAGE_REVENUE_CONSTRAINTS);
        value = revenue;
    }



    public static boolean isValidRevenue(String test) {
        return test.matches(QUANTITY_VALIDATION_REGEX);
    }

    /**
     * Increase revenue
     * @param price
     * @param quantity
     */
    public void increase(String price, String quantity) {
        this.value = Float.toString(
                Float.parseFloat(value) + (Float.parseFloat(price) * Float.parseFloat(quantity)));
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Revenue // instanceof handles nulls
                && value.equals(((Revenue) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\statistic\Statistic.java
``` java
package seedu.address.model.statistic;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Represents a Statistic in a month.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Statistic {
    public static final String STARTING_FIGURE = "0.00";
    // Data fields
    private volatile Revenue revenue;
    private volatile Inventory inventory;
    private volatile Expense expense;
    private int month;
    private int year;

    /**
     * Constructor for Json Jackson
     */
    public Statistic() {
        super();
    }
    /**
     * Every field must be present and not null.
     */
    public Statistic(int month, int year) {
        this.revenue = new Revenue(STARTING_FIGURE);
        this.inventory = new Inventory(STARTING_FIGURE);
        this.expense = new Expense((STARTING_FIGURE));
        this.month = month;
        this.year = year;
    }

    public Statistic(Inventory inventory, Expense expense, Revenue revenue, int month, int year) {
        this.revenue = revenue;
        this.inventory = inventory;
        this.expense = expense;
        this.month = month;
        this.year = year;
    }

    public String getMonth() {
        return Integer.toString(month);
    }

    public String getYear() {
        return Integer.toString(year);
    }

    public Revenue getRevenue() {
        return revenue;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Expense getExpense() {
        return expense;
    }

    /**
     * Increases the Revenue
     * @param price selling price of book
     * @param amount number of books sold
     */

    public void sell(String price, String cost, String
            amount) {
        revenue.increase(price, amount);
        inventory.decrease(cost, amount);
        expense.increase(cost, amount);
    }

    /**
     * Returns true if both statistic of the same month and year
     * have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameStatistic(Statistic otherStatistic) {
        if (otherStatistic == this) {
            return true;
        }

        return otherStatistic != null
                && otherStatistic.getMonth().equals(getMonth())
                && otherStatistic.getYear().equals(getYear())
                && (otherStatistic.getRevenue().equals(getRevenue())
                || otherStatistic.getInventory().equals(getInventory())
                || otherStatistic.getExpense().equals(getExpense()));
    }

    /**
     * Returns true if both stats have the same identity and data fields.
     * This defines a stronger notion of equality between two stats.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Statistic)) {
            return false;
        }

        Statistic otherStatistic = (Statistic) other;
        return otherStatistic.getMonth().equals(getMonth())
                && otherStatistic.getYear().equals(getYear())
                && otherStatistic.getExpense().equals(getExpense())
                && otherStatistic.getInventory().equals(getInventory())
                && otherStatistic.getRevenue().equals(getRevenue());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(month, year, revenue);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Month: ")
                .append(getMonth())
                .append(" Year: ")
                .append(getYear())
                .append(" Revenue: ")
                .append(revenue.toString())
                .append(" Expense: ")
                .append(expense.toString())
                .append(" Inventory: ")
                .append(inventory.toString());
        return builder.toString();
    }

}
```
###### \java\seedu\address\storage\JsonStatisticStorage.java
``` java
package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.statistic.Statistic;

/**
 * A class to access Statistics stored in the hard disk as a json file
 */
public class JsonStatisticStorage implements StatisticsStorage {
    private Path filePath;

    public JsonStatisticStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getStatisticFilePath() {
        return filePath;
    }

    @Override
    public Optional<Statistic> readStatistic() throws DataConversionException {
        return readStatistic(filePath);
    }

    /**
     * Similar to {@link #readStatistic()}
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<Statistic> readStatistic(Path prefsFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(prefsFilePath, Statistic.class);
    }

    @Override
    public void saveStatistic(Statistic userPrefs) throws IOException {
        JsonUtil.saveJsonFile(userPrefs, filePath);
    }

}
```
###### \java\seedu\address\storage\StatisticsStorage.java
``` java
package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.statistic.Statistic;

/**
 * Represents a storage for {@link seedu.address.model.statistic.Statistic}.
 */
public interface StatisticsStorage {
    /**
     * Returns the file path of the Statistic data file.
     */
    Path getStatisticFilePath();

    /**
     * Returns Statistic data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<Statistic> readStatistic() throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.address.model.statistic.Statistic} to the storage.
     * @param userPrefs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveStatistic(Statistic userPrefs) throws IOException;

}
```
