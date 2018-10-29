package seedu.address.logic.suggestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

//@@author elstonayx
/**
 * Trie data structure for quick searching of valid strings
 */
public class Trie {

    /**
     * Node data structure. Component of a Trie data structure.
     */
    private static class Node {
        private HashMap<Character, Node> children;
        private Node parent;
        private boolean isEndOfWord;

        private Node() {
            children = new HashMap<>();
            isEndOfWord = false;
            parent = null;
        }

        private Node getChild(char key) {
            return children.get(key);
        }

        private Set<Character> getChildren() {
            return children.keySet();
        }

        private void putChild(char key, Node childNode) {
            children.put(key, childNode);
            childNode.parent = this;
        }

        private void setEndOfWord() {
            this.isEndOfWord = true;
        }

        private boolean getIsEndOfWord() {
            return isEndOfWord;
        }
    }

    private Node rootNode;
    private Node searchCrawler;

    public Trie() {
        rootNode = new Node();
        resetSearchCrawlerToRoot();
    }

    /**
     * Allows insertion of a new String into the data structure
     * @param key Word to be stored into the data structure
     */
    public void insert(String key) {
        Node crawler = rootNode;
        char curChar;

        for (int level = 0; level < key.length(); level++) {
            curChar = key.charAt(level);
            if (crawler.getChild(curChar) == null) {
                crawler.putChild(curChar, new Node());
            }

            crawler = crawler.getChild(curChar);
        }

        crawler.setEndOfWord();
    }

    /**
     * Searches whether a key character exists in the data structure at that position of the key
     * @param key character to check if word exists at that character order
     * @return true if it exists, false if it doesn't exist
     */
    public boolean search(char key) {
        if (searchCrawler.getChild(key) == null) {
            return false;
        } else {
            searchCrawler = searchCrawler.getChild(key);
            return true;
        }
    }

    /**
     * Overloaded operator, allows for searching a string whether it exists in the data structure
     * @param key string to be searched
     * @return whether the string exists
     */
    public boolean search(String key) {
        Node crawler = rootNode;
        char curChar;

        for (int level = 0; level < key.length(); level++) {
            curChar = key.charAt(level);
            if (crawler.getChild(curChar) == null) {
                return false;
            }
            crawler = crawler.getChild(curChar);
        }

        return true;
    }

    /**
     * Moves the search crawler back to its parent.
     */
    public void moveSearchCrawlerToParent() {
        if (searchCrawler.parent != null) {
            searchCrawler = searchCrawler.parent;
        }
    }

    /**
     * Resets search crawler to root
     */
    public void resetSearchCrawlerToRoot() {
        searchCrawler = rootNode;
    }

    /**
     * Checks if current node points to the end of the word
     * @return true if it is at the end of the word
     */
    public boolean getIsEndOfWord() {
        return searchCrawler.getIsEndOfWord();
    }

    /**
     * Uses DFS to find all the possible words given the current prefix string
     * @param key prefix string to find words
     * @return ArrayList of possible words
     */
    public ArrayList<String> getListOfWords(String key) {
        Node subStringNode = getToSubStringNode(key);
        ArrayList<String> result = new ArrayList<>();
        findChildName(key, subStringNode, result);

        return result;
    }

    /**
     * DFS to find child. Adds substring into the result when is at the end of word.
     * Else, it continues traversing down the tree to find the end of the word, and appends
     * the current substring with the child key.
     * @param subString current substring formed.
     * @param parent current parent node
     * @param result result of a list of words from current substring and node.
     */
    private void findChildName(String subString, Node parent, List<String> result) {
        if (parent == null) {
            return;
        }

        if (parent.getIsEndOfWord()) {
            result.add(subString);
        } else {
            for (char child: parent.getChildren()) {
                findChildName(subString + child, parent.getChild(child), result);
            }
        }
    }

    private Node getToSubStringNode(String key) {
        Node crawler = rootNode;

        for (char child: key.toCharArray()) {
            crawler = crawler.getChild(child);
            if (crawler == null) {
                return null;
            }
        }

        return crawler;
    }
}
