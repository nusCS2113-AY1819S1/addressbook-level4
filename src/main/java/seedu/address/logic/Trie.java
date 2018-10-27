package seedu.address.logic;

import java.util.HashMap;

public class Trie {

    private static class Node {
        HashMap<Character, Node> children;
        Node parent;
        boolean isEndOfWord;

        Node() {
            children = new HashMap<>();
            isEndOfWord = false;
            parent = null;
        }

        Node getChild(char key) {
            return children.get(key);
        }

        void putChild(char key, Node childNode) {
            children.put(key, childNode);
            childNode.parent = this;
        }

        void setEndOfWord() {
            this.isEndOfWord = true;
        }
    }

    private Node rootNode;
    private Node searchCrawler;

    Trie() {
        rootNode = new Node();
        searchCrawler = rootNode;
    }

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

    public boolean search(char key) {
        if (searchCrawler.getChild(key) == null) {
            return false;
        } else {
            searchCrawler = searchCrawler.getChild(key);
            return true;
        }
    }

    public void moveSearchCrawlerToParent() {
        if (searchCrawler.parent != null) {
            searchCrawler = searchCrawler.parent;
        }
    }

}
