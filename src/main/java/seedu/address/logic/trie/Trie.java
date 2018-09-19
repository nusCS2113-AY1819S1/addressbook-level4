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
     * Command String constants
     */
    // TODO : Import these to all the command classes and trie class
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_CLEAR = "clear";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_EDIT = "edit";
    private static final String COMMAND_EXIT = "exit";
    private static final String COMMAND_FIND = "find";
    private static final String COMMAND_HELP = "help";
    private static final String COMMAND_HISTORY = "history";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_REDO = "redo";
    private static final String COMMAND_SELECT = "select";
    private static final String COMMAND_UNDO = "undo";

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
    public Trie(AddressBook inputAddressBook) {
        root = new TrieNode(ROOT_CHAR);
        baseList = new ArrayList<>();
        this.init(inputAddressBook);
    }

    public Trie() {
        root = new TrieNode(ROOT_CHAR);
        baseList = new ArrayList<>();
        this.initWithCommandWords();
    }

    private void initWithCommandWords() {
        this.insert(COMMAND_ADD);
        this.insert(COMMAND_CLEAR);
        this.insert(COMMAND_DELETE);
        this.insert(COMMAND_EDIT);
        this.insert(COMMAND_EXIT);
        this.insert(COMMAND_FIND);
        this.insert(COMMAND_HELP);
        this.insert(COMMAND_HISTORY);
        this.insert(COMMAND_LIST);
        this.insert(COMMAND_REDO);
        this.insert(COMMAND_SELECT);
        this.insert(COMMAND_UNDO);
    }

    /**
     * Initialises a Trie graph with a list of words
     * @param input
     */
    private void init(AddressBook inputAddressBook) {
        ArrayList<String> input = getListFromAddressBook(inputAddressBook);
        for (String item : input) {
            this.insert(item);
        }
    }

    public static ArrayList<String> getListFromAddressBook(AddressBook input) {
        ArrayList<String> output = new ArrayList<String>();

        for (Person person : input.getPersonList()) {
            output.add(person.getName().fullName);
        }

        return output;
    } 

    /**
     * Inserts the input string value to the class instance
     * @param value
     */
    public void insert(String value) {
        insertToGraph(value);
        baseList.add(value);
    }

    /**
     * Insert the given string value to the Trie graph.
     * 
     * @param keyString the string value to be inserted
     */
    private void insertToGraph(String keyString) {
        TrieNode ptr = root; // A TrieNode as pointer to traverse through the tree

        // Run through all characters in the given key string
        for (int i = 0; i < keyString.length(); i++) {
            char ch = keyString.charAt(i);

            boolean hasChar = false;
            // Run through all children of this node
            for (int j = 0; j < ptr.getChildrenSize(); j++) {
                if (ptr.getChildren().get(j).getValue() == ch) {
                    hasChar = true;
                    ptr = ptr.getChildren().get(j);
                    break;
                }
            }

            if (!hasChar) {
                TrieNode parent = ptr;
                ptr = ptr.appendChild(new TrieNode(ch));
                ptr.setParent(parent);
            }
            
        }

        // Mark end of word node
        ptr.setEndNode(true);
    }

    /**
     * Remove the input string value from the class instance
     * @param value
     */
    public void remove(String value) {
        // Check if this value exists
        if (!baseList.contains(value)) {
            return;
        }
        removeFromGraph(value);
        baseList.remove(value);
    }
 
        /**
     * Remove the input string value from the actual graph implementation
     * Traverse from the root to the last character (End node) of the string
     * Prerequisites: the value must exist in the Trie
     *
     * If the remove of this node does not affect any chidren node
     * (ie the node has no children), remove the whole word
     *
     * @param value
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
            ArrayList<TrieNode> children = pointer.getChildren();

            // Run through all children of this node
            for (int j = 0; j < pointer.getChildrenSize(); j++) {
                if (children.get(j).getValue() == value.charAt(i)) {
                    pointer = children.get(j);
                    break;
                }
            }
        }

        return pointer;
    }

    /**
     * Removes a word from the graph given the end node of that word
     * Removes every parent level node until a node that has more than one child
     * or it is an end node
     * @param pointer
     */
    private void removeWordFromGraph(TrieNode pointer) {
        // Set this node as non-end node first
        pointer.setEndNode(false);

        // Traverse upwards the trie
        while (!pointer.isEndNode() && pointer.getChildrenSize() == 0) {
            TrieNode parent = pointer.getParent();
            pointer = parent;
        }
    }

    /**
     * Testing codes section
     */
    public ArrayList<String> getPredictList(String prefix) {
        predictionsList = new ArrayList<>();
        StringBuilder charStack = new StringBuilder();

        TrieNode ptr = root;

        ptr = skipToStartNode(root, prefix);

        for (int i = 0; i < ptr.getChildren().size(); i++) {
            explore(charStack, ptr.getChildren().get(i));
        }

        return predictionsList;
    }

    private TrieNode skipToStartNode(TrieNode begin, String prefix) {
        TrieNode current = begin;
        boolean hasChar = false;

        for (int i = 0; i < prefix.length(); i++) {
            ArrayList<TrieNode> currList = current.getChildren();

            for (int j = 0; j < currList.size(); j++) {
                if (currList.get(j).getValue() == prefix.charAt(i)) {
                    current = current.getChildren().get(j);
                    hasChar = true;
                    break;
                }
            }

            if (!hasChar) {
                break;
            }
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
            predictionsList.add(charStack.toString());
        }

        // Explore all other neighbours
        for (int i = 0; i < ptr.getChildren().size(); i++) {
            TrieNode neighbour = ptr.getChildren().get(i);
            explore(charStack, neighbour);
        }

        // Pop the last character out of stack
        if (charStack.length() > 0) {
            charStack.deleteCharAt(charStack.length()-1);
        }
    }

}
