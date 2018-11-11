# kohjunkiat
###### \java\seedu\address\logic\parser\StockCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ISBN_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ISBN_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_ADD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_ADD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.StockCommand;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Quantity;
import seedu.address.testutil.StockBookDescriptorBuilder;

public class StockCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, StockCommand.MESSAGE_USAGE);

    private StockCommandParser parser = new StockCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index and isbn specified
        assertParseFailure(parser, " " + PREFIX_QUANTITY + VALID_QUANTITY_ADD, MESSAGE_INVALID_FORMAT);

        // no field specified with index
        assertParseFailure(parser, "1", Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        // no field specified with isbn
        assertParseFailure(parser, " " + PREFIX_ISBN + VALID_ISBN_ADD, Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        // no index and field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-8 q/" + VALID_QUANTITY_ADD, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 q/" + VALID_QUANTITY_ADD, MESSAGE_INVALID_FORMAT);

        // invalid isbn
        assertParseFailure(parser, " " + PREFIX_ISBN + INVALID_ISBN_DESC + " /q" + VALID_QUANTITY_ADD,
                Isbn.MESSAGE_ISBN_CONSTRAINTS);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 z/3", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid quantity with index
        assertParseFailure(parser, "1" + INVALID_QUANTITY_DESC, Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        // invalid quantity with isbn
        assertParseFailure(parser, ISBN_DESC_AMY + INVALID_QUANTITY_DESC,
                Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
    }

    @Test
    public void parse_quantityFieldSpecified_success() {
        Index targetIndex = INDEX_FIRST_BOOK;
        String index = Integer.toString(targetIndex.getZeroBased());
        String userInput = targetIndex.getOneBased() + QUANTITY_DESC_AMY;

        StockCommand.StockBookDescriptor descriptor =
                new StockBookDescriptorBuilder().withQuantity(VALID_QUANTITY_ADD).build();
        StockCommand expectedCommand = new StockCommand(index, "Index", descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\model\statistic\ExpenseTest.java
``` java
package seedu.address.model.statistic;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;



public class ExpenseTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Expense(null));
    }

    @Test
    public void constructor_invalidExpense_throwsIllegalArgumentException() {
        String invalidExpense = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Expense(invalidExpense));
    }

    @Test
    public void isValidExpense() {
        // null price
        Assert.assertThrows(NullPointerException.class, () -> Expense.isValidExpense(null));

        // blank price
        assertFalse(Expense.isValidExpense("")); // empty string
        assertFalse(Expense.isValidExpense(" ")); // spaces only


        // invalid parts
        assertFalse(Expense.isValidExpense("6.012")); // too many decimals
        assertFalse(Expense.isValidExpense("-3")); // no negative integers
        assertFalse(Expense.isValidExpense("a1.2")); // alphabet in string

        // valid revenue
        assertTrue(Expense.isValidExpense("5")); // No decimals
        assertTrue(Expense.isValidExpense("0.12")); //2 decimal place
        assertTrue(Expense.isValidExpense("1123.10")); // Only decimals
    }
}
```
###### \java\seedu\address\model\statistic\InventoryTest.java
``` java
package seedu.address.model.statistic;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;
public class InventoryTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Inventory(null));
    }

    @Test
    public void constructor_invalidInventory_throwsIllegalArgumentException() {
        String invalidInventory = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Inventory(invalidInventory));
    }

    @Test
    public void isValidInventory() {
        // null price
        Assert.assertThrows(NullPointerException.class, () -> Inventory.isValidInventory(null));

        // blank price
        assertFalse(Inventory.isValidInventory("")); // empty string
        assertFalse(Inventory.isValidInventory(" ")); // spaces only


        // invalid parts
        assertFalse(Inventory.isValidInventory("6.012")); // too many decimals
        assertFalse(Inventory.isValidInventory("-3")); // no negative integers
        assertFalse(Inventory.isValidInventory("a1.2")); // alphabet in string

        // valid revenue
        assertTrue(Inventory.isValidInventory("5")); // No decimals
        assertTrue(Inventory.isValidInventory("0.12")); //2 decimal place
        assertTrue(Inventory.isValidInventory("1123.10")); // Only decimals
    }
}
```
###### \java\seedu\address\model\statistic\RevenueTest.java
``` java
package seedu.address.model.statistic;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class RevenueTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Revenue(null));
    }

    @Test
    public void constructor_invalidRevenue_throwsIllegalArgumentException() {
        String invalidRevenue = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Revenue(invalidRevenue));
    }

    @Test
    public void isValidRevenue() {
        // null price
        Assert.assertThrows(NullPointerException.class, () -> Revenue.isValidRevenue(null));

        // blank price
        assertFalse(Revenue.isValidRevenue("")); // empty string
        assertFalse(Revenue.isValidRevenue(" ")); // spaces only


        // invalid parts
        assertFalse(Revenue.isValidRevenue("6.012")); // too many decimals
        assertFalse(Revenue.isValidRevenue("-3")); // no negative integers
        assertFalse(Revenue.isValidRevenue("a1.2")); // alphabet in string

        // valid revenue
        assertTrue(Revenue.isValidRevenue("5")); // No decimals
        assertTrue(Revenue.isValidRevenue("0.12")); //2 decimal place
        assertTrue(Revenue.isValidRevenue("1123.10")); // Only decimals
    }
}
```
###### \java\seedu\address\model\statistic\StatisticTest.java
``` java
package seedu.address.model.statistic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_JUNE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INVENTORY_JUNE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_JUNE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVENUE_JUNE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_JUNE;
import static seedu.address.testutil.TypicalStatistic.JAN;
import static seedu.address.testutil.TypicalStatistic.MAY;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.StatisticBuilder;

public class StatisticTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameStatistic() {
        // same object -> returns true
        assertTrue(JAN.isSameStatistic(JAN));

        // null -> returns false
        assertFalse(JAN.isSameStatistic(null));

        // different month and year -> returns false
        Statistic editedStatistic = new StatisticBuilder(JAN).withYear(VALID_YEAR_JUNE)
                .withMonth(VALID_MONTH_JUNE).build();
        assertFalse(JAN.isSameStatistic(editedStatistic));

        // different month -> returns false
        editedStatistic = new StatisticBuilder(JAN).withMonth(VALID_MONTH_JUNE).build();
        assertFalse(JAN.isSameStatistic(editedStatistic));

        // same month, same year , same inventory, different attributes -> returns true
        editedStatistic = new StatisticBuilder(JAN).withExpense(VALID_EXPENSE_JUNE)
                .withRevenue(VALID_REVENUE_JUNE).build();
        assertTrue(JAN.isSameStatistic(editedStatistic));

        // same month, same year, same revenue, different attributes -> returns true
        editedStatistic = new StatisticBuilder(JAN).withInventory(VALID_INVENTORY_JUNE)
                .withExpense(VALID_EXPENSE_JUNE).build();
        assertTrue(JAN.isSameStatistic(editedStatistic));

        // same month, same year, same expense, different attributes -> returns true
        editedStatistic = new StatisticBuilder(JAN).withRevenue(VALID_REVENUE_JUNE)
                .withInventory(VALID_INVENTORY_JUNE).build();
        assertTrue(JAN.isSameStatistic(editedStatistic));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Statistic janCopy = new StatisticBuilder(JAN).build();
        assertTrue(JAN.equals(janCopy));

        // same object -> returns true
        assertTrue(JAN.equals(JAN));



        // null -> returns false
        assertFalse(JAN.equals(null));

        // different type -> returns false
        assertFalse(JAN.equals(5));

        // different statistic -> returns false
        assertFalse(JAN.equals(MAY));

        // different inventory -> returns false
        Statistic editedStatistic = new StatisticBuilder(JAN).withInventory(VALID_INVENTORY_JUNE).build();
        assertFalse(JAN.equals(editedStatistic));

        // different revenue -> returns false
        editedStatistic = new StatisticBuilder(JAN).withRevenue(VALID_REVENUE_JUNE).build();
        assertFalse(JAN.equals(editedStatistic));

        // different expense -> returns false
        editedStatistic = new StatisticBuilder(JAN).withExpense(VALID_EXPENSE_JUNE).build();
        assertFalse(JAN.equals(editedStatistic));

        // different month -> returns false
        editedStatistic = new StatisticBuilder(JAN).withMonth(VALID_MONTH_JUNE).build();
        assertFalse(JAN.equals(editedStatistic));

        // different year -> returns false
        editedStatistic = new StatisticBuilder(JAN).withYear(VALID_YEAR_JUNE).build();
        assertFalse(JAN.equals(editedStatistic));
    }
}
```
###### \java\seedu\address\testutil\StatisticBuilder.java
``` java
package seedu.address.testutil;

import seedu.address.model.statistic.Expense;
import seedu.address.model.statistic.Inventory;
import seedu.address.model.statistic.Revenue;
import seedu.address.model.statistic.Statistic;

/**
 * A utility class to help with building Statistic objects.
 */
public class StatisticBuilder {

    public static final String DEFAULT_INVENTORY = "0.00";
    public static final String DEFAULT_EXPENSE = "0.00";
    public static final String DEFAULT_REVENUE = "0.00";
    public static final String DEFAULT_YEAR = "2018";
    public static final String DEFAULT_MONTH = "12";


    private Revenue revenue;
    private Inventory inventory;
    private Expense expense;
    private int month;
    private int year;

    public StatisticBuilder() {
        inventory = new Inventory(DEFAULT_INVENTORY);
        expense = new Expense(DEFAULT_EXPENSE);
        revenue = new Revenue(DEFAULT_REVENUE);
        year = Integer.parseInt(DEFAULT_YEAR);
        month = Integer.parseInt(DEFAULT_MONTH);
    }


    /**
     * Initializes the StatisticBuilder with the data of {@code statisticToCopy}.
     */
    public StatisticBuilder(Statistic statisticToCopy) {
        inventory = statisticToCopy.getInventory();
        expense = statisticToCopy.getExpense();
        revenue = statisticToCopy.getRevenue();
        year = Integer.parseInt(statisticToCopy.getYear());
        month = Integer.parseInt(statisticToCopy.getMonth());

    }

    /**
     * Sets the {@code Inventory} of the {@code Statistic} that we are building.
     */
    public StatisticBuilder withInventory(String inventory) {
        this.inventory = new Inventory(inventory);
        return this;
    }


    /**
     * Sets the {@code Quantity} of the {@code Statistic} that we are building.
     */
    public StatisticBuilder withMonth(String month) {
        this.month = Integer.parseInt(month);
        return this;
    }

    /**
     * Sets the {@code Expense} of the {@code Statistic} that we are building.
     */
    public StatisticBuilder withExpense(String expense) {
        this.expense = new Expense(expense);
        return this;
    }

    /**
     * Sets the {@code Revenue} of the {@code Statistic} that we are building.
     */
    public StatisticBuilder withRevenue(String revenue) {
        this.revenue = new Revenue(revenue);
        return this;
    }

    /**
     * Sets the {@code Cost} of the {@code Statistic} that we are building
     */
    public StatisticBuilder withYear(String year) {
        this.year = Integer.parseInt(year);
        return this;
    }

    public Statistic build() {
        return new Statistic(inventory, expense, revenue, month, year);
    }

}
```
###### \java\seedu\address\testutil\StockBookDescriptorBuilder.java
``` java
package seedu.address.testutil;

import seedu.address.logic.commands.StockCommand;
import seedu.address.model.book.Book;
import seedu.address.model.book.Quantity;

/**
 * A utility class to help with building StockBookDescriptor objects.
 */
public class StockBookDescriptorBuilder {
    private StockCommand.StockBookDescriptor descriptor;

    public StockBookDescriptorBuilder() {
        descriptor = new StockCommand.StockBookDescriptor();
    }

    public StockBookDescriptorBuilder(StockCommand.StockBookDescriptor descriptor) {
        this.descriptor = new StockCommand.StockBookDescriptor(descriptor);
    }

    public StockBookDescriptorBuilder(Book book) {
        descriptor = new StockCommand.StockBookDescriptor();
        descriptor.setQuantity(book.getQuantity());
    }

    /**
     * Sets the {@code Quantity} of the {@code EditBookDescriptor} that we are building.
     */
    public StockBookDescriptorBuilder withQuantity(String quantity) {
        descriptor.setQuantity(new Quantity(quantity));
        return this;
    }

    public StockCommand.StockBookDescriptor build() {
        return descriptor;
    }
}
```
###### \java\seedu\address\testutil\TypicalStatistic.java
``` java
package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_JUNE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_MAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INVENTORY_JUNE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INVENTORY_MAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_JUNE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_MAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVENUE_JUNE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVENUE_MAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_JUNE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_MAY;

import seedu.address.model.statistic.Statistic;

/**
 * A utility class containing a list of {@code Statistic} objects to be used in tests.
 */
public class TypicalStatistic {
    public static final Statistic JAN = new StatisticBuilder()
            .withInventory("10.10")
            .withExpense("9.90")
            .withRevenue("73.76")
            .withMonth("7")
            .withYear("2016").build();
    public static final Statistic FEB = new StatisticBuilder()
            .withInventory("90.00")
            .withExpense("97.00")
            .withRevenue("548.98")
            .withMonth("10")
            .withYear("2018").build();

    // Manually added - Statistic's details found in {@code CommandTestUtil}
    public static final Statistic MAY = new StatisticBuilder()
            .withInventory(VALID_INVENTORY_MAY)
            .withExpense(VALID_EXPENSE_MAY)
            .withRevenue(VALID_REVENUE_MAY)
            .withMonth(VALID_MONTH_MAY)
            .withYear(VALID_YEAR_MAY).build();
    public static final Statistic JUNE = new StatisticBuilder()
            .withInventory(VALID_INVENTORY_JUNE)
            .withExpense(VALID_EXPENSE_JUNE)
            .withRevenue(VALID_REVENUE_JUNE)
            .withMonth(VALID_MONTH_JUNE)
            .withYear(VALID_YEAR_JUNE).build();
}
```
