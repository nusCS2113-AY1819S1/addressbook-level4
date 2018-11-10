package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.BookInventory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.StockBookDescriptorBuilder;

import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BIOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.StockCommand.MESSAGE_QUANTITY_STOCK;
import static seedu.address.testutil.TypicalBooks.getTypicalBookInventory;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for StockCommand.
 */
public class StockCommandTest {
    private Model model = new ModelManager(getTypicalBookInventory(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_quantityFieldSpecifiedWithIndex_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredBookList().size());
        String lastIndex = Integer.toString(indexLastPerson.getZeroBased());
        Book lastBook = model.getFilteredBookList().get(indexLastPerson.getZeroBased());

        BookBuilder bookInList = new BookBuilder(lastBook);
        Integer expectedNewQuantity = lastBook.getQuantity().toInteger() + Integer.parseInt(VALID_QUANTITY_BIOLOGY);
        Book stockedBook = bookInList.withQuantity(Integer.toString(expectedNewQuantity)).build();

        StockCommand.StockBookDescriptor descriptor =
                new StockBookDescriptorBuilder().withQuantity(VALID_QUANTITY_BIOLOGY).build();
        StockCommand sellCommand = new StockCommand(lastIndex, "Index", descriptor);

        String expectedMessage = MESSAGE_QUANTITY_STOCK + descriptor.getQuantity().getValue()
                + "\n" + String.format(StockCommand.MESSAGE_STOCK_BOOK_SUCCESS, stockedBook);

        Model expectedModel = new ModelManager(new BookInventory(model.getBookInventory()), new UserPrefs());
        expectedModel.updateBook(lastBook, stockedBook);
        expectedModel.commitBookInventory();

        assertCommandSuccess(sellCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
