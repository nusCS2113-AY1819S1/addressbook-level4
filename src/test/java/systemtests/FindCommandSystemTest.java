//package systemtests;
//
//import static org.junit.Assert.assertFalse;
//import static seedu.address.commons.core.Messages.MESSAGE_BOOKS_LISTED_OVERVIEW;
//import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
//import static seedu.address.testutil.TypicalBooks.BIOLOGY;
//import static seedu.address.testutil.TypicalBooks.CHEMISTRY;
//import static seedu.address.testutil.TypicalBooks.DARWIN;
//import static seedu.address.testutil.TypicalBooks.KEYWORD_MATCHING_BIOLOGY;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//
//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.DeleteCommand;
//import seedu.address.logic.commands.FindCommand;
//import seedu.address.logic.commands.RedoCommand;
//import seedu.address.logic.commands.UndoCommand;
//import seedu.address.model.Model;
//import seedu.address.model.tag.Tag;
//
//public class FindCommandSystemTest extends BookInventorySystemTest {
//
//    @Test
//    public void find() {
//        /* Case: find multiple persons in BookInventory, command with leading spaces and trailing spaces
//         * -> 2 persons found
//         */
//        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_BIOLOGY + "   ";
//        Model expectedModel = getModel();
//        ModelHelper.setFilteredList(expectedModel, BIOLOGY); // Find for biology
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: repeat previous find command where book list is displaying the persons we are finding
//         * -> 2 persons found
//         */
//        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_BIOLOGY;
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find book where book list is not displaying the book we are finding -> 1 book found */
//        command = FindCommand.COMMAND_WORD + " Carl";
//        ModelHelper.setFilteredList(expectedModel, CHEMISTRY);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find multiple persons in BookInventory, 2 keywords -> 2 persons found */
//        command = FindCommand.COMMAND_WORD + " Benson Daniel";
//        ModelHelper.setFilteredList(expectedModel, BIOLOGY, DARWIN);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find multiple persons in BookInventory, 2 keywords in reversed order -> 2 persons found */
//        command = FindCommand.COMMAND_WORD + " Daniel Benson";
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find multiple persons in BookInventory, 2 keywords with 1 repeat -> 2 persons found */
//        command = FindCommand.COMMAND_WORD + " Daniel Benson Daniel";
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find multiple persons in BookInventory, 2 matching keywords and 1 non-matching keyword
//         * -> 2 persons found
//         */
//        command = FindCommand.COMMAND_WORD + " Daniel Benson NonMatchingKeyWord";
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: undo previous find command -> rejected */
//        command = UndoCommand.COMMAND_WORD;
//        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
//        assertCommandFailure(command, expectedResultMessage);
//
//        /* Case: redo previous find command -> rejected */
//        command = RedoCommand.COMMAND_WORD;
//        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
//        assertCommandFailure(command, expectedResultMessage);
//
//        /* Case: find same persons in BookInventory after deleting 1 of them -> 1 book found */
//        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
//        assertFalse(getModel().getBookInventory().getBookList().contains(BIOLOGY));
//        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_BIOLOGY;
//        expectedModel = getModel();
//        ModelHelper.setFilteredList(expectedModel, DARWIN);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find book in BookInventory, keyword is same as name but of different case -> 1 book found */
//        command = FindCommand.COMMAND_WORD + " MeIeR";
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find book in BookInventory, keyword is substring of name -> 0 persons found */
//        command = FindCommand.COMMAND_WORD + " Mei";
//        ModelHelper.setFilteredList(expectedModel);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find book in BookInventory, name is substring of keyword -> 0 persons found */
//        command = FindCommand.COMMAND_WORD + " Meiers";
//        ModelHelper.setFilteredList(expectedModel);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find book not in BookInventory -> 0 persons found */
//        command = FindCommand.COMMAND_WORD + " Mark";
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find phone number of book in BookInventory -> 0 persons found */
//        command = FindCommand.COMMAND_WORD + " " + DARWIN.getIsbn().value;
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find address of book in BookInventory -> 0 persons found */
//        command = FindCommand.COMMAND_WORD + " " + DARWIN.getQuantity().getValue();
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find email of book in BookInventory -> 0 persons found */
//        command = FindCommand.COMMAND_WORD + " " + DARWIN.getPrice().value;
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find tags of book in BookInventory -> 0 persons found */
//        List<Tag> tags = new ArrayList<>(DARWIN.getTags());
//        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find while a book is selected -> selected card deselected */
//        showAllPersons();
//        selectPerson(Index.fromOneBased(1));
//        assertFalse(getBookListPanel().getHandleToSelectedCard().getName().equals(DARWIN.getName().fullName));
//        command = FindCommand.COMMAND_WORD + " Daniel";
//        ModelHelper.setFilteredList(expectedModel, DARWIN);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardDeselected();
//
//        /* Case: find book in empty BookInventory -> 0 persons found */
//        deleteAllPersons();
//        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_BIOLOGY;
//        expectedModel = getModel();
//        ModelHelper.setFilteredList(expectedModel, DARWIN);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: mixed case command word -> rejected */
//        command = "FiNd Meier";
//        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
//    }
//
//    /**
//     * Executes {@code command} and verifies that the command box displays an empty string, the result display
//     * box displays {@code Messages#MESSAGE_BOOKS_LISTED_OVERVIEW} with the number of people in the filtered list,
//     * and the model related components equal to {@code expectedModel}.
//     * These verifications are done by
//     * {@code BookInventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
//     * selected card updated accordingly, depending on {@code cardStatus}.
//     * @see BookInventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     */
//    private void assertCommandSuccess(String command, Model expectedModel) {
//        String expectedResultMessage = String.format(
//                MESSAGE_BOOKS_LISTED_OVERVIEW, expectedModel.getFilteredBookList().size());
//        System.out.println(expectedResultMessage);
//        executeCommand(command);
//        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
//        assertCommandBoxShowsDefaultStyle();
//        assertStatusBarUnchanged();
//    }
//
//    /**
//     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
//     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
//     * These verifications are done by
//     * {@code BookInventorySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
//     * error style.
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
