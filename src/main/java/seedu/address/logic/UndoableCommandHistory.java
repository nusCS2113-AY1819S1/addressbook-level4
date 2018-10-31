package seedu.address.logic;

import java.util.Stack;

public class UndoableCommandHistory {
    private Stack<String> undoableCommandList;

    public UndoableCommandHistory() {
        undoableCommandList = new Stack<>();
    }

    public void addTDL() {
        undoableCommandList.push("TDL");
    }

    public void addET() {
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
