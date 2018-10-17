package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyStockList;
import seedu.address.model.StockList;
import seedu.address.model.item.Item;
import seedu.address.testutil.ItemBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_itemAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingItemAdded modelStub = new ModelStubAcceptingItemAdded();
        Item validItem = new ItemBuilder().build();

        CommandResult commandResult = new AddCommand(validItem).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validItem), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validItem), modelStub.itemsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateItem_throwsCommandException() throws Exception {
        Item validItem = new ItemBuilder().build();
        AddCommand addCommand = new AddCommand(validItem);
        ModelStub modelStub = new ModelStubWithItem(validItem);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_ITEM);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Item arduino = new ItemBuilder().withName("Arduino").build();
        Item lidar = new ItemBuilder().withName("Lidar").build();
        AddCommand addArduinoCommand = new AddCommand(arduino);
        AddCommand addLidarCommand = new AddCommand(lidar);

        // same object -> returns true
        assertTrue(addArduinoCommand.equals(addArduinoCommand));

        // same values -> returns true
        AddCommand addArduinoCommandCopy = new AddCommand(arduino);
        assertTrue(addArduinoCommand.equals(addArduinoCommandCopy));

        // different types -> returns false
        assertFalse(addArduinoCommand.equals(1));

        // null -> returns false
        assertFalse(addArduinoCommand.equals(null));

        // different item -> returns false
        assertFalse(addArduinoCommand.equals(addLidarCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addItem(Item item) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyStockList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyStockList getStockList() {
            throw new AssertionError("This method should not be called.");
        }

        //@@author kelvintankaiboon
        @Override
        public void saveStockList(String fileName) {
            throw new AssertionError("This method should not be called.");
        }
        //@@author

        @Override
        public boolean hasItem(Item item) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteItem(Item target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateItem(Item target, Item editedItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Item> getFilteredItemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredItemList(Predicate<Item> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoStockList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoStockList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoStockList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoStockList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitStockList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single item.
     */
    private class ModelStubWithItem extends ModelStub {
        private final Item item;

        ModelStubWithItem(Item item) {
            requireNonNull(item);
            this.item = item;
        }

        @Override
        public boolean hasItem(Item item) {
            requireNonNull(item);
            return this.item.isSameItem(item);
        }
    }

    /**
     * A Model stub that always accept the item being added.
     */
    private class ModelStubAcceptingItemAdded extends ModelStub {
        final ArrayList<Item> itemsAdded = new ArrayList<>();

        @Override
        public boolean hasItem(Item item) {
            requireNonNull(item);
            return itemsAdded.stream().anyMatch(item::isSameItem);
        }

        @Override
        public void addItem(Item item) {
            requireNonNull(item);
            itemsAdded.add(item);
        }

        @Override
        public void commitStockList() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyStockList getStockList() {
            return new StockList();
        }
    }

}
