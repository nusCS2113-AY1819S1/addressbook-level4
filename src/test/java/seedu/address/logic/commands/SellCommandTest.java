package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BIOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SellCommand.MESSAGE_QUANTITY_SOLD;
import static seedu.address.testutil.TypicalBooks.getTypicalBookInventory;

import org.junit.Test;

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
}
