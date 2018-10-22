package seedu.address.logic.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.collections.ObservableList;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyEventManager;
import seedu.address.model.event.Event;
import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;
import seedu.address.storage.UserStorage;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;

public class SignupCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullUser_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SignupCommand(null);
    }

    @Test
    public void execute_successfulSignup() throws Exception {
        Username username = new Username("admin");
        Password password = new Password("root");
        User user = new User(username, password);

        JsonUserStorageStub jsonUserStorageStub = new JsonUserStorageStub();
        ModelStubAcceptUser modelStubAcceptUser = new ModelStubAcceptUser(user, jsonUserStorageStub);

        CommandResult commandResult = new SignupCommand(user).execute(modelStubAcceptUser, commandHistory);

        assertEquals(String.format(SignupCommand.MESSAGE_SUCCESS, username.toString()), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_failedSignup_userExists() throws  Exception {
        Username username = new Username("admin");
        Password password = new Password("root");
        User user = new User(username, password);

        JsonUserStorageStub jsonUserStorageStub = new JsonUserStorageStub();
        jsonUserStorageStub.createUser(username.toString(), password.toString());
        ModelStubAcceptUser modelStubAcceptUser = new ModelStubAcceptUser(user, jsonUserStorageStub);

        SignupCommand signupCommand = new SignupCommand(user);

        thrown.expect(CommandException.class);
        thrown.expectMessage(SignupCommand.MESSAGE_EXISTS);
        signupCommand.execute(modelStubAcceptUser, commandHistory);
    }

    /**
     * A default JsonUserStorage stub that contains mocks to all the methods.
     */
    private class JsonUserStorageStub implements UserStorage {
        private JsonObject jsonObject;

        JsonUserStorageStub() {
            jsonObject = new JsonObject();
        }

        @Override
        public void createUser(String username, String password) {
            jsonObject.addProperty(username, password);
        }

        @Override
        public JsonObject getUserAccounts() {
            return jsonObject;
        }
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void createUser(User user) {
            throw new AssertionError("This method should not be called!");
        }

        public boolean authenticate() {
            throw new AssertionError("This method should not be called!");
        }

        @Override
        public boolean getAdminStatus() {
            throw new AssertionError("This method should not be called!");
        }

        @Override
        public boolean userExists(User user) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void logUser(User user) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void clearUser() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyEventManager newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyEventManager getEventManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateEvent(Event target, Event editedEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoEventManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoEventManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoEventManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoEventManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitEventManager() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that simulates a signup.
     */
    private class ModelStubAcceptUser extends ModelStub {
        private JsonUserStorageStub jsonUserStorage;
        private User user;

        ModelStubAcceptUser(User user, JsonUserStorageStub jsonUserStorage) {
            requireNonNull(user);
            this.user = user;
            this.jsonUserStorage = jsonUserStorage;
        }

        @Override
        public boolean userExists(User user) {
            requireNonNull(user);
            boolean isPresent = false;
            String username = user.getUsername().toString();

            JsonObject userAccounts = jsonUserStorage.getUserAccounts();
            if (userAccounts.has(username)) {
                JsonElement password = userAccounts.get(username);
                isPresent = user.getPassword().toString().equals(password.getAsString());
            }

            return isPresent;
        }

        @Override
        public void createUser(User user) {
            jsonUserStorage.createUser(user.getUsername().toString(), user.getPassword().toString());
        }
    }
}
