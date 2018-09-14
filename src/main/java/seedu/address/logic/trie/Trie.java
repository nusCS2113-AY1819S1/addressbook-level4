package seedu.address.logic.trie;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A bi-directional Tree structure that stems from the root node
 * Each node is a TrieNode object that contains the node information
 */

public class Trie {

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
    public Trie () {
        root = new TrieNode(ROOT_CHAR);
        baseList = new ArrayList<>();
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
     * Inserts the input string value to the actual Trie graph implementation
     * @param value
     */
    private void insertToGraph(String value) {
        TrieNode ptr = root; // A TrieNode as pointer to traverse through the tree

        // Run through all characters in the given string value
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            boolean hasChar = false;
            ArrayList<TrieNode> children = ptr.getChildren();

            // Run through all children of this node
            for (int j = 0; j < children.size(); j++) {
                if (children.get(j).getValue() == ch) {
                    hasChar = true;
                    ptr = children.get(j);
                    break;
                }
            }

            // There is no such node so create a new node
            if (!hasChar) {
                ptr = ptr.appendChild(new TrieNode(ch));
            }

        }
        ptr.setEndNode(true); // Set end node
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
     * Three conditions for removal / setting of end node:
     *
     * If the end node (last character) has at least one child,
     * set the end node to a non-end node
     *
     * If any previous nodes of the end node (last character) is an end node,
     * and the end node (last character) has zero child,
     * remove all the nodes beginning from the end node (last character)
     * to the first of the previous nodes that is an end node, excluding.
     *
     * If any previous nodes of the end node (last character) has at least one child,
     * and the end node (last character) has zero child,
     * remove all the nodes beginning from the end node (last character)
     * to the first of the previous nodes that has at least one child, excluding.
     *
     * @param value
     */
    private void removeFromGraph(String value) {

    }
}
