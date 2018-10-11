package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBooks.getTypicalBookInventory;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.testutil.BookBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalBookInventory(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Book validBook = new BookBuilder().build();

        Model expectedModel = new ModelManager(model.getBookInventory(), new UserPrefs());
        expectedModel.addBook(validBook);
        expectedModel.commitBookInventory();

        assertCommandSuccess(new AddCommand(validBook), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validBook), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Book bookInList = model.getBookInventory().getBookList().get(0);
        assertCommandFailure(new AddCommand(bookInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_BOOK);
    }

}
