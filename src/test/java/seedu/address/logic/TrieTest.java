package seedu.address.logic;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.AddCommand;

public class TrieTest {
    private Trie dictionary;

    @Before
    public void setUp() {
        dictionary = new Trie();
    }

    @Test
    public void insert() {
        dictionary.insert(AddCommand.COMMAND_WORD);
    }

    @Test
    public void searchAdd() {
        dictionary.insert(AddCommand.COMMAND_WORD);
        assertTrue(dictionary.search('a'));
        assertTrue(dictionary.search('d'));
        assertTrue(dictionary.search('d'));
    }

    @Test
    public void moveUpCrawler() {
        dictionary.insert(AddCommand.COMMAND_WORD);

        assertTrue(dictionary.search('a'));
        dictionary.moveSearchCrawlerToParent();

        assertTrue(dictionary.search('a'));
        dictionary.moveSearchCrawlerToParent();
        dictionary.moveSearchCrawlerToParent();

        assertTrue(dictionary.search('a'));
    }

}
