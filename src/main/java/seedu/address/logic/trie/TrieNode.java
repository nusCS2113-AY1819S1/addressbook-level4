package seedu.address.logic.trie;

import java.util.ArrayList;

public class TrieNode {

    private boolean isEndNode;
    private char value;
    private TrieNode parent;
    private ArrayList<TrieNode> children;

    /**
     * Default constructor with no parameters
     * @param value
     */
    public TrieNode(char value) {
        // Initialise all instance variables
        isEndNode = false;
        parent = null;
        children = new ArrayList<>();
        this.value = value;
    }

    public TrieNode getParent() {
        return this.parent;
    }

    public void setParent(TrieNode parent) {
        this.parent = parent;
    }

    public char getValue() {
        return this.value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public ArrayList<TrieNode> getChildren() {
        return children;
    }

    public TrieNode appendChild(TrieNode child) {
        children.add(child);
        children.get(children.size()-1).setParent(this);
        return children.get(children.size()-1);
    }

    public boolean isEndNode() {
        return isEndNode;
    }

    public void setEndNode(boolean value) {
        isEndNode = value;
    }

}
