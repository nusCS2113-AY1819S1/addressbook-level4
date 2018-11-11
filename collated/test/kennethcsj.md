# kennethcsj
###### \java\seedu\address\logic\commands\CheckCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.commons.core.Messages.MESSAGE_BOOKS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBooks.ART;
import static seedu.address.testutil.TypicalBooks.BIOLOGY;
import static seedu.address.testutil.TypicalBooks.getTypicalBookInventory;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.QuantityContainsNumberPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code CheckCommand}
 */
public class CheckCommandTest {
    private Model model = new ModelManager(getTypicalBookInventory(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalBookInventory(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        QuantityContainsNumberPredicate firstPredicate =
                new QuantityContainsNumberPredicate(Collections.singletonList("5"));
        QuantityContainsNumberPredicate secondPredicate =
                new QuantityContainsNumberPredicate(Collections.singletonList("10"));

        CheckCommand checkFirstCommand = new CheckCommand(firstPredicate);
        CheckCommand checkSecondCommand = new CheckCommand(secondPredicate);

        // same object -> returns equal
        assertEquals(checkFirstCommand, checkFirstCommand);

        // same values -> returns equal
        CheckCommand checkFirstCommandCopy = new CheckCommand(firstPredicate);
        assertEquals(checkFirstCommand, checkFirstCommandCopy);

        // different type -> return not equal
        assertNotEquals(checkFirstCommand, 1);

        // null -> not equal
        assertNotEquals(checkFirstCommand, null);

        // different quantity -> returns not equal
        assertNotEquals(checkFirstCommand, checkSecondCommand);
    }

    @Test
    public void execute_nonZeroValue_booksFound () {
        String expectedMessage = String.format(MESSAGE_BOOKS_LISTED_OVERVIEW, 2);
        QuantityContainsNumberPredicate predicate = preparePredicate("10 9 8 7 6 5 4 3 2 1 0");
        CheckCommand command = new CheckCommand(predicate);
        expectedModel.updateFilteredBookList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ART, BIOLOGY), model.getFilteredBookList());
    }

