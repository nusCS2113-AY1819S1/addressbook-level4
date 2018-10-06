package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_ITEMS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalItems.ARDUINO;
import static seedu.address.testutil.TypicalItems.KEYWORD_MATCHING_AR;
import static seedu.address.testutil.TypicalItems.MOTOR;
import static seedu.address.testutil.TypicalItems.RPLIDAR;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class FindCommandSystemTest extends StockListSystemTest {

    @Test
    public void find() {
        /* Case: find multiple items in address book, command with leading spaces and trailing spaces
         * -> 2 items found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_AR + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ARDUINO, RPLIDAR); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where item list is displaying the items we are finding
         * -> 2 items found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_AR;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find item where item list is not displaying the item we are finding -> 1 item found */
        command = FindCommand.COMMAND_WORD + " motor";
        ModelHelper.setFilteredList(expectedModel, MOTOR);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple items in address book, 2 keywords -> 2 items found */
        command = FindCommand.COMMAND_WORD + " Arduino Rplidar";
        ModelHelper.setFilteredList(expectedModel, ARDUINO, RPLIDAR);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple items in address book, 2 keywords in reversed order -> 2 items found */
        command = FindCommand.COMMAND_WORD + " Rplidar Arduino";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple items in address book, 2 keywords with 1 repeat -> 2 items found */
        command = FindCommand.COMMAND_WORD + " Rplidar Arduino Rplidar";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple items in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 items found
         */
        command = FindCommand.COMMAND_WORD + " Arduino Rplidar NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same items in address book after deleting 1 of them -> 1 item found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getStockList().getItemList().contains(RPLIDAR));
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_AR;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ARDUINO);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find item in address book, keyword is same as name but of different case -> 1 item found */
        command = FindCommand.COMMAND_WORD + " aR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find item in address book, keyword is substring of name -> 1 items found */
        command = FindCommand.COMMAND_WORD + " ardui";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ARDUINO);
        assertCommandSuccess(command, expectedModel);

        /* Case: find item in address book, name is substring of keyword -> 0 items found */
        command = FindCommand.COMMAND_WORD + " Arduinooooo";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find item not in address book -> 0 items found */
        command = FindCommand.COMMAND_WORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find minQuantity of item in address book -> 0 items found */
        command = FindCommand.COMMAND_WORD + " " + ARDUINO.getMinQuantity().toString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find quantity of item in address book -> 0 items found */
        command = FindCommand.COMMAND_WORD + " " + ARDUINO.getQuantity().toString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of item in address book -> 0 items found */
        List<Tag> tags = new ArrayList<>(ARDUINO.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a item is selected -> selected card deselected */
        showAllItems();
        selectItem(Index.fromOneBased(1));
        assertFalse(getItemListPanel().getHandleToSelectedCard().getName().equals(ARDUINO.getName().fullName));
        command = FindCommand.COMMAND_WORD + " Arduino";
        ModelHelper.setFilteredList(expectedModel, ARDUINO);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find item in empty address book -> 0 items found */
        deleteAllItems();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_AR;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ARDUINO);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_ITEMS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code StockListSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see StockListSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_ITEMS_LISTED_OVERVIEW, expectedModel.getFilteredItemList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code StockListSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see StockListSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
