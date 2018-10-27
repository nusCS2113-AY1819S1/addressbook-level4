package seedu.address.logic;

import java.util.HashMap;

/**
 * @author elstonayx
 * Trie data structure for quick searching of valid strings
 */
public class Trie {

    /**
     * @author elstonayx
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

        private void putChild(char key, Node childNode) {
            children.put(key, childNode);
            childNode.parent = this;
        }

        private void setEndOfWord() {
            this.isEndOfWord = true;
        }
    }

    private Node rootNode;
    private Node searchCrawler;

    public Trie() {
        rootNode = new Node();
        searchCrawler = rootNode;
    }

    /**
     * allows insertion of a new String into the data structure
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
     * Moves the search crawler back to its parent.
     */
    public void moveSearchCrawlerToParent() {
        if (searchCrawler.parent != null) {
            searchCrawler = searchCrawler.parent;
        }
    }

}