    private QuantityContainsNumberPredicate preparePredicate(String userInput) {
        return new QuantityContainsNumberPredicate(Arrays.asList(userInput.split("\\s+", 999)));
    }
}
```
###### \java\seedu\address\logic\commands\SellCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_ADD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_ADD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BIOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBookAtIndex;
import static seedu.address.logic.commands.SellCommand.MESSAGE_QUANTITY_SOLD;
import static seedu.address.testutil.TypicalBooks.getTypicalBookInventory;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_ONE_BASED_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_ONE_BASED_SECOND_BOOK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.BookInventory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.SellBookDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for SellCommand.
 */
public class SellCommandTest {

    private Model model = new ModelManager(getTypicalBookInventory(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_quantityFieldSpecifiedWithIndex_failure() {
        SellCommand.SellBookDescriptor descriptor =
                new SellBookDescriptorBuilder().withQuantity(VALID_QUANTITY_BIOLOGY).build();
        SellCommand sellCommand = new SellCommand(INDEX_ONE_BASED_FIRST_BOOK, "Index", descriptor);

        String expectedMessage = SellCommand.MESSAGE_MIN_QUANTITY;

        assertCommandFailure(sellCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_quantityFieldSpecifiedWithIsbn_failure() {
        SellCommand.SellBookDescriptor descriptor =
                new SellBookDescriptorBuilder().withQuantity(VALID_QUANTITY_BIOLOGY).build();
        SellCommand sellCommand = new SellCommand(VALID_ISBN_ADD, "Isbn", descriptor);

        String expectedMessage = SellCommand.MESSAGE_MIN_QUANTITY;

        assertCommandFailure(sellCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_quantityFieldSpecifiedWithIndex_success() {
        Index indexLastBook = Index.fromOneBased(model.getFilteredBookList().size());
        String lastIndex = Integer.toString(indexLastBook.getZeroBased());
        Book lastBook = model.getFilteredBookList().get(indexLastBook.getZeroBased());

        BookBuilder bookInList = new BookBuilder(lastBook);
        Integer expectedQuantityLeft = lastBook.getQuantity().toInteger() - Integer.parseInt(VALID_QUANTITY_BIOLOGY);
        Book soldBook = bookInList.withQuantity(Integer.toString(expectedQuantityLeft)).build();

        SellCommand.SellBookDescriptor descriptor =
                new SellBookDescriptorBuilder().withQuantity(VALID_QUANTITY_BIOLOGY).build();
        SellCommand sellCommand = new SellCommand(lastIndex, "Index", descriptor);

        String expectedMessage = MESSAGE_QUANTITY_SOLD + descriptor.getQuantity().getValue()
                + "\n" + String.format(SellCommand.MESSAGE_SELL_BOOK_SUCCESS, soldBook);

        Model expectedModel = new ModelManager(new BookInventory(model.getBookInventory()), new UserPrefs());
        expectedModel.updateBook(lastBook, soldBook);
        expectedModel.commitBookInventory();

        assertCommandSuccess(sellCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_quantityFieldSpecifiedWithIsbn_success() {
        Book lastBook = model.getBook("9781401312855");

        BookBuilder bookInList = new BookBuilder(lastBook);
        Integer expectedQuantityLeft = lastBook.getQuantity().toInteger() - Integer.parseInt(VALID_QUANTITY_ADD);
        Book soldBook = bookInList.withQuantity(Integer.toString(expectedQuantityLeft)).build();

        SellCommand.SellBookDescriptor descriptor =
                new SellBookDescriptorBuilder().withQuantity(VALID_QUANTITY_ADD).build();
        SellCommand sellCommand = new SellCommand("9781401312855", "Isbn", descriptor);

        String expectedMessage = MESSAGE_QUANTITY_SOLD + descriptor.getQuantity().getValue()
                + "\n" + String.format(SellCommand.MESSAGE_SELL_BOOK_SUCCESS, soldBook);

        Model expectedModel = new ModelManager(new BookInventory(model.getBookInventory()), new UserPrefs());
        expectedModel.updateBook(lastBook, soldBook);
        expectedModel.commitBookInventory();

        assertCommandSuccess(sellCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of BookInventory
     */
    @Test
    public void execute_invalidBookIndexFilteredList_failure() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);
        String outOfBoundIndex = INDEX_ONE_BASED_SECOND_BOOK;

        // ensures that outOfBoundIndex is still in bounds of BookInventory list
        assertTrue(Integer.parseInt(outOfBoundIndex) - 1 < model.getBookInventory().getBookList().size());

        SellCommand sellCommand = new SellCommand(outOfBoundIndex, "Index",
                new SellBookDescriptorBuilder().withQuantity(VALID_QUANTITY_BIOLOGY).build());

        assertCommandFailure(sellCommand, model, commandHistory, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }
}
```
###### \java\seedu\address\logic\parser\CheckCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.CheckCommand;
import seedu.address.model.book.QuantityContainsNumberPredicate;

public class CheckCommandParserTest {

    private CheckCommandParser parser = new CheckCommandParser();

    @Test
    public void parse_invalidArg_throwsParseException() {
        // empty arguments
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE));

        // multiple arguments
        assertParseFailure(parser, "4 5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE));

        // negative argument
        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE));

        // not numeric argument
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE));

        // 1000 and above argument
        assertParseFailure(parser, "1000",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_EXCEEDED_QUANTITY));
    }

    @Test
    public void parse_validArgs_returnsCheckCommand () {
        // no leading and trailing whitespaces
        CheckCommand expectedCheckCommand =
                new CheckCommand(new QuantityContainsNumberPredicate(Arrays.asList("3", "2", "1", "0")));
        assertParseSuccess(parser, "3", expectedCheckCommand);

        // whitespaces between command and quantity
        assertParseSuccess(parser, "\n \t 3", expectedCheckCommand);
    }
}
```
###### \java\seedu\address\logic\parser\SellCommandParserTest.java
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
import seedu.address.logic.commands.SellCommand;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Quantity;
import seedu.address.testutil.SellBookDescriptorBuilder;

public class SellCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE);

    private SellCommandParser parser = new SellCommandParser();

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
        assertParseFailure(parser, "-5 q/" + VALID_QUANTITY_ADD, MESSAGE_INVALID_FORMAT);

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

        SellCommand.SellBookDescriptor descriptor =
                new SellBookDescriptorBuilder().withQuantity(VALID_QUANTITY_ADD).build();
        SellCommand expectedCommand = new SellCommand(index, "Index", descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\testutil\SellBookDescriptorBuilder.java
``` java
package seedu.address.testutil;

