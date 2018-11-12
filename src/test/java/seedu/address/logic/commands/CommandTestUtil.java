package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BookInventory;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditBookDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_ADD = "Amy Bee";
    public static final String VALID_NAME_BIOLOGY = "Bob Choo";
    public static final String VALID_ISBN_ADD = "9780748137992";
    public static final String VALID_ISBN_BIOLOGY = "978-1-56619-909-4";
    public static final String VALID_PRICE_ADD = "19.97";
    public static final String VALID_PRICE_BIOLOGY = "98.21";
    public static final String VALID_COST_ADD = "19.60";
    public static final String VALID_COST_BIOLOGY = "98.01";
    public static final String VALID_QUANTITY_ADD = "1";
    public static final String VALID_QUANTITY_BIOLOGY = "8";
    public static final String VALID_TAG_SCIENCE = "husband";
    public static final String VALID_TAG_STUDIES = "friend";

    public static final String VALID_REVENUE_JUNE = "1.00";
    public static final String VALID_REVENUE_MAY = "2.00";
    public static final String VALID_INVENTORY_JUNE = "3.00";
    public static final String VALID_INVENTORY_MAY = "4.00";
    public static final String VALID_EXPENSE_JUNE = "5.00";
    public static final String VALID_EXPENSE_MAY = "6.00";
    public static final String VALID_YEAR_JUNE = "2017";
    public static final String VALID_YEAR_MAY = "2018";
    public static final String VALID_MONTH_JUNE = "9";
    public static final String VALID_MONTH_MAY = "5";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_ADD;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BIOLOGY;
    public static final String ISBN_DESC_AMY = " " + PREFIX_ISBN + VALID_ISBN_ADD;
    public static final String ISBN_DESC_BOB = " " + PREFIX_ISBN + VALID_ISBN_BIOLOGY;
    public static final String PRICE_DESC_AMY = " " + PREFIX_PRICE + VALID_PRICE_ADD;
    public static final String PRICE_DESC_BOB = " " + PREFIX_PRICE + VALID_PRICE_BIOLOGY;
    public static final String COST_DESC_AMY = " " + PREFIX_COST + VALID_COST_ADD;
    public static final String COST_DESC_BOB = " " + PREFIX_COST + VALID_COST_BIOLOGY;
    public static final String QUANTITY_DESC_AMY = " " + PREFIX_QUANTITY + VALID_QUANTITY_ADD;
    public static final String QUANTITY_DESC_BOB = " " + PREFIX_QUANTITY + VALID_QUANTITY_BIOLOGY;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_STUDIES;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_SCIENCE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_ISBN_DESC = " " + PREFIX_ISBN + "911a"; // 'a' not allowed in phones
    public static final String INVALID_PRICE_DESC = " " + PREFIX_PRICE + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_QUANTITY_DESC = " " + PREFIX_QUANTITY; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditBookDescriptor DESC_AMY;
    public static final EditCommand.EditBookDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditBookDescriptorBuilder()
                .withName(VALID_NAME_ADD)
                .withIsbn(VALID_ISBN_ADD)
                .withPrice(VALID_PRICE_ADD)
                .withCost(VALID_COST_ADD)
                .withQuantity(VALID_QUANTITY_ADD)
                .withTags(VALID_TAG_STUDIES).build();
        DESC_BOB = new EditBookDescriptorBuilder()
                .withName(VALID_NAME_BIOLOGY)
                .withIsbn(VALID_ISBN_BIOLOGY)
                .withPrice(VALID_PRICE_BIOLOGY)
                .withCost(VALID_COST_BIOLOGY)
                .withQuantity(VALID_QUANTITY_BIOLOGY)
                .withTags(VALID_TAG_SCIENCE, VALID_TAG_STUDIES).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the BookInventory and the filtered book list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        BookInventory expectedBookInventory = new BookInventory(actualModel.getBookInventory());
        List<Book> expectedFilteredList = new ArrayList<>(actualModel.getFilteredBookList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedBookInventory, actualModel.getBookInventory());
            assertEquals(expectedFilteredList, actualModel.getFilteredBookList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the book at the given {@code targetIndex} in the
     * {@code model}'s BookInventory.
     */
    public static void showBookAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredBookList().size());

        Book book = model.getFilteredBookList().get(targetIndex.getZeroBased());
        final String[] splitName = book.getName().fullName.split("\\s+");
        model.updateFilteredBookList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredBookList().size());
    }

    /**
     * Deletes the first book in {@code model}'s filtered list from {@code model}'s BookInventory.
     */
    public static void deleteFirstBook(Model model) {
        Book firstBook = model.getFilteredBookList().get(0);
        model.deleteBook(firstBook);
        model.commitBookInventory();
    }

}
