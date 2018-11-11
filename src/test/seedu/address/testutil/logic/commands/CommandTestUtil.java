package seedu.address.testutil.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.InventoryList;
import seedu.address.model.Model;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.NameContainsKeywordsPredicate;
import seedu.address.model.user.manager.ManagerModel;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String VALID_DRINK_NAME_COCA_COLA = "Coca Cola";
    public static final String VALID_DRINK_RETAIL_PRICE = "25.23";
    public static final String VALID_DRINK_COST_PRICE = "4.00";
    public static final String VALID_DRINK_QUANTITY = "300";
    public static final String VALID_DRINK_TAG_SOFTDRINK = "softDrink";
    public static final String VALID_DRINK_TAG_TEA = "tea";


    public static final String VALID_TRANSACTION_AMOUNT_MONEY = "10.00";
    public static final String VALID_TRANSACTION_QUANTITY = "10";

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
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, ManagerModel actualModel,
                                            CommandHistory actualCommandHistory,
                                            String expectedMessage, ManagerModel expectedModel) {
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
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        InventoryList expectedInventoryList = new InventoryList(actualModel.getInventoryList());
        List<Drink> expectedFilteredList = new ArrayList<>(actualModel.getFilteredDrinkList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedInventoryList, actualModel.getInventoryList());
            assertEquals(expectedFilteredList, actualModel.getFilteredDrinkList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the drink at the given {@code targetIndex} in the
     * {@code model}'s inventory list.
     */
    public static void showDrinkAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredDrinkList().size());

        Drink drink = model.getFilteredDrinkList().get(targetIndex.getZeroBased());
        final String[] splitName = drink.getName().name.split("\\s+");
        model.updateFilteredDrinkList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredDrinkList().size());
    }

}
