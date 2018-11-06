package seedu.address.logic.commands;
//@@author liu-tianhang
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
import seedu.address.commons.core.LoginInfo;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.manager.AddDrinkCommand;
import seedu.address.model.InventoryList;
import seedu.address.model.ReadOnlyInventoryList;
import seedu.address.model.drink.Drink;
import seedu.address.model.user.AuthenticationLevel;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;
import seedu.address.model.user.manager.ManagerModel;
import seedu.address.testutil.drinks.DrinkBuilder;

public class AddDrinkCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddDrinkCommand (null);
    }

    @Test
    public void execute_drinkAcceptedByManagerModel_addSuccessful() throws Exception {
        ManagerModelStubAcceptingPersonAdded modelStub = new ManagerModelStubAcceptingPersonAdded ();
        Drink validDrink = new DrinkBuilder ().build ();

        CommandResult commandResult = new AddDrinkCommand(validDrink).execute (modelStub, commandHistory);

        assertEquals(String.format(AddDrinkCommand.MESSAGE_SUCCESS, validDrink.getName ().toString (),
                                    validDrink.getRetailPrice ().toString ()), commandResult.feedbackToUser);
        assertEquals (Arrays.asList (validDrink), modelStub.drinksAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }
    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Drink validDrink = new DrinkBuilder ().build ();
        AddDrinkCommand addDrinkCommand = new AddDrinkCommand (validDrink);
        ManagerModelStub modelStub = new ManagerModelStubWithDrink(validDrink);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddDrinkCommand.MESSAGE_DUPLICATE_DRINK);
        addDrinkCommand.execute(modelStub, commandHistory);
    }
    @Test
    public void equals() {
        Drink coke = new DrinkBuilder().withName ("coca cola").build();
        Drink pepsi = new DrinkBuilder().withName ("pepsi").build();
        AddDrinkCommand addCokeCommand = new AddDrinkCommand(coke);
        AddDrinkCommand addPepsiCommand = new AddDrinkCommand(pepsi);

        // same object -> returns true
        assertTrue(addCokeCommand.equals(addCokeCommand));

        // same values -> returns true
        AddDrinkCommand addCokeCommandCopy = new AddDrinkCommand(coke);
        assertTrue(addCokeCommand.equals(addCokeCommandCopy));

        // different types -> returns false
        assertFalse(addCokeCommand.equals(1));

        // null -> returns false
        assertFalse(addCokeCommand.equals(null));

        // different person -> returns false
        assertFalse(addCokeCommand.equals(addPepsiCommand));
    }

    private class ManagerModelStub implements ManagerModel {

        @Override
        public void createNewAccount (UserName userName, Password password, AuthenticationLevel authenticationLevel) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAccount (UserName userName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData (ReadOnlyInventoryList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyInventoryList getInventoryList () {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDrink (Drink drink) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDrink (Drink target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDrink (Drink drink) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Drink> getFilteredDrinkList () {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDrinkList (Predicate<Drink> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void changePassword (UserName userName, Password newHashedPassword) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public LoginInfo getLoginInfo (UserName userName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isUserNameExist (UserName userName) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A model stub that contains a single drink
     */
    private class ManagerModelStubWithDrink extends ManagerModelStub {
        private final Drink drink;

        ManagerModelStubWithDrink(Drink drink) {
            requireNonNull(drink);
            this.drink = drink;
        }
        @Override
        public boolean hasDrink (Drink drink) {
            requireNonNull (drink);
            return this.drink.isSameDrink (drink);
        }
    }
    /**
     * A model stub that always accept the drink being added.
     */
    public class ManagerModelStubAcceptingPersonAdded extends ManagerModelStub {
        final ArrayList<Drink> drinksAdded = new ArrayList<> ();

        @Override
        public boolean hasDrink (Drink drink) {
            requireNonNull (drink);
            return drinksAdded.stream ().anyMatch (drink:: isSameDrink);
        }

        @Override
        public void addDrink (Drink drink) {
            requireNonNull (drink);
            drinksAdded.add (drink);
        }

        @Override
        public ReadOnlyInventoryList getInventoryList () {
            return new InventoryList ();
        }
    }
}
