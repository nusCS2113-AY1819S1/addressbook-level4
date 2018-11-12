//@@author kennethcsj
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
