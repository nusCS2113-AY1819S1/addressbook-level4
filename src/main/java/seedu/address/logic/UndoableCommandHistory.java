package seedu.address.logic;

import java.util.Stack;

/**
 * Stores the history of undoable commands executed.
 */
public class UndoableCommandHistory {
    private Stack<String> undoableCommandList;

    public UndoableCommandHistory() {
        undoableCommandList = new Stack<>();
    }

    /**
     * Appends {@code userInput} to the list of user input entered if the input is undoable TodoList command.
     */
    public void addTodoList() {
        undoableCommandList.push("TDL");
    }

    /**
     * Appends {@code userInput} to the list of user input entered if the input is undoable Expenditure command..
     */
    public void addExpenditureTracker() {
        undoableCommandList.push("ET");
    }

    public String getCommand() {
        if (undoableCommandList.empty()) {
            return "EMPTY";
        }
        else {
            return undoableCommandList.pop();
        }
    }
}
