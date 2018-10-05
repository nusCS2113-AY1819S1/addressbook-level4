package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import seedu.address.ui.MainWindow;


/**
 * Stores the history of commands executed.
 */
public class CommandHistory {
    private LinkedList<String> userInputHistory;

    public CommandHistory() {
        userInputHistory = new LinkedList<>();
    }

    public CommandHistory(CommandHistory commandHistory) {
        userInputHistory = new LinkedList<>(commandHistory.userInputHistory);
    }

    /**
     * Appends {@code userInput} to the list of user input entered.
     */
    public void add(String userInput) {
        requireNonNull(userInput);
        if (MainWindow.getIsSensitiveInformation()) {
            userInputHistory.add("Sensitive information!");
            MainWindow.setIsSensitiveInformation(false);
        } else {
            userInputHistory.add(userInput);
        }
    }

    /**
     * Returns a defensive copy of {@code userInputHistory}.
     */
    public List<String> getHistory() {
        return new LinkedList<>(userInputHistory);
    }

    public String getLastExecutedCommand() {
        try {
            return userInputHistory.getLast();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof CommandHistory)) {
            return false;
        }

        // state check
        CommandHistory other = (CommandHistory) obj;
        return userInputHistory.equals(other.userInputHistory);
    }

    @Override
    public int hashCode() {
        return userInputHistory.hashCode();
    }
}