import seedu.address.logic.commands.SellCommand;
import seedu.address.model.book.Book;
import seedu.address.model.book.Quantity;

/**
 * A utility class to help with building SellBookDescriptor objects.
 */
public class SellBookDescriptorBuilder {

    private SellCommand.SellBookDescriptor descriptor;

    public SellBookDescriptorBuilder() {
        descriptor = new SellCommand.SellBookDescriptor();
    }

    public SellBookDescriptorBuilder(SellCommand.SellBookDescriptor descriptor) {
        this.descriptor = new SellCommand.SellBookDescriptor(descriptor);
    }

    public SellBookDescriptorBuilder(Book book) {
        descriptor = new SellCommand.SellBookDescriptor();
        descriptor.setQuantity(book.getQuantity());
    }

    /**
     * Sets the {@code Quantity} of the {@code EditBookDescriptor} that we are building.
     */
    public SellBookDescriptorBuilder withQuantity(String quantity) {
        descriptor.setQuantity(new Quantity(quantity));
        return this;
    }

    public SellCommand.SellBookDescriptor build() {
        return descriptor;
    }
}
```
###### \java\seedu\address\ui\CommandBoxTest.java
``` java
    @Test
    public void handleKeyPress_tab () {
        // empty command box
        assertStoredIsbn(KeyCode.TAB, "");

        // other prefix present
        commandBoxHandle.setText("n/");
        assertStoredIsbn(KeyCode.TAB, "n/");
        commandBoxHandle.setText("q/");
        assertStoredIsbn(KeyCode.TAB, "q/");

        // invalid prefix present
        commandBoxHandle.setText("z/");
        assertStoredIsbn(KeyCode.TAB, "z/");

        // valid prefix with no input, with multiple press
        commandBoxHandle.setText("i/");
        assertStoredIsbn(KeyCode.TAB, "i/9780748137992");
        assertStoredIsbn(KeyCode.TAB, "i/9781401309572");
        assertStoredIsbn(KeyCode.TAB, "i/9780062294432");
        assertStoredIsbn(KeyCode.TAB, "i/9780062472601");
        assertStoredIsbn(KeyCode.TAB, "i/9780767905923");
        assertStoredIsbn(KeyCode.TAB, "i/9781401310462");
        assertStoredIsbn(KeyCode.TAB, "i/9781401312855");
        assertStoredIsbn(KeyCode.TAB, "i/9780748137992"); // restarts when reach end of list

        // valid prefix with half input isbn, with multiple presses
        commandBoxHandle.setText("i/97814");
        assertStoredIsbn(KeyCode.TAB, "i/9781401309572");
        assertStoredIsbn(KeyCode.TAB, "i/9781401310462");
        assertStoredIsbn(KeyCode.TAB, "i/9781401312855");
        assertStoredIsbn(KeyCode.TAB, "i/9781401309572"); // restarts when reach end of list

        // valid prefix with half input isbn not in list
        commandBoxHandle.setText("i/77");
        assertStoredIsbn(KeyCode.TAB, "i/77");

        // valid prefix with invalid input
        commandBoxHandle.setText("i/ab");
        assertStoredIsbn(KeyCode.TAB, "i/ab");

        // commands  with isbn prefix
        commandBoxHandle.setText("sell i/");
        assertStoredIsbn(KeyCode.TAB, "sell i/9780748137992");
        commandBoxHandle.setText("stock i/");
        assertStoredIsbn(KeyCode.TAB, "stock i/9780748137992");
    }

```
