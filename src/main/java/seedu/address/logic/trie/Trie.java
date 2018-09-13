package seedu.address.logic.trie;

import java.lang.reflect.Array;
import java.util.ArrayList;

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

    public Trie () {
        root = new TrieNode(ROOT_CHAR);
        baseList = new ArrayList<>();
    }

    public void insert(String value) {
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

            if (!hasChar) {
                ptr = ptr.appendChild(new TrieNode(ch));
            }

        }

        // Mark end of word node
        ptr.setEndNode(true);
    }
}
