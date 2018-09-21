package seedu.address.logic.trie;

import java.lang.reflect.Array;
import java.util.ArrayList;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.AddressBook;

/**
 * A bi-directional Tree structure that stems from the root node
 * Each node is a TrieNode object that contains the node information
 */

public class Trie {

    /**
     * Testing variables
     */
    private ArrayList<String> predictionsList;

    /**
     * Class constants
     */
    private final char ROOT_CHAR = '.';

    /**
     * Class variables
     */
    private TrieNode root;
    private ArrayList<String> baseList;

    /**
     * Default constructor
     */
    public Trie(ArrayList<String> inputList) {
        root = new TrieNode(ROOT_CHAR);
        baseList = new ArrayList<>(inputList);
        init();
    }

    /**
     * Initialise the Trie instance with the items in baseList
     */
    private void init() {

        for (int i = 0; i < baseList.size(); i++) {
            this.insertToGraph(baseList.get(i));
        }
    }

    /**
     * Inserts the input string value to the class instance
     * @param value
     */
    public void insert(String value) {
        // Check if this value exists
        if (!baseList.contains(value)) {
            insertToGraph(value);
            baseList.add(value);
        }
    }

    /**
     * Insert the given string value to the Trie graph.
     * @param keyString the string value to be inserted
     */
    private void insertToGraph(String keyString) {
        // A TrieNode as pointer to traverse through the tree
        TrieNode pointer = root;

        // Run through all characters in the given key string
        for (int i = 0; i < keyString.length(); i++) {
            char ch = keyString.charAt(i);
            ArrayList<TrieNode> children = pointer.getChildren();

            if (children.contains(new TrieNode(ch))) {
                // Set the pointer to that node
                pointer = children.get(children.indexOf(new TrieNode(ch)));
            }
            else {
                // Create a new node
                TrieNode parent = pointer;
                pointer = pointer.appendChild(new TrieNode(ch));
                pointer.setParent(parent);
            }
        }
        // Mark end of word node
        pointer.setEndNode(true);
    }

    /**
     * Remove the input string value from the class instance
     * @param value the value to remove
     */
    public void remove(String value) {
        // Check if this value exists
        if (baseList.contains(value)) {
            removeFromGraph(value);
            baseList.remove(value);
        }
    }
        /**
     * Remove the input string value from the actual graph implementation
     * Traverse from the root to the last character (End node) of the string
     * Prerequisites: the value must exist in the Trie
     *
     * If the remove of this node does not affect any chidren node
     * (ie the node has no children), remove the whole word
     *
     * @param value the value to remove
     */
    private void removeFromGraph(String value) {
        TrieNode pointer;

        pointer = traverseToEndNode(value);

        // Check for the conditions for removal or setting of end node
        if (pointer.getChildrenSize() == 0) {
            removeWordFromGraph(pointer);
        }
        else {
            pointer.setEndNode(false);
        }
    }

    /**
     * Traverse the Trie from the start character to the end character of the given string value
     * @param value the given value
     * @return the TrieNode referencing the end node
     */
    private TrieNode traverseToEndNode(String value) {
        TrieNode pointer = root;

        // Run through all characters in the string and reaches the end node
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            ArrayList<TrieNode> children = pointer.getChildren();
            pointer = children.get(children.indexOf(new TrieNode(ch)));
        }

        return pointer;
    }

    /**
     * Removes a word from the graph given the end node of that word
     * Removes every parent level node until a node that has more than one child
     * or it is an end node
     * @param pointer the first node in the graph to be removed
     */
    private void removeWordFromGraph(TrieNode pointer) {
        // Set this node as non-end node first
        pointer.setEndNode(false);

        // Traverse upwards the trie
        while (!pointer.isEndNode() && pointer.getChildrenSize() == 0) {
            TrieNode parent = pointer.getParent();
            parent.removeChild(pointer);
            pointer = parent;
        }
    }

    /**
     * Testing codes section
     */
    public ArrayList<String> getPredictList(String prefix) {
        predictionsList = new ArrayList<>();
        StringBuilder charStack = new StringBuilder();

        TrieNode startNode = skipToStartNode(root, prefix);
        if (startNode.equals(root)) {
            return predictionsList;
        }

        if (startNode.getChildrenSize() == 0) {
            charStack.append(' ');
            predictionsList.add(charStack.toString());
        }

        // If the startNode has only ONE child, build the charStack to the first
        // node that has more than one child or the first mismatch character
        if (startNode.getChildrenSize() == 1) {
            charStack = buildSingleStack(startNode);
            predictionsList.add(charStack.toString());
            return predictionsList;
        }

        for (int i = 0; i < startNode.getChildren().size(); i++) {
            explore(charStack, startNode.getChildren().get(i));
        }

        return predictionsList;
    }

    private StringBuilder buildSingleStack(TrieNode startNode) {
        StringBuilder charStack = new StringBuilder();
        while(startNode.getChildrenSize() == 1) {
            charStack.append(startNode.getFirstChild().getValue());
            startNode = startNode.getFirstChild();
        }

        if (startNode.getChildrenSize() == 0) {
            charStack.append(' ');
        }

        return charStack;
    }

    private TrieNode skipToStartNode(TrieNode begin, String prefix) {
        TrieNode current = begin;
        boolean hasChar = false;

        for (int i = 0; i < prefix.length(); i++) {
            ArrayList<TrieNode> currList = current.getChildren();
            hasChar = false;

            for (int j = 0; j < currList.size(); j++) {
                if (currList.get(j).getValue() == prefix.charAt(i)) {
                    current = current.getChildren().get(j);
                    hasChar = true;
                    break;
                }
            }

            if (!hasChar) {
                current = root;
                break;
            }
        }

        if (current.getChildrenSize() == 0) {

        }

        return current;
    }

    private void explore(StringBuilder charStack, TrieNode ptr) {
        // Push the character of current node to stack
        if (ptr.getValue() != '.') {
            charStack.append(ptr.getValue());
        }

        // We have hit the end of a word but the branch continues or there are more branch
        if (ptr.isEndNode()) {
            if (ptr.getChildrenSize() == 0) {
                charStack.append(' ');
            }
            predictionsList.add(charStack.toString());
        }

        // Explore all other neighbours
        for (int i = 0; i < ptr.getChildren().size(); i++) {
            TrieNode neighbour = ptr.getChildren().get(i);
            explore(charStack, neighbour);
        }

        if (charStack.charAt(charStack.length()-1) == ' ') {
            charStack.deleteCharAt(charStack.length()-1);
        }

        // Pop the last character out of stack
        if (charStack.length() > 0) {
            charStack.deleteCharAt(charStack.length()-1);
        }
    }

}
