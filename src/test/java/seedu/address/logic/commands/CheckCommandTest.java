//@@author kennethcsj
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
