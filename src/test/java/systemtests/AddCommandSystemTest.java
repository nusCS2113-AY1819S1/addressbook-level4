//package systemtests;
//
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.commands.CommandTestUtil.COST_DESC_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_ISBN_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.ISBN_DESC_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.ISBN_DESC_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
//import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_BIOLOGY;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BIOLOGY;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BIOLOGY;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BIOLOGY;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
//import static seedu.address.testutil.TypicalBooks.ART;
//import static seedu.address.testutil.TypicalBooks.ADD;
//import static seedu.address.testutil.TypicalBooks.BOB;
//import static seedu.address.testutil.TypicalBooks.CHEMISTRY;
//import static seedu.address.testutil.TypicalBooks.HISTORY;
//import static seedu.address.testutil.TypicalBooks.IT;
//import static seedu.address.testutil.TypicalBooks.KEYWORD_MATCHING_BIOLOGY;
//
//import org.junit.Test;
//
//import seedu.address.commons.core.Messages;
//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.AddCommand;
//import seedu.address.logic.commands.RedoCommand;
//import seedu.address.logic.commands.UndoCommand;
//import seedu.address.model.Model;
//import seedu.address.model.book.Book;
//import seedu.address.model.book.Isbn;
//import seedu.address.model.book.Name;
//import seedu.address.model.book.Price;
//import seedu.address.model.book.Quantity;
//import seedu.address.model.tag.Tag;
//import seedu.address.testutil.BookBuilder;
//import seedu.address.testutil.BookUtil;
//
//public class AddCommandSystemTest extends BookInventorySystemTest {
//    @Test
//    public void add() {
//        Model model = getModel();
//
//        /* ------------------------ Perform add operations on the shown unfiltered list
// ----------------------------- */
//
//        /* Case: add a book without tags to a non-empty BookInventory, command with leading spaces and trailing spaces
//         * -> added
//         */
//        Book toAdd = ADD;
//        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + ISBN_DESC_AMY + " "
//                + PRICE_DESC_AMY + "   " + COST_DESC_AMY + " " + QUANTITY_DESC_AMY + "   " + TAG_DESC_FRIEND + " ";
//        assertCommandSuccess(command, toAdd);
//        /* Case: undo adding Amy to the list -> Amy deleted */
//        command = UndoCommand.COMMAND_WORD;
//        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
//        assertCommandSuccess(command, model, expectedResultMessage);
//
//        /* Case: redo adding Amy to the list -> Amy added again */
//        command = RedoCommand.COMMAND_WORD;
//        model.addBook(toAdd);
//        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
//        assertCommandSuccess(command, model, expectedResultMessage);
//
//        /* Case: add a book with all fields same as another book in the BookInventory except name -> added */
//        toAdd = new BookBuilder(ADD).withName(VALID_NAME_BIOLOGY).build();
//        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + ISBN_DESC_AMY
//                + PRICE_DESC_AMY + COST_DESC_AMY + QUANTITY_DESC_AMY
//                + TAG_DESC_FRIEND;
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: add a book with all fields same as another book in the BookInventory except phone and email
//         * -> added
//         */
//        toAdd = new BookBuilder(ADD).withIsbn(VALID_ISBN_BIOLOGY).withPrice(VALID_PRICE_BIOLOGY).build();
//        command = BookUtil.getAddCommand(toAdd);
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: add to empty BookInventory -> added */
//        deleteAllPersons();
//        assertCommandSuccess(ART);
//
//        /* Case: add a book with tags, command with parameters in random order -> added */
//        toAdd = BOB;
//        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + ISBN_DESC_BOB + QUANTITY_DESC_BOB + NAME_DESC_BOB
//                + TAG_DESC_HUSBAND + PRICE_DESC_BOB;
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: add a book, missing tags -> added */
//        assertCommandSuccess(HISTORY);
//
//        /* -------------------------- Perform add operation on the shown filtered list
// ------------------------------ */
//
//        /* Case: filters the book list before adding -> added */
//        showPersonsWithName(KEYWORD_MATCHING_BIOLOGY);
//        assertCommandSuccess(IT);
//
//        /* ------------------------ Perform add operation while a book card is selected --------------------------- */
//
//        /* Case: selects first card in the book list, add a book -> added, card selection remains unchanged */
//        selectPerson(Index.fromOneBased(1));
//        assertCommandSuccess(CHEMISTRY);
//
//        /* ----------------------------------- Perform invalid add operations
// --------------------------------------- */
//
//        /* Case: add a duplicate book -> rejected */
//        command = BookUtil.getAddCommand(HISTORY);
//        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOK);
//
//        /* Case: add a duplicate book except with different phone -> rejected */
//        toAdd = new BookBuilder(HISTORY).withIsbn(VALID_ISBN_BIOLOGY).build();
//        command = BookUtil.getAddCommand(toAdd);
//        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOK);
//
//        /* Case: add a duplicate book except with different email -> rejected */
//        toAdd = new BookBuilder(HISTORY).withPrice(VALID_PRICE_BIOLOGY).build();
//        command = BookUtil.getAddCommand(toAdd);
//        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOK);
//
//        /* Case: add a duplicate book except with different address -> rejected */
//        toAdd = new BookBuilder(HISTORY).withQuantity(VALID_QUANTITY_BIOLOGY).build();
//        command = BookUtil.getAddCommand(toAdd);
//        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOK);
//
//        /* Case: add a duplicate book except with different tags -> rejected */
//        command = BookUtil.getAddCommand(HISTORY) + " " + PREFIX_TAG.getPrefix() + "friends";
//        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOK);
//
//        /* Case: missing name -> rejected */
//        command = AddCommand.COMMAND_WORD + ISBN_DESC_AMY + PRICE_DESC_AMY + QUANTITY_DESC_AMY;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//
//        /* Case: missing phone -> rejected */
//        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PRICE_DESC_AMY + QUANTITY_DESC_AMY;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//
//        /* Case: missing email -> rejected */
//        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + ISBN_DESC_AMY + QUANTITY_DESC_AMY;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//
//        /* Case: missing address -> rejected */
//        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + ISBN_DESC_AMY + PRICE_DESC_AMY;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
//
//        /* Case: invalid keyword -> rejected */
//        command = "adds " + BookUtil.getBookDetails(toAdd);
//        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);
//
//        /* Case: invalid name -> rejected */
//        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + ISBN_DESC_AMY + PRICE_DESC_AMY + QUANTITY_DESC_AMY;
//        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);
//
//        /* Case: invalid phone -> rejected */
//        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_ISBN_DESC + PRICE_DESC_AMY + QUANTITY_DESC_AMY;
//        assertCommandFailure(command, Isbn.MESSAGE_ISBN_CONSTRAINTS);
//
//        /* Case: invalid email -> rejected */
//        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + ISBN_DESC_AMY + INVALID_PRICE_DESC + QUANTITY_DESC_AMY;
//        assertCommandFailure(command, Price.MESSAGE_PRICE_CONSTRAINTS);
//
//        /* Case: invalid address -> rejected */
//        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + ISBN_DESC_AMY + PRICE_DESC_AMY + INVALID_QUANTITY_DESC;
//        assertCommandFailure(command, Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
//
//        /* Case: invalid tag -> rejected */
//        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + ISBN_DESC_AMY + PRICE_DESC_AMY + QUANTITY_DESC_AMY
//                + INVALID_TAG_DESC;
//        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
//    }
//
//    /**
//     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
//     * 1. Command box displays an empty string.<br>
//     * 2. Command box has the default style class.<br>
//     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
//     * {@code toAdd}.<br>
//     * 4. {@code InventoryStorage} and {@code BookListPanel} equal to the corresponding components in
//     * the current model added with {@code toAdd}.<br>
//     * 5. Browser url and selected card remain unchanged.<br>
//     * 6. Status bar's sync status changes.<br>
//     * Verifications 1, 3 and 4 are performed by
//     * {@code BookInventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * @see BookInventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     */
//    private void assertCommandSuccess(Book toAdd) {
//        assertCommandSuccess(BookUtil.getAddCommand(toAdd), toAdd);
//    }
//
//    /**
//     * Performs the same verification as {@code assertCommandSuccess(Book)}. Executes {@code command}
//     * instead.
//     * @see AddCommandSystemTest#assertCommandSuccess(Book)
//     */
//    private void assertCommandSuccess(String command, Book toAdd) {
//        Model expectedModel = getModel();
//        expectedModel.addBook(toAdd);
//        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);
//
//        assertCommandSuccess(command, expectedModel, expectedResultMessage);
//    }
//
//    /**
//     * Performs the same verification as {@code assertCommandSuccess(String, Book)} except asserts that
//     * the,<br>
//     * 1. Result display box displays {@code expectedResultMessage}.<br>
//     * 2. {@code InventoryStorage} and {@code BookListPanel} equal to the corresponding components in
//     * {@code expectedModel}.<br>
//     * @see AddCommandSystemTest#assertCommandSuccess(String, Book)
//     */
//    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
//        executeCommand(command);
//        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
//        assertSelectedCardUnchanged();
//        assertCommandBoxShowsDefaultStyle();
//        assertStatusBarUnchangedExceptSyncStatus();
//    }
//
//    /**
//     * Executes {@code command} and asserts that the,<br>
//     * 1. Command box displays {@code command}.<br>
//     * 2. Command box has the error style class.<br>
//     * 3. Result display box displays {@code expectedResultMessage}.<br>
//     * 4. {@code InventoryStorage} and {@code BookListPanel} remain unchanged.<br>
//     * 5. Browser url, selected card and status bar remain unchanged.<br>
//     * Verifications 1, 3 and 4 are performed by
//     * {@code BookInventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * @see BookInventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     */
//    private void assertCommandFailure(String command, String expectedResultMessage) {
//        Model expectedModel = getModel();
//
//        executeCommand(command);
//        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
//        assertSelectedCardUnchanged();
//        assertCommandBoxShowsErrorStyle();
//        assertStatusBarUnchanged();
//    }
//}
